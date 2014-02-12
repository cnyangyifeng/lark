package cn.me.xdf.model.bam;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.spring.ApplicationContextHelper;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.message.MessageService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-19
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class CourseLogic {

    public static final int SOURCE_UPDATE = 1;

    public static final int CATALOG_UPDATE = 2;

    private BamCourse bamCourse;
    private String catalogId;
    private SourceNote sourceNote;
    
    private MessageService messageService = null;

    /**
     * 更新类型
     */
    private int updateType;


    public CourseLogic(BamCourse bamCourse, SourceNote sourceNote,MessageService service) {
        this.bamCourse = bamCourse;
        this.sourceNote = sourceNote;
        this.catalogId = sourceNote.getFdCatalogId();
        updateType = SOURCE_UPDATE;
        messageService = service;
        initData();
    }

    public CourseLogic(BamCourse bamCourse, SourceNote sourceNote, String catalogId, int updateType,MessageService service) {
        this.bamCourse = bamCourse;
        this.sourceNote = sourceNote;
        this.updateType = updateType;
        this.catalogId = catalogId;
        messageService = service;
        initData();
    }

    private void initData() {
        if (updateType == SOURCE_UPDATE) {
            toMateridThrough();
        } else if (updateType == CATALOG_UPDATE) {
            toMateridThroughByCatalogId();
        }
        toCatalogThrough();
        toCourseThroug();
    }


    public BamCourse getBamCourse() {
        return bamCourse;
    }

    /**
     * 根据节ID设置此节下的素材全部通过
     */
    private void toMateridThroughByCatalogId() {
        List<CourseContent> courseContents = bamCourse.getCourseContents();
        if (courseContents == null)
            return;

        for (CourseContent content : courseContents) {
            if (content.getCatalog().getFdId().equals(catalogId)) {
                content.getMaterial().setThrough(true);
                messageService.saveMaterialMessage(bamCourse,sourceNote, content.getCatalog(), content.getMaterial());
            }
        }
        bamCourse.setCourseContentJson(JsonUtils.writeObjectToJson(courseContents));
    }

    /**
     * 设置当前素材通过
     */
    private void toMateridThrough() {
        List<CourseContent> courseContents = bamCourse.getCourseContents();
        if (courseContents == null)
            return;

        for (CourseContent content : courseContents) {
            if (content.getCatalog().getFdId().equals(sourceNote.getFdCatalogId())
            	&& content.getMaterial().getFdId().equals(sourceNote.getFdMaterialId())) {
                content.getMaterial().setThrough(true);
                messageService.saveMaterialMessage(bamCourse,sourceNote, content.getCatalog(), content.getMaterial());
            }
        }
        bamCourse.setCourseContentJson(JsonUtils.writeObjectToJson(courseContents));
    }


    /**
     * 设置当前节通过
     */
    private void toCatalogThrough() {
        List<MaterialInfo> materialInfos = bamCourse.getMaterialByCatalog(catalogId);
        if (CollectionUtils.isEmpty(materialInfos))
            return;
        boolean isThrought = true;
        for (MaterialInfo materialInfo : materialInfos) {
            if (!materialInfo.getThrough()) {
                isThrought = false;
            }
        }
        
        List<CourseCatalog> catalogs = bamCourse.getCatalogs();
        for (CourseCatalog catalog : catalogs) {
            if (catalog.getFdId().equals(catalogId)) {
	             if (isThrought) {
		              catalog.setEndDate(new Date());
		              catalog.setThrough(true);
		              messageService.saveLectureMessage(bamCourse, catalog);
	             }else{
	            	 if(catalog.getThrough()==null){
	            		 catalog.setThrough(false);
	            	 }
	             }
            }
        }
            bamCourse.setCatalogJson(JsonUtils.writeObjectToJson(catalogs));
        
    }

    /**
     * 设置此课程结束
     */
    private void toCourseThroug() {
        List<CourseCatalog> catalogs = bamCourse.getCatalogs();
        boolean isThrought = true;
        for (CourseCatalog catalog : catalogs) {
            if (Constant.CATALOG_TYPE_LECTURE.equals(catalog.getFdType()) &&(catalog.getThrough()==null || !catalog.getThrough()))
                isThrought = false;
        }

        if (isThrought) {
            bamCourse.setEndDate(new Date());
            bamCourse.setThrough(true);
            messageService.saveCourseMessage(bamCourse);
        }
    }


}

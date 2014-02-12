package cn.me.xdf.model.bam;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.web.SpringUtils;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.service.bam.BamMaterialService;
import cn.me.xdf.service.bam.process.SourceNodeService;

/**
 * 重新将课程模板载入学习进程中
 * @author zuoyi
 */
public class CourseReLoad {
	private BamCourse hisbamCourse;
	private BamCourse newbamCourse;
	private BamMaterialService bamMaterialService=null;
	
	public CourseReLoad(BamCourse hisbamCourse,BamCourse newbamCourse) {
        this.hisbamCourse = hisbamCourse;
        this.newbamCourse = newbamCourse;
        bamMaterialService = (BamMaterialService)SpringUtils.getBean("bamMaterialService");
        initData();
    }
	
	private void initData() {
		newbamCourse.setFdId(hisbamCourse.getFdId());
		newbamCourse.setStartDate(hisbamCourse.getStartDate());
        toMateridThrough();
        toCatalogThrough();
        toCourseThroug();
    }
	
	public BamCourse getBamCourse() {
        return newbamCourse;
    }
	
	/**
     * 设置当前素材通过
     */
    private void toMateridThrough() {
        List<CourseContent> courseContents = newbamCourse.getCourseContents();
        if (courseContents == null)
            return;

        for (CourseContent content : courseContents) {
            content.getMaterial().setThrough(getMaterialThrough(content));
        }
        newbamCourse.setCourseContentJson(JsonUtils.writeObjectToJson(courseContents));
    }

    /**
     * 从历史进程中获取素材是否通过
     */
    private boolean getMaterialThrough(CourseContent content){
    	Object obj = bamMaterialService.reCalculateMaterial(content.getMaterial().getFdType(), content.getCatalog().getFdId(), content.getMaterial().getFdId());
    	if(obj!=null){
    		return (Boolean)obj;
    	}
    	List<CourseContent> contents = hisbamCourse.getCourseContents();
    	if(!CollectionUtils.isEmpty(contents)){
    		for (CourseContent temp : contents) {
                if (temp.getCatalog().getFdId().equals(content.getCatalog().getFdId())
                	&& temp.getMaterial().getFdId().equals(content.getMaterial().getFdId())) {
                    return temp.getMaterial().getThrough();
                }
            }
    	}
    	return false;
    }
    
    /**
     * 设置当前节通过
     */
    private void toCatalogThrough() {
        List<CourseCatalog> catalogs = newbamCourse.getCatalogs();
        for (CourseCatalog catalog : catalogs) {
        	 List<MaterialInfo> materialInfos = newbamCourse.getMaterialByCatalog(catalog.getFdId());
             if (CollectionUtils.isEmpty(materialInfos))
                 continue;
             boolean isThrought = true;
             for (MaterialInfo materialInfo : materialInfos) {
                 if (materialInfo.getThrough()) {
                	 catalog.setThrough(false);
                 }else{
                	 isThrought = false;
                 }
             }
             if (isThrought) {
            	 CourseCatalog c = hisbamCourse.getCatalogById(catalog.getFdId());
	              catalog.setEndDate(c!=null&&c.getEndDate()!=null?c.getEndDate():new Date());
	              catalog.setThrough(true);
             }
        }
        newbamCourse.setCatalogJson(JsonUtils.writeObjectToJson(catalogs));
        
    }

    /**
     * 设置此课程结束
     */
    private void toCourseThroug() {
        List<CourseCatalog> catalogs = newbamCourse.getCatalogs();
        boolean isThrought = true;
        for (CourseCatalog catalog : catalogs) {
            if (Constant.CATALOG_TYPE_LECTURE.equals(catalog.getFdType()) &&(catalog.getThrough()==null || !catalog.getThrough()))
                isThrought = false;
        }
        if (isThrought) {
        	newbamCourse.setEndDate(hisbamCourse.getEndDate()!=null?hisbamCourse.getEndDate():new Date());
        	newbamCourse.setThrough(true);
        }
    }
}

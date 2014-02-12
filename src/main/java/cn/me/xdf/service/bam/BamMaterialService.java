package cn.me.xdf.service.bam;

import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.material.MaterialEnum;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.service.bam.source.ISourceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-11-6
 * Time: 上午11:05
 * <p/>
 * 素材进程处理类
 */
@Transactional(readOnly = true)
public class BamMaterialService extends SimpleService {


    private Map<String, ISourceService> sourceMap;

    public void setSourceMap(Map<String, ISourceService> sourceMap) {
        this.sourceMap = sourceMap;
    }


    /**
     * 保存素材完成记录
     *
     * @param request
     */
    @Transactional(readOnly = false)
    public Object saveSourceNode(String sourceType,WebRequest request) {
        return sourceMap.get(MaterialEnum.valueOf("m_"+sourceType).getBean()).saveSourceNode(request);
    }


    public Object findSourceByMaterials(BamCourse bamCourse, CourseCatalog catalog) {
        return null;
    }

    /**
     * 获取素材子表详细信息
     * @param request
     * @return
     */
    public Object findSubInfoByMaterial(String sourceType,WebRequest request) {
    	return sourceMap.get(MaterialEnum.valueOf("m_"+sourceType).getBean()).findSubInfoByMaterial(request);
    }
    
    /**
     * 获取素材详细信息
     * @param bamCourse 人员对应的课程信息
     * @param catalog   对应的节信息
     * @return
     */
    public Object findMaterialDetailInfo(String sourceType,BamCourse bamCourse, CourseCatalog catalog, String fdid){
    	return sourceMap.get(MaterialEnum.valueOf("m_"+sourceType).getBean()).findMaterialDetailInfo(bamCourse, catalog,fdid);
    }
    
    public Object reCalculateMaterial(String sourceType,String catalogId,String materialId){
    	return sourceMap.get(MaterialEnum.valueOf("m_"+sourceType).getBean()).reCalculateMaterial(catalogId,materialId);
    }
}

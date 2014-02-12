package cn.me.xdf.service.material.source;

import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.material.MaterialInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午4:54
 * 需要的方法可以在此类里添加。
 */
public interface ISourceService {

    /**
     * 根据素材实体查询具体的素材数据
     *
     * @param materialInfo
     * @return
     */
    public Object findSourceByMateral(MaterialInfo materialInfo);


    /**
     * 获取章节下的所有素材
     *
     * @param catalog
     * @return
     */
    public Object findSourceByCourseCatalog(CourseCatalog catalog);


    /**
     * 获取具体的素材信息
     * 如根据materialInf获取AttMain
     *
     * @param materialInfos
     * @return
     */
    public Object findSourceByMaterials(List<MaterialInfo> materialInfos);

}

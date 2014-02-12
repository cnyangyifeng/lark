package cn.me.xdf.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.service.BaseService;

/**
 * 
 * 课程章节service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseCatalogService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseCatalog> getEntityClass() {
		return CourseCatalog.class;
	}
	
	/**
	 * 查找课程权限
	 * @param courseId 课程ID
	 * @return List 章节列表
	 */
	@Transactional(readOnly = true)
	public List<CourseCatalog> getCatalogsByCourseId(String courseId){
		//根据课程ID查找章节，并按总序号升序
		Finder finder = Finder
				.create("from CourseCatalog catalog ");
		finder.append("where catalog.courseInfo.fdId = :courseId order by catalog.fdTotalNo");
		finder.setParam("courseId", courseId);		
		return  super.find(finder);
	}

	/**
	 * 根据章ID查找章下的节
	 * @param fdId 章ID
	 * @return List 节列表
	 */
	@Transactional(readOnly = true)
	public List<CourseCatalog> getChildCatalog(String fdId) {
		Finder finder = Finder
				.create("from CourseCatalog catalog ");
		finder.append("where catalog.hbmParent.fdId = :fdId");
		finder.setParam("fdId", fdId);		
		return  super.find(finder);
	}

	/**
	 * 根据课程ID删除章节
	 * @param fdId 课程ID
	 */
	@Transactional(readOnly = false)
	public void deleteByCourseId(String courseId) {
		List<CourseCatalog> list = getCatalogsByCourseId(courseId);
		if(list!=null && list.size()>0){
			for(CourseCatalog catalog:list){
				//递归删除章节
				deleteCatalogs(catalog);
			}
		}
	}

	/**
	 * 递归删除章节
	 * @param catalog 上层章节
	 */
	private void deleteCatalogs(CourseCatalog catalog) {
		List<CourseCatalog> list = getChildCatalog(catalog.getFdId());
		if(list!=null && list.size()>0){
			for(CourseCatalog subcatalog:list){
				//递归删除章节
				deleteCatalogs(subcatalog);
			}
		}
		super.deleteEntity(catalog);
	}
	/**
	 * 查看课程下的节素材是否包含作业 
	 */
	@Transactional(readOnly=false)
	public boolean getIsCourseOfTask(String courseId){
		Finder finder=Finder.create("  from CourseCatalog cata");
		finder.append(" where cata.courseInfo.fdId=:courseId and cata.fdMaterialType='10' ");
		finder.setParam("courseId", courseId);
		List catalist=find(finder);
		if(catalist!=null&&catalist.size()>0){
			return true;
		}
		
		return false;
	}
}

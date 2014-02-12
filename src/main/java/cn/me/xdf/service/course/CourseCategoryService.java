package cn.me.xdf.service.course;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseCategory;
import cn.me.xdf.service.BaseService;


/**
 * 
 * 课程分类service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseCategoryService extends BaseService{
	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseCategory> getEntityClass() {
		return CourseCategory.class;
	}

	/**
	 * 查找所有课程分类
	 * @return List 分类列表
	 */
	@Transactional(readOnly = true)
	public List<CourseCategory> findAll(){
		//根据课程ID查找章节，并按总序号升序
		Finder finder = Finder
				.create("from CourseCategory category order by category.fdOrder");		
		return  super.find(finder);
	}
	
	/**
	 * 是否被课程使用
	 * @param fdId 分类ID
	 * @return boolean
	 */
	public boolean isUsedByCourse(String fdId){
		//根据类别ID查找是否有课程引用
		Finder finder = Finder
						.create("from CourseInfo c where c.fdCategory.fdId =:fdId ").setParam("fdId", fdId);	
		return super.hasValue(finder);
	}
	
	@Override
	public void delete(Serializable[] ids) {
		if (ids == null)
			return;
		for (Serializable id : ids) {
			if(!isUsedByCourse(id.toString())){
				delete(getEntityClass(), id);
			}
		}
	}
	
	public int getMaxOrder(){
		Finder finder = Finder
				.create("from CourseCategory category order by category.fdOrder desc");		
		List<CourseCategory> list = (List<CourseCategory>) getPage(finder, 1,1).getList();
		if(list.size()==0){
			return 0;
		}else{
			return list.get(0).getFdOrder();
		}
		
	}
}

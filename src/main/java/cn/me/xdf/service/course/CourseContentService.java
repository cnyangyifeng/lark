package cn.me.xdf.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.service.BaseService;

/**
 * 
 * 课程素材关系表service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseContentService extends BaseService{
	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseContent> getEntityClass() {
		return CourseContent.class;
	}
	
	/**
	 * 根据课程查找课程与素材的关系
	 * @param catalogId 章节ID
	 * @return List 关系列表
	 */
	@Transactional(readOnly = true)
	public List<CourseContent> findContentsByCatalogIdId(String catalogId){
		//根据课程ID查找标签
		Finder finder = Finder
				.create(" from CourseContent content where content.catalog.fdId=:catalogId");	
		finder.append(" order by content.fdMaterialNo ");
		finder.setParam("catalogId", catalogId);		
		return  super.find(finder);
	}
	
	/**
	 * 根据章节ID删除课程与素材的关系
	 * @param catalogId 章节ID
	 */
	@Transactional(readOnly = false)
	public void deleteByCatalogId(String catalogId) {
		Finder finder = Finder
				.create(" from CourseContent content where  content.catalog.fdId=:catalogId");	
		finder.setParam("catalogId", catalogId);
		List<CourseContent> list = super.find(finder);
		if(list!=null && list.size()>0){
			for(CourseContent content:list){
				super.deleteEntity(content);
			}
		}
	}
}

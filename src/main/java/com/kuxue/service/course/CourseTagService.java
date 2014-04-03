package com.kuxue.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.course.CourseTag;
import com.kuxue.model.course.TagInfo;
import com.kuxue.service.BaseService;

/**
 * 
 * 课程标签中间表service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseTagService extends BaseService{
	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseTag> getEntityClass() {
		return CourseTag.class;
	}

	/**
	 * 根据课程查找课程已设置的标签
	 * @param courseId 课程ID
	 * @return List 标签列表
	 */
	@Transactional(readOnly = true)
	public List<TagInfo> findTagByCourseId(String courseId){
		//根据课程ID查找标签
		Finder finder = Finder
				.create("select tag.tag from CourseTag tag where tag.courses.fdId=:courseId");	
		finder.setParam("courseId", courseId);		
		return  super.find(finder);
	}
	
	/**
	 * 保存课程与标签的关系
	 * @param courseTag 课程标签
	 */
	@Transactional(readOnly = false)
	public void save(CourseTag courseTag) {
		Finder finder = Finder
				.create(" from CourseTag tag where tag.courses.fdId=:courseId and tag.tag.fdId=:tagId");	
		finder.setParam("courseId", courseTag.getCourses().getFdId());
		finder.setParam("tagId", courseTag.getTag().getFdId());
		//如果关系表中已经存在，则不保存
		if(super.findUnique(finder)==null){
			super.save(courseTag);
		}		
	}

	/**
	 * 根据课程ID删除课程与标签的关系
	 * @param courseId 课程ID
	 */
	@Transactional(readOnly = false)
	public void deleteByCourseId(String courseId) {
		Finder finder = Finder
				.create(" from CourseTag tag where  tag.courses.fdId=:courseId");	
		finder.setParam("courseId", courseId);
		List<CourseTag> list = super.find(finder);
		if(list!=null && list.size()>0){
			for(CourseTag tag:list){
				super.deleteEntity(tag);
			}
		}
	}
	
	/**
	 * 根据标签id删除课程与标签的关系
	 * @param courseId 课程ID
	 */
	@Transactional(readOnly = false)
	public void deleteByTagId(String TagId) {
		Finder finder = Finder
				.create(" from CourseTag courseTag where  courseTag.tag.fdId=:tagId");	
		finder.setParam("tagId", TagId);
		List<CourseTag> list = super.find(finder);
		if(list!=null && list.size()>0){
			for(CourseTag tag:list){
				super.deleteEntity(tag);
			}
		}
	}
	
	/**
	 * 根据标签查询课程
	 */
	public Pagination findCourseByTag(String tagId, String key, int pageNo) {
		Finder finder = Finder.create("from CourseTag courseTag ");
		finder.append("where courseTag.tag.fdId =:fdId and courseTag.courses.fdTitle like:key");
		finder.setParam("fdId", tagId);
		finder.setParam("key", '%' + key + '%');
		Pagination pagination = getPage(finder, pageNo, SimplePage.DEF_COUNT);
		return pagination;
	}
}

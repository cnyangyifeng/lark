package cn.me.xdf.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseTag;
import cn.me.xdf.model.course.TagInfo;
import cn.me.xdf.service.BaseService;

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
}

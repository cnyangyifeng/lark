package com.kuxue.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.model.course.CourseContent;
import com.kuxue.model.course.CourseGroupAuth;
import com.kuxue.model.course.SeriesCourses;
import com.kuxue.service.BaseService;

/**
 * 
 * 课程群组关系service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseGroupAuthService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseGroupAuth> getEntityClass() {
		return CourseGroupAuth.class;
	}
	
	public void deleteByCourseId(String courseId){
		Finder finder=Finder.create(" from CourseGroupAuth cga ");
		finder.append(" where cga.course.fdId=:courseId");
		finder.setParam("courseId", courseId);
		List<CourseGroupAuth> list = find(finder);
		if(list!=null && list.size()>0){
			for(CourseGroupAuth cga:list){
				deleteEntity(cga);
			}
		}
	}
}

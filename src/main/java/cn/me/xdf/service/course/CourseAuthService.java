package cn.me.xdf.service.course;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseAuth;
import cn.me.xdf.service.BaseService;

/**
 * 
 * 课程权限service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseAuthService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseAuth> getEntityClass() {
		return CourseAuth.class;
	}
	
	
	/**
	 * 添加或修改课程权限
	 * 
	 * @return CourseAuth
	 */
	@Transactional(readOnly = false)
	public CourseAuth saveOrUpdateCourseAuth(CourseAuth courseAuth){
		CourseAuth auth =findByCourseIdAndUserId(courseAuth.getCourse().getFdId(), courseAuth.getFdUser().getFdId());
		if(auth==null){
			return save(courseAuth);
		}else{
			courseAuth.setFdId(auth.getFdId());
			return update(courseAuth);
		}
	}
	
	
	/**
	 * 删除课程权限
	 * 
	 */
	@Transactional(readOnly = false)
	public void deleCourseAuth(CourseAuth courseAuth){
		delete(courseAuth);
	}
	
	
	/**
	 * 查找课程权限
	 * 
	 * @return CourseAuth
	 */
	@Transactional(readOnly = true)
	public CourseAuth findByCourseIdAndUserId(String courseId,String userId){
		Finder finder = Finder
				.create("from CourseAuth anth ");
		finder.append("where anth.course.fdId = :courseId and anth.fdUser.fdId = :userId");
		finder.setParam("courseId", courseId);
		finder.setParam("userId", userId);
		List list=super.find(finder);
		if(list!=null&&list.size()>0){
			return (CourseAuth) super.find(finder).get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据课程Id删除课程权限
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void deleCourseAuthByCourseId(String courseId){
		Finder finder = Finder
				.create("from CourseAuth anth ");
		finder.append("where anth.course.fdId = :courseId");
		finder.setParam("courseId", courseId);
		List<CourseAuth> list = super.find(finder);
		for (CourseAuth courseAuth : list) {
			delete(courseAuth.getFdId());
		}
	}
}

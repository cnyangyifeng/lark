package com.kuxue.service.system;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.system.CoursePicture;
import com.kuxue.service.BaseService;

/**
 * 
 * 课程/系列课程与图片库关系service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CoursePictureService extends BaseService{
	@SuppressWarnings("unchecked")
	@Override
	public  Class<CoursePicture> getEntityClass() {
		return CoursePicture.class;
	}

	/**
	 * 根据图片库Id删除课程、系列课程与图片库的关系
	 * 
	 * @param id 图片库Id
	 */
	public void deleteByPictureId(String id) {
		StringBuffer sql = new StringBuffer(" delete from ixdf_ntp_course_picture cp  ");
		sql.append(" where cp.fdPictureId  = '"+id+"'  ");
		executeSql(sql.toString());		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 根据课程或系列课程Id删除课程、系列课程与图片库的关系
	 * 
	 * @param id 图片库Id
	 */
	public void deleteByCourseId(String id) {
		StringBuffer sql = new StringBuffer(" delete from ixdf_ntp_course_picture cp  ");
		sql.append(" where cp.fdCourseId  = '"+id+"'  ");
		executeSql(sql.toString());		// TODO Auto-generated method stub
		
	}

	/**
	 * 根据课程或系列课程获取图片ID
	 * 
	 * @param courseId 课程或系列课程ID
	 * @return String 图片ID
	 */
	public String getPicuterIdByCourseId(String courseId) {
		List<CoursePicture> coursePictures = this.findByCriteria(CoursePicture.class, Value.eq("fdCourseId", courseId));
		if(coursePictures!=null && coursePictures.size()>0){
			return ((CoursePicture)coursePictures.get(0)).getFdPictureId();
		}
		return "";
	}
}

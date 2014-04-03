package com.kuxue.service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.system.PictureLibrary;
import com.kuxue.service.BaseService;
import com.kuxue.service.base.AttMainService;

/**
 * 
 * 图片库service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class PictureLibraryService extends BaseService{

	@Autowired
	private CoursePictureService coursePictureService;
	
	@Autowired
	private AttMainService attMainService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<PictureLibrary> getEntityClass() {
		return PictureLibrary.class;
	}
	
	/**
	 * 获取图片库
	 * 
	 * @param pageNo 页数
	 * @param pageSize 每页显示条数
	 * @return 图片库信息
	 */
	public Pagination getPictureList(int pageNo,int pageSize) {
		Finder finder = Finder.create("from PictureLibrary c   ");
		return getPage(finder,pageNo,pageSize);
	}

	/**
	 * 根据ID集合删除图片库
	 * 
	 * @param ids 图片库ID集合
	 */
	public void deletePictures(String[] ids) {
		if (ids == null)
			return;
		for (Serializable id : ids) {
			coursePictureService.deleteByPictureId((String)id);
			delete(getEntityClass(), id);
			attMainService.deleteAttMainByModelId((String)id);
		}
	}

	/**
	 * 根据图片名称条件删除图片库
	 * 
	 * @param param 图片库名称
	 */
	public void deletePicturesByParam(String param) {
		//删除课程、系列课程与图片库的关系
		StringBuffer sql = new StringBuffer(" delete from ixdf_ntp_course_picture cp  ");
		sql.append(" where cp.fdPictureId  in (select fdId from ixdf_ntp_picturelibrary p   ");
		if (StringUtils.isNotBlank(param)) {
			sql.append(" where p.fdName like '%"+param+"%'");
		}
		sql.append(" )");
		executeSql(sql.toString());
		
		//更新图片库对应附件业务模型为空，再由定时任务清理附件
		sql = new StringBuffer(" update ixdf_ntp_att_main a  set a.fdmodelid=null,a.fdmodelname=null,a.fdkey=null ");
		sql.append(" where a.fdmodelid  in (select fdId from ixdf_ntp_picturelibrary p   ");
		if (StringUtils.isNotBlank(param)) {
			sql.append(" where p.fdName like '%"+param+"%'");
		}
		sql.append(" )");
		executeSql(sql.toString());
		
		//删除图片库
		sql = new StringBuffer(" delete from ixdf_ntp_picturelibrary p   ");
		if (StringUtils.isNotBlank(param)) {
			sql.append(" where p.fdName like '%"+param+"%'");
		}
		executeSql(sql.toString());
		
	}
	
	/**
	 * 根据课程或系列课程获取图片附件ID
	 * 
	 * @param courseId 课程或系列课程ID
	 * @return String 图片附件ID
	 */
	public String getAttIdByCoursePicture(String courseId){
		String pictureId = coursePictureService.getPicuterIdByCourseId(courseId);
		if(StringUtils.isNotBlank(pictureId)){
			AttMain attMain=attMainService.getByModelIdAndModelName(pictureId,PictureLibrary.class.getName());
			if(attMain!=null){
				return attMain.getFdId();
			}
		}
		return null;
	}
}

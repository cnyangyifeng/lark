package com.kuxue.service.system;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.kuxue.common.hibernate4.Finder;
import com.kuxue.model.base.Constant;
import com.kuxue.model.system.CourseSkin;
import com.kuxue.service.BaseService;
import com.kuxue.service.base.AttMainService;
/**
 * 
 * 课程皮肤service
 * 
 * @author zuoyi
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseSkinService  extends BaseService{
	
	@Autowired
	private AttMainService attMainService;
	
	public static final String DEFAULT_COURSE_SKIN_PATH = "/theme/default";
	public static final String DEFAULT_SERIES_SKIN_PATH = "/theme-series/default";
	public static final String DEFAULT_PROFILE_SKIN_PATH = "/theme-profile/default";

	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseSkin> getEntityClass() {
		return CourseSkin.class;
	}

	/**
	 * 删除课程皮肤
	* @param ids 皮肤ID集合
	 */
	public void deleteSkin(String[] ids) {
		// TODO Auto-generated method stub
		updateCourseInfoSkin(ids);
		if (ids == null)
			return;
		for (Serializable id : ids) {
			delete(getEntityClass(), id);
			attMainService.deleteAttMainByModelId((String)id);
		}
	}

	/**
	 * 将课程中引用的课程皮肤更新为空
	 * 
	 * 
	 *
	 * 
	* @param ids 皮肤ID集合
	 */
	private void updateCourseInfoSkin(String[] ids) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(" update ixdf_ntp_course c  set c.fdSkinId=null ");
		if (ArrayUtils.isNotEmpty(ids)) {
			sql.append(" where c.fdSkinId  in ( ");
			for(int i=0;i<ids.length;i++){
				if(i==ids.length-1){
					sql.append(" '"+ids[i]+"' ");
				}else{
					sql.append(" '"+ids[i]+"', ");
				}
			}
			sql.append(" ) ");
		}
		executeSql(sql.toString());
	}

	/**
	 * 更新所有皮肤的是否默认选项为否
	 * 
	 */
	public void updateDefaultSkin(String fdType) {
		// TODO Auto-generated method stub
		Finder finder = Finder.create("from CourseSkin c where c.fdType =:fdType  ");
		finder.setParam("fdType", fdType);
		List list = find(finder);
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				CourseSkin skin = (CourseSkin)list.get(i);
				skin.setFdDefaultSkin(false);
				save(skin);
			}
		}
		
	}

	/**
	 * 查找默认皮肤路径
	 * @return 默认皮肤的路径
	 */
	public String getDefaultSkin(String fdType){
		Finder finder = Finder.create("from CourseSkin c where c.fdType=:fdType and  c.fdDefaultSkin=:fdDefaultSkin ");
		finder.setParam("fdType", fdType);
		finder.setParam("fdDefaultSkin", true);
		List list = find(finder);
		if(list!=null && list.size()>0 && list.get(0)!=null){
			return ((CourseSkin)list.get(0)).getFdSkinPath();
		}
		if(Constant.SKIN_TYPE_PROFILE.equals(fdType)){
			return DEFAULT_PROFILE_SKIN_PATH;
		}else if(Constant.SKIN_TYPE_SERIES.equals(fdType)){
			return DEFAULT_SERIES_SKIN_PATH;
		}else{
			return DEFAULT_COURSE_SKIN_PATH;
		}
	}
	
	/**
	 * 根据类型获取全部皮肤列表
	 * @param fdType 类型（课程、系列、个人主页）
	 * @return 课程皮肤列表
	 */
	public List getCourseList(String fdType){
		Finder finder = Finder.create("from CourseSkin c where c.fdType =:fdType  order by c.fdCreateTime ");
		finder.setParam("fdType", fdType);
		return find(finder);
	}

}

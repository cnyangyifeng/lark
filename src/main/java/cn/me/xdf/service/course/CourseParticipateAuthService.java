package cn.me.xdf.service.course;

import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.course.CourseParticipateAuth;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.utils.ShiroUtils;
/**
 * 
 * 课程参与权限service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = true)
public class CourseParticipateAuthService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<CourseParticipateAuth> getEntityClass() {
		return CourseParticipateAuth.class;
	}
	
	/**
	 * 添加或修改参与人
	 * 
	 * @return CourseParticipateAuth
	 */
	@Transactional(readOnly = false)
	public CourseParticipateAuth saveOrUpdateCourseParticipateAuth(CourseParticipateAuth CourseParticipateAuth){
		List<CourseParticipateAuth> auth = findByProperty("fdId",CourseParticipateAuth.getFdId());
		if(auth.size()<=0){
			return save(CourseParticipateAuth);
		}else{
			return update(auth.get(0));
		}
	}
	/**
	 * 某课程授权列表
	 */
	@Transactional(readOnly=false)
	public Pagination findSingleCourseAuthList(String courseId,String orderStr,Integer pageNo,Integer pageSize,String keyword){
		Finder finder=Finder.create(" from CourseParticipateAuth cpa left join cpa.fdTeacher person left join person.hbmParent hbmperson left join cpa.fdUser u left join u.hbmParent hbmu ");
//		Finder finder=Finder.create("from CourseParticipateAuth cpa ");//该方式会过滤掉无导师课程
		finder.append(" where cpa.course.fdId=:courseId  ");
		if(!ShiroUtils.isAdmin()){
			finder.append("and cpa.fdAuthorizer.fdId=:fdAuthorizerId  ");
			finder.setParam("fdAuthorizerId", ShiroUtils.getUser().getId());
		}
		finder.setParam("courseId", courseId);
		if(StringUtil.isNotBlank(keyword)){//搜索关键字是否存在
			finder.append("and ( u.fdName like :namestr or person.fdName like :tnamestr or u.fdEmail like :emailstr or hbmu.fdName like:deptstr or person.fdEmail like :temailstr or hbmperson.fdName like:tdeptstr)");
			finder.setParam("namestr", "%"+keyword+"%");
			finder.setParam("deptstr", "%"+keyword+"%");
			finder.setParam("emailstr", "%"+keyword+"%");
			finder.setParam("tnamestr", "%"+keyword+"%");
			finder.setParam("tdeptstr", "%"+keyword+"%");
			finder.setParam("temailstr", "%"+keyword+"%");
		}
		if("mentor".equals(orderStr)){//按导师查询
			finder.append(" order by nlssort(person.fdName,'NLS_SORT=SCHINESE_PINYIN_M')");
		}else if("teacher".equals(orderStr)){
			finder.append(" order by nlssort(cpa.fdUser.fdName,'NLS_SORT=SCHINESE_PINYIN_M')");
		}else if("createtime".equals(orderStr)){
			
			finder.append(" order by cpa.fdCreateTime desc");
		}else{
			finder.append(" order by cpa.fdCreateTime desc");
		}
		
		return getPage(finder, pageNo, pageSize);
	}
	/**
	 * 某课程授权教师检查
	 */
	@Transactional(readOnly=false)
	public boolean findCouseParticipateAuthById(String courseId,String teacherId){
		Finder finder=Finder.create(" from CourseParticipateAuth cpa");
		finder.append(" where cpa.fdUser.fdId=:teacherId and cpa.course.fdId=:courseId");
		finder.setParam("teacherId", teacherId);
		finder.setParam("courseId", courseId);
		List list=find(finder);
		if(list!=null&&list.size()>0){
			return false;
		}else{
			return true;
		}
		
	}
	/**
	 * 根据课程id和人员id找出课程权限
	 * @param courseId
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=false)
	public CourseParticipateAuth findAuthByCourseIdandUserId(String courseId,String userId){
		List<CourseParticipateAuth> letters = findByCriteria(CourseParticipateAuth.class,
				Value.eq("course.fdId", courseId),
				Value.eq("fdUser.fdId", userId));
		if(CollectionUtils.isEmpty(letters)){
			return null;
		}
		return letters.get(0);
	}
	/**
	 * 查看用户是否有学习课程权限
	 * 
	 * @param courseId
	 * @param userId
	 * @return
	 */
	public boolean findAuthByCourseIdAndUserId(String courseId ,String userId){
		Finder finder = Finder.create("");
		finder.append("select course.fdId id ");
		finder.append("  from ixdf_ntp_course course ");
		finder.append("  left join IXDF_NTP_COURSE_PARTICI_AUTH cpa ");
		finder.append("    on (course.fdId = cpa.fdcourseid and cpa.fduserid ='"+userId+"') ");
		finder.append(" where ( ");
		finder.append("       ((course.isPublish = 'Y' or (course.fdPassword is not null or course.fdPassword != '')) and ");
		finder.append("         (course.fdId in  ");
		finder.append("                      (select ga.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga  ");
		finder.append("                      where ga.fdgroupid in  ");
		finder.append("                           ( select ga.fdgroupid from sys_org_group_element soge ,sys_org_element soe1org,sys_org_element soe2dep,sys_org_element soe3per ");
		finder.append("                            where ga.fdgroupid = soge.fd_groupid and (soe1org.fdid = soe2dep.fd_parentid and soe2dep.fdid = soe3per.fd_parentid and soe3per.fdid='"+userId+"' ) and ( soge.fd_elementid = soe1org.fdid or  soge.fd_elementid = soe3per.fdid or  soge.fd_elementid = soe2dep.fdid ) ");
		finder.append("                            ) ");
		finder.append("                       ) ");
		finder.append("          )  ");
		finder.append("          or ");
		finder.append("          ( ");
		finder.append("          course.fdId not in( select ga2.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga2 ) ");
		finder.append("         ) ");
		finder.append("       ) ");
		finder.append("       or (cpa.fduserid = '"+userId+"') ");
		finder.append("       ) ");
		finder.append("   and course.fdStatus = '01' ");
		finder.append("   and course.isAvailable = 'Y' ");
		finder.append("   and course.fdId='"+ courseId +"' ");
		List<Map> list = findBySQL(finder.getOrigHql(), null, null );
		if(list.size()==0){
			return false;
		}else{
			return true;
		}
		
	}
}

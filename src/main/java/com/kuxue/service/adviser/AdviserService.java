package com.kuxue.service.adviser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.material.Task;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.process.SourceNote;
import com.kuxue.model.process.TaskRecord;
import com.kuxue.service.AccountService;
import com.kuxue.service.BaseService;
import com.kuxue.service.bam.process.SourceNodeService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.ShiroUtils;
import com.kuxue.view.model.VCheckTaskData;
/**
 * 指导老师的service
 * @author hp
 *
 */
@Service
@Transactional(readOnly = true)
public class AdviserService extends BaseService{
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private MaterialService materialService;
	
	@Transactional(readOnly = false)
	public List<AttMain> findNotesAtts(String fdId){
		SourceNote note = sourceNodeService.get(SourceNote.class, fdId);
		Set<TaskRecord> taskRexords = note.getTaskRecords();
		List<AttMain> attMains = new ArrayList();
		for (TaskRecord taskRecord : taskRexords) {
		   attMains = attMainService.getAttMainsByModelIdAndModelName(taskRecord.getFdId(), TaskRecord.class.getName());
		}
		return attMains;
	}
	/**
	 * 找出导出模板时list
	 * @param ids
	 * @return
	 */
	public List<VCheckTaskData> findCheckDataList(String[] modelIds,String fdType){
		List<VCheckTaskData> adviserList = new ArrayList<VCheckTaskData>();
		for (String modelId : modelIds) {
			SourceNote note = sourceNodeService.get(SourceNote.class, modelId);
			SysOrgPerson person = accountService.load(note.getFdUserId());//人员信息
			VCheckTaskData vdata = new VCheckTaskData();
			vdata.setUserName(person.getRealName());//名字
			vdata.setUserDept(person.getDeptName());//部门名字
			vdata.setUserEmail(person.getFdEmail());//邮箱
			vdata.setUserTel(person.getFdMobileNo());//电话
			CourseInfo courseInfo = courseService.get(note.getFdCourseId());
			vdata.setCourseName(courseInfo.getFdTitle());//课程
			CourseCatalog courseCatalog = courseCatalogService.get(note.getFdCatalogId());
			vdata.setCurrentCatalog(courseCatalog.getFdName());//节
			vdata.setGuideName(ShiroUtils.getUser().getName());//导师名字
			if(fdType.equalsIgnoreCase("checked")){
				MaterialInfo info = materialService.load(note.getFdMaterialId());
				List<Task> tasks = info.getTasks();
				Double totalScore = 0.0;
				for (Task task : tasks) {
					totalScore += task.getFdStandardScore();
				}
				vdata.setTotalScore(totalScore);//总分
				vdata.setPassScore(info.getFdScore());//标准分
				vdata.setGuideScore(note.getFdScore());//导师打分
				if(note.getFdStatus().equals(Constant.TASK_STATUS_FAIL)){
					vdata.setIsPass("否");
				}
				if(note.getFdStatus().equals(Constant.TASK_STATUS_PASS)){
					vdata.setIsPass("是");
				}
			}
			adviserList.add(vdata);
		}
		return adviserList;
	}
	/**
	 * 根据分页信息构建模板list
	 * @param list
	 * @return
	 */
	public List<VCheckTaskData> findCheckDataByPageList(List list){
		List<VCheckTaskData> adviserList = new ArrayList<VCheckTaskData>();
		for (Object obj : list) {
			VCheckTaskData vdata = new VCheckTaskData();
			Map map = (Map) obj;
			SysOrgPerson person = (SysOrgPerson)accountService.load((String)map.get("FDUSERID"));//人员信息
			vdata.setUserName(person.getRealName());//名字
			vdata.setUserDept(person.getDeptName());//部门名字
			vdata.setUserEmail(person.getFdEmail());//邮箱
			vdata.setUserTel(person.getFdMobileNo());//电话
			CourseInfo courseInfo = courseService.get((String)map.get("FDCOURSEID"));
			vdata.setCourseName(courseInfo.getFdTitle());//课程
			CourseCatalog courseCatalog = courseCatalogService.get((String)map.get("FDCATALOGID"));
			vdata.setCurrentCatalog(courseCatalog.getFdName());//节
			vdata.setGuideName(ShiroUtils.getUser().getName());//导师名字
			if(map.get("FDAPPRAISERID")!=null){
				MaterialInfo info = materialService.load((String)map.get("FDMATERIALID"));
				List<Task> tasks = info.getTasks();
				Double totalScore = 0.0;
				for (Task task : tasks) {
					totalScore += task.getFdStandardScore();
				}
				vdata.setTotalScore(totalScore);//总分
				vdata.setPassScore(info.getFdScore());//标准分
				vdata.setGuideScore(new Double(map.get("FDSCORE").toString()));//导师打分
				String fdStatus = (String)map.get("FDSTATUS");
				if(fdStatus.equals(Constant.TASK_STATUS_FAIL)){
					vdata.setIsPass("否");
				}
				if(fdStatus.equals(Constant.TASK_STATUS_PASS)){
					vdata.setIsPass("是");
				}
			}
			
			adviserList.add(vdata);
		 }
		return adviserList;
	}
	/**
	 * 找出我是指导老师的所有课程信息
	 * @param fdType 已批改/或批改
	 * @param pageNo 
	 * @param pageSize
	 * @param fdName 搜索栏输入条目
	 * @param order 排序（事件/课程/导师/新教师
	 * @return
	 */
	@Transactional(readOnly = false)
	public Pagination findAdivserCouserList(String fdType,Integer pageNo, Integer pageSize,String fdName,String order){
		 Finder finder = Finder.create(" select note.* from IXDF_NTP_SOURCE_NOTE note");
		 finder.append(" left join SYS_ORG_PERSON person on person.fdid = note.fduserid");
		 finder.append(" left join IXDF_NTP_COURSE course on course.fdid = note.fdcourseid");
		 if(fdType.equalsIgnoreCase("checked")){//导师已批改
			 finder.append(" where note.fdappraiserid=:appraiserid");
			 finder.setParam("appraiserid", ShiroUtils.getUser().getId());
		 }else{
			 finder.append(" where note.fdappraiserid is null"); 
		 }
		 finder.append(" and exists(select auth.fdid from IXDF_NTP_COURSE_PARTICI_AUTH auth");
		 finder.append(" where note.fdcourseid=auth.fdcourseid");
		 finder.append(" and note.fduserid=auth.fduserid");
		 finder.append(" and auth.fdteacherid=:teacherId )");
		 finder.setParam("teacherId", ShiroUtils.getUser().getId());
		 finder.append(" and exists(select material.fdid from IXDF_NTP_MATERIAL material where material.fdid=note.fdmaterialid");
		 finder.append("and material.fdtype=:fdType )");
		 finder.setParam("fdType", Constant.MATERIAL_TYPE_JOBPACKAGE);//作业包
		 if(StringUtil.isNotBlank(fdName)&&StringUtil.isNotEmpty(fdName)){
			finder.append(" and person.realname like :fdName");
			finder.setParam("fdName", '%' + fdName + '%');
		 }
		 if("fdcreatetime".equalsIgnoreCase(order)){
			 finder.append(" order by note.fdoperationdate desc ");
		 }
		 if("fdName".equalsIgnoreCase(order)){
			 finder.append(" order by course.fdtitle ");
		 }
		 if("user".equalsIgnoreCase(order)){
			 finder.append(" order by person.realname ");
		 }
		 
		 Pagination page = getPageBySql(finder, pageNo, pageSize);
		 return page;
	}
	
	@Override
	public <T> Class<T> getEntityClass() {
		return null;
	}

}

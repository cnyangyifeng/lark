package cn.me.xdf.action.adviser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jodd.io.FileNameUtil;
import jodd.util.StringUtil;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.page.SimplePage;
import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseContent;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.material.Task;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.model.process.TaskRecord;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.adviser.AdviserService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.bam.process.SourceNodeService;
import cn.me.xdf.service.bam.process.TaskRecordService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.course.CourseCatalogService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.service.material.TaskService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;

@Controller 
@RequestMapping(value = "/ajax/adviser")
@Scope("request")
public class AdviserAjaxController {
	
	@Autowired
	private AdviserService adviserService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private TaskRecordService taskRecordService;
	
	
	/**
	 * 找出批改是当前作业包信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "findCheckTaskDetail")
	@ResponseBody
	public String findCheckTaskDetail(HttpServletRequest request){
		String noteId = request.getParameter("noteId");
		SourceNote note = sourceNodeService.get(SourceNote.class, noteId);
		MaterialInfo info = materialService.load(note.getFdMaterialId());//作业包
		Map taskData = new HashMap();
		taskData.put("id", note.getFdId());
		//////////此处为得到作业包序号
		BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(note.getFdUserId(), note.getFdCourseId());
		if(bamCourse!=null){
			List<CourseContent> content = bamCourse.getCourseContents();
			for (CourseContent courseContent : content) {
				if(courseContent.getMaterial().getFdId().equals(note.getFdMaterialId())){
					taskData.put("num", courseContent.getFdMaterialNo());break;
				}
			}
		}else{
			taskData.put("num", "");
		}
		taskData.put("name", info.getFdName());
		/////////此处为设置总分
		List<Task> tasks = info.getTasks();
		double totalScore = 0;
		for (Task task : tasks) {
			totalScore += task.getFdStandardScore();
		}
		taskData.put("fullScore", totalScore);//总分
		taskData.put("scorePass", info.getFdScore());//及格分
		double fdScore = 0;
		Set<TaskRecord> Record = note.getTaskRecords();
        for (TaskRecord taskRecord : Record) {
        	if(taskRecord.getFdScore()!=null){
        		fdScore += taskRecord.getFdScore();
        	}
		}
		taskData.put("score", fdScore);//老师打分
		
		taskData.put("taskCount", info.getTasks().size());//作业数量
		taskData.put("intro", info.getFdDescription()==null?"":info.getFdDescription());//介绍
		String status = note.getFdStatus();
		if(status.equals(Constant.TASK_STATUS_FINISH)||status.equals(Constant.TASK_STATUS_UNFINISH)){
			taskData.put("status", "unfinish");//状态  unfinish, pass, fail
		}else if(status.equals(Constant.TASK_STATUS_PASS)){
			taskData.put("status", "pass");
		}else if(status.equals(Constant.TASK_STATUS_FAIL)){
			taskData.put("status", "fail");
		}
		////////////////////作业详情
		List<Map> taskRescordlist = new ArrayList<Map>();
		Set<TaskRecord> taskRecordList = note.getTaskRecords();
		for (TaskRecord temp : taskRecordList) {
			Map taskRescord = new HashMap();
			Task task = taskService.get(temp.getFdTaskId());//找出相对应的作业
			taskRescord.put("id", temp.getFdId());
			taskRescord.put("index", task.getFdOrder());//序号
			taskRescord.put("name", task.getFdName());//作业名称
			taskRescord.put("stem", task.getFdSubject()==null?"":task.getFdSubject());//作业简介
			if(temp.getFdStatus().equals(Constant.TASK_STATUS_UNFINISH)){
				taskRescord.put("status", "null");//00未答
			}else if(temp.getFdStatus().equals(Constant.TASK_STATUS_FINISH)){
				taskRescord.put("status", "unchecked");//提交未检查
			}else if(temp.getFdStatus().equals(Constant.TASK_STATUS_CHECK)){
				taskRescord.put("status", "checked");//检查 通过
			}
			if(task.getFdType().equals(Constant.TASK_TYPE_ONLINE)){//在线
				taskRescord.put("answer", temp.getFdAnswer());//检查 通过
			}
		    //作业类型
			taskRescord.put("type", task.getFdType().equals(Constant.TASK_TYPE_UPLOAD)?"uploadWork":"onlineAnswer");
			taskRescord.put("totalScore", task.getFdStandardScore());
			///导师信息
			Map rating = new HashMap();
			if(temp.getFdScore()!=null){
			  rating.put("score", temp.getFdScore());//得分	
			}else{
			  rating.put("score", 0);
			}
			if(temp.getFdCreateTime()!=null){
				rating.put("time", DateUtil.getInterval(DateUtil.convertDateToString(temp.getFdCreateTime()), "yyyy/MM/dd hh:mm aa"));
			}
			rating.put("comment", temp.getFdComment());
			taskRescord.put("rating", rating);
			/////////附件开始 (学生上传)
			List<AttMain> listTaskAttachment = attMainService
					.getByModeslIdAndModelNameAndKey(temp.getFdId(), TaskRecord.class.getName(),task.getFdId());
			List<Map> listAtt = new ArrayList();
			if(listTaskAttachment!=null&&!listTaskAttachment.isEmpty()){
				for (AttMain attMain : listTaskAttachment) {
					Map attMap = new HashMap();
					attMap.put("id", attMain.getFdId());
					attMap.put("name", attMain.getFdFileName());
					attMap.put("fileUrl", attMain.getFdFilePath());
					String name = FilenameUtils.getExtension(attMain.getFdFileName()).toLowerCase(Locale.ENGLISH);
				    String VIDEO = ".wmv.wm.asf.asx.rm.rmvb.ra.ram.mpg.mpeg.mpe.vob.dat.mov" +
				   		".3gp.mp4.mp4v.m4v.mkv.avi.flv.f4v.mts.doc.xls.docx.xlsx.pdf.ppt.pptx.pps.ppsx";
					if(VIDEO.contains(name)){
						attMap.put("type", "onlinePlay");
						attMap.put("mtype", attMain.getFdFileType());//增加类型
						attMap.put("fileNetId", attMain.getFileNetId());
						attMap.put("fName", FileNameUtil.getName(attMain.getFdFilePath()));
						attMap.put("playCode", attMain.getPlayCode());
					}else{
						attMap.put("type", "notOnlinePlay");
						attMap.put("fileNetId", attMain.getFileNetId());
					}
					if(attMain.getFileUrl()!=null){//播放地址
						attMap.put("mediaUrl", attMain.getFileUrl());
					}
					listAtt.add(attMap);
				 }
			}
			 
			taskRescord.put("listTaskAttachment", listAtt);
			/////////附件开始 (作业自带)
			List<AttMain> listAttachment = attMainService
					.getByModeslIdAndModelNameAndKey(task.getFdId(), Task.class.getName(),"taskAtt");
			taskRescord.put("listAttachment", listAttachment);
			taskRescordlist.add(taskRescord);
		}
		taskData.put("listTask", taskRescordlist);
		return JsonUtils.writeObjectToJson(taskData);
	}
	/**
	 * 老师批改页面头部 得到用户信息和课程
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findCourseAndUser")
	@ResponseBody
	public String findCourseAndUser(HttpServletRequest request){
		String noteId = request.getParameter("noteId");
		SourceNote note = sourceNodeService.get(SourceNote.class, noteId);
		Map introData = new HashMap();
		CourseInfo courseInfo = courseService.get(note.getFdCourseId());
		introData.put("id", courseInfo.getFdId());
		introData.put("courseName", courseInfo.getFdTitle());
		CourseCatalog courseCatalog = courseCatalogService.get(note.getFdCatalogId());
		introData.put("currLecture", courseCatalog.getFdName());//当前节
		introData.put("mentor", ShiroUtils.getUser().getName());
		SysOrgPerson person = accountService.findById(note.getFdUserId());//学习课程人员
		Map user = new HashMap();//封装人员信息
		user.put("name", person.getRealName());
		user.put("imgUrl", person.getPoto());
		user.put("org", person.getHbmParent()==null?"":person.getHbmParent().getHbmParentOrg().getFdName());
		user.put("department", person.getDeptName());
		user.put("phone", person.getFdWorkPhone());
		user.put("mail", person.getFdEmail());
		user.put("link", person.getFdId());
		if(person.getFdId().equals(ShiroUtils.getUser().getId())){
			user.put("flag", true);
		}else{
			user.put("flag", false);
		}
		introData.put("user", user);////封装人员信息
		return JsonUtils.writeObjectToJson(introData);
	}
	
	@RequestMapping("/updateTaskRecord/{fdId}")
	@ResponseBody
	public Integer updateTaskRecord(@PathVariable("fdId") String fdId,HttpServletRequest request){
		String fdComment = request.getParameter("fdComment");
		String score = request.getParameter("score");
		TaskRecord record = taskRecordService.get(fdId);
		record.setFdComment(fdComment);//设置评语
		record.setFdScore(Double.parseDouble(score));//设置评分
		record.setFdStatus(Constant.TASK_STATUS_CHECK);//设置为已检查
		record.setFdCreateTime(new Date());//设置导师批改时间
		record.setFdAppraiserId(ShiroUtils.getUser().getId());//设置导师
		taskRecordService.save(record);
		Task task = taskService.get(record.getFdTaskId());
		return task.getFdOrder();
	}
	/**
	 * 更新soursenote里边的导师批课信息
	 * @param fdId
	 * @param request
	 */
	@RequestMapping("/updateSourseNote/{fdId}")
	@ResponseBody
	public void updateSourseNote(@PathVariable("fdId") String fdId,HttpServletRequest request){
		SourceNote note = sourceNodeService.get(SourceNote.class, fdId);
		double totalScore = 0.0;
		Set<TaskRecord> Record = note.getTaskRecords();
        for (TaskRecord taskRecord : Record) {
        	if(taskRecord.getFdScore()!=null){
        		totalScore += taskRecord.getFdScore();
        	}
        	if(taskRecord.getFdAppraiserId()==null){//题目未作答的时候
        		taskRecord.setFdAppraiserId(ShiroUtils.getUser().getId());//设置导师
        		taskRecord.setFdCreateTime(new Date());//设置导师批改时间
        		taskRecord.setFdScore(0.0);//设置评分
        		taskRecord.setFdStatus(Constant.TASK_STATUS_CHECK);//设置为已检查
        		taskRecordService.save(taskRecord);
        	}
		}		
		note.setFdScore(totalScore);
		MaterialInfo info = materialService.load(note.getFdMaterialId());//作业包
		if(info.getFdScore()>totalScore){
			note.setFdStatus(Constant.TASK_STATUS_FAIL);//未通过
			note.setIsStudy(false);//设置为true
		}else{
			note.setFdStatus(Constant.TASK_STATUS_PASS);//通过
			note.setIsStudy(true);//设置为true
		}
		note.setFdAppraiserId(ShiroUtils.getUser().getId());//指导老师
		sourceNodeService.saveSourceNode(note);
	}
	/**
	 * 返回当前sourcenote对应的taskScord的附件
	 * @param fdId
	 * @param request
	 * @return
	 */
	@RequestMapping("/findAttsBySoureceId/{fdId}")
	@ResponseBody
	public String findAttsBySoureceId(@PathVariable("fdId") String fdId,HttpServletRequest request){
		SourceNote note = sourceNodeService.get(SourceNote.class, fdId);
		Map attmap = new HashMap();
		attmap.put("attIds", findAtt(note));
		CourseCatalog log = courseCatalogService.get(note.getFdCatalogId());
		CourseInfo course = courseService.get(note.getFdCourseId());
		SysOrgPerson person = accountService.get(note.getFdUserId());
		MaterialInfo info = materialService.get(note.getFdMaterialId());
		attmap.put("course", course.getFdTitle());
		attmap.put("log", log.getFdName());
		attmap.put("info", info.getFdName());
		attmap.put("person", person.getRealName());
		return JsonUtils.writeObjectToJson(attmap);
	}
	/**
	 * 批量下载时 根据soursenodeId 找出附件 id
	 * @param fdIds
	 * @param request
	 * @return
	 */
	@RequestMapping("/findAttsBySoureceIds/{fdIds}")
	@ResponseBody
	public String findAttsBySoureceIds(@PathVariable("fdIds") String[] fdIds,HttpServletRequest request){
		
		List<String> attMainList = new ArrayList<String>();
        for (int i = 0; i < fdIds.length; i++) {
            List<AttMain> attMains = adviserService.findNotesAtts(fdIds[i]);
            for (AttMain attMain : attMains) {
            	if(StringUtil.isNotBlank(attMain.getFileNetId())){
            		attMainList.add(attMain.getFdId());
            	}
			}
        }
        Map attmap = new HashMap();
        attmap.put("attIds", attMainList);
		return JsonUtils.writeObjectToJson(attmap);
	}
	
	/**
	 * 得到有没有未批改作业
	 * @return
	 */
	@RequestMapping(value="getUnCheckNum")
	@ResponseBody
	public Integer getUnCheckNum(){
		if (ShiroUtils.getUser() == null) {
            return 0;
        }
		Pagination page  = adviserService.findAdivserCouserList("unchecked", 1, SimplePage.DEF_COUNT, null, null);
		return page.getTotalCount();
	}
	
	
	/**
	 * 找出我所批改的作业
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "findCheckTaskList")
	@ResponseBody
	public String findCheckTaskList(HttpServletRequest request){
		String pageNoStr = request.getParameter("pageNo");
		String keyword = request.getParameter("keyword");//搜索项
		String fdType = request.getParameter("fdType");//已批改、未批改
		String order = request.getParameter("order");
		if(StringUtil.isBlank(order)){
			order = "fdcreatetime";
		}
		if(StringUtil.isBlank(fdType)){
			fdType = "unchecked";
		}
		int pageNo;
		if (StringUtil.isNotBlank(pageNoStr)) {
			pageNo = Integer.parseInt(pageNoStr);
		} else {
			pageNo = 1;
		}
		Pagination page  = adviserService.findAdivserCouserList(fdType, pageNo, SimplePage.DEF_COUNT, keyword, order);
		Map data =  new HashMap();
		List checkedData = new ArrayList();
		if(page.getTotalCount()>0){
			List list = page.getList();
			for(int i=0;i<list.size();i++){
				Map map = new HashMap();
				Map pageMap = (Map) list.get(i);
				map.put("id", (String)pageMap.get("FDID"));
				SysOrgPerson person = accountService.findById((String)pageMap.get("FDUSERID"));//学习课程人员
				Map user = new HashMap();//封装人员信息
				user.put("name", person.getRealName());
				user.put("imgUrl", person.getPoto());
				user.put("org", person.getHbmParent()==null?"":person.getHbmParent().getHbmParentOrg().getFdName());
				user.put("department", person.getDeptName());
				user.put("phone", person.getFdWorkPhone());
				user.put("mail", person.getFdEmail());
				user.put("link", person.getFdId());//人员id
				if(person.getFdId().equals(ShiroUtils.getUser().getId())){
					user.put("flag", true);
				}else{
					user.put("flag", false);
				}
				map.put("user", user);////封装人员信息
				CourseInfo courseInfo = courseService.get((String)pageMap.get("FDCOURSEID"));
				map.put("courseName", courseInfo.getFdTitle());//课程名字
				map.put("mentor", ShiroUtils.getUser().getName());
				CourseCatalog courseCatalog = courseCatalogService.get((String)pageMap.get("FDCATALOGID"));
				map.put("currLecture", courseCatalog.getFdName());//节名称
				MaterialInfo info = materialService.load((String)pageMap.get("FDMATERIALID"));
				map.put("taskPaper", info.getFdName());
				if(fdType.equalsIgnoreCase("unchecked")){//未检查
					SourceNote note = sourceNodeService.get(SourceNote.class, (String)pageMap.get("FDID"));
					String zipname = courseInfo.getFdTitle()+"_"+info.getFdName()+"_"+person.getRealName();
					map.put("downloadBoxUrl", findAtt(note));//下载作业包
					map.put("zipname", zipname);
				}else{
					List<Task> tasks = info.getTasks();
					Double totalScore = 0.0;
					for (Task task : tasks) {
						totalScore += task.getFdStandardScore();
					}
					map.put("scoreTotal", totalScore);//总分
					map.put("scorePass", info.getFdScore());//及格分
					map.put("score", pageMap.get("FDSCORE"));//老师打分
					if(pageMap.get("FDSTATUS").equals(Constant.TASK_STATUS_FAIL)){
						map.put("isPass", false);//没有通过
					}else if(pageMap.get("FDSTATUS").equals(Constant.TASK_STATUS_PASS)){
						map.put("isPass", true);//通过
					}
				}
				checkedData.add(map);
			} 
		}
		Map paging = new HashMap();
		paging.put("totalPage", page.getTotalPage());
		paging.put("currentPage", page.getPageNo());
		paging.put("totalCount", page.getTotalCount());
		paging.put("StartPage", page.getStartPage());
		paging.put("EndPage",page.getEndPage());
		paging.put("StartOperate", page.getStartOperate());
		paging.put("EndOperate", page.getEndOperate());
		paging.put("startNum", page.getStartNum());
		paging.put("endNum", page.getEndNum());
		data.put("checkedData", checkedData);
		data.put("paging",paging);
		return JsonUtils.writeObjectToJson(data);
	}
	/////找附件
	private Object[] findAtt(SourceNote note){
		Set<TaskRecord> taskRexords = note.getTaskRecords();
		List<String> attAll = new ArrayList<String>();
		for (TaskRecord taskRecord : taskRexords) {
			List<AttMain> attMains = attMainService.getAttMainsByModelIdAndModelName(taskRecord.getFdId(), TaskRecord.class.getName());
			if(attMains!=null&&!attMains.isEmpty()){
				for (AttMain attMain : attMains) {
					if(attMain!=null&&StringUtil.isNotBlank(attMain.getFileNetId())){
						attAll.add(attMain.getFdId());
					}
				}
			}
		}
		return attAll.toArray();
	}

}

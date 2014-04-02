package com.kuxue.action.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.utils.array.ArrayUtils;
import com.kuxue.common.utils.array.SortType;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.material.MaterialAuth;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.material.Task;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.service.material.TaskService;
import com.kuxue.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/taskPaper")
@Scope("request")
public class TaskPaperAjaxController {
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private AccountService accountService;
	
	
	@RequestMapping(value = "saveOrUpdateTaskPaper")
	@ResponseBody
	public Map<String,String> saveOrUpdateTaskPaper(HttpServletRequest request){
		Map<String,String> resultMap = new HashMap<String,String>();
		String materialId = request.getParameter("materialId");
		MaterialInfo info;
		String taskId = request.getParameter("taskId");
		String examPaperIntro = request.getParameter("examPaperIntro");
		String studyTime = request.getParameter("studyTime");
		SysOrgPerson orgPerson = accountService.load(ShiroUtils.getUser().getId());
		Task task;
		if(StringUtil.isBlank(materialId)){//先进行作业包素材的保存
			info = new MaterialInfo();
			info.setCreator(orgPerson);
			info.setFdAuthor(ShiroUtils.getUser().getName());
			info.setFdAuthorDescription(orgPerson.getSelfIntroduction());
			info.setFdType(Constant.MATERIAL_TYPE_JOBPACKAGE);
			info.setFdCreateTime(new Date());
			info.setFdName(request.getParameter("materialName"));
			info.setIsPublish(false);
			info.setIsDownload(true);
			info.setIsAvailable(true);
			info.setFdDescription(examPaperIntro);
			info.setFdStudyTime(StringUtil.isAllBlank(studyTime)?0:Integer.parseInt(studyTime));
			materialService.save(info);
		}else{
			info = materialService.load(materialId);
			info.setFdName(request.getParameter("materialName"));
			info.setFdDescription(examPaperIntro);
			info.setFdStudyTime(StringUtil.isAllBlank(studyTime)?0:Integer.parseInt(studyTime));
			materialService.save(info);
		}
		String examType = request.getParameter("examType");//题型 01上传作业，02在线作答
		String examName = request.getParameter("examName");//作业题目
		String fdSubject = request.getParameter("examStem");//作业简介
		String examScore = request.getParameter("examScore");//作业总分
		double score=0.0;
		if(StringUtil.isNotBlank(examScore)){
		  score = Double.parseDouble(examScore);
		}
		if(StringUtil.isBlank(taskId)||taskId.equals("undefined")){//作业id为空时新建
			task = new Task();
			task.setFdName(examName);
			task.setFdStandardScore(score);
			if(examType.equals("uploadWork")){
				task.setFdType(Constant.TASK_TYPE_UPLOAD);//上传作业
			} else {
				task.setFdType(Constant.TASK_TYPE_ONLINE);//在线作答
			}
			task.setFdSubject(fdSubject);
			task.setTaskPackage(info);
			task.setFdOrder(info.getTasks()==null?0:info.getTasks().size());
			taskService.save(task);
		} else {//更改
			task = taskService.load(taskId);
			task.setFdName(examName);
			task.setFdStandardScore(score);
			if(examType.equals("uploadWork")){
				task.setFdType(Constant.TASK_TYPE_UPLOAD);//上传作业
			} else {
				task.setFdType(Constant.TASK_TYPE_ONLINE);//在线作答
			}
			task.setFdSubject(fdSubject);
			task.setTaskPackage(info);
			taskService.save(task);
		}
		
		List<AttMain> attMains = new ArrayList<AttMain>();
		String attString = request.getParameter("listAttachment");
		//删除之前的选项
		List<AttMain> oldAttMains = attMainService.findByCriteria(AttMain.class,
		                Value.eq("fdModelId", task.getFdId()),
		                Value.eq("fdModelName", Task.class.getName()));
		for (AttMain attMain : oldAttMains) {
			attMainService.deleteAttMain(attMain.getFdId());
		}
		// 更新选项附件
		if (StringUtil.isNotBlank(attString)) {
			List<Map> att = JsonUtils.readObjectByJson(attString, List.class);
			for (Map map : att) {
				AttMain e = attMainService.get(map.get("id").toString());
				e.setFdModelId(task.getFdId());
				e.setFdModelName(Task.class.getName());
				e.setFdKey("taskAtt");
				e.setFdOrder(map.get("index").toString());
				attMains.add(e);
				attMainService.save(e);
			}
		}
		double count=getTaskTotal(info);
		double countT=Double.parseDouble(examScore);
		if(StringUtil.isBlank(taskId)||taskId.equals("undefined")){
			count += countT;
		}
	    info.setFdScore(count);
		materialService.save(info);
		
		resultMap.put("materialId", info.getFdId());
		return resultMap;
	}
	/**
	 * 更改作业包素材信息
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "updateTaskPaperMaterial")
	@ResponseBody
	public void updateTaskPaperMaterial(HttpServletRequest request){
		String id = request.getParameter("materialId");//素材id
		String examPaperName = request.getParameter("examPaperName");//作业包名称
		String examPaperIntro = request.getParameter("examPaperIntro");//作业包介绍
		String author = request.getParameter("author");
		String authorIntro = request.getParameter("authorIntro");
		String permission = request.getParameter("permission");//是否公开
		String listExam = request.getParameter("listExam");//作业列表
		String kingUser = request.getParameter("kingUser");//授权人员
		String passScore = request.getParameter("passScore");//及格分
		String studyTime = request.getParameter("studyTime");//学习时长
		Integer time = 0;
		if(StringUtil.isNotBlank(studyTime)){
			time = new Integer(studyTime);
		}
		List<Map> taskPaper;
		MaterialInfo info = materialService.load(id);
		if(StringUtil.isBlank(listExam)){
			taskPaper = new ArrayList<Map>();
		}else{
			taskPaper = JsonUtils.readObjectByJson(listExam, List.class);
		}
		List<Map> users;
		if(StringUtil.isBlank(kingUser)){
			users = new ArrayList<Map>();
		}else{
			users = JsonUtils.readObjectByJson(kingUser, List.class);
		}
		
		List<Task> taskList = new ArrayList<Task>();
		//添加作业列表
		int index=0;
		for (Map<?, ?> map : taskPaper) {
			Task task = taskService.get(map.get("id").toString());
			task.setFdStandardScore(new Double(map.get("editingCourse").toString()));
			task.setFdOrder(index++);
			taskService.save(task);
			taskList.add(task);
		}
		//删除多余作业
		List<Task> oldTaskList = taskService.findByProperty("taskPackage.fdId", info.getFdId());
		for (Task task : oldTaskList) {
			if(!taskList.contains(task)){
				deleteTask(task.getFdId());
			}
		}
		
		List<MaterialAuth> materialAuths = new ArrayList<MaterialAuth>();
		//添加授权人员信息
		if(!permission.equals("open")){
			for (Map map : users) {
				String personid =map.get("id").toString();
				if(personid.equals(info.getCreator().getFdId())){
					continue;
				}
				MaterialAuth materialAuth = new MaterialAuth();
				materialAuth.setFdUser((SysOrgPerson)accountService.load(personid));
				materialAuth.setIsReader(map.get("tissuePreparation").toString().equals("true")?true:false);
				materialAuth.setIsEditer(map.get("editingCourse").toString().equals("true")?true:false);
				materialAuth.setMaterial(info);
				materialAuths.add(materialAuth);
			}
		}
		//更新权限
		materialService.updateMaterialAuth(id, materialAuths);
		info.setFdName(examPaperName);
		info.setFdDescription(examPaperIntro);
		info.setFdAuthor(author);
		info.setFdAuthorDescription(authorIntro);
		info.setFdScore(new Double(passScore));
		info.setIsDownload(true);
		info.setIsAvailable(true);
		info.setIsPublish(permission.equals("open")?true:false);
		info.setFdStudyTime(time);
		info.setTasks(taskList);
		materialService.save(info);
	}
	/**
	 * 根据作业id找作业 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getTaskByTaskId")
	@ResponseBody
	public String getTaskByTaskId(HttpServletRequest request){
		String taskId = request.getParameter("id");
		Task task = taskService.load(taskId);
		Map<String, Object> map = new HashMap<String, Object>();
		if(task.getFdType().equals(Constant.TASK_TYPE_UPLOAD)){
			map.put("examType", "uploadWork");
		}else if(task.getFdType().equals(Constant.TASK_TYPE_ONLINE)){
			map.put("examType", "onlineAnswer");
		}
		map.put("taskId", task.getFdId());
		map.put("examName", task.getFdName());
		map.put("examScore", task.getFdStandardScore());
		map.put("examStem", task.getFdSubject());
		
		List<AttMain> atts = attMainService.getByModeslIdAndModelNameAndKey(task.getFdId(), Task.class.getName(),"taskAtt");
		List<Map<String,String>> attList = new ArrayList<Map<String,String>>();
		if(atts!=null){
			for (AttMain attMain : atts) {
				if(attMain.getFdKey().equals("taskAtt")){
				 Map<String,String> mapAtt = new HashMap<String,String>();
				 mapAtt.put("id", attMain.getFdId());
				 mapAtt.put("index", attMain.getFdOrder());
				 mapAtt.put("name", attMain.getFdFileName());
				 if(attMain.getFileNetId()!=null){
					 mapAtt.put("url", attMain.getFdId()); 
				 }else{
					 mapAtt.put("url", null);
				 }
				 attList.add(mapAtt);
				}
			}
			map.put("listAttachment",  attList);
		}
		return JsonUtils.writeObjectToJson(map);
	}
	/**
	 * 根据素材(作业包)id获取作业 初始化作业包页面
	 * @param request
	 * @return 
	 */
	@RequestMapping(value = "getTaskByMaterialId")
	@ResponseBody
	public String getTaskByMaterialId(HttpServletRequest request) {
		String materialId = request.getParameter("materialId");
		MaterialInfo info = materialService.load(materialId);
		List<Task> taskList = info.getTasks();
		//进行排序
		ArrayUtils.sortListByProperty(taskList, "fdOrder", SortType.HIGHT);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map;
		for(Task task:taskList){
			map = new HashMap<String,String>();
			map.put("id", task.getFdId());
			map.put("subject", task.getFdName());
			map.put("score", task.getFdStandardScore().intValue()+"");
		    list.add(map);
		}
		return JsonUtils.writeObjectToJson(list);
	}
	

	/**
	 * 根据作业包id删除作业
	 * @param materialId
	 */
	@RequestMapping(value = "deleteTaskByTaskId")
	@ResponseBody
	public void deleteTaskByTaskId(HttpServletRequest request){
		String taskId = request.getParameter("taskId");
		deleteTask(taskId);
	}
	private void deleteTask(String fdId){
		attMainService.deleteAttMainByModelId(fdId);
		taskService.delete(fdId);
	}
	/**
	 * 统计作业的总分
	 */
	private double getTaskTotal(MaterialInfo materialInfo){
		List<Map> tasks=taskService.getTaskByMaterId(materialInfo);
		if(tasks!=null&&tasks.size()>0){
			double count=0;
			for(Map map:tasks){
				count+=Double.parseDouble(map.get("score").toString());
			}
			return count;
		}
		return 0;
	}
}

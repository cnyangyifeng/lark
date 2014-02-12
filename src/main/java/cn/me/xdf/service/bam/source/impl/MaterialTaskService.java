package cn.me.xdf.service.bam.source.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.material.ExamQuestion;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.material.Task;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.process.AnswerRecord;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.model.process.TaskRecord;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.bam.process.SourceNodeService;
import cn.me.xdf.service.bam.source.ISourceService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.service.material.TaskService;
import cn.me.xdf.utils.ShiroUtils;

@Service("materialTaskService")
public class MaterialTaskService extends SimpleService implements ISourceService  {
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private AccountService accountService;
	
	@Override
    public Object findSourceByMaterials(BamCourse bamCourse, CourseCatalog catalog) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object saveSourceNode(WebRequest request) {
		String catalogId = request.getParameter("catalogId");
		String bamId = request.getParameter("bamId");
		String taskPaperId = request.getParameter("fdid");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		MaterialInfo info = materialService.get(taskPaperId);
		SourceNote sourceNode = new SourceNote();
		List<Task> taskList = taskService.findByProperty("taskPackage.fdId",taskPaperId);
		Set<TaskRecord> answerRecords = new HashSet<TaskRecord>();
		List<AttMain> attMains = new ArrayList<AttMain>();
		///////////////////////////////////
		for (Task task : taskList) {
			TaskRecord taskRecord = new TaskRecord();
			taskRecord.setFdTaskId(task.getFdId());//对应作业id
			if(task.getFdType().equals(Constant.TASK_TYPE_ONLINE)){//在线作答
				String answer = request.getParameter("answer_"+task.getFdId());
				taskRecord.setFdAnswer(answer);//保存答案
				if(StringUtil.isBlank(answer)){
					taskRecord.setFdStatus(Constant.TASK_STATUS_UNFINISH);//00 未答
				} else {
					taskRecord.setFdStatus(Constant.TASK_STATUS_FINISH);//01 答完
				}
			}
			if(task.getFdType().equals(Constant.TASK_TYPE_UPLOAD)){//上传作业
				String[] taskAttArrId = request.getParameterValues("attach_"+task.getFdId());
				if(taskAttArrId!=null){
					for (String taskAttId : taskAttArrId) {
						AttMain attMain = attMainService.get(taskAttId);
						attMain.setFdModelId(taskRecord.getFdId());
						attMain.setFdModelName(TaskRecord.class.getName());
						attMain.setFdKey(task.getFdId());
						attMainService.save(attMain);
						attMains.add(attMain);///保存附件
					}
				}
				if(attMains.isEmpty()){
					taskRecord.setFdStatus(Constant.TASK_STATUS_UNFINISH);//00 未答
				} else {
					taskRecord.setFdStatus(Constant.TASK_STATUS_FINISH);//01 答完
				}
				taskRecord.setAttMains(attMains);
			}
			answerRecords.add(taskRecord);
		}
		sourceNode.setTaskRecords(answerRecords);
		sourceNode.setFdCourseId(bamCourse.getCourseId());
		sourceNode.setFdCatalogId(catalogId);
		sourceNode.setFdUserId(ShiroUtils.getUser().getId());
		sourceNode.setFdOperationDate(new Date());
		sourceNode.setFdMaterialId(info.getFdId());
		if(answerRecords.isEmpty()){
			sourceNode.setFdStatus(Constant.TASK_STATUS_UNFINISH);
		}else{
			sourceNode.setFdStatus(Constant.TASK_STATUS_FINISH);
		}
        return sourceNodeService.saveSourceNode(sourceNode);  
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object findSubInfoByMaterial(WebRequest request) {
		String materialId = request.getParameter("materialId");
		String catalogId = request.getParameter("catalogId");//节id
		List<Map> list = new ArrayList<Map>();//存放作业列表
		MaterialInfo materialInfo = materialService.load(materialId);
		if(!materialInfo.getIsAvailable()){
			return list;
		}
		List<Task> taskList = materialInfo.getTasks();
		///作业包状态
		// ///////////////评分人操作信息
		SourceNote sourceNote = sourceNodeService.getSourceNote(materialInfo.getFdId(),
							catalogId, ShiroUtils.getUser().getId());
		for(Task task:taskList){
			Map taskMap = new HashMap();
			taskMap.put("id", task.getFdId());
			taskMap.put("index", task.getFdOrder());
			taskMap.put("examType", task.getFdType().equals(Constant.TASK_TYPE_UPLOAD)?"uploadWork":"onlineAnswer");
			taskMap.put("examScore", task.getFdStandardScore().intValue()); 	
			taskMap.put("examName", task.getFdName());
			taskMap.put("examStem", task.getFdSubject());
			
			List<AttMain> taskAtt = attMainService.getByModeslIdAndModelNameAndKey(task.getFdId(), Task.class.getName(),"taskAtt");
			if(taskAtt!=null){
				taskMap.put("listAttachment", taskAtt);//存放作业附件信息
			}
			/////////////////评分人操作信息
			if(sourceNote != null) {
				Set<TaskRecord> taskRecordList = sourceNote.getTaskRecords();
				for (TaskRecord taskRecord : taskRecordList) {
					if(taskRecord.getFdTaskId().equals(task.getFdId())){
						Map record = new HashMap();
						if(taskRecord.getFdScore()!=null){
							record.put("score", taskRecord.getFdScore());
						}
						record.put("comment", taskRecord.getFdComment());
						if(StringUtil.isNotBlank(taskRecord.getFdAppraiserId())){
							SysOrgPerson person = accountService.load(taskRecord.getFdAppraiserId());
							Map teacher= new HashMap();
							teacher.put("imgUrl", person.getPoto());
							teacher.put("link", person.getFdId());
							teacher.put("name", person.getFdName());
							record.put("teacher", teacher);
						}
						taskMap.put("teacherRating", record);
						///作业状态
						if(taskRecord.getFdStatus().equals(Constant.TASK_STATUS_UNFINISH)){
							taskMap.put("status", "null");//未答
						}else if(taskRecord.getFdStatus().equals(Constant.TASK_STATUS_FINISH)){
							taskMap.put("status", "finish");//已答
					    //////////// 已下两个状态 先从作业包 中取
						}else if(sourceNote.getFdStatus().equals(Constant.TASK_STATUS_PASS)){
							taskMap.put("status", "success");//导师批改
						}else if(sourceNote.getFdStatus().equals(Constant.TASK_STATUS_FAIL)){
							taskMap.put("status", "error");//导师批改
						}
						List<AttMain> answerAtt = attMainService.getByModeslIdAndModelNameAndKey(taskRecord.getFdId(), TaskRecord.class.getName(),task.getFdId());
						if(answerAtt!=null){
							taskMap.put("listTaskAttachment", answerAtt);//存放答题者上传的附件
						}
						taskMap.put("answer", taskRecord.getFdAnswer());
						break;
					}
				}
			}else{
				taskMap.put("status", "null");//未答
			}
			
			list.add(taskMap);
		}
		return list;
	}

	@Override
	public Object findMaterialDetailInfo(BamCourse bamCourse, CourseCatalog catalog,String fdid) {
		Map map = new HashMap();
		List<MaterialInfo> material = bamCourse.getMaterialByCatalog(catalog);
		List list = new ArrayList();
		if(material!=null){
			for(MaterialInfo minfo:material){
				Map materialTemp = new HashMap();
				materialTemp.put("id", minfo.getFdId());
				materialTemp.put("name", minfo.getFdName());
				Map m = materialService.getTotalSorce(minfo.getFdId());
				materialTemp.put("fullScore", m.get("totalscore"));
				materialTemp.put("examCount", m.get("num"));
				materialTemp.put("examPaperTime", minfo.getFdStudyTime());
				materialTemp.put("examPaperIntro", minfo.getFdDescription());
				materialTemp.put("examPaperStatus", sourceNodeService.getStatus(minfo, catalog.getFdId(), ShiroUtils.getUser().getId()));
				list.add(materialTemp);
			}
		}
		map.put("listExamPaper", list);
		return map;
	}

	@Override
	public Object reCalculateMaterial(String catalogId, String materialId) {
		MaterialInfo materialInfo = materialService.get(materialId); 
		if(!materialInfo.getIsAvailable()){
			return null;
		}
		
		SourceNote sourceNote = sourceNodeService.getSourceNote(materialId, catalogId, ShiroUtils.getUser().getId());
		if(sourceNote==null){
			return null;
		}else{
			if(Constant.TASK_STATUS_FINISH.equals(sourceNote.getFdStatus())){
				return false;
			}
		}
		//素材中的作业
		List<Task> taskList = materialInfo.getTasks();
		//学习记录中的最近一次作业记录
		Set<TaskRecord> taskRecordList = sourceNote.getTaskRecords();
		double score = 0;
		for(Task task:taskList){
			for (TaskRecord taskRecord : taskRecordList) {
				if(taskRecord.getFdTaskId().equals(task.getFdId())){
					score += taskRecord.getFdScore()==null?0:taskRecord.getFdScore();	
				}
			}
		}
		if(score>=materialInfo.getFdScore()){
			return true;
		}
		return false;
	}
	
}

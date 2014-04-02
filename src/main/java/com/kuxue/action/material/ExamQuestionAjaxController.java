package com.kuxue.action.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuxue.common.hibernate4.Value;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.material.ExamOpinion;
import com.kuxue.model.material.ExamQuestion;
import com.kuxue.model.material.MaterialAuth;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.material.ExamOpinionService;
import com.kuxue.service.material.ExamQuestionService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/examquestion")
@Scope("request")
public class ExamQuestionAjaxController {

	@Autowired
	private MaterialService materialService;

	@Autowired
	private ExamOpinionService examOpinionService;

	@Autowired
	private ExamQuestionService examQuestionService;

	@Autowired
	private AttMainService attMainService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "saveOrUpdateExamQuestion")
	@ResponseBody
	public Map saveOrUpdateExamQuestion(HttpServletRequest request) {

		String materialName = request.getParameter("materialName");
		String materialintro = request.getParameter("materialintro");
		String materialScore = request.getParameter("materialScore");
		String materIalId = request.getParameter("materIalId");
		String authorIntro = request.getParameter("authorIntro");
		String author = request.getParameter("author");
		MaterialInfo exam;
		if(StringUtil.isBlank(materIalId)){
			exam = new MaterialInfo();
			exam.setCreator((SysOrgPerson) accountService.load(ShiroUtils.getUser()
					.getId()));
			exam.setFdType(Constant.MATERIAL_TYPE_TEST);
			exam.setFdName(materialName);
			exam.setFdCreateTime(new Date());
			exam.setIsPublish(false);
			exam.setIsDownload(true);
			exam.setIsAvailable(true);
			exam.setFdDescription(materialintro);
			exam.setFdAuthor(author);
			exam.setFdAuthorDescription(authorIntro);
			exam.setFdStudyTime(StringUtil.isAllBlank(materialScore)?0:Integer.parseInt(materialScore));
			//保存测试
			materialService.save(exam);
		}else{
			exam = materialService.get(materIalId);
			exam.setFdDescription(materialintro);
			exam.setFdAuthor(author);
			exam.setFdAuthorDescription(authorIntro);
			exam.setFdStudyTime(StringUtil.isAllBlank(materialScore)?0:Integer.parseInt(materialScore));
		}
		String questionId = request.getParameter("questionId");
		ExamQuestion examQuestion;
		if(StringUtil.isEmpty(questionId)||questionId.equals("undefined")){
			examQuestion = new ExamQuestion();
			examQuestion.setFdOrder(exam.getQuestions()==null?0:exam.getQuestions().size());
		}else{
			examQuestion = examQuestionService.get(questionId);
		}
		String fdType = request.getParameter("examType");
		String fdSubject = request.getParameter("examStem");
		String fdStandardScore = request.getParameter("examScore");
		String attString = request.getParameter("listAttachment");
		String answer = "";
		String opinionString = request.getParameter("listExamAnswer");
		examQuestion.setExam(exam);
		examQuestion.setFdStandardScore(new Double(fdStandardScore));
		examQuestion.setFdSubject(fdSubject);
		if (fdType.equals("single")) {
			// 单选
			examQuestion.setFdType(Constant.EXAM_QUESTION_SINGLE_SELECTION);
		} else if (fdType.equals("multiple")) {
			// 多选
			examQuestion.setFdType(Constant.EXAM_QUESTION_MULTIPLE_SELECTION);
		} else {
			// 填空
			examQuestion.setFdType(Constant.EXAM_QUESTION_CLOZE);
		}
		// ///////////////////////////////保存试题
		examQuestionService.save(examQuestion);
		if (StringUtil.isBlank(opinionString)) {
			String[] s = fdSubject.split("#");
			String res = "";
			for (int i = 0; i < s.length; i++) {
				if (i % 2 == 1) {
					res = res + s[i]+"#";
				}
			}
			examQuestion.setFdQuestion(res);
		} else {
			//删除之前的选项
			examOpinionService.deleteOpinionsByQuestionId(examQuestion.getFdId());
			//保存新选项
			List<Map> poinion = JsonUtils.readObjectByJson(opinionString,
					List.class);
			for (Map map : poinion) {
				ExamOpinion e = new ExamOpinion();
				e.setFdOrder(new Integer(map.get("index").toString()));
				e.setOpinion((String) map.get("name"));
				e.setQuestion(examQuestion);
				String isAnswer = map.get("isAnswer").toString();
				e.setIsAnswer(isAnswer.equals("true"));
				examOpinionService.save(e);
				// 得到答案
				if (isAnswer.equals("true")) {
					answer += e.getFdId() + "#";
				}
			}
			examQuestion.setFdQuestion(answer);
		}
		examQuestionService.save(examQuestion);
		
		double count=getExamTotal(exam);
	    double countT=Double.parseDouble(fdStandardScore);
	    if(StringUtil.isEmpty(questionId)||questionId.equals("undefined")){
	    	count+= countT;
	    }
		exam.setFdScore(count);
		materialService.save(exam);
		// 更新选项附件
		updateAttMain(attString, examQuestion.getFdId());
		Map retMap = new HashMap();
		retMap.put("materIalId", exam.getFdId());
		retMap.put("examQuestionId", examQuestion.getFdId());
		retMap.put("examQuestionSub", examQuestion.getFdSubject());
		return retMap;
	}

	@RequestMapping(value = "getExamsByMaterialId")
	@ResponseBody
	public String getExamsByMaterialId(HttpServletRequest request) {
		String questionId = request.getParameter("id");
		ExamQuestion question = examQuestionService.get(questionId);
		Map map = new HashMap();
		int type = question.getFdType();
		String questionType = "";
		if (type == 1) {
			questionType = "single";
		} else if (type == 2) {
			questionType = "multiple";
		} else {
			questionType = "completion";
		}
		map.put("examType", questionType);
		map.put("examScoreTotal", question.getFdStandardScore());
		map.put("examScore", question.getFdStandardScore());
		map.put("examStem", question.getFdSubject());
		List<ExamOpinion> examOpinions = question.getOpinions();
		List<Map> examOpinionsList = new ArrayList<Map>();
		for (ExamOpinion examOpinion : examOpinions) {
			Map map1 = new HashMap();
			map1.put("id", examOpinion.getFdId());
			map1.put("index", examOpinion.getFdOrder());
			map1.put("name", examOpinion.getOpinion());
			map1.put("isAnswer", examOpinion.getIsAnswer());
			examOpinionsList.add(map1);
		}
		map.put("listExamAnswer", examOpinionsList);
		List<AttMain> atts = attMainService.findByCriteria(AttMain.class,
				Value.eq("fdModelId", question.getFdId()),
				Value.eq("fdModelName", ExamQuestion.class.getName()));
		List<Map> attList = new ArrayList<Map>();
		for (AttMain attMain : atts) {
			Map map2 = new HashMap();
			map2.put("id", attMain.getFdId());
			map2.put("index", attMain.getFdOrder());
			map2.put("name", attMain.getFdFileName());
			if(attMain.getFileNetId()!=null){
				map2.put("url", attMain.getFdId());
			 }else{
				 map2.put("url", null);
			 }
			attList.add(map2);
		}
		map.put("listAttachment",  attList);
		return JsonUtils.writeObjectToJson(map);
	}
	
	@RequestMapping(value = "UpdateExamQuestionAndMaterial")
	@ResponseBody
	public void UpdateExamQuestionAndMaterial(HttpServletRequest request){
		//获取数据
		String id = request.getParameter("id");
		String examPaperName = request.getParameter("examPaperName");
		String examPaperIntro = request.getParameter("examPaperIntro");
		String author = request.getParameter("author");
		String authorIntro = request.getParameter("authorIntro");
		String permission = request.getParameter("permission");
		String listExam = request.getParameter("listExam");
		String kingUser = request.getParameter("kingUser");
		String score = request.getParameter("score");
		String studyTime = request.getParameter("studyTime");
		List<Map> exams;
		MaterialInfo info = materialService.findUniqueByProperty("fdId", id);
		if(StringUtil.isBlank(listExam)){
			exams = new ArrayList<Map>();
		}else{
			exams = JsonUtils.readObjectByJson(listExam, List.class);
		}
		List<Map> users;
		if(StringUtil.isBlank(kingUser)){
			users = new ArrayList<Map>();
		}else{
			users = JsonUtils.readObjectByJson(kingUser, List.class);
		}
		//更新试题分数
		List<ExamQuestion> examQuestions = new ArrayList<ExamQuestion>();
		for (Map map : exams) {
			ExamQuestion q =examQuestionService.findUniqueByProperty("fdId",map.get("id").toString());
			q.setFdStandardScore(new Double(map.get("editingCourse").toString()));
			q.setFdOrder(new Integer(map.get("index").toString()));
			examQuestionService.save(q);
			examQuestions.add(q);
		}
		//删除多余的试题
		List<ExamQuestion> oldExamQuestions = examQuestionService.findByProperty("exam.fdId", info.getFdId());
		for (ExamQuestion examQuestion : oldExamQuestions) {
			if(!examQuestions.contains(examQuestion)){
				examQuestionService.deleteQuestion(examQuestion.getFdId());
			}
		}
		List<MaterialAuth> materialAuths = new ArrayList<MaterialAuth>();
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
		//跟新权限
		materialService.updateMaterialAuth(id, materialAuths);
		//更改测试信息
		info.setFdName(examPaperName);
		info.setFdDescription(examPaperIntro);
		info.setFdAuthor(author);
		info.setFdAuthorDescription(authorIntro);
		info.setFdScore(new Double(score));
		info.setIsDownload(true);
		info.setIsAvailable(true);
		info.setIsPublish(permission.equals("open")?true:false);
		info.setFdStudyTime(new Integer(studyTime));
		info.setQuestions(examQuestions);
		materialService.save(info);
	}
	
	@RequestMapping(value = "deleExamsByMaterialId")
	@ResponseBody
	public void deleExamsByMaterialId(HttpServletRequest request) {
		String materIalId = request.getParameter("materialId");
		MaterialInfo info = materialService.findUniqueByProperty("fdId", materIalId);
		List<ExamQuestion> examQuestions = info.getQuestions();
		for (ExamQuestion examQuestion : examQuestions) {
			examQuestionService.deleteQuestion(examQuestion.getFdId());
		}
		
	}
	/**
	 * 删除测试中的某个试题
	 * @param request
	 */
	@RequestMapping(value = "deleQuestionToExam")
	@ResponseBody
	public void deleQuestionToExam(HttpServletRequest request){
		String questionId = request.getParameter("questionId");
		String examId = request.getParameter("examId");		
		ExamQuestion delQuestion = examQuestionService.get(questionId);
		MaterialInfo info = materialService.get(examId);
		List<ExamQuestion> examQuestions = info.getQuestions();
		examQuestions.remove(delQuestion);
		info.setQuestions(examQuestions);
		materialService.save(info);
		examQuestionService.deleteQuestion(questionId);
	}
	
	/**
	 *  更新选项附件
	 * @param attString
	 * @param examQuestionId
	 */
	private void updateAttMain(String attString,String examQuestionId){
		//删除之前的选项
		List<AttMain> oldAttMains =attMainService.getAttMainsByModelIdAndModelName(examQuestionId, ExamQuestion.class.getName());
		for (AttMain attMain : oldAttMains) {
			attMainService.deleteAttMain(attMain.getFdId());
		}
		// 更新选项附件
		if (StringUtil.isNotBlank(attString)) {
			List<Map> att = JsonUtils.readObjectByJson(attString, List.class);
			for (Map map : att) {
				AttMain e = attMainService.get(map.get("id").toString());
				e.setFdModelId(examQuestionId);
				e.setFdModelName(ExamQuestion.class.getName());
				e.setFdOrder(map.get("index").toString());
				e.setFdKey("ExamQuestion");
				attMainService.save(e);
			}
		}
	}
	/**
	 * 统计试题的总分
	 */
	private double getExamTotal(MaterialInfo materialInfo){
		List<Map> examQuestions=materialService.getExamQuestionByMaterId(materialInfo);
		if(examQuestions!=null&&examQuestions.size()>0){
			double count=0;
			for(Map map:examQuestions){
				count+=Double.parseDouble(map.get("score").toString());
			}
			return count;
		}
		return 0;
	}
}

package cn.me.xdf.service.bam.source.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.bam.BamCourse;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.material.ExamOpinion;
import cn.me.xdf.model.material.ExamQuestion;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.material.Task;
import cn.me.xdf.model.process.AnswerRecord;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.SimpleService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.bam.process.SourceNodeService;
import cn.me.xdf.service.bam.source.ISourceService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.material.ExamQuestionService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.utils.ShiroUtils;



/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
@Service("materialQuestionsService")
public class MaterialQuestionsService extends SimpleService implements ISourceService {
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private ExamQuestionService examQuestionService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
    @Override
    public Object findSourceByMaterials(BamCourse bamCourse, CourseCatalog catalog) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object saveSourceNode(WebRequest request) {
    	String fdMtype = request.getParameter("fdMtype");
		String catalogId = request.getParameter("catalogId");
		String bamId = request.getParameter("bamId");
		String materialFdid = request.getParameter("fdid");
		String[] anwers = request.getParameterValues("examAnswer");
		int time=0;
		long starTime =new Long( request.getParameter("startTime"));
		Date endTime= new Date();
		time = (int) ((endTime.getTime()-starTime)/(60*1000));
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		//试题对应选项信息
		Map<String,String> anwer = new HashMap<String,String>();
		if(anwers!=null){
			for (String an : anwers) {
				String[] anwerSS= an.split(":");
				String anwerSS1="";
				String anwerSS2="";
				if(anwerSS.length==2){
					anwerSS1=anwerSS[0];
					anwerSS2=anwerSS[1];
				}
				if(anwerSS.length==1){
					anwerSS1=anwerSS[0];
					anwerSS2="";
				}
				if(!anwer.containsKey(anwerSS[0])){
					anwer.put(anwerSS1, anwerSS2);
				}else{
					anwer.put(anwerSS1, anwer.get(anwerSS1)+"#"+anwerSS2);
				};
			}
		}
		List<ExamQuestion> examQuestions = examQuestionService.findByProperty("exam.fdId", materialFdid);
		double sorce=0;
		for (ExamQuestion examQuestion : examQuestions) {
			String answer=anwer.get(examQuestion.getFdId());
			String rightAnswer = examQuestion.getFdQuestion();
			if((answer+"#").equals(rightAnswer)){
				sorce =  sorce+examQuestion.getFdStandardScore();
			}
		}
		MaterialInfo info = materialService.get(materialFdid);
		double passSorce  = info.getFdScore();
		SourceNote sourceNode =new SourceNote();
		sourceNode.setFdCourseId(bamCourse.getCourseId());
		sourceNode.setFdCatalogId(catalogId);
		sourceNode.setFdUserId(ShiroUtils.getUser().getId());
		sourceNode.setIsStudy(sorce>=passSorce);
		sourceNode.setFdOperationDate(new Date());
		sourceNode.setFdScore(sorce);
		sourceNode.setFdExamTime(time);
		sourceNode.setFdMaterialId(info.getFdId());
		Set<AnswerRecord> answerRecords = new HashSet<AnswerRecord>();
		for (ExamQuestion question : examQuestions) {
			AnswerRecord answerRecord = new AnswerRecord();
			answerRecord.setFdQuestionId(question.getFdId());
			answerRecord.setFdAnswer(anwer.get(question.getFdId()));
			answerRecords.add(answerRecord);
		}
		sourceNode.setAnswerRecords(answerRecords);
		return sourceNodeService.saveSourceNode(sourceNode);

    }

	@Override
	public Object findSubInfoByMaterial(WebRequest request) {
		List<Map> listExam = new ArrayList<Map>();
		String catalogId = request.getParameter("catalogId");
		String materialId =request.getParameter("materialId");
		MaterialInfo materialInfo = materialService.get(materialId); 
		if(!materialInfo.getIsAvailable()){
			return listExam;
		}
		SourceNote sourceNote = sourceNodeService.getSourceNote(materialId, catalogId, ShiroUtils.getUser().getId());
		List<ExamQuestion> examQuestions = materialInfo.getQuestions();
		for (ExamQuestion examQuestion2 : examQuestions) {
			Map map2 = new HashMap();
			map2.put("id", examQuestion2.getFdId());
			map2.put("index", examQuestion2.getFdOrder());
			if(sourceNote==null){
				map2.put("status", "null");
			}else{
				Set<AnswerRecord> answerRecords = sourceNote.getAnswerRecords();
				for (AnswerRecord answerRecord : answerRecords) {
					if(answerRecord.getFdQuestionId().equals(examQuestion2.getFdId())){
						map2.put("status", (answerRecord.getFdAnswer()+"#").equals(examQuestion2.getFdQuestion())?"success":"error");
						break;
					}
				}
			}
			
			map2.put("examScore", examQuestion2.getFdStandardScore());
			map2.put("examType", examQuestion2.getFdType().equals(Constant.EXAM_QUESTION_SINGLE_SELECTION)?"single":(examQuestion2.getFdType().equals(Constant.EXAM_QUESTION_MULTIPLE_SELECTION)?"multiple":"completion"));
			if(examQuestion2.getFdType().equals(Constant.EXAM_QUESTION_CLOZE)){
				String answer = examQuestion2.getFdQuestion();
				String [] answers = new String[answer.split("#").length];
				for (int i=0;i<answers.length;i++) {
					answers[i]="";
				}
				if(sourceNote!=null){
					Set<AnswerRecord> answerRecords = sourceNote.getAnswerRecords();
					for (AnswerRecord answerRecord : answerRecords) {
						if(answerRecord.getFdQuestionId().equals(examQuestion2.getFdId())){
							if(answerRecord.getFdAnswer()!=null){
								for (int i = 0; i < answers.length; i++) {
									String [] an = answerRecord.getFdAnswer().split("#");
									if(i<an.length){
										answers[i]=an[i];
									}
								}
								break;
							}
						}
					}
				}
				
				String subject = examQuestion2.getFdSubject();
				String [] subjects = subject.split("#");
				String res = "";
				 for (int i = 0,j=1; i < subjects.length; i++) {
					 String html = "<span class='line'><label for='examAnswer"+j+"' class='icon-circle-bg' >"+j+"</label></span>";
					if (i % 2 ==0) {
						res = res + subjects[i];
					}else{
						j++;
						res = res + html;
					}
				 }
				 map2.put("listExamAnswer", answers);
				 map2.put("examStem", res);
			}else{
				map2.put("examStem", examQuestion2.getFdSubject());
				List<Map> opinionlist = new ArrayList<Map>();
				List<ExamOpinion> examOpinions = examQuestion2.getOpinions();
				for (ExamOpinion examOpinion : examOpinions) {
					Map opinionMap = new HashMap();
					opinionMap.put("id", examOpinion.getFdId());
					opinionMap.put("index", examOpinion.getFdOrder());
					opinionMap.put("name", examOpinion.getOpinion());
					opinionMap.put("isAnswer", examOpinion.getIsAnswer());
					if(sourceNote==null){
						opinionMap.put("isChecked", false);
					}else{
						Set<AnswerRecord> answerRecords = sourceNote.getAnswerRecords();
						for (AnswerRecord answerRecord : answerRecords) {
							if(answerRecord.getFdQuestionId().equals(examQuestion2.getFdId())){
								if(answerRecord.getFdAnswer()==null){
									opinionMap.put("isChecked", false);
								}else{
									opinionMap.put("isChecked", answerRecord.getFdAnswer().contains(examOpinion.getFdId()));
								}
								break;
							}
						}
					}
					opinionlist.add(opinionMap);
				}
				map2.put("listExamAnswer", opinionlist);
			};
			
			
			
			List<AttMain> taskAtt = attMainService.
					getByModeslIdAndModelNameAndKey(examQuestion2.getFdId(),ExamQuestion.class.getName(),"ExamQuestion");
			
			map2.put("listAttachment", taskAtt);
			listExam.add(map2);
		}
		return listExam;
	}

	@Override
	public Object findMaterialDetailInfo(BamCourse bamCourse, CourseCatalog catalog, String fdid) {
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
		}
		//素材中的试题
		List<ExamQuestion> examQuestions = materialInfo.getQuestions();
		//学习记录中的最近一次答题记录
		Set<AnswerRecord> answerRecords = sourceNote.getAnswerRecords();
		double score = 0;
		for (ExamQuestion examQuestion2 : examQuestions) {
				for (AnswerRecord answerRecord : answerRecords) {
					if(examQuestion2.getFdId().equals(answerRecord.getFdQuestionId())
						&& examQuestion2.getFdQuestion().equals(answerRecord.getFdAnswer()+"#")){
						score += examQuestion2.getFdStandardScore();
					}
				}
			
		}
		if(score>=materialInfo.getFdScore()){
			return true;
		}
		return false;
	}
	
}

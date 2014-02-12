package cn.me.xdf.service.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.material.ExamOpinion;
import cn.me.xdf.model.material.ExamQuestion;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.service.base.AttMainService;

/**
 * 
 * 试题实体service
 * 
 * @author
 * 
 */
@Service
@Transactional(readOnly = true)
public class ExamQuestionService extends BaseService{

	@Autowired
	private ExamOpinionService examOpinionService;
	
	@Autowired
	private AttMainService attMainService;
	
	@SuppressWarnings("unchecked")
	@Override
	public  Class<ExamQuestion> getEntityClass() {
		return ExamQuestion.class;
	}
	
	public void deleteQuestion(String questionId){
		//删除选项
		List<ExamOpinion> examOpinions = examOpinionService.findByProperty("question.fdId", questionId);
		for (ExamOpinion examOpinion : examOpinions) {
			examOpinionService.delete(examOpinion.getFdId());
		}
		//删除试题附件
		List<AttMain> attMains = attMainService.findByCriteria(AttMain.class,
                Value.eq("fdModelId", questionId),
                Value.eq("fdModelName", ExamQuestion.class.getName()));
		for (AttMain attMain : attMains) {
			attMainService.deleteAttMain(attMain.getFdId());
		}
		//删除试题
		delete(questionId);
	}

}

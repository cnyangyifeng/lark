package com.kuxue.service.material;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.model.material.ExamOpinion;
import com.kuxue.service.BaseService;

/**
 * 
 * 试题选项实体service
 * 
 * @author
 * 
 */
@Service
@Transactional(readOnly = false)
public class ExamOpinionService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<ExamOpinion> getEntityClass() {
		return ExamOpinion.class;
	}
	
	public void deleteOpinionsByQuestionId(String questionId){
		List<ExamOpinion> oldPoinions = findByProperty("question.fdId", questionId);
		for (ExamOpinion examOpinion : oldPoinions) {
			delete(examOpinion.getFdId());
		}
	}

}

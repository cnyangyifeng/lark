package com.kuxue.service.bam.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.model.process.AnswerRecord;
import com.kuxue.service.BaseService;

@Service
@Transactional(readOnly = false)
public class AnswerRecordService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public Class<AnswerRecord> getEntityClass() {
		return AnswerRecord.class;
	}
}

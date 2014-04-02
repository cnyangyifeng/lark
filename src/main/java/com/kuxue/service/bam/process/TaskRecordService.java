package com.kuxue.service.bam.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.model.process.TaskRecord;
import com.kuxue.service.BaseService;

@Service
@Transactional(readOnly = false)
public class TaskRecordService extends BaseService{
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<TaskRecord> getEntityClass() {
		return TaskRecord.class;
	}

}

package cn.me.xdf.service.bam.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.model.process.AnswerRecord;
import cn.me.xdf.service.BaseService;

@Service
@Transactional(readOnly = false)
public class AnswerRecordService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public Class<AnswerRecord> getEntityClass() {
		return AnswerRecord.class;
	}
}

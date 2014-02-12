package cn.me.xdf.quartz;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.me.xdf.service.letter.PrivateLetterService;

public class LetterClearQuartz implements Serializable{
	
	private static final Logger log= LoggerFactory.getLogger(LetterClearQuartz.class);
	
	@Autowired
	private PrivateLetterService privateLetterService;
	
	public void executeTodo(){
		log.info("开始执行定时任务：---删除无用PrivateLetter");
		StringBuilder sql = new StringBuilder();
		sql.append(" delete ixdf_ntp_letter t where not exists ");
		sql.append("(select m.privateletterid from IXDF_NTP_LETTER_CONNECT m where m.privateletterid=t.fdId)");
		privateLetterService.executeSql(sql.toString());
		log.info("结束执行定时任务：---删除无用PrivateLetter");
	}

}

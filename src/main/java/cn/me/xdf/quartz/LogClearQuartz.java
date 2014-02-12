package cn.me.xdf.quartz;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.me.xdf.service.log.LogAppService;
import cn.me.xdf.service.log.LogLoginService;
import cn.me.xdf.service.log.LogLogoutService;

public class LogClearQuartz implements Serializable {

	private static final Logger log = LoggerFactory
			.getLogger(LogClearQuartz.class);

	@Autowired
	private LogAppService logAppService;

	@Autowired
	private LogLogoutService logLogoutService;

	@Autowired
	private LogLoginService logLoginService;

	public void executeTodo() {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, -3);
		Date dd = ca.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dd);
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH)+1;// 获取月份
		log.info("开始执行定时任务：---删除3个月前的日志");
		//删除操作日志
		StringBuilder sql1 = new StringBuilder();
		sql1.append(" delete IXDF_NTP_LOGAPP log where log.time<to_date('" + year + "-" + month + "-1 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
		logAppService.executeSql(sql1.toString());
		//删除登录日志
		StringBuilder sql2 = new StringBuilder();
		sql2.append(" delete IXDF_NTP_LOGLOGIN log where log.time<to_date('" + year + "-" + month + "-1 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
		logLoginService.executeSql(sql2.toString());
		//删除登出日志
		StringBuilder sql3 = new StringBuilder();
		sql3.append(" delete IXDF_NTP_LOGLOGOUT log where log.time<to_date('" + year + "-" + month + "-1 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
		logLogoutService.executeSql(sql3.toString());
		log.info("结束执行定时任务：---删除3个月前的日志");
	}

	public static void main(String[] args) {
		
	}
}

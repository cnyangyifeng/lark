package cn.me.xdf.common.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间默认格式：yyyy-MM-dd HH:mm:ss
 * 
 * @author 斌
 * 
 */
public class TimeUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static Timestamp covertDate(Date date) {
		String sdate = sdf.format(date);
		return Timestamp.valueOf(sdate);
	}

	public static String covertDateStr(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static String covertTimeStamp(Timestamp time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(time);
	}

	/*
	 * 计算两个日期之间间隔的小时数，保留一位小数
	 */
	public static Float getBetweenHours(Timestamp start, Timestamp end) {
		DecimalFormat df = new DecimalFormat("#.##");
		float hours = 0;
		long time = 0;

		time = end.getTime() - start.getTime();
		hours = time / 3600000;

		return Float.valueOf(df.format(hours));
	}
}

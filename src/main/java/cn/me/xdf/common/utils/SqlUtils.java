package cn.me.xdf.common.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

	public static String parseSqlRoundIn(List<String> values) {
		if (values == null || values.isEmpty())
			return null;
		StringBuilder sql = new StringBuilder();
		for (String value : values) {
			sql.append("'").append(value).append("',");
		}

		return StringUtils.removeEnd(sql.toString(), ",");
	}

	public static String parseSqlRoundIn(String[] values) {
		if (values == null)
			return null;
		StringBuilder sql = new StringBuilder();
		for (String value : values) {
			sql.append("'").append(value).append("',");
		}

		return StringUtils.removeEnd(sql.toString(), ",");
	}

}

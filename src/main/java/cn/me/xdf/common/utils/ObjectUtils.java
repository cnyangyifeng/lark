package cn.me.xdf.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ObjectUtils {

	/**
	 * 比较对象的大小
	 * 
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static int compare(Object arg1, Object arg2) {
		if (arg1 == null || arg2 == null) {
			return 0;
		}
		if (!arg1.getClass().equals(arg2.getClass()))
			throw new RuntimeException("arg1 not equals arg2");
		if (arg1 instanceof String) {
			return arg1.toString().compareTo(arg2.toString());
		} else if (arg1 instanceof Integer) {
			return NumberUtils.toInt(arg1.toString())
					- NumberUtils.toInt(arg2.toString());
		}
		throw new RuntimeException("不支持的类型,只支持String和Integer类型");
	}

}

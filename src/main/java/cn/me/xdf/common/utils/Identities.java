package cn.me.xdf.common.utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author xiaobin
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	/**
	 * 生成主键（32位）
	 * 
	 * @return
	 */
	public static String generateID() {
		return generateID(System.currentTimeMillis());
	}

	/**
	 * 根据指定时间生成主键，该方法只能用来对比主键生成时间，切忌不能用来生成主键插入数据库
	 * 
	 * 作者：曹映辉 日期：2011-6-16
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static String generateID(Date date) {
		return generateID(date.getTime());
	}

	/**
	 * 根据指定时间生成主键
	 * 
	 * @param time
	 * @return
	 */
	public static String generateID(long time) {
		String rtnVal = Long.toHexString(time);
		rtnVal += UUID.randomUUID();
		rtnVal = rtnVal.replaceAll("-", "");
		return rtnVal.substring(0, 32);
	}

	/**
	 * 查看主键生成时间
	 * 
	 * @param id
	 */
	protected static void printIDTime(String id) {
		String timeInfo = id.substring(0, 11);
		System.out.println(new Date(Long.parseLong(timeInfo, 16)));
	}

	/**
	 * 根据ID获取该ID创建的时间
	 * 
	 * @param id
	 * @return
	 */
	public static Date getIDCreateTime(String id) {
		String timeInfo = id.substring(0, 11);
		return new Date(Long.parseLong(timeInfo, 16));
	}

	//测试生成主键
	public static void main(String[] args) {
		System.out.println(generateID(System.currentTimeMillis()));
	}
}
package cn.me.xdf.utils;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import cn.me.xdf.common.utils.RegexUtils;
import cn.me.xdf.sso.e2.util.AESX3;

public class ComUtils {

	public static String HTTP_URL = "http://ntp.xdf.cn/otp";

	/**
	 * 根据新东方的email邮件获取登录名
	 * 
	 * @param email
	 * @return
	 */
	public static String getLoginNameByEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return email;
		}
		if (RegexUtils.checkEmail(email, 100)) {
			return email.substring(0, email.lastIndexOf('@'));
		}
		return email;
	}

	/**
	 * 获取人员的默认头像
	 * 
	 * @return
	 */
	public static String getDefaultPoto() {
		return "resources/images/default-face-180.png";
	}

	public static String getFileUrl(String fdId, String fileName) {
		return "http://me.xdf.cn/iportal/sys/attachment/sys_att_swf/viewer.do?method=viewer&fdId="
				+ fdId + "&seq=0&type=iwork&fileName=" + fileName;
	}

	// 调用得到人员头像URL
	public static String getURLByLoginName(String loginName) {
		final String _password = "123.com!@#!@#";
		// 一共3种_original, _129129, _9494
		final String _type = "_9494";
		String _key = loginName + _password;
		String _enKey = AESX3.md5(_key);

		StringBuffer _url = new StringBuffer

		("http://img.staff.xdf.cn/Photo/");

		if (StringUtils.isBlank(_enKey))
			return null;

		String _hash12 = _enKey.substring(_enKey.length() - 2,

		_enKey.length());
		String _hash34 = _enKey.substring(_enKey.length() - 4,
				_enKey.length() - 2);

		_url.append(_hash12 + "/");
		_url.append(_hash34 + "/");
		_url.append(_enKey.toLowerCase());
		_url.append(_type);
		_url.append(".jpg");

		return _url.toString();
	}

	/**
	 * 功能描述 : 检测当前URL是否可连接或是否有效
	 * 
	 * @param url
	 *            指定URL网络地址
	 * 
	 * @return boolean
	 */
	public static synchronized boolean isConnect(String url) {
		URL urlStr;
		HttpURLConnection connection;
		if (url == null || url.length() <= 0) {
			return false;
		}
		try {
			urlStr = new URL(url);
			connection = (HttpURLConnection) urlStr.openConnection();
			int state = connection.getResponseCode();
			if (state == 200) {
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		String loginName = getLoginNameByEmail("yyf262300@BJ@xdf.cn");
		System.out.println("loginName===" + loginName);
	}

}

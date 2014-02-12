package cn.me.xdf.sso.e2.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 跟cookie相关的帮助类
 * 
 * @author 苏轶 2010-3-15
 */
public class CookieHelper {
	/**
	 * 查找指定名称的cookie值，如果没有找到则返回null
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {
		// 解决Cookie值等于号取不到的问题。
		String cookieHeader = request.getHeader("Cookie");
		if (StringUtils.isNotBlank(cookieHeader)) {
			String[] cookies = cookieHeader.split(";");
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					String cookie = cookies[i].trim();
					if (cookie.substring(0, cookie.indexOf("="))
							.equalsIgnoreCase(cookieName)) {
						return cookie.substring(cookie.indexOf("=") + 1,
								cookie.length());
					}
				}
			}
		}
		return null;
	}

	/**
	 * 清空cookie
	 */
	public static void clearCookie(HttpServletRequest request,
			HttpServletResponse response, String token) {
		Cookie[] cookies = request.getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(token)) {
				}
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		} catch (Exception ex) {
			System.out.println("清空Cookies发生异常！");
		}
	}
}

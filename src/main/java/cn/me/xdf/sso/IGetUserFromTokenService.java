package cn.me.xdf.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpMethod;

import cn.me.xdf.sso.e2.vo.UserModel;

public interface IGetUserFromTokenService {

	/**
	 * 根据cookie获取当前用户
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public UserModel getCurrentUser(HttpServletRequest request,
			HttpServletResponse response, String cookieValue) throws Exception;
	
	
	public HttpMethod getPostMethod(String cookieValue) throws Exception;

}

package cn.me.xdf.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 人员登出
 * 
 * <pre>
 * Spring Bean的ID为：ssoLogoutService
 * </pre>
 * 
 * @author xiaobin
 * 
 */
public interface ILogoutService {

	/**
	 * e2 后台退出接口声明
	 * 
	 * @throws Exception
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}

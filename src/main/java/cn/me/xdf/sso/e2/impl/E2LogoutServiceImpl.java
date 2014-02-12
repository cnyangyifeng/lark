package cn.me.xdf.sso.e2.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.me.xdf.sso.ILogoutService;
import cn.me.xdf.sso.e2.constant.SSOToE2Constant;

/**
 * E2登出Service
 * 
 * @author xiaobin
 * 
 */
public class E2LogoutServiceImpl implements ILogoutService {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rtnUrl = "";
		response.sendRedirect(ssoToE2Constant.getE2RootUrl()
				+ "Logout.aspx?AppId=" + ssoToE2Constant.getAppId()
				+ "&ReturnUrl=" + rtnUrl);
	}

	private SSOToE2Constant ssoToE2Constant;

	public SSOToE2Constant getSsoToE2Constant() {
		return ssoToE2Constant;
	}

	public void setSsoToE2Constant(SSOToE2Constant ssoToE2Constant) {
		this.ssoToE2Constant = ssoToE2Constant;
	}

}

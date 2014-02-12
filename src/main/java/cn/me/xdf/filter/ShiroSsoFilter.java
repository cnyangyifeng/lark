package cn.me.xdf.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import cn.me.xdf.sso.IGetUserFromTokenService;
import cn.me.xdf.sso.e2.constant.SSOToE2Constant;
import cn.me.xdf.sso.e2.util.CookieHelper;
import cn.me.xdf.sso.e2.vo.UserModel;
import cn.me.xdf.sso.model.TrustedSsoAuthenticationToken;

public class ShiroSsoFilter extends BasicHttpAuthenticationFilter {

	@Autowired
	private IGetUserFromTokenService ssoGetUserFromTokenService;

	@Autowired
	private SSOToE2Constant ssoToE2Constant;

	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("username===="+req.getParameter("username"));
		AuthenticationToken token = null;
		String cookieValue = CookieHelper.getCookie(req,
				ssoToE2Constant.getCookieName());
		UserModel userModel = null;
		try {
			userModel = ssoGetUserFromTokenService.getCurrentUser(req, res,
					cookieValue);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (userModel == null) {
			// return null;
			token = new TrustedSsoAuthenticationToken("");
		} else {
			token = new TrustedSsoAuthenticationToken(userModel.getLoginName());
		}
		return token;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		boolean loggedIn = false;
		loggedIn = executeLogin(request, response);
		if (!loggedIn) {
			saveRequestAndRedirectToLogin(request, response);
		}
		return loggedIn;
	}

	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			/*String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);*/
			return false;
		}
		try {
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}
}
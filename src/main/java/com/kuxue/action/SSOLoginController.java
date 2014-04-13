package com.kuxue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.sso.IGetUserFromTokenService;
import com.kuxue.sso.e2.util.CookieHelper;
import com.kuxue.sso.e2.vo.UserModel;
import com.kuxue.sso.model.ssoToken;

@Controller
@Scope("request")
public class SSOLoginController {
	
	private static final Logger log = LoggerFactory
			.getLogger(SSOLoginController.class);
	
	@Autowired
	private IGetUserFromTokenService ssoGetUserFromTokenService;
	
	@Autowired
	private AccountService accountService;
	/**
     * 登录处理接口
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String doLogin(HttpServletRequest request,HttpServletResponse response){
    	String cookieValue = CookieHelper.getCookie(request,ssoGetUserFromTokenService.getSsoToE2Constant().getCookieName());
    	if(StringUtil.isEmpty(cookieValue)){
    		return "redirect:/login";
    	}
    	try{
    	UserModel userModel = ssoGetUserFromTokenService.getCurrentUser(request, response, cookieValue);
    	String userName = userModel.getLoginName();
    	AuthenticationToken token =  new ssoToken(userName,"1");
    	Subject currentUser =SecurityUtils.getSubject();
    	
    		  currentUser.login(token);
    		  return "redirect:/successPage";
    	} catch (Exception e){
    		log.error("ssoLogin fail."+e.getMessage());
    		return "redirect:/login";
    	}
        
    }
    
    

}

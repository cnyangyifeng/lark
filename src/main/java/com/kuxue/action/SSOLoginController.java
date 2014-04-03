package com.kuxue.action;

import javax.servlet.http.HttpServletRequest;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Scope("request")
public class SSOLoginController {
	 
	/**
     * 登录处理接口
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String doLogin(HttpServletRequest request){
    	String userName = request.getParameter("userName");
    	String password = request.getParameter("password");
    	UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
    	Subject currentUser =SecurityUtils.getSubject();
    	try{
    		  currentUser.login(token);
    		  return "redirect:/successPage";
    	} catch (Exception e){
    		return "redirect:/login";
    	}
        
    }
    
    

}

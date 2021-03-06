package com.kuxue.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.UserRoleService;
import com.kuxue.utils.ShiroUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
//	@Autowired
//	private NotifyService notifyService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = "/successPage", method = RequestMethod.GET)
	public String successPage(Model model) {
		model.addAttribute("userId", ShiroUtils.getUser().getId());
		return "redirect:/course/courseIndex";

	}
	/**
	 * 获得忘记密码的页面
	 * @return
	 */
	@RequestMapping(value = "/forgotPwd")
	public String forgotPwd() {
		return "/forgot";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getUrl (Model model){
		if(ShiroUtils.getUser() != null){
			String uid = ShiroUtils.getUser().getId();
			SysOrgPerson person = accountService.load(uid);
			String fdIcoUrl = person.getPoto();
			model.addAttribute("fdIcoUrl",fdIcoUrl);
			
		}
		return "/index";
	}
}

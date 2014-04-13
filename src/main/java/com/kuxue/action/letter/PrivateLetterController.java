package com.kuxue.action.letter;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.letter.ConnectLetterService;
import com.kuxue.service.letter.PrivateLetterService;
import com.kuxue.service.letter.RelationLetterService;
import com.kuxue.utils.ShiroUtils;

/**
 * 私信的controller
 * @author yuhuizhe
 */
@Controller
@RequestMapping(value = "/letter")
@Scope("request")
public class PrivateLetterController {
	
	@Autowired
	private PrivateLetterService privateLetterService;
	
	@Autowired
	private RelationLetterService relationLetterService;
	
	@Autowired
	private ConnectLetterService connectLetterService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 返回发送私信页面
	 * @return
	 */
	@RequestMapping(value="sendLetter")
	public String sendLetter(Model model){
		model.addAttribute("active", "sendLetter");
		model.addAttribute("sendUserId", ShiroUtils.getUser().getId());
		return "letter/letter_add";
	}
	/**
	 * 返回发送邮件页面
	 * @return
	 */
	@RequestMapping(value="sendEmail")
	public String sendEmail(Model model){
		model.addAttribute("active", "sendEmail");
		return "letter/sendEmail";
	}
	/**
	 * 返回与某个人的对话
	 * @return
	 */
	@RequestMapping(value="letterDetail")
	public String letterDetail(Model model,HttpServletRequest request){
		model.addAttribute("active", "letterList");
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNotBlank(fdId)){
			SysOrgPerson person = accountService.get(fdId);
			model.addAttribute("person", person);
			String org = person.getHbmParentOrg()!=null?person.getHbmParentOrg().getFdName():"";
			String deptName = person.getDeptName();
			String personLength = person.getFdEmail()+org+person.getDeptName();
			if(personLength.length()>40){
				if(org.length()>10){
					org = org.substring(0, 9)+"...";
				}
				if(deptName.length()>10){
					deptName = deptName.substring(0, 9)+"...";
				}
			}
			model.addAttribute("deptName", deptName);
			model.addAttribute("org", org);
		}
		return "letter/letter_detail";
	}
	/**
	 * 找出我的私信列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="findLetterList")
	public String findLetterList(Model model){
		model.addAttribute("active", "letterList");
		return "/letter/letter_list";
	}
	
	
	
}






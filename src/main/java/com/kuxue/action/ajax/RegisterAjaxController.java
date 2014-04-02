package com.kuxue.action.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuxue.common.json.JsonUtils;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.organization.SysOrgDepart;
import com.kuxue.model.organization.SysOrgElement;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.RegisterService;
import com.kuxue.service.SysOrgDepartService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.utils.MD5Util;
import com.kuxue.utils.ShiroUtils;

/**
 * 注册验证
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/register")
@Scope("request")
public class RegisterAjaxController {
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private SysOrgDepartService sysOrgDepartService;
	
	@Autowired
    private AttMainService attMainService;

	@RequestMapping(value = "checkIdentityCard")
	@ResponseBody
	public int checkIdentityCard(HttpServletRequest request) {
		String str = request.getParameter("str");

		int count = registerService.checkIdentityCard(str);

		return count;
	}
	
	@RequestMapping(value = "checkIdentitymail")
	@ResponseBody
	public int checkIdentitymail(HttpServletRequest request) {
		String str = request.getParameter("str");
		int count = registerService.checkIdentitymail(str);
		return count;
	}

	@RequestMapping(value = "getDeparts")
	@ResponseBody
	public String getDeparts(HttpServletRequest request) {
		String str = request.getParameter("id");
		String info = "";
		List<SysOrgDepart> list1 = registerService.getDepartsByParent(str);
		for (SysOrgDepart SysOrgDepart : list1) {
			String id = SysOrgDepart.getFdId();
			String name = SysOrgDepart.getFdName();
			info = info + id + ":" + name + "=";

		}
		return info;

	}

	@RequestMapping(value = "register")
	@ResponseBody
	public String register(HttpServletRequest request) {
		try {
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String cradid = request.getParameter("cradid");
			String departid = request.getParameter("departid");
			String deptName = request.getParameter("deptName");
			String tel = request.getParameter("tel");
			String img = request.getParameter("img");
			String sex = request.getParameter("sex");
			String birthday = request.getParameter("birthday");
			String bloodend = request.getParameter("bloodend");
			SysOrgPerson orgPerson = new SysOrgPerson();
			//orgPerson.setPassword(password);
			orgPerson.setPassword(MD5Util.getMD5String(password));
			orgPerson.setFdEmail(email);
			orgPerson.setLoginName(email);
			orgPerson.setFdName(name);
			orgPerson.setFdIdentityCard(cradid);
			SysOrgElement element = sysOrgDepartService.getSysOrgElementById(departid);
			orgPerson.setHbmParent(element);
			orgPerson.setFdWorkPhone(tel);
			orgPerson.setFdSex(sex);
			orgPerson.setFdBirthDay(birthday);
			orgPerson.setFdBloodType(bloodend);
			orgPerson.setAvailable(true);
			orgPerson.setFdPhotoUrl(img);
			orgPerson.setFdIsEmp("0");
			registerService.registerPerson(orgPerson);
			String attMainId=request.getParameter("attId");
			if(StringUtil.isNotBlank(attMainId)){
				AttMain attMain = attMainService.get(attMainId);
		    	attMain.setFdModelId(orgPerson.getFdId());
		    	attMain.setFdModelName(SysOrgPerson.class.getName());
		    	attMain.setFdKey("Person");
		    	attMainService.save(attMain);
			}
			if (ShiroUtils.getUser() == null) {
				return "redirect:/login";
			}
			return "redirect:/register/list";
		} catch (Exception e) {
			e.printStackTrace();
			if (ShiroUtils.getUser() == null) {
				return "redirect:/loginerr";
			}
			return "redirect:/register/listerr";
			
		}
		

	}
	/**
	 * 修改密码时验证原密码是否正确
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkOldPwd")
	@ResponseBody
	public String checkOldPwd(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		String oldPwd = request.getParameter("str");
		String fdId = request.getParameter("fdId");
		SysOrgPerson person = accountService.load(fdId);
        String userPwd = person.getPassword();
        if(userPwd.equals(MD5Util.getMD5String(oldPwd))){
        	map.put("flag", "1");//代表是正确
        } else {
        	map.put("flag", "0");//代表是不正确
        }
        return JsonUtils.writeObjectToJson(map);
	}
	
	/**
     * 修改密码的方法
     *
     * @param sysOrgPersonTemp
     * @param request
     * @return
     */
    @RequestMapping(value = "updateTeacherPwd")
    @ResponseBody
    public void updateTeacherPwd(HttpServletRequest request) {
        String fdId = ShiroUtils.getUser().getId();
        String password = request.getParameter("password");
        SysOrgPerson person = accountService.get(fdId);
        person.setPassword(MD5Util.getMD5String(password));
        accountService.save(person);
    }

}

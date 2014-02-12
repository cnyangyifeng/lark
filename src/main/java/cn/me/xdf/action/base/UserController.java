package cn.me.xdf.action.base;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.organization.SysOrgPersonTemp;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.RegisterService;
import cn.me.xdf.service.SysOrgPersonService;
import cn.me.xdf.utils.MD5Util;

/**
 * 用户管理
 * 
 * @author zuoyi
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
	@Autowired
	private SysOrgPersonService sysOrgPersonService;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("active", "user");
		String pageNo = request.getParameter("pageNo");
		String fdType = request.getParameter("fdType");
		if (StringUtils.isBlank(pageNo)) {
			pageNo = String.valueOf(1);
		}
		Finder finder = Finder.create(" from SysOrgPerson p where (p.loginName <> 'admin' or p.loginName is null) and (p.fdAvailable <> '00' or p.fdAvailable is null ) ");
		String param = request.getParameter("fdKey");
		if (StringUtils.isNotBlank(param)) {
			finder.append(" and (lower(p.loginName) like :param  or lower(p.fdName) like :param or lower(p.hbmParent.fdName) like :param ) ").setParam("param",
					"%"+param+"%");
		}
		if(StringUtils.isNotBlank(fdType)){
			finder.append(" and p.fdIsEmp = :isEmp ").setParam("isEmp", fdType);
		}
		Pagination page = sysOrgPersonService.getPage(finder,
				Integer.parseInt(pageNo));

		model.addAttribute("page", page);
		model.addAttribute("fdKey", param);
		model.addAttribute("fdType", fdType);
		return "/admin/user/list";
	}
	
	@RequestMapping(value = "updateUserInfo/{id}")
	public String updateUserInfo(Model model,HttpServletRequest request,
			@PathVariable("id") String id) {
		model.addAttribute("active", "user");
		model.addAttribute("admin", "admin");
		return "forward:/register/updateTeacher?id="+id;
	}
	
	@RequestMapping(value = "resetPassword/{id}")
	public String resetPassword(Model model,HttpServletRequest request,
			@PathVariable("id") String id) {
		model.addAttribute("active", "user");
		request.setAttribute("fdId", id);
		if(StringUtil.isBlank(id)){
			return "redirect:/register/changePwd";
		}
		SysOrgPerson person = sysOrgPersonService.get(id);
		request.setAttribute("fdEmail", person.getFdEmail());
		return "/admin/user/rePassword";
	}
	
	@RequestMapping(value = "savePassword")
	public String savePassword(HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		String fdPassword = request.getParameter("fdPassword");
		SysOrgPerson person = accountService.get(fdId);
        person.setPassword(MD5Util.getMD5String(fdPassword));
        accountService.save(person);
		return "redirect:/admin/user/list";
	}
	
	@RequestMapping(value = "deleteUser")
	public String deleteUser(Model model, HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String param = request.getParameter("fdKey");
		String fdType = request.getParameter("fdType");
		String selectAll = request.getParameter("selectAll");
		if(StringUtils.isBlank(selectAll) && ArrayUtils.isNotEmpty(ids)){
			for(String id:ids){
				SysOrgPerson person = accountService.get(id);
				if("0".equals(person.getFdIsEmp())){
					person.setFdAvailable("00");
			        accountService.save(person);
				}
			}
		}else if(StringUtils.isNotBlank(selectAll) && !"1".equals(fdType)){
			StringBuffer sql = new StringBuffer(" update sys_org_person p set p.fdavailable = ? ");
			sql.append(" where p.FD_IS_EMP = '0' ");
			if (StringUtils.isNotBlank(param)) {
				sql.append(" and (lower(p.FD_LOGIN_NAME) like '%"+param+"%' or p.fdid in (select e.fdid ");
				sql.append(" from sys_org_element e ");
				sql.append(" where e.fd_name like ");
				sql.append(" '%"+param+"%') or p.fdid in (select tem.fdid ");
				sql.append(" from sys_org_element tem ");
				sql.append(" where tem.fd_parentid in ");
				sql.append(" (select e1.fdid ");
				sql.append("  from sys_org_element e1 ");
				sql.append("  where e1.fd_name like '%"+param+"%'))) ");
			}
			sysOrgPersonService.executeSql(sql.toString(), "00");
		}
		model.addAttribute("fdKey", param);
		model.addAttribute("fdType", fdType);
		return "redirect:/admin/user/list";
	}
}

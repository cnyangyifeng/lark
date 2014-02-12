package cn.me.xdf.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.log.LogApp;
import cn.me.xdf.model.log.LogLogin;
import cn.me.xdf.model.log.LogLogout;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.log.LogAppService;
import cn.me.xdf.service.log.LogLoginService;
import cn.me.xdf.service.log.LogLogoutService;
import cn.me.xdf.utils.DateUtil;

/**
 * 日志设置
 * 
 * @author zhaoqi
 */
@Controller
@RequestMapping(value = "/admin/log")
public class LogContorller {

	
	@Autowired
	private AccountService accountService;
	@Autowired
	private LogLoginService logLoginService;
	@Autowired
	private LogLogoutService logLogoutService;
	@Autowired
	private LogAppService logAppService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo,String fdKey, HttpServletRequest request) {
		model.addAttribute("active", "log");
		if (StringUtils.isBlank(pageNo)) {
			pageNo = String.valueOf(1);
		}
		if (StringUtils.isBlank(fdKey)) {
			fdKey = "";
		}
		model.addAttribute("fdKey", fdKey);
		String fdType = request.getParameter("fdType");
		if (StringUtils.isEmpty(fdType)) {
			fdType="LogLogin";
		}
		Finder finder = Finder.create("");
		Pagination page=null;
		List<Map> returnList = new ArrayList<Map>();
		if(fdType.equals("LogLogin")){
			finder.append("from LogLogin l where (l.person.fdName like '%"+fdKey+"%' or l.person.fdEmail like '%"+fdKey+"%'  or l.person.hbmParent.fdName like '%"+fdKey+"%') ");
			finder.append("order by l.time desc");
			page= logLoginService.getPage(finder,Integer.parseInt(pageNo));
			List<LogLogin> list = (List<LogLogin>) page.getList();
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("fdLogId", list.get(i).getFdId());
				map.put("fdEmail", list.get(i).getPerson().getFdEmail());
				map.put("fdUserName", list.get(i).getPerson().getFdName());
				map.put("fdUserDep", list.get(i).getPerson().getHbmParent()==null?"":list.get(i).getPerson().getHbmParent().getFdName());
				map.put("time", DateUtil.convertDateToString(list.get(i).getTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("logType", "登录");
				returnList.add(map);
			}
		}else if(fdType.equals("LogLogout")){
			finder.append("from LogLogout l where (l.person.fdName like '%"+fdKey+"%' or l.person.fdEmail like '%"+fdKey+"%'  or l.person.hbmParent.fdName like '%"+fdKey+"%')");
			finder.append("order by l.time desc");
			page= logLogoutService.getPage(finder,Integer.parseInt(pageNo));
			List<LogLogout> list = (List<LogLogout>) page.getList();
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("fdLogId", list.get(i).getFdId());
				map.put("fdUserName", list.get(i).getPerson().getFdName());
				map.put("fdEmail", list.get(i).getPerson().getFdEmail());
				map.put("fdUserDep", list.get(i).getPerson().getHbmParent()==null?"":list.get(i).getPerson().getHbmParent().getFdName());
				map.put("time", DateUtil.convertDateToString(list.get(i).getTime(), "yyyy-MM-dd HH:mm:ss"));
				map.put("logType", "登出");
				returnList.add(map);
			}
		}else if(fdType.equals("LogApp")){
			finder.append("select l from LogApp l , SysOrgPerson o  where l.personId=o.fdId and (o.fdName like '%"+fdKey+"%' or o.fdEmail like '%"+fdKey+"%' or o.hbmParent.fdName like '%"+fdKey+"%' )  ");
			finder.append("order by l.time desc");
			page= logAppService.getPage(finder,Integer.parseInt(pageNo));
			List<LogApp> list = (List<LogApp>) page.getList();
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				map.put("fdLogId", list.get(i).getFdId());
				SysOrgPerson orgPerson = accountService.load(list.get(i).getPersonId());
				map.put("fdUserName", orgPerson.getFdName());
				map.put("fdEmail", orgPerson.getFdEmail());
				map.put("fdUserDep", orgPerson.getHbmParent()==null?"":orgPerson.getHbmParent().getFdName());
				map.put("time", DateUtil.convertDateToString(list.get(i).getTime(), "yyyy-MM-dd HH:mm:ss"));
				if(list.get(i).getMethod().equals(Constant.DB_UPDATE)){
					map.put("logType", "修改");
				}else if(list.get(i).getMethod().equals(Constant.DB_DELETE)){
					map.put("logType", "删除");
				}else{
					map.put("logType", "插入");
				}
				returnList.add(map);
			}
		}
		
		model.addAttribute("page", page);
		model.addAttribute("list", returnList);
		model.addAttribute("fdType", fdType);
		return "/admin/log/list";
	}
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(Model model, String logId,String logType, HttpServletRequest request) {
		model.addAttribute("active", "log");
		model.addAttribute("fdType", logType);
		Map map = new HashMap();
		if(logType.equals("LogLogin")){
			LogLogin logLogin = logLoginService.get(logId);
			map.put("fdLogId", logLogin.getFdId());
			map.put("fdUserName", logLogin.getPerson().getFdName());
			map.put("fdUserDep", logLogin.getPerson().getHbmParent()==null?"":logLogin.getPerson().getHbmParent().getFdName());
			map.put("time", DateUtil.convertDateToString(logLogin.getTime(), "yyyy-MM-dd HH:mm:ss"));
			map.put("logType", "登录日志");
			map.put("ip", logLogin.getIp());

		}else if(logType.equals("LogLogout")){
			LogLogout logLogout = logLogoutService.get(logId);
			map.put("fdLogId", logLogout.getFdId());
			map.put("fdUserName", logLogout.getPerson().getFdName());
			map.put("fdUserDep", logLogout.getPerson().getHbmParent()==null?"":logLogout.getPerson().getHbmParent().getFdName());
			map.put("time", DateUtil.convertDateToString(logLogout.getTime(), "yyyy-MM-dd HH:mm:ss"));
			map.put("logType", "登出日志");
			map.put("ip", logLogout.getIp());
		}else if(logType.equals("LogApp")){
			LogApp logApp = logAppService.get(logId);
			map.put("fdLogId", logApp.getFdId());
			SysOrgPerson orgPerson = accountService.load(logApp.getPersonId());
			map.put("fdUserName", orgPerson.getFdName());
			map.put("fdUserDep", orgPerson.getHbmParent()==null?"":orgPerson.getHbmParent().getFdName());
			map.put("time", DateUtil.convertDateToString(logApp.getTime(), "yyyy-MM-dd HH:mm:ss"));
			if(logApp.getMethod().equals(Constant.DB_UPDATE)){
				map.put("logType", "操作日志（修改）");
			}else if(logApp.getMethod().equals(Constant.DB_DELETE)){
				map.put("logType", "操作日志（删除）");
			}else{
				map.put("logType", "操作日志（插入）");
			}
			map.put("content", logApp.getContent().trim());
			map.put("modelId", logApp.getModelId());
			map.put("modelName", logApp.getModelName());
		}
		model.addAttribute("map", map);
		return "/admin/log/view";
	}
	
	@RequestMapping(value = "delete")
	public String deleteById(String fdType,String fdId, HttpServletRequest request) {
		if(fdType.equals("LogLogin")){
			logLoginService.delete(fdId);
		}else if(fdType.equals("LogLogout")){
			logLogoutService.delete(fdId);
		}else if(fdType.equals("LogApp")){
			logAppService.delete(fdId);
		}
		return "redirect:/admin/log/list?fdType="+fdType;
	}
	
	@RequestMapping(value = "deleteAll")
	public String deleteAll(HttpServletRequest request) {
		String fdType = request.getParameter("fdType");
		String [] ids =request.getParameterValues("ids");
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		String isAll = request.getParameter("selectCheckbox");
		if(isAll!=null&&isAll.equals("all")){
			if(fdType.equals("LogLogin")){
				Finder finder = Finder.create("delete from IXDF_NTP_LOGLOGIN la where la.fdId in( select l.fdId from IXDF_NTP_LOGLOGIN l , SYS_ORG_ELEMENT o where l.fdpersonid=o.fdId and o.fd_name like '%"+key+"%' )  ");
				logLoginService.executeSql(finder.getOrigHql());
			}else if(fdType.equals("LogLogout")){
				Finder finder = Finder.create("delete from IXDF_NTP_LOGLOGOUT la where  la.fdId in( select l.fdId from IXDF_NTP_LOGLOGOUT l , SYS_ORG_ELEMENT o where l.fdpersonid=o.fdId and o.fd_name like '%"+key+"%' )  ");
				logLoginService.executeSql(finder.getOrigHql());
			}else if(fdType.equals("LogApp")){
				Finder finder = Finder.create("delete from IXDF_NTP_LOGAPP la where la.fdId in( select l.fdId from IXDF_NTP_LOGAPP l , SYS_ORG_ELEMENT o where l.personId=o.fdId and o.fd_name like '%"+key+"%' ) ");
				logLoginService.executeSql(finder.getOrigHql());
			}
		}else{
			if(fdType.equals("LogLogin")){
				for (int i = 0; i < ids.length; i++) {
					logLoginService.delete(ids[i]);
				}
			}else if(fdType.equals("LogLogout")){
				for (int i = 0; i < ids.length; i++) {
					logLogoutService.delete(ids[i]);
				}
			}else if(fdType.equals("LogApp")){
				for (int i = 0; i < ids.length; i++) {
					logAppService.delete(ids[i]);
				}
			}
		}
		
		return "redirect:/admin/log/list?fdType="+fdType;
	}
	
	
	
}

package com.kuxue.action.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCategory;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.system.CourseSkin;
import com.kuxue.model.system.PageConfig;
import com.kuxue.service.AccountService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.system.CourseSkinService;
import com.kuxue.utils.ShiroUtils;

/**
 * 课程皮肤管理
 * 
 * @author zuoyi
 */

@Controller
@RequestMapping(value = "/admin/course/skin")
public class CourseSkinContorller {

	@Autowired
	private CourseSkinService courseSkinService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AttMainService attMainService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "courseskin");
		String fdType = request.getParameter("fdType");
		if(StringUtil.isBlank(fdType)){
			fdType = Constant.SKIN_TYPE_COURSE;
		}
		List list = courseSkinService.getCourseList(fdType);
		model.addAttribute("list", list);
		model.addAttribute("fdType", fdType);
		return "/admin/courseskin/list";
	}
	
	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		model.addAttribute("active", "courseskin");
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");
		CourseSkin skin = null;
		model.addAttribute("attId", "");
		if(StringUtils.isNotBlank(fdId)){
			skin = courseSkinService.load(fdId);
			AttMain attMain=attMainService.getByModelIdAndModelName(skin.getFdId(),CourseSkin.class.getName());
			if(attMain!=null){
				model.addAttribute("attId", attMain.getFdId()+"?n="+new Random().nextInt(100));
			}
		}
		model.addAttribute("bean", skin);
		model.addAttribute("fdType", fdType);
		return "/admin/courseskin/edit";
	}
	
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request) {
		String id = request.getParameter("fdId");
		String name = request.getParameter("fdName");
		String path = request.getParameter("fdSkinPath");
		String isDefault = request.getParameter("fdDefaultSkin");
		CourseSkin skin = new CourseSkin();
		if(StringUtils.isNotBlank(id)){
			skin = courseSkinService.load(id);
		}else{
			skin.setFdCreateTime(new Date());
			//获取当前用户信息
			SysOrgPerson sysOrgPerson=accountService.load(ShiroUtils.getUser().getId());
			skin.setCreator(sysOrgPerson);
			String fdType = request.getParameter("fdType");
			if(StringUtil.isBlank(fdType)){
				fdType = Constant.SKIN_TYPE_COURSE;
			}
			skin.setFdType(fdType);
		}
		skin.setFdName(name);
		skin.setFdSkinPath(path);
		if(Constant.BOOLEAN_YES.equals(isDefault)){
			//先取消其他的默认皮肤，然后设置此皮肤为默认皮肤
			courseSkinService.updateDefaultSkin(skin.getFdType());
			skin.setFdDefaultSkin(true);
		}else{
			skin.setFdDefaultSkin(false);
		}
		
		courseSkinService.save(skin);
		
		String attMainId = request.getParameter("attIdID");
		if(StringUtil.isNotBlank(attMainId)){
			// 先清理附件库(清理该皮肤的原始图片附件)
			ArrayList<String> atts = new ArrayList<String>();
			atts.add(attMainId);
			attMainService.deleteAttMainByModelIdExpAttId(skin.getFdId(),atts);
			AttMain attMain = attMainService.get(attMainId);
			attMain.setFdModelId(skin.getFdId());
			attMain.setFdModelName(CourseSkin.class.getName());
			attMain.setFdKey("CourseSkin");
			// 保存最新的附件
			attMainService.save(attMain);
		}
		return "redirect:/admin/course/skin/list?fdType="+skin.getFdType();
	}
	
	@RequestMapping(value = "delete")
	public String delete(Model model, HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String fdType = request.getParameter("fdType");
		if(ArrayUtils.isNotEmpty(ids)){
			courseSkinService.deleteSkin(ids);
		}
		return "redirect:/admin/course/skin/list?fdType="+fdType;
	}
}

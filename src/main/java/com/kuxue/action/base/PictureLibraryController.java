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
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.system.CourseSkin;
import com.kuxue.model.system.PictureLibrary;
import com.kuxue.service.AccountService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.system.CourseSkinService;
import com.kuxue.service.system.PictureLibraryService;
import com.kuxue.utils.ShiroUtils;

/**
 * 图片库管理
 * 
 * @author zuoyi
 */

@Controller
@RequestMapping(value = "/admin/picture")
public class PictureLibraryController {
	
	@Autowired
	private PictureLibraryService pictureLibraryService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AttMainService attMainService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "picture");
		if (StringUtils.isBlank(pageNo)) {
			pageNo = String.valueOf(1);
		}
		Finder finder = Finder.create("from PictureLibrary c   ");

		String param = request.getParameter("fdKey");
		if (StringUtils.isNotBlank(param)) {
			finder.append(" where c.fdName like :param ").setParam("param",
					"%"+param+"%");
		}
		Pagination page = pictureLibraryService.getPage(finder,
				Integer.parseInt(pageNo));
		
		model.addAttribute("page", page);
		model.addAttribute("fdKey", param);
		return "/admin/picture/list";
	}
	
	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		model.addAttribute("active", "picture");
		String fdId = request.getParameter("fdId");
		PictureLibrary picture = null;
		model.addAttribute("attId", "");
		if(StringUtils.isNotBlank(fdId)){
			picture = pictureLibraryService.load(fdId);
			AttMain attMain=attMainService.getByModelIdAndModelName(picture.getFdId(),PictureLibrary.class.getName());
			if(attMain!=null){
				model.addAttribute("attId", attMain.getFdId()+"?n="+new Random().nextInt(100));
			}
		}
		model.addAttribute("bean", picture);
		return "/admin/picture/edit";
	}
	
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request) {
		String id = request.getParameter("fdId");
		String name = request.getParameter("fdName");
				
		PictureLibrary picture = new PictureLibrary();
		if(StringUtils.isNotBlank(id)){
			picture = pictureLibraryService.load(id);
		}else{
			picture.setFdCreateTime(new Date());
			//获取当前用户信息
			SysOrgPerson sysOrgPerson=accountService.load(ShiroUtils.getUser().getId());
			picture.setCreator(sysOrgPerson);
		}
		picture.setFdName(name);
		
		pictureLibraryService.save(picture);
		
		String attMainId = request.getParameter("attIdID");
		if(StringUtil.isNotBlank(attMainId)){
			// 先清理附件库(清理该皮肤的原始图片附件)
			ArrayList<String> atts = new ArrayList<String>();
			atts.add(attMainId);
			attMainService.deleteAttMainByModelIdExpAttId(picture.getFdId(),atts);
			AttMain attMain = attMainService.get(attMainId);
			attMain.setFdModelId(picture.getFdId());
			attMain.setFdModelName(PictureLibrary.class.getName());
			attMain.setFdKey("PictureLibrary");
			// 保存最新的附件
			attMainService.save(attMain);
		}
		return "redirect:/admin/picture/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Model model, HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String param = request.getParameter("fdKey");
		String selectAll = request.getParameter("selectAll");
		if(StringUtils.isBlank(selectAll) && ArrayUtils.isNotEmpty(ids)){
			pictureLibraryService.deletePictures(ids);
		}else if(StringUtils.isNotBlank(selectAll)){
			pictureLibraryService.deletePicturesByParam(param);
		}
		model.addAttribute("fdKey", param);
		return "redirect:/admin/picture/list";
	}
}

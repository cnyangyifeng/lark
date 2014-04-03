package com.kuxue.action.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kuxue.common.page.Pagination;
import com.kuxue.model.course.CourseCategory;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.course.TagInfo;
import com.kuxue.service.course.CourseTagService;
import com.kuxue.service.course.TagInfoService;
import com.kuxue.utils.ShiroUtils;

/**
 * 课程标签管理
 * 
 * @author hanhl
 */
@Controller
@RequestMapping(value = "/admin/tag")
public class TagContorller {
	@Autowired
	private TagInfoService tagInfoService;
	
	@Autowired
	private CourseTagService courseTagService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("active", "tag");
		String pageNo=request.getParameter("pageNo");
		if (StringUtils.isBlank(pageNo)) {
			pageNo = String.valueOf(1);
		}
		String key=request.getParameter("fdKey");
		Pagination page =  tagInfoService.findTagsByKey(key,
				Integer.parseInt(pageNo));
		model.addAttribute("page", page);
		model.addAttribute("fdKey",key);
		return "/admin/tag/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Model model, HttpServletRequest request) {
		String  tagId = request.getParameter("fdId");
		courseTagService.deleteByTagId(tagId);
		tagInfoService.delete(tagId);
		model.addAttribute("active", "tag");
		return "redirect:/admin/tag/list";
	}
	
	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		String  tagId = request.getParameter("fdId");
		model.addAttribute("active", "tag");
		model.addAttribute("fdId",tagId);
		return "/admin/tag/edit";
	}
	@RequestMapping(value = "deleteAll")
	public String deleteAll(Model model, HttpServletRequest request) {
		String fdKey = request.getParameter("fdKey");
		String [] ids =request.getParameterValues("ids");
		String isAll = request.getParameter("selectCheckbox");
		if(isAll!=null&&isAll.equals("all")){//关键字为空:全删除,非空:全删关键字结果集
			Pagination page=tagInfoService.findTagsByKey(fdKey,
					1);
			if(page.getTotalPage()>0){
				for(int i=0;i<page.getTotalPage();i++){
					page=tagInfoService.findTagsByKey(fdKey,1);
					deleteTag(page);
				}
			}
		}else if(isAll!=null&&isAll.equals("notAll")){//关键字为空:删除当前页,非空:删除关键字结果集下的当前页;
			Pagination page=tagInfoService.findTagsByKey(fdKey,1);
			deleteTag(page);
		}else{//根据id删除
			for(int i=0;i<ids.length;i++){
				//先删课程与标签关系
				courseTagService.deleteByTagId(ids[i]);
				tagInfoService.delete(ids[i]);
			}
		}
		model.addAttribute("active", "tag");
		return "redirect:/admin/tag/list";
	}
	private void deleteTag(Pagination page){
		List list=page.getList();
		if(list!=null && list.size()>0){
			for(Object obj:list){
				TagInfo tag = (TagInfo)obj;
				//先删课程与标签关系
				courseTagService.deleteByTagId(tag.getFdId());
				tagInfoService.deleteEntity(tag);
			}
		}
	}
}

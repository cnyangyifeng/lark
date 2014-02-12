package cn.me.xdf.action.course;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.course.SeriesInfo;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.course.SeriesInfoService;
import cn.me.xdf.utils.ShiroUtils;
/*
 * 系列
 */
@Controller
@RequestMapping(value = "/series")
@Scope("request")
public class SeriesController {
	
	@Autowired
	private SeriesInfoService seriesInfoService;
	@Autowired
	private AttMainService attMainService;
	
	
	/**
	 * 查询系列课程;
	 * author hanhl
	 */
	@RequestMapping(value="findSeriesInfos")
	public String findSeries(Model model,HttpServletRequest request){
		String fdName = request.getParameter("fdName");
		String pageNo = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page=seriesInfoService.findSeriesInfosOrByName(fdName, pageNo, orderbyStr);
		model.addAttribute("page", page);
		return "/course/course_list";
	}
	/**
	 * 创建新系列
	 */
	@RequestMapping(value="add")
	public String add(HttpServletRequest request){
		String seriesId=request.getParameter("seriesId");
		if(StringUtil.isNotEmpty(seriesId)){
			SeriesInfo series = seriesInfoService.get(seriesId);
			if(series!=null){
				request.setAttribute("series", series);
			}
		}
		return "/course/series_add";
	}
	/**
	 * 后台系列课程管理预览或编辑
	 */
	@RequestMapping(value="pagefoward")
	public String pagefoward(HttpServletRequest request){
		String seriesId=request.getParameter("seriesId");
		if(StringUtil.isNotEmpty(seriesId)){
			if(seriesInfoService.getSeriesOfUser(seriesId)||ShiroUtils.isAdmin()){//如果是系列创建者跳转到系列编辑页
			return "redirect:/series/add?seriesId="+seriesId;
			}else{                                          
				return "/course/series_preview";//其他则跳转到系列预览
			}
		}else{
			return "redirect:/series/add";
		}
	}
	/**
	 * 后台系列课程管理预览系列
	 * @param request
	 */
	@RequestMapping(value = "previewSeries")
	public String previewSeries(HttpServletRequest request) {
	
		return "/course/series_preview";
	}
	/**
	 * 学习系列系列首页
	 */
	@RequestMapping(value="studayfoward")
	public String studayfoward(HttpServletRequest request){
				return "/course/series_release";
	}
}

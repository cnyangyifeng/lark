package cn.me.xdf.action.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
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
import cn.me.xdf.model.course.CourseCategory;
import cn.me.xdf.model.organization.SysOrgGroup;
import cn.me.xdf.service.course.CourseCategoryService;

/**
 * 课程分类的管理
 * 
 * @author zuoyi
 */
@Controller
@RequestMapping(value = "/admin/category")
public class CategoryContorller {
	@Autowired
	private CourseCategoryService courseCategoryService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	// @RequiresRoles({"admin"})
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "category");
		if (StringUtils.isBlank(pageNo)) {
			pageNo = String.valueOf(1);
		}
		Finder finder = Finder.create("from CourseCategory c   ");

		String param = request.getParameter("fdKey");
		if (StringUtils.isNotBlank(param)) {
			finder.append(" where c.fdName like :param ").setParam("param",
					"%"+param+"%");
		}
		Pagination page = courseCategoryService.getPage(finder,
				Integer.parseInt(pageNo));

		model.addAttribute("page", page);
		model.addAttribute("fdKey", param);
		return "/admin/category/list";
	}
	
	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		model.addAttribute("active", "category");
		String fdId = request.getParameter("fdId");
		CourseCategory category = null;
		if(StringUtils.isNotBlank(fdId)){
			category = courseCategoryService.load(fdId);
		}
		model.addAttribute("bean", category);
		return "/admin/category/edit";
	}
	
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request) {
		String id = request.getParameter("fdId");
		String name = request.getParameter("fdName");
		String descrip = request.getParameter("fdDescrip");
		CourseCategory category = new CourseCategory();
		if(StringUtils.isNotBlank(id)){
			category = courseCategoryService.load(id);
		}
		category.setFdName(name);
		category.setFdDescription(descrip);
		category.setFdOrder(courseCategoryService.getMaxOrder()+1);
		courseCategoryService.save(category);
		return "redirect:/admin/category/list";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Model model, HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String param = request.getParameter("fdKey");
		String selectAll = request.getParameter("selectAll");
		if(StringUtils.isBlank(selectAll) && ArrayUtils.isNotEmpty(ids)){
			courseCategoryService.delete(ids);
		}else if(StringUtils.isNotBlank(selectAll)){
			StringBuffer sql = new StringBuffer(" delete ixdf_ntp_course_category c  ");
			sql.append(" where c.fdId not in (select distinct fdCategoryId from ixdf_ntp_course ) ");
			if (StringUtils.isNotBlank(param)) {
				sql.append(" and c.fdName like '%"+param+"%'");
			}
			
			courseCategoryService.executeSql(sql.toString());
		}
		model.addAttribute("fdKey", param);
		return "redirect:/admin/category/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String deleteById(RedirectAttributes redirectAttributes,
			@PathVariable("id") String id) {
		if(!courseCategoryService.isUsedByCourse(id)){
			courseCategoryService.delete(id);
		}
		return "redirect:/admin/category/list";
	}
}

package cn.me.xdf.action.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.model.course.CourseCategory;
import cn.me.xdf.model.system.PageConfig;
import cn.me.xdf.service.course.CourseCategoryService;

/**
 * 课程分类信息的ajax
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/category")
@Scope("request")
public class CourseCategoryAjaxContorller {
	
	
	@Autowired
	private CourseCategoryService courseCategoryService;
	
	/**
	 * 获取课程分类信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getCourseCategory")
	@ResponseBody
	public String getCourseCategory(HttpServletRequest request) {
		List<CourseCategory> categories = courseCategoryService.findAll();
		Map returnMap = new HashMap();
		List<Map> lists = new ArrayList<Map>();
		for (CourseCategory courseCategory : categories) {
			Map map = new HashMap();
			map.put("courseCategoryId", courseCategory.getFdId());
			map.put("courseCategoryName", courseCategory.getFdName());
			map.put("order", courseCategory.getFdOrder());
			map.put("fdId", courseCategory.getFdId());
			lists.add(map);
		}
		returnMap.put("list", lists);
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	/**
	 * 获取课程分类信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "updateOrder")
	@ResponseBody
	public String updateOrder(HttpServletRequest request) {
		String categorys=request.getParameter("category");
		List<Map> category = JsonUtils.readObjectByJson(categorys, List.class);
		if(category!=null && category.size()>0){
			for(Map lectureMap:category){
				String pId = (String)lectureMap.get("id");
				if(StringUtil.isNotEmpty(pId)){
					CourseCategory c = courseCategoryService.get(pId);
					c.setFdOrder((Integer)lectureMap.get("order"));
					courseCategoryService.save(c);
				}
			}
		}
		return "";
	}

}

package com.kuxue.action.course;

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

import com.kuxue.common.json.JsonUtils;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseContent;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseContentService;
import com.kuxue.service.material.MaterialService;

/**
 * 课程素材信息的ajax
 * 
 * @author zuoyi
 * 
 */
@Controller
@RequestMapping(value = "/ajax/courseContent")
@Scope("request")
public class CourseContentAjaxController {

	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private CourseContentService courseContentService;
	
	@Autowired
	private MaterialService materialService;
	
	/**
	 * 获取当前节中的素材列表
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getMaterialsByCategoryId")
	@ResponseBody
	public String getMaterialsByCategoryId(HttpServletRequest request) {
		//获取节ID
		String catalogId = request.getParameter("catalogId");
		Map map = new HashMap();

		if(StringUtil.isNotEmpty(catalogId)){
			CourseCatalog catalog = courseCatalogService.get(catalogId);
			if(catalog!=null){
				map.put("learnTime", catalog.getFdStudyTime());
				map.put("sectionsIntro", catalog.getFdDescription());
				map.put("isElective", catalog.getFdPassCondition());
				List<Map> list = new ArrayList<Map>();
				//将课程中的素材列表信息返回到页面
				List<CourseContent> contents = courseContentService.findContentsByCatalogIdId(catalogId);
				if(contents!=null && contents.size()>0){
					for(CourseContent content:contents){
						Map materialMap = new HashMap();
						materialMap.put("id", content.getMaterial().getFdId());
						materialMap.put("index", content.getFdMaterialNo());
						materialMap.put("title", content.getMaterial().getFdName());
						list.add(materialMap);
					}
				}
				map.put("mediaList", list);
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 保存节内容
	 * @param request
	 */
	@RequestMapping(value = "saveCourseContent")
	@ResponseBody
	public void saveCourseContent(HttpServletRequest request) {
		//获取节ID
		String catalogId = request.getParameter("catalogId");
		//获取节名称
		String pageTitle = request.getParameter("pageTitle");
		//获取学习时长
		String learnTime = request.getParameter("learnTime");
		//获取节描述
		String sectionsIntro = request.getParameter("sectionsIntro");
		//获取节的必修选修设置
		String isElective = request.getParameter("isElective");
		//获取节内容类型
		String type = request.getParameter("type");
		//获取节内容列表
		String mediaList = request.getParameter("mediaList");
		
		if(StringUtil.isNotEmpty(catalogId)){
			CourseCatalog catalog = courseCatalogService.get(catalogId);
			if(catalog!=null){
				if(StringUtil.isNotEmpty(pageTitle)){
					catalog.setFdName(pageTitle);
				}
				if(StringUtil.isNotEmpty(learnTime)){
					catalog.setFdStudyTime(learnTime);
				}
				catalog.setFdDescription(sectionsIntro);
				catalog.setFdPassCondition(Double.valueOf(isElective));
				//先将课程与素材的关系清空，然后再进行保存
				courseContentService.deleteByCatalogId(catalogId);
				catalog.setFdTotalContent(0);
				catalog.setFdMaterialType(type);
				if(StringUtil.isNotEmpty(mediaList)){
					//解析页面传递的素材列表
					List<Map> contents = JsonUtils.readObjectByJson(mediaList, List.class);
					if(contents!=null && contents.size()>0){
						catalog.setFdTotalContent(contents.size());
						for(Map map:contents){
							String contentId = (String)map.get("id");
							String index = (String)map.get("index");
							String title = (String)map.get("title");
							if(StringUtil.isNotEmpty(contentId)){
								MaterialInfo material = materialService.get(contentId);
								if(material!=null && material.getIsAvailable()){
									CourseContent courseContent = new CourseContent();
									courseContent.setMaterial(material);
									courseContent.setFdRemark(title);
									courseContent.setFdMaterialNo(Integer.parseInt(index));
									courseContent.setCatalog(catalog);
									courseContentService.save(courseContent);
								}
							}
						}
					}
				}
				courseCatalogService.save(catalog);
			}
		}
	}
}

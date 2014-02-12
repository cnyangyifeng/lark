package cn.me.xdf.action.course;

import java.util.ArrayList;
import java.util.Date;
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
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.SysOrgPersonService;
import cn.me.xdf.service.course.CourseCatalogService;
import cn.me.xdf.service.course.CourseContentService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.utils.ShiroUtils;




/**
 * 课程目录
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/catalog")
@Scope("request")
public class CourseCatalogAjaxController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseContentService courseContentService;
	
	/**
	 * 获取当前课程的章节信息
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getCatalogJsonByCourseId")
	@ResponseBody
	public String getCatalogJsonByCourseId(HttpServletRequest request) {
		//获取课程ID
		String courseId = request.getParameter("courseId");
		List<Map> chapter = new ArrayList<Map>();
		List<Map> lecture = new ArrayList<Map>();
		if(StringUtil.isNotEmpty(courseId)){
			//根据课程ID取章节列表
			List<CourseCatalog> catalogs = courseCatalogService.getCatalogsByCourseId(courseId);
			if(catalogs!=null && catalogs.size()>0){
				for(CourseCatalog catalog:catalogs){
					if(Constant.CATALOG_TYPE_CHAPTER==catalog.getFdType()){
						//将章加入list中
						Map chapterMap = new HashMap();
						chapterMap.put("id", catalog.getFdId());
						chapterMap.put("index", catalog.getFdTotalNo());
						chapterMap.put("num", catalog.getFdNo());
						chapterMap.put("title", catalog.getFdName());
						chapter.add(chapterMap);
					}else{
						//将节加入list中
						Map lectureMap = new HashMap();
						lectureMap.put("id", catalog.getFdId());
						lectureMap.put("index", catalog.getFdTotalNo());
						lectureMap.put("num", catalog.getFdNo());
						lectureMap.put("title", catalog.getFdName());
						lectureMap.put("type", catalog.getMaterialType());
						lectureMap.put("isElective", catalog.getFdPassCondition());
						lecture.add(lectureMap);
					}					
				}
			}
		}		
		//将章节信息转换成json返回到页面
		Map map = new HashMap();
		map.put("chapter", chapter);
		map.put("lecture", lecture);
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 根据章节ID删除章节信息
	 * @param request
	 */
	@RequestMapping(value = "deleteCatalogById")
	@ResponseBody
	public void deleteCatalogById(HttpServletRequest request) {
		//获取章节ID
		String fdId = request.getParameter("fdid");
		CourseCatalog courseCatalog = courseCatalogService.get(fdId);
		//如果删除的是节，那么需要重新计算课程的总节数
		if(Constant.CATALOG_TYPE_LECTURE==courseCatalog.getFdType()){
			//需要删除节与素材的关系
			courseContentService.deleteByCatalogId(fdId);
			//重新计算课程总节数
			reCalculateCourseInfo(courseCatalog.getCourseInfo().getFdId(),courseCatalog.getCourseInfo().getFdTotalPart()-1);
		}else{
			//如果删除的是章，那么需要更新章下的节所在的章
			List<CourseCatalog> childList = courseCatalogService.getChildCatalog(fdId);
			if(childList!=null && childList.size()>0){
				for(CourseCatalog child : childList){
					child.setHbmParent(null);
					courseCatalogService.save(child);
				}
			}
		}
		courseCatalogService.delete(fdId);
	}
		
	/**
	 * 更新章节的顺序
	 * @param request
	 */
	@RequestMapping(value = "updateCatalogOrder")
	@ResponseBody
	public void updateCatalogOrder(HttpServletRequest request) {
		//获取课程ID
		String courseId = request.getParameter("courseid");
		//获取章
		String chapter = request.getParameter("chapter");
		//获取节
		String lecture = request.getParameter("lecture");
		//如果章信息不为空，则将章信息转为list循环更新
		if(StringUtil.isNotEmpty(chapter)){
			List<Map> chapters = JsonUtils.readObjectByJson(chapter, List.class);
			if(chapters!=null && chapters.size()>0){
				for(Map chapterMap:chapters){
					String chapterId = (String)chapterMap.get("id");
					if(StringUtil.isNotEmpty(chapterId)){
						CourseCatalog courseCatalog = courseCatalogService.get(chapterId);
						courseCatalog.setFdNo((Integer)chapterMap.get("num"));
						courseCatalog.setFdTotalNo((Integer)chapterMap.get("index"));
						courseCatalogService.save(courseCatalog);
					}
				}
			}
		}
		//如果节信息不为空，则将节信息转为list循环更新
		if(StringUtil.isNotEmpty(lecture)){
			List<Map> lectures = JsonUtils.readObjectByJson(lecture, List.class);
			if(lectures!=null && lectures.size()>0){
				for(Map lectureMap:lectures){
					String lectureId = (String)lectureMap.get("id");
					if(StringUtil.isNotEmpty(lectureId)){
						CourseCatalog courseCatalog = courseCatalogService.get(lectureId);
						courseCatalog.setFdNo((Integer)lectureMap.get("num"));
						courseCatalog.setFdTotalNo((Integer)lectureMap.get("index"));
						courseCatalogService.save(courseCatalog);
					}
				}
			}
		}
		
		reCalculateCatalog(courseId);
	}
	
	/**
	 * 根据章节ID更新章节名称
	 * @param request
	 */
	@RequestMapping(value = "updateCatalogNameById")
	@ResponseBody
	public void updateCatalogNameById(HttpServletRequest request) {
		//获取章节ID
		String fdId = request.getParameter("fdid");
		//获取章节名称
		String fdName = request.getParameter("title");
		CourseCatalog courseCatalog = courseCatalogService.get(fdId);
		courseCatalog.setFdName(fdName);
		courseCatalogService.save(courseCatalog);
	}
	
	/**
	 * 新增章节
	 * @param request
	 * @return String 新增的章节id及
	 */
	@RequestMapping(value = "addCatalog")
	@ResponseBody
	public String addCatalog(HttpServletRequest request) {
		//获取课程ID
		String courseId = request.getParameter("courseid");
		//获取添加的是否为章
		String isChapter = request.getParameter("ischapter");
		//获取总序号
		int fdTotalNo = Integer.parseInt(request.getParameter("fdtotalno"));	
		//获取章节序号
		int fdNo = Integer.parseInt(request.getParameter("fdno"));	
		//获取章节名称
		String fdName = request.getParameter("title");
		//获取当前用户信息
		SysOrgPerson sysOrgPerson=accountService.load(ShiroUtils.getUser().getId());
		//创建时间
		Date createdate=new Date();
		CourseCatalog courseCatalog = new CourseCatalog();
		//如果页面没有传课程ID,说明是新增的课程模板，并且新增第一章，则需要先保存课程
		CourseInfo course = new CourseInfo();
		if(StringUtil.isNotEmpty(courseId)){
			course = courseService.get(courseId);
			courseCatalog.setCourseInfo(course);
		}else{
			//新建课程时总节数设置为0
			course.setFdTotalPart(0);
			course.setFdStatus(Constant.COURSE_TEMPLATE_STATUS_DRAFT);
			course.setIsAvailable(true);
			course.setIsPublish(false);
			course.setIsOrder(true);
			course.setCreator(sysOrgPerson);
			course.setFdAuthor(ShiroUtils.getUser().getName());//作者
			course.setFdAuthorDescription(sysOrgPerson.getSelfIntroduction());
			course.setFdCreateTime(createdate);
			course.setFdTitle("未命名课程");
			courseService.save(course);
			courseCatalog.setCourseInfo(course);
		}
		courseCatalog.setFdName(fdName);
		courseCatalog.setFdTotalNo(fdTotalNo);
		courseCatalog.setFdNo(fdNo);
		if("true".equals(isChapter)){
			courseCatalog.setFdType(Constant.CATALOG_TYPE_CHAPTER);
			courseCatalog.setFdTotalPart(0);
		}else{
			courseCatalog.setFdType(Constant.CATALOG_TYPE_LECTURE);
			courseCatalog.setFdTotalContent(0);
			//重新计算课程总节数
			reCalculateCourseInfo(course.getFdId(),course.getFdTotalPart()+1);
		}
		 courseCatalogService.save(courseCatalog);
		 
		 reCalculateCatalog(course.getFdId());
		 Map map = new HashMap();
		 map.put("id", courseCatalog.getFdId());
		 map.put("courseid", course.getFdId());
		 return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 重新计算课程的总节数
	 * @param courseId 课程ID
	 */
	private void reCalculateCourseInfo(String courseId,int totalPart){
		CourseInfo courseInfo = courseService.get(courseId);
		if(courseInfo!=null){
			courseInfo.setFdTotalPart(totalPart);
			courseService.save(courseInfo);
		}
	}
	
	/**
	 * 重新计算章节信息
	 * @param courseId 课程ID
	 */
	private void reCalculateCatalog(String courseId){
		int totalPart = 0;
		String hbmparentid = "";
		//根据课程ID取章节列表
		List<CourseCatalog> catalogs = courseCatalogService.getCatalogsByCourseId(courseId);
		if(catalogs!=null && catalogs.size()>0){
			for(CourseCatalog catalog:catalogs){
				//判断如果是章，则记录下章的ID，并且将章中的总节数置0
				if(Constant.CATALOG_TYPE_CHAPTER==catalog.getFdType()){
					hbmparentid = catalog.getFdId();
					totalPart = 0;
				}
				//否则如果是节，则将总节数累加
				else{
					totalPart++;
				}
				if(StringUtil.isNotEmpty(hbmparentid) && Constant.CATALOG_TYPE_LECTURE==catalog.getFdType()){
					//如果记录的章ID不为空并且当前是节的话，根据章ID取出章，将总节数更新到章中，同时更新节的上级
					CourseCatalog courseCatalog = courseCatalogService.get(hbmparentid);
					courseCatalog.setFdTotalPart(totalPart);
					courseCatalogService.save(courseCatalog);
					catalog.setHbmParent(courseCatalog);
					courseCatalogService.save(catalog);
				}
			}
		}
	}
}

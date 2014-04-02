package com.kuxue.action.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.bam.BamCourse;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseAuth;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseCategory;
import com.kuxue.model.course.CourseGroupAuth;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.course.CourseParticipateAuth;
import com.kuxue.model.course.CourseTag;
import com.kuxue.model.course.TagInfo;
import com.kuxue.model.organization.SysOrgGroup;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.score.ScoreStatistics;
import com.kuxue.service.AccountService;
import com.kuxue.service.SysOrgGroupService;
import com.kuxue.service.SysOrgPersonService;
import com.kuxue.service.UserRoleService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseAuthService;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseCategoryService;
import com.kuxue.service.course.CourseContentService;
import com.kuxue.service.course.CourseGroupAuthService;
import com.kuxue.service.course.CourseParticipateAuthService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.course.CourseTagService;
import com.kuxue.service.course.SeriesCoursesService;
import com.kuxue.service.course.TagInfoService;
import com.kuxue.service.score.ScoreStatisticsService;
import com.kuxue.service.studyTack.StudyTrackService;
import com.kuxue.utils.ShiroUtils;
import com.kuxue.model.organization.RoleEnum;
/**
 * 课程信息的ajax
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/course")
@Scope("request")
public class CourseAjaxController {

	
	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private CourseTagService courseTagService;

	@Autowired
	private TagInfoService tagInfoService;

	@Autowired
	private SeriesCoursesService seriesCoursesService;

	@Autowired
	private CourseContentService courseContentService;

	@Autowired
	private CourseCatalogService courseCatalogService;

	@Autowired
	private CourseAuthService courseAuthService;
	
	@Autowired
	private AttMainService attMainService;
  
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseParticipateAuthService courseParticipateAuthService;
	
	@Autowired
	private ScoreStatisticsService statisticsService;
	
	@Autowired
	private ScoreStatisticsService scoreStatisticsService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private SysOrgPersonService sysOrgPersonService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private SysOrgGroupService sysOrgGroupService;
	
	@Autowired
	private CourseGroupAuthService courseGroupAuthService;
	
	@Autowired
	private StudyTrackService studyTrackService;
	/**
	 * 获取当前课程的基本信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getBaseCourseInfoById")
	@ResponseBody
	public String getBaseCourseInfoById(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		Map map = new HashMap();

		// 将所有课程分类信息转换成json返回到页面
		List<CourseCategory> categorys = courseCategoryService.findAll();
		if (categorys != null && categorys.size() > 0) {
			List<Map> cateList = new ArrayList<Map>();
			for (CourseCategory category : categorys) {
				Map catemap = new HashMap();
				catemap.put("id", category.getFdId());
				catemap.put("title", category.getFdName());
				cateList.add(catemap);
			}
			map.put("courseTypeList", cateList);
			// 默认将第一个分类选中
			map.put("courseType", "");//categorys.get(0).getFdId());
		}
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null) {
				map.put("courseTit", course.getFdTitle());
				map.put("subTit", course.getFdSubTitle());
				map.put("sectionOrder", course.getIsOrder());
				map.put("isCompulsoryCourse", course.getIsCompulsoryCourse());
				if(course.getFdPrice()!=null){
					String fdPrice = new java.text.DecimalFormat("0.00").format(course.getFdPrice());
					map.put("fdPrice", fdPrice);
				}else{
					map.put("fdPrice", "");
				}
				
				if (course.getFdCategory() != null) {
					map.put("courseType", course.getFdCategory().getFdId());
				}
				// 将课程的标签信息返回到页面
				List<TagInfo> tagList = courseTagService
						.findTagByCourseId(courseId);
				if (tagList != null && tagList.size() > 0) {
					List<String> tags = new ArrayList<String>();
					for (TagInfo tag : tagList) {
						tags.add(tag.getFdName());
					}
					map.put("keyword", tags);
				}
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 保存课程的基本信息
	 * 
	 * @param request
	 * @return String 课程ID
	 */
	@RequestMapping(value = "saveBaseInfo")
	@ResponseBody
	public String saveBaseInfo(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		// 获取课程标题
		String courseTitle = request.getParameter("courseTitle");
		// 获取课程副标题
		String subTitle = request.getParameter("subTitle");
		// 获取课程学习顺序
		String sectionOrder = request.getParameter("sectionOrder");
		// 获取课程标签
		String keyword = request.getParameter("keyword");
		// 获取课程分类ID
		String courseType = request.getParameter("courseType");
		//定价信息
		String fdPrice = request.getParameter("fdPrice");
		//获取当前用户信息
		SysOrgPerson sysOrgPerson=accountService.load(ShiroUtils.getUser().getId());
		//创建时间
		Date createdate=new Date();
		// 获取课程必修选修
		String isCompulsoryCourse = request.getParameter("isCompulsoryCourse");
		
		Map map = new HashMap();
		CourseInfo course = new CourseInfo();
		if (StringUtil.isNotEmpty(courseId)) {
			course = courseService.get(courseId);
			if (course == null) {
				course = new CourseInfo();
				course.setFdTitle(courseTitle);
				course.setFdSubTitle(subTitle);
				if(StringUtil.isNotBlank(fdPrice)){
					course.setFdPrice(Double.parseDouble(fdPrice));
				}
				// 新建课程时总节数设置为0
				course.setFdTotalPart(0);
				course.setFdStatus(Constant.COURSE_TEMPLATE_STATUS_DRAFT);
				course.setIsAvailable(true);
				course.setIsPublish(false);
				course.setIsOrder(Boolean.valueOf(sectionOrder));
				course.setIsCompulsoryCourse(Boolean.valueOf(isCompulsoryCourse));
				// 将分类保存到课程中
				if (StringUtil.isNotEmpty(courseType)) {
					CourseCategory category = courseCategoryService
							.get(courseType);
					course.setFdCategory(category);
				}else{
					course.setFdCategory(null);
				}
				course.setCreator(sysOrgPerson);
				course.setFdAuthor(ShiroUtils.getUser().getName());
				course.setFdAuthorDescription(sysOrgPerson.getSelfIntroduction());
				course.setFdCreateTime(createdate);
				course.setFdAuthorId(sysOrgPerson.getFdId());
				course = courseService.save(course);
			} else {
				course.setFdTitle(courseTitle);
				course.setFdSubTitle(subTitle);
				course.setIsOrder(Boolean.valueOf(sectionOrder));
				course.setIsCompulsoryCourse(Boolean.valueOf(isCompulsoryCourse));
				// 将分类保存到课程中
				if (StringUtil.isNotEmpty(courseType)) {
					CourseCategory category = courseCategoryService
							.get(courseType);
					course.setFdCategory(category);
				}else{
					course.setFdCategory(null);
				}
				if(StringUtil.isNotBlank(fdPrice)){
					course.setFdPrice(Double.parseDouble(fdPrice));
				}
				
				course = courseService.save(course);
			}
		} else {
			course.setFdTitle(courseTitle);
			course.setFdSubTitle(subTitle);
			// 新建课程时总节数设置为0
			course.setFdTotalPart(0);
			course.setFdStatus(Constant.COURSE_TEMPLATE_STATUS_DRAFT);
			course.setIsAvailable(true);
			course.setIsPublish(false);
			course.setIsOrder(Boolean.valueOf(sectionOrder));
			course.setIsCompulsoryCourse(Boolean.valueOf(isCompulsoryCourse));
			// 将分类保存到课程中
			if (StringUtil.isNotEmpty(courseType)) {
				CourseCategory category = courseCategoryService.get(courseType);
				course.setFdCategory(category);
			}
			course.setCreator(sysOrgPerson);
			course.setFdAuthor(ShiroUtils.getUser().getName());
			course.setFdAuthorDescription(sysOrgPerson.getSelfIntroduction());
			course.setFdAuthorId(sysOrgPerson.getFdId());
			course.setFdCreateTime(createdate);
			course = courseService.save(course);
			courseId = course.getFdId();
		}

		// 保存标签库中没有的标签
		if (StringUtil.isNotEmpty(keyword)) {
			String[] tags = keyword.split(",");
			for (String tagName : tags) {
				if (StringUtil.isEmpty(tagName)) {
					continue;
				}
				TagInfo tagInfo = tagInfoService.getTagByName(tagName);
				if (tagInfo == null) {
					tagInfo = new TagInfo();
					tagInfo.setFdName(tagName);
					tagInfo = tagInfoService.save(tagInfo);
				}
				// 保存课程与标签的关系
				CourseTag courseTag = new CourseTag();
				courseTag.setCourses(course);
				courseTag.setTag(tagInfo);
				courseTagService.save(courseTag);
			}
		}
		map.put("courseid", courseId);
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 获取当前课程的详细信息
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "getDetailCourseInfoById")
	@ResponseBody
	public String getDetailCourseInfoById(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		Map map = new HashMap();
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null) {
				// 课程摘要
				map.put("courseAbstract", course.getFdSummary());
				// 课程作者
				map.put("courseAuthor", course.getFdAuthor()==null?
						ShiroUtils.getUser().getName():course.getFdAuthor());
				// 作者Id
				map.put("courseAuthorId", course.getFdAuthorId());
				// 作者描述
				map.put("authorDescrip", course.getFdAuthorDescription());
				// 学习目标
				String learnObjectives = course.getFdLearnAim() == null ? ""
						: course.getFdLearnAim();
				map.put("learnObjectives", learnObjectives);
				map.put("learnObjectiveslist", buildString(learnObjectives));
				// 建议群体
				String suggestedGroup = course.getFdProposalsGroup() == null ? ""
						: course.getFdProposalsGroup();
				map.put("suggestedGroup", suggestedGroup);
				map.put("suggestedGrouplist", buildString(suggestedGroup));
				// 课程要求
				String courseRequirements = course.getFdDemand() == null ? ""
						: course.getFdDemand();
				map.put("courseRequirements", courseRequirements);
				map.put("courseRequirementslist", buildString(courseRequirements));
			}
		}else{
			//默认设置创建者为作者
			map.put("courseAuthor", ShiroUtils.getUser().getName());
		}
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 根据#号分隔字符串,返回list
	 * 
	 * @param
	 * @return List
	 */
	private static List buildString(String s) {
		List list = new ArrayList();
		String[] ls = s.split("#");
		for (String tmp : ls) {
			if (StringUtil.isEmpty(tmp)) {
				continue;
			}
			list.add(tmp);
		}
		return list;
	}

	/**
	 * 保存课程的详细信息
	 * 
	 * @param request
	 */
	@RequestMapping(value = "saveDetailInfo")
	@ResponseBody
	public void saveDetailInfo(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		// 课程摘要
		String courseAbstract = request.getParameter("courseAbstract");
		// 课程作者
		String courseAuthor = request.getParameter("courseAuthor");
		// 课程作者Id
		String courseAuthorId = request.getParameter("courseAuthorId");
		// 作者描述
		String authorDescrip = request.getParameter("authorDescrip");
		// 学习目标
		String learnObjectives = request.getParameter("learnObjectives");
		// 建议群体
		String suggestedGroup = request.getParameter("suggestedGroup");
		// 课程要求
		String courseRequirements = request.getParameter("courseRequirements");
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null) {
				// 课程摘要
				course.setFdSummary(courseAbstract);
				// 课程作者
				course.setFdAuthor(courseAuthor);
				// 课程作者
				course.setFdAuthorId(courseAuthorId);
				// 作者描述
				course.setFdAuthorDescription(authorDescrip);
				// 学习目标
				course.setFdLearnAim(learnObjectives);
				// 建议群体
				course.setFdProposalsGroup(suggestedGroup);
				// 课程要求
				course.setFdDemand(courseRequirements);
				courseService.save(course);
			}
		}
	}

	/**
	 * 根据标签名称模糊查询标签信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findTagInfosByKey")
	@ResponseBody
	public List<TagInfo> findTagInfosByKey(HttpServletRequest request) {
		// key
		String key = request.getParameter("q");
		List<TagInfo> tagInfos = tagInfoService.findTagInfosByKey(key);
		return tagInfos;
	}

	/**
	 * 修改课程权限(是否公开)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateIsPublish")
	@ResponseBody
	public void updateIsPublish(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		String isPublish = request.getParameter("isPublish");
		String fdPassword = request.getParameter("fdPassword");
		CourseInfo courseInfo = courseService.findUniqueByProperty("fdId",
				courseId);
		if (isPublish.equals("open")) {
			courseInfo.setIsPublish(true);
			courseInfo.setFdPassword("");
		} else {
			courseInfo.setIsPublish(false);
			courseInfo.setFdPassword(fdPassword);
		}
		courseService.save(courseInfo);
		List<CourseGroupAuth> delCourseGroupAuth = courseGroupAuthService.findByProperty("course.fdId", courseId);
		for (CourseGroupAuth courseGroupAuth : delCourseGroupAuth) {
			courseGroupAuthService.delete(courseGroupAuth.getFdId());
		}
		String groupIds = request.getParameter("groupIds");
		String [] ids = groupIds.split(":");
		for (String string : ids) {
			if(!string.equals("all")&&StringUtil.isNotEmpty(string)){
				CourseGroupAuth courseGroupAuth = new CourseGroupAuth();
				courseGroupAuth.setCourse(courseInfo);
				SysOrgGroup sysOrgGroup = sysOrgGroupService.get(string);
				courseGroupAuth.setGroup(sysOrgGroup);
				courseGroupAuthService.save(courseGroupAuth);
			}
			
		}
		if((!isPublish.equals("open"))&&StringUtil.isEmpty(fdPassword)){
			List<CourseGroupAuth> delCourseGroupAuth1 = courseGroupAuthService.findByProperty("course.fdId", courseId);
			for (CourseGroupAuth courseGroupAuth : delCourseGroupAuth1) {
				courseGroupAuthService.delete(courseGroupAuth.getFdId());
			}
		}
	}

	/**
	 * 得到指定课程的权限信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAuthInfoByCourseId")
	@ResponseBody
	public String getAuthInfoByCourseId(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		List<Map> list = courseService.findAuthInfoByCourseId(courseId);
		Map map = new HashMap();
		map.put("user", list);
		CourseInfo course = courseService.get(courseId);
		SysOrgPerson orgPerson = course.getCreator();
		map.put("createrid", orgPerson.getFdId());
		map.put("createrimgUrl", orgPerson.getPoto());
		map.put("creatername", orgPerson.getRealName());
		map.put("creatermail", orgPerson.getFdEmail());
		map.put("createrdepartment", orgPerson.getDeptName());
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 得到指定课程的是否公开信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getIsPublishInfo")
	@ResponseBody
	public String getIsPublishInfo(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");
		CourseInfo courseInfo = courseService.get(courseId);
		Map map = new HashMap();
		map.put("action", "");
		map.put("permission", (courseInfo.getIsPublish() == null || courseInfo
				.getIsPublish() == true) ? "open" : "encrypt");
		map.put("encryptType",
				StringUtil.isBlank(courseInfo.getFdPassword()) ? "authorized"
						: "passwordProtect");
		map.put("coursePwd", courseInfo.getFdPassword());
		
		List<CourseGroupAuth> groupAuths = courseGroupAuthService.findByProperty("course.fdId", courseId);
		List<Map> list = new ArrayList<Map>();
		for (CourseGroupAuth courseGroupAuth : groupAuths) {
			Map m = new HashMap();
			m.put("id", courseGroupAuth.getGroup().getFdId());
			m.put("gName", courseGroupAuth.getGroup().getFdName());
			list.add(m);
		}
		map.put("list", list);
		return JsonUtils.writeObjectToJson(map);
	}

	/*
	 * 查询课程列表 或者根据关键字搜索 author hanhl
	 */
	@RequestMapping(value = "getCoureInfosOrByKey")
	public String getCoureInfosOrByKey(Model model, HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = courseService.findCourseInfosByName( fdTitle,
				pageNoStr, orderbyStr,Constant.COUSER_TEMPLATE_MANAGE);
		model.addAttribute("page", page);
		return "/course/divcourselist";
	}
	
	/*
	 * 查询课程列表 或者根据关键字搜索 author hanhl
	 */
	@RequestMapping(value = "deleteAllCoursesByKey")
	public void deleteAllCoursesByKey(Model model, HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = courseService.findCourseInfosByName( fdTitle,
				pageNoStr, orderbyStr,Constant.COUSER_TEMPLATE_MANAGE);
		int i = page.getTotalPage();
		if(i>0){
			for(int j=0;j<i;j++){
				page = courseService.findCourseInfosByName( fdTitle,
						"1", orderbyStr,Constant.COUSER_TEMPLATE_MANAGE);
				List list = page.getList();
				if(list!=null && list.size()>0){
					for(Object obj:list){
						Map map = (Map)obj;
						String courseId = (String)map.get("FDID");
						if(ShiroUtils.isAdmin()){//管理员删除不判断权限
							delCourseById(courseId);
						}else{
							if(StringUtil.isNotBlank(findEditAuth(courseId))){//非管理员删除
								delCourseById(courseId);
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * 修改课程授权信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateCourseAuth")
	@ResponseBody
	public void updateCourseAuth(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");

		String data = request.getParameter("data");
		List<Map> list = JsonUtils.readObjectByJson(data, List.class);
		List<CourseAuth> auths = new ArrayList<CourseAuth>();
		CourseInfo course = new CourseInfo();
		course.setFdId(courseId);
		for (Map map : list) {
			CourseAuth auth = new CourseAuth();
			auth.setCourse(course);
			SysOrgPerson fdUser = accountService.load((String) map.get("id"));
			auth.setFdUser(fdUser);
			auth.setIsAuthStudy((Boolean) map.get("tissuePreparation"));
			auth.setIsEditer((Boolean) map.get("editingCourse"));
			auths.add(auth);
		}
		courseService.updateCourseAuth(courseId, auths);
	}

	/**
	 * 删除课程
	 * 
	 * @param request
	 */
	@RequestMapping(value = "deleteCourse")
	@ResponseBody
	public void deleteCourse(HttpServletRequest request) {
		// 获取课程ID
		String fdIds = request.getParameter("courseId");
		if (StringUtil.isNotEmpty(fdIds)) {
			String[] courses = fdIds.split(",");
			String courseId = "";
			for(int i=0;i<courses.length;i++){
				courseId = courses[i];
				delCourseById(courseId);
			}
		}
	}
	
	//删除课程
	private void delCourseById(String courseId){
		CourseInfo course = courseService.get(courseId);
		if (course != null && course.getIsAvailable()) {
			// 需要判断课程状态是发布还是草稿，如果是发布，则只改是否有效的状态，如果是草稿，则删除课程及课程相关数据。
			if (Constant.COURSE_TEMPLATE_STATUS_DRAFT.equals(course
					.getFdStatus())) {
				// 删除课程与关键字的关系
				courseTagService.deleteByCourseId(courseId);
				// 删除课程权限
				courseAuthService.deleCourseAuthByCourseId(courseId);
				// 获取课程下的所有章节
				List<CourseCatalog> list = courseCatalogService
						.getCatalogsByCourseId(courseId);
				if (list != null && list.size() > 0) {
					for (CourseCatalog catalog : list) {
						if (Constant.CATALOG_TYPE_LECTURE == catalog
								.getFdType()) {
							// 删除节与内容的关系
							courseContentService.deleteByCatalogId(catalog
									.getFdId());
						}
					}
				}
				// 删除章节
				courseCatalogService.deleteByCourseId(courseId);
				//草稿状态下 删除课程的群组关系
				courseGroupAuthService.deleteByCourseId(courseId);
				// 删除课程
				courseService.delete(courseId);
			} else {
				// 删除已发布课程模板时，需要删除课程与系列的关系，则否会在系列中显示该课程，其他关系保持不变。
				seriesCoursesService.deleteByCourseId(courseId);
				// 修改课程模板有效状态
				course.setIsAvailable(false);
				courseService.save(course);
			}
		}
	}

	/*
	 * 课程封页图片 author hanhl
	 */
	@RequestMapping(value = "cover")
	@ResponseBody
	public void courseCover(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");
		String attMainId = request.getParameter("attId");
		if(StringUtil.isNotBlank(attMainId)){
			// 先清理附件库(清理该课程下的原始附件)
			ArrayList<String> atts = new ArrayList<String>();
			atts.add(attMainId);
			attMainService.deleteAttMainByModelIdExpAttId(courseId,atts);
			AttMain attMain = attMainService.get(attMainId);
			attMain.setFdModelId(courseId);
			attMain.setFdModelName(CourseInfo.class.getName());
			attMain.setFdKey("Course");
			// 保存最新的附件
			attMainService.save(attMain);
		}
	}

	/*
	 * 加载课程页面
	 */
	@RequestMapping(value = "showcover")
	@ResponseBody
	public String getCourseCoverById(HttpServletRequest

	request) {
		String courseId = request.getParameter("courseId");
		AttMain attMain = attMainService.getByModelId(courseId);
		Map map = new HashMap();
		if(attMain!=null){
		map.put("coverUrl", attMain.getFdId()+"?n="+new Random().nextInt(100));
		}else{
			map.put("coverUrl", "");	
		}
		return JsonUtils.writeObjectToJson(map);
	}
	/*
	 * 删除数据过滤:
	 */
	
	@RequestMapping(value = "deleFiter")
	@ResponseBody
	public String deleteFiter(HttpServletRequest request){
		String fdIds = request.getParameter("courseId");
		String deleType=request.getParameter("deleType");
		if("0".equals(deleType)){//选择删除
				return getDeleteKeys(fdIds);//获取可删除id
		}else{//删除全部
				return getDeleteKeys(fdIds);
		}
	}
	/*
	 * 根据课程id查找课程权限
	 * author hanhl
	 * 1.当前课程创建者
	 * 2.拥有编辑权限
	 */
	private String findEditAuth(String couserId){
		CourseInfo courseInfo =courseService .load(couserId);
		if(courseInfo.getCreator().getFdId().equals(ShiroUtils.getUser().getId())){
			return courseInfo.getFdId();
		}
		CourseAuth auth = courseAuthService.findByCourseIdAndUserId(couserId,ShiroUtils.getUser().getId());
	    if(auth!=null&&auth.getIsEditer()==true){
	    	return courseInfo.getFdId();
	    }
		
		return null;
		   
	}
	/*
	 * 获取可执行删除的课程id返回页面
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private String getDeleteKeys(String fdIds){
		List deltes=new ArrayList();
		Map delekeys = new HashMap();
		String[] courseIds = fdIds.split(",");
		String fdId = "";
		String auth = "";
		for (int i = 0; i < courseIds.length; i++) {
			fdId = courseIds[i];
			auth = findEditAuth(fdId);
			if(StringUtil.isBlank(auth)){
				continue;
			}else{
				delekeys.put("id",auth);
				deltes.add(delekeys);
			}
		}
		return JsonUtils.writeObjectToJson(deltes);
		 
	}
	/*
	 * 查询授权课程列表 或者根据关键字搜索 author hanhl
	 */
	@RequestMapping(value = "getCoureAuthInfosOrByKey")
	public String getCoureAuthInfosOrByKey(Model model, HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = courseService.findCourseInfosByName( fdTitle,
				pageNoStr, orderbyStr,Constant.COUSER_AUTH_MANAGE);
		model.addAttribute("page", page);
		return "/course/divcourseauthlist";
	}
	/**
	 * 某课程授权列表
	 */
	public  SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd h:m:s a");
	@RequestMapping(value="getSingleCourseAuths")
	@ResponseBody
	public String getSingleCourseAuths(HttpServletRequest request){
		String courseId=request.getParameter("courseId");
		String orderStr=request.getParameter("order");
		String pageNostr=request.getParameter("pageNo");
		String keyword=request.getParameter("keyword");
		int pageNo;
		if (StringUtil.isNotBlank(pageNostr)) {
			pageNo = Integer.parseInt(pageNostr);
		} else {
			pageNo = 1;
		}
		CourseInfo courseInfo=courseService.load(courseId);
		AttMain attMain = attMainService.getByModelId(courseId);
		Map courses=new HashMap();
		courses.put("courseId", courseInfo.getFdId());//课程id
		if(attMain!=null){
			courses.put("coverUrl", attMain.getFdId());
		}else{
			courses.put("coverUrl", "");	
		}//课程封面
		ScoreStatistics scoreStatistics=statisticsService.findScoreStatisticsByModelNameAndModelId(CourseInfo.class.getName(), courseInfo.getFdId());
		if(scoreStatistics!=null){
		courses.put("courseScore", scoreStatistics.getFdAverage());//课程评分;
		}else{
			courses.put("courseScore", 0);//课程评分;
		}
		courses.put("courseName", courseInfo.getFdTitle()==""||courseInfo.getFdTitle()==null?"":courseInfo.getFdTitle());//课程名称
		courses.put("courseAuthor", courseInfo.getFdAuthor()==""||courseInfo.getFdAuthor()==null?"":courseInfo.getFdAuthor());//课程作者;
		//获取课程授权列表
		List coursepas=new ArrayList();
		Pagination page=courseParticipateAuthService.findSingleCourseAuthList(courseId,orderStr,pageNo,SimplePage.DEF_COUNT,keyword);
		if(page.getTotalCount()>0){
			List list = page.getList();
			for(int i=0;i<list.size();i++){
				Object [] obj=(Object[]) list.get(i);
				CourseParticipateAuth cpa =(CourseParticipateAuth)obj[0] ;
				Map mcpa=new HashMap();//授权信息
				mcpa.put("id", cpa.getFdId());
				mcpa.put("time", sdf.format(cpa.getFdCreateTime()));
				Map teacher=new HashMap();//教师信息
				teacher.put("tid", cpa.getFdUser().getFdId());
				teacher.put("imgUrl",  cpa.getFdUser().getPoto());
				teacher.put("link",  "");
				teacher.put("name",cpa.getFdUser().getRealName());
				teacher.put("mail",  cpa.getFdUser().getFdEmail()==null?"":cpa.getFdUser().getFdEmail());
				teacher.put("department",  cpa.getFdUser().getDeptName()==null?"":cpa.getFdUser().getDeptName());
//				teacher.put("org", cpa.getFdUser().getHbmParent().getFdName());
				Map mentor=null;//导师信息
				if(cpa.getFdTeacher()!=null){
					mentor=new HashMap();
					mentor.put("mid", cpa.getFdTeacher().getFdId());
					mentor.put("imgUrl",  cpa.getFdTeacher().getPoto());
					mentor.put("link",  "");
					mentor.put("name",cpa.getFdTeacher().getRealName());
					mentor.put("mail",  cpa.getFdTeacher().getFdEmail()==null?"":cpa.getFdTeacher().getFdEmail());
					mentor.put("department",  cpa.getFdTeacher().getDeptName()==null?"":cpa.getFdTeacher().getDeptName());
//					mentor.put("org", cpa.getFdTeacher().getHbmParent().getFdName());
				}
				mcpa.put("teacher", teacher);
				mcpa.put("mentor", mentor);
				coursepas.add(mcpa);
			}
		}
		boolean isOftask=courseCatalogService.getIsCourseOfTask(courseId);
		Map map=new HashMap();
		map.put("totalPage", page.getTotalPage());
		map.put("currentPage", pageNo);
		map.put("list", coursepas);
		map.put("totalCount", page.getTotalCount());
		map.put("course",courses);
		map.put("StartPage", page.getStartPage());
		map.put("EndPage",page.getEndPage());
		map.put("StartOperate", page.getStartOperate());
		map.put("EndOperate", page.getEndOperate());
		map.put("isOftask", isOftask);//该课程是否包含作业;
		map.put("startNum",page.getStartNum());;
		map.put("endNum", page.getEndNum());
		return JsonUtils.writeObjectToJson(map);
	}
	/**
	 * 某课程授权添加
	 */
	@RequestMapping(value="saveCourseParticipateAuth")
	@ResponseBody
	public boolean saveCourseParticipateAuth(HttpServletRequest request){
		String courseId=request.getParameter("courseId");
		String teacherId=request.getParameter("teacher");
		//查看当前用户是否已授权
		boolean isexist=courseParticipateAuthService.findCouseParticipateAuthById(courseId,teacherId);
		if(isexist){
			String mentorId=request.getParameter("mentor");
			if(StringUtil.isNotEmpty(mentorId)){
				//添加导师角色,如果已存在则不能保存
				if(userRoleService.isEmptyPerson(mentorId, RoleEnum.valueOf("guidance"))){
					userRoleService.addUserRole(mentorId,"guidance");
				}
			}
			CourseInfo courseInfo=courseService.load(courseId);
			SysOrgPerson teacher=accountService.findById(teacherId);
			SysOrgPerson mentor=accountService.findById(mentorId);
			SysOrgPerson authorizer=accountService.findById(ShiroUtils.getUser().getId());
			CourseParticipateAuth cpa=new CourseParticipateAuth();
			cpa.setCourse(courseInfo);
			cpa.setFdUser(teacher);//教师
			cpa.setFdTeacher(mentor);//导师
			cpa.setFdAuthorizer(authorizer);//课程授权人
		    cpa.setFdCreateTime(new Date());
		    cpa.setVersion(0);
		    courseParticipateAuthService.save(cpa);
		}
		return isexist;	
	    
	}
	/**
	 * 根据id删除某课程授权数据
	 * 
	 */
	@RequestMapping(value="deleteCouseParticAuthById")
	@ResponseBody
	public void deleteCouseParticAuthById(HttpServletRequest request){
		String cpaid=request.getParameter("cpaId");
		String[] ids=cpaid.split(",");
		if(ids.length<2){
			courseParticipateAuthService.delete(ids[0]);
		}else{
			for(int i=0;i<ids.length;i++){
				courseParticipateAuthService.delete(ids[i]);
			}
		}
		
	}
	/**
	 * 根据关键字删除某课程授权数据 ,若没有,则删除所有
	 * 
	 */
	@RequestMapping(value="deleteAllCourseParticAuth")
	@ResponseBody
	public void deleteAllCourseParticAuth(HttpServletRequest request){
		String courseId=request.getParameter("courseId");
		String orderStr=request.getParameter("order");
		String pageNostr=request.getParameter("pageNo");
		String keyword=request.getParameter("keyword");
		int pageNo;
		if (StringUtil.isNotBlank(pageNostr)) {
			pageNo = Integer.parseInt(pageNostr);
		} else {
			pageNo = 1;
		}
		Pagination page=courseParticipateAuthService.findSingleCourseAuthList(courseId,orderStr,pageNo,SimplePage.DEF_COUNT,keyword);
		int i = page.getTotalPage();
		if(i>0){
			for(int j=0;j<i;j++){
				page = courseParticipateAuthService.findSingleCourseAuthList(courseId,orderStr,1,SimplePage.DEF_COUNT,keyword);
				List list = page.getList();
				if(list!=null && list.size()>0){
					for(Object obj:list){
						Object [] temp=(Object[]) obj;
						CourseParticipateAuth cpa =(CourseParticipateAuth)temp[0] ;
						courseParticipateAuthService.delete(cpa.getFdId());
					}
				}
			}
		}
	}

	@RequestMapping(value="getMyCoursesIndexInfo")
	@ResponseBody
	public String getMyCoursesIndexInfo(HttpServletRequest request){
		
		Map returnMap = new HashMap();
		String userId = request.getParameter("userId");
		//String type = request.getParameter("type");
		int pageNo = new Integer(request.getParameter("pageNo"));
		String pageSizeString = request.getParameter("pageSize");
		int pageSize=30;
		if(StringUtil.isNotEmpty(pageSizeString)){
			pageSize=Integer.parseInt(pageSizeString);
		}
		String studyType = request.getParameter("studyType");//studing:正在学习；studed:学完；
		String isCompulsoryCourse = request.getParameter("isCompulsoryCourse");
		
		Finder finder = Finder.create("");
		finder.append("select course.fdId id ");
		finder.append("  from IXDF_NTP_COURSE course ");
		finder.append("  left join IXDF_NTP_COURSE_PARTICI_AUTH cpa ");
		finder.append("    on (course.fdId = cpa.fdcourseid and cpa.fduserid ='"+userId+"') ");
		
		finder.append("  left join IXDF_NTP_BAM_SCORE bam ");
		finder.append("    on ( bam.courseId = course.fdid and bam.preTeachId = '"+userId+"'  ) ");
		
		finder.append(" where ( ");
		finder.append("       ((course.isPublish = 'Y' or (course.fdPassword is not null or course.fdPassword != '')) and ");
		finder.append("         ((course.fdId in  ");
		finder.append("                      (select ga.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga  ");
		finder.append("                      where ga.fdgroupid in  ");
		finder.append("                           (select ga.fdgroupid from SYS_ORG_GROUP_ELEMENT soge ,SYS_ORG_ELEMENT soe1org,SYS_ORG_ELEMENT soe2dep,SYS_ORG_ELEMENT soe3per ");
		finder.append("                            where ga.fdgroupid = soge.fd_groupid and (soe1org.fdid = soe2dep.fd_parentid and soe2dep.fdid = soe3per.fd_parentid and soe3per.fdid='"+userId+"' ) and ( soge.fd_elementid = soe1org.fdid or  soge.fd_elementid = soe3per.fdid or  soge.fd_elementid = soe2dep.fdid ) ");
		finder.append("                            ) ");
		finder.append("                       ) ");
		finder.append("          )  ");
		finder.append("          or ");
		finder.append("          ( ");
		finder.append("          course.fdId not in( select ga2.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga2 ) ");
		finder.append("         ) ");
		finder.append("       )) ");
		finder.append("       or (cpa.fduserid = '"+userId+"') ");
		finder.append("       ) ");
		finder.append("   and course.fdStatus = '01' ");
		finder.append("   and course.isAvailable = 'Y' ");
		if(isCompulsoryCourse.equals("required")){
			finder.append(" and (course.isCompulsoryCourse = 'Y' or course.isCompulsoryCourse is null) ");
		}else{
			finder.append(" and course.isCompulsoryCourse = 'N' ");
		}
		/*带分类查询
		 * if(StringUtil.isEmpty(type)){
			finder.append(" and (course.fdcategoryid is null or course.fdcategoryid='')" );
		}else if(!type.equals("all")){
			finder.append(" and course.fdcategoryid=:type" );
			finder.setParam("type", type);
		}	*/
		Finder finderED = Finder.create(finder.getOrigHql()) ;
		Finder finderING =  Finder.create(finder.getOrigHql()) ;
		finderED = finderED.append(" and bam.through = 'Y' ");//学完
		finderING = finderING.append(" and (bam.through = 'N' or bam.through is null ) ");//正在学习
		int edCSum = courseService.getPageBySql(finderED, pageNo, pageSize).getTotalCount();
		int ingCSum = courseService.getPageBySql(finderING, pageNo, pageSize).getTotalCount();
		Finder finderOver;
		if("finish".equals(studyType)){
			finder.append(" and bam.through = 'Y' ");//学完
		}else{
			finder.append(" and (bam.through = 'N' or bam.through is null ) ");//正在学习
		}
		finder.append("   order by course.fdCreateTime desc ");
		Pagination pag=	courseService.getPageBySql(finder, pageNo, pageSize);
		List<Map> courseInfos =  (List<Map>) pag.getList();
		/*if(pag.getTotalPage()>=pageNo){
			if(pag.getList().size()==30){
				returnMap.put("hasMore", true);
			}else{
				returnMap.put("hasMore", false);
			}
		}else{
			returnMap.put("hasMore", false);
		}*/
		List<Map> lists = new ArrayList<Map>();
		if(pag.getTotalPage()>=pageNo){
			for (Map courseInfoMap : courseInfos) {
				CourseInfo courseInfo = courseService.get((String)courseInfoMap.get("ID")); 
				Map map = new HashMap();
				List<AttMain> attMains = attMainService.getAttMainsByModelIdAndModelName(courseInfo.getFdId(), CourseInfo.class.getName());
				map.put("imgUrl", attMains.size()==0?"":(attMains.get(0).getFdId()+"?n="+new Random().nextInt(100)));
				map.put("learnerNum", getLearningTotalNo(courseInfo.getFdId()));
				map.put("name", courseInfo.getFdTitle());
				map.put("id", courseInfo.getFdId());
				map.put("issuer", courseInfo.getFdAuthor()); 
				ScoreStatistics scoreStatistics = scoreStatisticsService.findScoreStatisticsByModelNameAndModelId(CourseInfo.class.getName(), courseInfo.getFdId());
				map.put("score", scoreStatistics==null?0.0:scoreStatistics.getFdAverage());
				map.put("raterNum",  scoreStatistics==null?0:scoreStatistics.getFdScoreNum());
				if(userId.equals(ShiroUtils.getUser().getId())){
					map.put("isme", true);
				}else{
					map.put("isme", false);
				}
				BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseInfo.getFdId());
				if(bamCourse==null){
					map.put("progress", "0%");
					map.put("statusLearning", "not");
				}else{
					map.put("progress", getBamBar(bamCourse.getFdId()));
					if(bamCourse.getThrough()==true){
						map.put("statusLearning", "pass");
					}else{
						map.put("statusLearning", "doing");
					}
				}
				if(courseInfo.getIsPublish()){
					map.put("permission", "open");
				}else{
					if(StringUtil.isEmpty(courseInfo.getFdPassword())){
						map.put("permission", "authorize");
					}else{
						map.put("permission", "encrypt");
					}
				}
				map.put("dataId", courseInfo.getFdId());
				lists.add(map);
			}
		}
		returnMap.put("list", lists);
		returnMap.put("type", "single");
		returnMap.put("edCSum", edCSum);
		returnMap.put("ingCSum", ingCSum);
		returnMap.put("page", pag);
		
		return JsonUtils.writeObjectToJson(returnMap);
	}
	/**
	 * 从课程学习进程中获取当前学习的教师总数
	 * @param courseId 课程ID
	 * @return int 学习的教师总数
	 */
	private int getLearningTotalNo(String courseId) {
		Finder finder = Finder.create(" from BamCourse where courseId = :courseId");
		finder.setParam("courseId", courseId);
		Pagination page = bamCourseService.getPage(finder, 1, 15);
		return page.getTotalCount();
	}
	
	
	/**
	 * 获取用户课程信息
	 * 
	 * 
	 */
	@RequestMapping(value="getUserCourseInfo")
	@ResponseBody
	private String getUserCourseInfo(HttpServletRequest request) {
		Map returnMap = new HashMap();
		String userId = request.getParameter("userId");
		if(userId.equals(ShiroUtils.getUser().getId())){
			returnMap.put("isme", true);
		}else{
			returnMap.put("isme", false);
		}
		SysOrgPerson orgPerson = accountService.load(userId);
		returnMap.put("name", orgPerson.getRealName());
		returnMap.put("img", orgPerson.getPoto());
		returnMap.put("sex", orgPerson.getFdSex());
		returnMap.put("org", orgPerson.getHbmParent()==null?"不详":(orgPerson.getHbmParent().getHbmParentOrg()==null?"不详":orgPerson.getHbmParent().getHbmParentOrg().getFdName()));
		returnMap.put("dep", orgPerson.getDeptName()==null?"不详":orgPerson.getDeptName());
		returnMap.put("tel", orgPerson.getFdWorkPhone()==null?"不详":orgPerson.getFdWorkPhone());
		returnMap.put("bird", orgPerson.getFdBirthDay()==null?"不详":orgPerson.getFdBirthDay());
		returnMap.put("bool", orgPerson.getFdBloodType()==null?"不详":orgPerson.getFdBloodType());
		returnMap.put("selfIntroduction", orgPerson.getSelfIntroduction()==null?"这家伙很懒，也不好好介绍一下自己~":orgPerson.getSelfIntroduction());
		Finder finder1 = Finder.create("select count(*) from BamCourse b where b.preTeachId = :preTeachId and b.through=:through");
		finder1.setParam("preTeachId", userId);
		finder1.setParam("through", true);
		Long finishSum = bamCourseService.findUnique(finder1);
		returnMap.put("finishSum", finishSum);
		Finder finder = Finder.create("");
		finder.append("select count(*) sum ");
		finder.append("  from IXDF_NTP_COURSE course ");
		finder.append("  left join IXDF_NTP_COURSE_PARTICI_AUTH cpa ");
		finder.append("    on (course.fdId = cpa.fdcourseid and cpa.fduserid ='"+userId+"') ");
		finder.append("  left join IXDF_NTP_BAM_SCORE bam ");
		finder.append("    on ( bam.courseId = course.fdid and bam.preTeachId = '"+userId+"'  ) ");
		finder.append(" where ( ");
		finder.append("       ((course.isPublish = 'Y' or (course.fdPassword is not null or course.fdPassword != '')) and ");
		finder.append("         ((course.fdId in  ");
		finder.append("                      (select ga.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga  ");
		finder.append("                      where ga.fdgroupid in  ");
		finder.append("                           (select ga.fdgroupid from SYS_ORG_GROUP_ELEMENT soge ,SYS_ORG_ELEMENT soe1org,SYS_ORG_ELEMENT soe2dep,SYS_ORG_ELEMENT soe3per ");
		finder.append("                            where ga.fdgroupid = soge.fd_groupid and (soe1org.fdid = soe2dep.fd_parentid and soe2dep.fdid = soe3per.fd_parentid and soe3per.fdid='"+userId+"' ) and ( soge.fd_elementid = soe1org.fdid or  soge.fd_elementid = soe3per.fdid or  soge.fd_elementid = soe2dep.fdid ) ");
		finder.append("                            ) ");
		finder.append("                       ) ");
		finder.append("          )  ");
		finder.append("          or ");
		finder.append("          ( ");
		finder.append("          course.fdId not in( select ga2.fdCourseId from IXDF_NTP_COURSE_GROUP_AUTH ga2 ) ");
		finder.append("         ) ");
		finder.append("       )) ");
		finder.append("       or (cpa.fduserid = '"+userId+"') ");
		finder.append("       ) ");
		finder.append("   and bam.through = 'N' ");
		finder.setParam("preTeachId", userId);
		finder.setParam("through", false);
		List quer =bamCourseService.findBySQL(finder.getOrigHql(), null, null);
		int unfinishSum = Integer.parseInt(quer.get(0).toString()) ;
		returnMap.put("unfinishSum",unfinishSum);
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	@RequestMapping(value="getAllCoursesIndexInfo")
	@ResponseBody
	public String getAllCoursesIndexInfo(HttpServletRequest request){
		
		Map returnMap = new HashMap();
		String userId = request.getParameter("userId");
		String type = request.getParameter("type");
		int pageNo = new Integer(request.getParameter("pageNo"));
		Finder finder = Finder.create("");
		finder.append("select c.fdId ID , case when s.fdAverage is null THEN 0 else s.fdAverage end aver from IXDF_NTP_COURSE c left join IXDF_NTP_SCORE_STATISTICS s on c.fdId=s.fdModelId " );
		if(type.equals("all")){
			finder.append(" where c.fdStatus=:fdStatus and c.isAvailable='Y' order by aver desc" );
			finder.setParam("fdStatus", Constant.COURSE_TEMPLATE_STATUS_RELEASE);
		}else if(StringUtil.isEmpty(type)){
			finder.append(" where c.isAvailable='Y' and c.fdStatus=:fdStatus  and (c.fdcategoryid is null or c.fdcategoryid='') order by aver desc" );
			finder.setParam("fdStatus", Constant.COURSE_TEMPLATE_STATUS_RELEASE);
		}else{
			finder.append(" where c.isAvailable='Y' and c.fdStatus=:fdStatus  and c.fdcategoryid=:type  order by aver desc" );
			finder.setParam("fdStatus", Constant.COURSE_TEMPLATE_STATUS_RELEASE);
			finder.setParam("type", type);
		}
		Pagination pag=	courseService.getPageBySql(finder, pageNo, 30);
		List<Map> courseInfos =  (List<Map>) pag.getList();
		if(pag.getTotalPage()>=pageNo){
			if(pag.getList().size()==30){
				returnMap.put("hasMore", true);
			}else{
				returnMap.put("hasMore", false);
			}
		}else{
			returnMap.put("hasMore", false);
		}
		List<Map> lists = new ArrayList<Map>();
		if(pag.getTotalPage()>=pageNo){
			for (Map map1 : courseInfos) {
				CourseInfo courseInfo = courseService.get((String)map1.get("ID"));
				Map map = new HashMap();
				List<AttMain> attMains = attMainService.getAttMainsByModelIdAndModelName(courseInfo.getFdId(), CourseInfo.class.getName());
				map.put("imgUrl", attMains.size()==0?"":(attMains.get(0).getFdId()+"?n="+new Random().nextInt(100)));
				map.put("learnerNum", getLearningTotalNo(courseInfo.getFdId()));
				map.put("name", courseInfo.getFdTitle());
				map.put("issuer", courseInfo.getFdAuthor()); 
				ScoreStatistics scoreStatistics = scoreStatisticsService.findScoreStatisticsByModelNameAndModelId(CourseInfo.class.getName(), courseInfo.getFdId());
				map.put("score", scoreStatistics==null?0:scoreStatistics.getFdAverage());
				map.put("raterNum",  scoreStatistics==null?0:scoreStatistics.getFdScoreNum());
				BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseInfo.getFdId());
				if(bamCourse==null){
					map.put("isLearning", false);
				}else{
					map.put("isLearning", true);
					if(bamCourse.getThrough()==true){
						map.put("isThrough", true);
					}else{
						map.put("isThrough", false);
					}
				}
				map.put("dataId", courseInfo.getFdId());
				lists.add(map);
			}
		}
		returnMap.put("list", lists);
		returnMap.put("type", "single");
		
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	@RequestMapping(value="getCoursesTop5ByScore")
	@ResponseBody
	public String getCoursesTop5ByScore(HttpServletRequest request){
		Map returnMap = new HashMap();
		Finder finder = Finder.create("");
		finder.append("select c.fdId ID,case when s.fdAverage is null THEN 0 else s.fdAverage end aver from IXDF_NTP_COURSE c left join IXDF_NTP_SCORE_STATISTICS s on c.fdId=s.fdModelId where c.fdStatus=:fdStatus and c.isAvailable='Y' order by aver desc" );
		finder.setParam("fdStatus", Constant.COURSE_TEMPLATE_STATUS_RELEASE);
		List<Map> list = (List<Map>) courseService.getPageBySql(finder, 1,5).getList();
		List<Map> list2 = new ArrayList<Map>();
		for (Map map1 : list) {
			Map map = new HashMap();
			List<AttMain> attMains = attMainService.getAttMainsByModelIdAndModelName((String)map1.get("ID"), CourseInfo.class.getName());
			map.put("attId", attMains.size()==0?"":attMains.get(0).getFdId());
			map.put("courseId", (String)map1.get("ID"));
			list2.add(map);
		}
		returnMap.put("list", list2);
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	@RequestMapping(value="getGroupTop10")
	@ResponseBody
	public String getGroupTop10(HttpServletRequest request){
		String key = request.getParameter("q");
		List<SysOrgGroup> groups = sysOrgGroupService.findGroupTop10ByKey(key);
		List<Map> returnList = new ArrayList<Map>();
/*		if("全体教职员工".contains(key)){
			Map mAlll = new HashMap();
			mAlll.put("groupId", "all");
			mAlll.put("groupName", "全体教职员工");
			returnList.add(mAlll);
		}*/
		for (SysOrgGroup sysOrgGroup : groups) {
			Map map = new HashMap();
			map.put("groupName", sysOrgGroup.getFdName());
			map.put("groupId", sysOrgGroup.getFdId());
			returnList.add(map);
		}
		return JsonUtils.writeObjectToJson(returnList);
	}
	
	/**
	 * 根据bamid查看进度条
	 */
	private String getBamBar(String bamId){

		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		Map passMap = studyTrackService.passInfoByBamId(bamCourse.getFdId());
		int sums;//共完成数
		String width="";//百分比
		if(passMap.size()==0){
			sums=0;
			width="0%";
		}else{
			if(passMap.get("coursePass")==null){
				CourseCatalog catalog = (CourseCatalog)passMap.get("courseCatalogNow");//当前环节
				List<CourseCatalog> catalogs =bamCourse.getCatalogs();
				int sum=0;
				int finishSum=0;
				for (int i=0;i< catalogs.size();i++) {
					CourseCatalog courseCatalog = catalogs.get(i);
					if(Constant.CATALOG_TYPE_CHAPTER == courseCatalog.getFdType()){
						continue;
					}
					sum++;
					if(courseCatalog.getThrough()!=null&&courseCatalog.getThrough()){
						finishSum++;
					}
				}
				sums=finishSum;
				width=(finishSum*100/sum)+"%";
			}else{
				width="100%";
			}
		}
		return width;
	
	}
	
	
}

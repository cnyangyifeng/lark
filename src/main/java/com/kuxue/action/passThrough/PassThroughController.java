package com.kuxue.action.passThrough;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.utils.ComUtils;
import com.kuxue.common.utils.array.ArrayUtils;
import com.kuxue.common.utils.array.SortType;
import com.kuxue.model.bam.BamCourse;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.score.ScoreStatistics;
import com.kuxue.service.AccountService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.bam.BamMaterialService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseParticipateAuthService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.course.VisitorService;
import com.kuxue.service.score.ScoreStatisticsService;
import com.kuxue.service.system.CourseSkinService;
import com.kuxue.utils.DateUtil;
import com.kuxue.utils.ShiroUtils;

/**
 * 课程闯关
 * 
 * @author zuoyi
 * 
 */
@Controller
@RequestMapping(value = "/passThrough")
@Scope("request")
public class PassThroughController {

	//课程service
	@Autowired
	private CourseService courseService;
	
	//课程进程service
	@Autowired
	private BamCourseService bamCourseService;
	
	//课程参与权限service
	@Autowired
	private CourseParticipateAuthService courseParticipateAuthService;
	
	//评分统计service
	@Autowired
	private ScoreStatisticsService scoreStatisticsService;
	
	@Autowired
	private BamMaterialService bamMaterialService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private CourseSkinService courseSkinService;
	
	/**
	 * 课程学习首页
	 * @param request
	 */
	@RequestMapping(value = "getCourseHome/{courseId}")
	public String getCourseHome(@PathVariable("courseId") String courseId,HttpServletRequest request) {
		if(StringUtil.isNotEmpty(courseId)){
			CourseInfo course = courseService.get(courseId);
			if(course!=null && course.getIsAvailable()){
				//从进程表中取当前用户所选课程的进程信息
				BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(ShiroUtils.getUser().getId(),course.getFdId());
				List<CourseCatalog> courseCatalogs = new ArrayList<CourseCatalog>();
				if(bamCourse!=null){
					if(bamCourse.getIsUpdate()){
						bamCourse = bamCourseService.updateBamCourse(course, ShiroUtils.getUser().getId());
					}
					//章节信息
					courseCatalogs = bamCourse.getCatalogs();
					if(courseCatalogs!=null){
						ArrayUtils.sortListByProperty(courseCatalogs, "fdTotalNo", SortType.HIGHT);
					}
				}else{
					courseCatalogs = courseCatalogService.getCatalogsByCourseId(course.getFdId());
				}
				if(bamCourse==null){
					request.setAttribute("isLearning", false);
				}else{
					request.setAttribute("isLearning", true);
					if(bamCourse.getThrough()==true){
						request.setAttribute("isThrough", true);
					}else{
						request.setAttribute("isThrough", false);
					}
				}
				request.setAttribute("catalog", courseCatalogs);
				//当前作者的图片(当作者和创建者是相同时候使用创建者的照片)
				if(StringUtil.isEmpty(course.getFdAuthorId())){
					request.setAttribute("imgUrl",ComUtils.getDefaultPoto());
				}else{
					// 作者头像
					SysOrgPerson orgPerson = accountService.get(course.getFdAuthorId());
					if(orgPerson==null){
						request.setAttribute("imgUrl",ComUtils.getDefaultPoto());
					}else{
						request.setAttribute("imgUrl",orgPerson.getPoto());
					}
				}
				//获取当前课程正在学习的新教师
				int studayTotalNo = getLearningTotalNo(course.getFdId());
				request.setAttribute("studayTotalNo", studayTotalNo);
				//获取该课程的评分统计值
				//课程信息
				request.setAttribute("course", course);
				//皮肤信息
				if(course.getFdSkin()!=null){
					request.setAttribute("skinPath", course.getFdSkin().getFdSkinPath());
				}else{
					request.setAttribute("skinPath", courseSkinService.getDefaultSkin(Constant.SKIN_TYPE_COURSE));
				}
				//课程图片
				request.setAttribute("courseAtt", courseService.getCoursePicture(courseId));
				//课程评分统计
				ScoreStatistics scoreStatistics =  scoreStatisticsService.findUniqueByProperty(ScoreStatistics.class,Value.eq("fdModelName", CourseInfo.class.getName()),Value.eq("fdModelId", course.getFdId()));
				request.setAttribute("courseScore", scoreStatistics==null?0.0:scoreStatistics.getFdAverage());
				
			}else{
				//否则需要跳转到发现课程
				return "redirect:/course/courseIndex";
			}
		}else{
			//否则需要跳转到发现课程
			return "redirect:/course/courseIndex";
		}
		return "/passThrough/course_home";
	}
	
	/**
	 * 获取课程学习内容
	 * @param request
	 */
	@RequestMapping(value = "getStudyContent")
	public String getStudyContent(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");
		String catalogId = request.getParameter("catalogId");
		String fdMtype = request.getParameter("fdMtype");
		String fdPassword = request.getParameter("fdPassword");
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);   
        if (map!=null)  {
        	courseId = (String)map.get("courseId");
    		catalogId = (String)map.get("catalogId");
    		fdMtype = (String)map.get("fdMtype");
    		fdPassword = (String)map.get("fdPassword");
        }
		if(StringUtil.isBlank(courseId)){
			return "redirect:/course/courseIndex";
		}
		CourseInfo course = courseService.get(courseId);
		if(!course.getIsPublish()){
			if(course.getFdPassword()!=null){//密码课
				if(!(StringUtil.isNotBlank(fdPassword)&&fdPassword.equals(course.getFdPassword())&&courseParticipateAuthService
						.findAuthByCourseIdAndUserId(courseId,ShiroUtils.getUser().getId()))){
					return "redirect:/passThrough/getCourseHome/"+courseId;
				}
			}else{//授权课
				if(courseParticipateAuthService
						.findCouseParticipateAuthById(courseId,ShiroUtils.getUser().getId())==null){//无权
					return "redirect:/passThrough/getCourseHome/"+courseId;
				}
			}
		}else{
			if(!courseParticipateAuthService.findAuthByCourseIdAndUserId(courseId,ShiroUtils.getUser().getId())){
				return "redirect:/passThrough/getCourseHome/"+courseId;
			}
		}
		BamCourse bamCourse = bamCourseService.
				getCourseByUserIdAndCourseId(ShiroUtils.getUser().getId(),courseId);
		if(bamCourse==null){
			//如果进程信息为空，则先保存进程信息
			bamCourseService.saveBamCourse(course, ShiroUtils.getUser().getId());
			bamCourse = bamCourseService.getCourseByUserIdAndCourseId(ShiroUtils.getUser().getId(),courseId);
		}
		if(StringUtil.isBlank(catalogId)&&StringUtil.isBlank(fdMtype)){
			List<CourseCatalog> courseCatalogs = bamCourse.getCatalogs();
			if(courseCatalogs!=null){
				ArrayUtils.sortListByProperty(courseCatalogs, "fdTotalNo", SortType.HIGHT);
			    /////添加开始学习按钮 找出当前人员学习的当前节
				for (CourseCatalog courseCatalog : courseCatalogs) {
				  if(courseCatalog.getFdType().equals(Constant.CATALOG_TYPE_CHAPTER)){
					  continue;
				  }
				  if(bamCourse.getThrough()){
					 //设置正在学习的当前节
					 request.setAttribute("catalogId", courseCatalog.getFdId());
					 //设置正在学习的当前节的素材类型
					 request.setAttribute("fdMtype", courseCatalog.getFdMaterialType());
					 break;   
				  }
				 
					if(courseCatalog.getThrough()!=null&&courseCatalog.getThrough()){
						continue;
					}else{
						//设置正在学习的当前节
						request.setAttribute("catalogId", courseCatalog.getFdId());
						//设置正在学习的当前节的素材类型
						request.setAttribute("fdMtype", courseCatalog.getFdMaterialType());
						break; 
					}
				 
			   }
			}
		}else{
			//设置正在学习的当前节
			request.setAttribute("catalogId", catalogId);
			request.setAttribute("fdMtype", fdMtype);
		}
		request.setAttribute("courseId", course.getFdId());
		request.setAttribute("bamId", bamCourse.getFdId());
		bamCourseService.updateCourseStartTime(bamCourse.getFdId());
		if(course.getFdSkin()!=null){
			request.setAttribute("skinPath", course.getFdSkin().getFdSkinPath());
		}else{
			request.setAttribute("skinPath", courseSkinService.getDefaultSkin(Constant.SKIN_TYPE_COURSE));
		}
		//页面跳转，跳转到课程学习页面
		return "/passThrough/course_content_study";
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
	 * 提交试题后作业
	 * @param request
	 */
	@RequestMapping(value = "submitExamOrTask")
	public String submitExamOrTask(WebRequest request,RedirectAttributes redirectAttributes) {
		String fdMtype = request.getParameter("fdMtype");
		String catalogId = request.getParameter("catalogId");
		String courseId =request.getParameter("courseId");
		bamMaterialService.saveSourceNode(fdMtype, request);
		redirectAttributes.addFlashAttribute("courseId", courseId);
		redirectAttributes.addFlashAttribute("catalogId", catalogId);
		redirectAttributes.addFlashAttribute("fdMtype", fdMtype);
		CourseInfo course = courseService.get(courseId);
		if(!course.getIsPublish()&&StringUtil.isNotBlank(course.getFdPassword())){
			redirectAttributes.addFlashAttribute("fdPassword", course.getFdPassword());
		}
		return  "redirect:/passThrough/getStudyContent";
	}
	
	/**
	 * 学习心情页面
	 * @param request
	 */
	@RequestMapping(value = "getCourseFeeling")
	public String getCourseFeeling(HttpServletRequest request) {
		String userId="";
		if (StringUtil.isEmpty(request.getParameter("userId"))) {
			userId = ShiroUtils.getUser().getId();
		} else {
			userId = request.getParameter("userId");
		}
		request.setAttribute("userId", userId);
		request.setAttribute("courseId", request.getParameter("courseId"));
		request.setAttribute("isMe", userId.equals(ShiroUtils.getUser().getId()));
		BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, request.getParameter("courseId"));
		if(bamCourse==null){
			return "/course/course_index";
		}else{
			visitorService.saveVisitor(ShiroUtils.getUser().getId(),bamCourse.getFdId());
			return "/passThrough/course_feeling";
		}
		
	}
	/**
	 * 学习心情页面
	 * @param request
	 */
	@RequestMapping(value = "getCourseFeelingByBamId")
	public String getCourseFeelingByBamId(HttpServletRequest request){
		String bamId=request.getParameter("bamId");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		request.setAttribute("userId", bamCourse.getPreTeachId());
		request.setAttribute("courseId", bamCourse.getCourseId());
		request.setAttribute("isMe", bamCourse.getPreTeachId().equals(ShiroUtils.getUser().getId()));
		visitorService.saveVisitor(ShiroUtils.getUser().getId(),bamId);
		return "/passThrough/course_feeling";
	}
	
	/**
	 * 证书页面
	 * @param request
	 */
	@RequestMapping(value = "getCertificate")
	public String getCertificate(HttpServletRequest request){
		String bamId=request.getParameter("bamId");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		
        CourseInfo courseInfo = courseService.get(bamCourse.getCourseInfo().getFdId());
        SysOrgPerson person = courseInfo.getCreator();
        SysOrgPerson orgPerson = accountService.get(bamCourse.getPreTeachId());
        String rootUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        String imgUrl = "";
        if(bamCourse.getThrough()==false){
			return "/passThrough/course_feeling?courseId="+courseInfo.getFdId()+"&userId="+orgPerson.getFdId();
		}
        if (orgPerson.getPoto().indexOf("http") > -1) {
            imgUrl = orgPerson.getPoto();
        } else {
            imgUrl = rootUrl + orgPerson.getPoto();
        }
        request.setAttribute("imgUrl", imgUrl);
        request.setAttribute("userName", orgPerson.getRealName());
        request.setAttribute("date", DateUtil.convertDateToString(((bamCourse.getEndDate() == null) ? new Date() : bamCourse.getEndDate())));
        request.setAttribute("eName", orgPerson.getLoginName());
        String aId = courseInfo.getFdAuthorId();
		if(StringUtil.isEmpty(aId)){
			request.setAttribute("dep", person.getDeptName());
		}else{
			SysOrgPerson person2 = accountService.get(aId);
			request.setAttribute("dep", person2.getDeptName());
		}
        request.setAttribute("til", courseInfo.getFdTitle());
		return "/passThrough/certificate";
	}
}

package cn.me.xdf.action.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.utils.ComUtils;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseAuth;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.course.CourseAuthService;
import cn.me.xdf.service.course.CourseCatalogService;
import cn.me.xdf.service.course.CourseParticipateAuthService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.service.score.ScoreStatisticsService;
import cn.me.xdf.utils.ShiroUtils;

/**
 * 课程
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/course")
@Scope("request")
public class CourseController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private AttMainService attMainService;

	@Autowired
	private CourseCatalogService courseCatalogService;

	@Autowired
	private CourseAuthService courseAuthService;

	@Autowired
	private ScoreStatisticsService statisticsService;

	@Autowired
	private CourseParticipateAuthService courseParticipateAuthService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BamCourseService bamCourseService;

	/*
	 * 
	 * 获取课程列表 author hanhl
	 */
	@RequestMapping(value = "findcourseInfos")
	public String findcourseInfos(Model model, HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = courseService.findCourseInfosByName(fdTitle,
				pageNoStr, orderbyStr, Constant.COUSER_TEMPLATE_MANAGE);
		model.addAttribute("page", page);
		return "/course/course_list";
	}

	@RequestMapping(value = "add")
	public String editCourse(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null) {
				request.setAttribute("course", course);
			}
		}
		return "/course/course_add";
	}

	/**
	 * 发布课程
	 * 
	 * @param request
	 */
	@RequestMapping(value = "releaseCourse")
	public String releaseCourse(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null)
			{
				if(Constant.COURSE_TEMPLATE_STATUS_DRAFT.equals(course
						.getFdStatus())){
					course.setFdStatus(Constant.COURSE_TEMPLATE_STATUS_RELEASE);
					courseService.save(course);
				}else{
					//如果是已经发布的课程再次发布需要标识进程中相应的课程为已更新
					bamCourseService.setCourseIsUpdate(courseId);
				}
			}
		}
		return "redirect:/course/findcourseInfos?fdType=12&order=fdcreatetime";
	}

	/**
	 * 预览课程
	 * 
	 * @param request
	 */
	@RequestMapping(value = "previewCourse")
	public String previewCourse(HttpServletRequest request) {
		// 获取课程ID
		String courseId = request.getParameter("courseId");
		if (StringUtil.isNotEmpty(courseId)) {
			CourseInfo course = courseService.get(courseId);
			if (course != null) {
				List<CourseCatalog> catalog = courseCatalogService
						.getCatalogsByCourseId(courseId);
				request.setAttribute("course", course);
				request.setAttribute("catalog", catalog);
				// 课程图片
				AttMain attMain = attMainService.getByModelIdAndModelName(
						courseId, CourseInfo.class.getName());
				request.setAttribute("courseAtt",
						attMain != null ? attMain.getFdId() : "");
				// 当前作者的图片(当作者和创建者是相同时候使用创建者的照片)
				if (course.getFdAuthor()!=null&&course.getFdAuthor().equals(
						course.getCreator().getRealName())) {
					request.setAttribute("imgUrl", course.getCreator()
							.getPoto());
				} else {
					request.setAttribute("imgUrl", ComUtils.getDefaultPoto());
				}
			}
		}
		return "/course/course_preview";
	}

	/**
	 * 课程管理页面跳转
	 */
	@RequestMapping(value = "pagefoward")
	public String pagefoward(HttpServletRequest request) {
		String courseId = request.getParameter("courseId");
		if (getUserAuth(courseId)||ShiroUtils.isAdmin()) {
			return "redirect:/course/add?courseId=" + courseId;// 有编辑权限(当前用户是课程创建者)到编辑页面
		} else {
			return "redirect:/course/previewCourse?courseId=" + courseId;// 到课程预览页面
		}
	}

	/**
	 * 查询当前用户课程权限
	 */
	private Boolean getUserAuth(String courseId) {
		if (ShiroUtils.isAdmin()) {// 超管直接到edit
			return true;
		}
		CourseInfo courseInfo = courseService.load(courseId);
		if (courseInfo.getCreator().getFdId()
				.equals(ShiroUtils.getUser().getId())) {// 当前创建者
			return true;
		}
		CourseAuth auth = courseAuthService.findByCourseIdAndUserId(courseId,
				ShiroUtils.getUser().getId());// 有编辑权限
		if (auth != null && auth.getIsEditer() == true) {
			return true;
		}
		return false;// 公开的
	}

	/**
	 * 课程授权列表 author hanhl
	 */
	@RequestMapping(value = "getCourseAuthInfos")
	public String getCourseAuthInfos(Model model, HttpServletRequest request) {
		String fdTitle = request.getParameter("fdTitle");
		String pageNoStr = request.getParameter("pageNo");
		String orderbyStr = request.getParameter("order");
		Pagination page = courseService.findCourseInfosByName(fdTitle,
				pageNoStr, orderbyStr, Constant.COUSER_AUTH_MANAGE);
		model.addAttribute("page", page);
		return "/course/courseauth_list";

	}

	/**
	 * 某课程授权
	 */
	@RequestMapping(value = "getSingleCourseAuthInfo")
	public String getCourseAuthInfo(HttpServletRequest request) {
		return "/course/courseauth_manage";
	}

	@RequestMapping(value = "courseIndex")
	public String courseIndex(HttpServletRequest request) {
		if (StringUtil.isEmpty(request.getParameter("userId"))) {
			request.setAttribute("userId", ShiroUtils.getUser().getId());
			request.setAttribute("isMe", true);
		} else {
			request.setAttribute("userId", request.getParameter("userId"));
			if(ShiroUtils.getUser().getId().equals(request.getParameter("userId"))){
				request.setAttribute("isMe", true);
			}else{
				request.setAttribute("isMe", false);
			}
		}
		return "/course/course_index";
	}

	@RequestMapping(value = "courseIndexAll")
	public String courseIndexAll(HttpServletRequest request) {
		request.setAttribute("userId", ShiroUtils.getUser().getId());
		return "/course/course_index_all";
	}
}

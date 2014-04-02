package com.kuxue.action.studyTrack;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuxue.service.AccountService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.studyTack.StudyTrackService;
/**
 * 学习跟踪
 * 
 * @author zhaoq
 * 
 */
@Controller
@RequestMapping(value = "/studyTrack")
public class StudyTrackController {
	
	@Autowired
	private StudyTrackService studyTrackService;

	@Autowired
	private BamCourseService bamCourseService ;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private AttMainService attMainService;
	
	/**
	 * 学习跟踪首页(我是主管)
	 * 
	 * @param request
	 */
	@RequestMapping(value = "getStudyTrackDirector")
	public String getStudyTrackDirector(HttpServletRequest request) {
		return "/studyTrack/study_track_director";
	}
	
	/**
	 * 学习跟踪首页(我是导师)
	 * 
	 * @param request
	 */
	@RequestMapping(value = "getStudyTrackTutor")
	public String getStudyTrackTutor(HttpServletRequest request) {
		return "/studyTrack/study_track_tutor";
	}

}

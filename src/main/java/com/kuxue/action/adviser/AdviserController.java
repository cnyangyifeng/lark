package com.kuxue.action.adviser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.common.utils.excel.AbsExportExcel;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.material.Task;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.process.SourceNote;
import com.kuxue.service.AccountService;
import com.kuxue.service.adviser.AdviserService;
import com.kuxue.service.bam.process.SourceNodeService;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.ShiroUtils;
import com.kuxue.view.model.VCheckTaskData;

@Controller
@RequestMapping(value = "/adviser")
@Scope("request")
public class AdviserController {
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private AdviserService adviserService;
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseCatalogService courseCatalogService;
	
	/**返回批改作业详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkTaskDetail")
	public String checkTaskDetail(){
		return "/adviser/checkTaskDetail";
	}
	
	/**返回批改作业详情页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTaskDetail")
	public String getTaskDetail(){
		return "/adviser/checkTaskDetail";
	}
	
	/**
	 * 返回到时批改作业页面
	 * @return
	 */
	@RequestMapping(value = "checkTask")
	public String checkTask(){
		return "/adviser/checkTask";
	}

}

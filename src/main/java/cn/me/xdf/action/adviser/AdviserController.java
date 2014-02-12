package cn.me.xdf.action.adviser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.page.SimplePage;
import cn.me.xdf.common.utils.excel.AbsExportExcel;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.material.Task;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.process.SourceNote;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.adviser.AdviserService;
import cn.me.xdf.service.bam.process.SourceNodeService;
import cn.me.xdf.service.course.CourseCatalogService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.utils.ShiroUtils;
import cn.me.xdf.view.model.VCheckTaskData;

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
	
	/**
	 * 返回到时批改作业页面
	 * @return
	 */
	@RequestMapping(value = "checkTask")
	public String checkTask(){
		return "/adviser/checkTask";
	}

}

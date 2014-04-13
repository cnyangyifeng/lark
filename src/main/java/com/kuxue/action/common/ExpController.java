package com.kuxue.action.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuxue.common.download.DownloadHelper;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.common.utils.Zipper;
import com.kuxue.common.utils.array.ArrayUtils;
import com.kuxue.common.utils.array.SortType;
import com.kuxue.common.utils.excel.AbsExportExcel;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.material.ExamOpinion;
import com.kuxue.model.material.ExamQuestion;
import com.kuxue.model.material.MaterialAuth;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.material.Task;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.SysOrgPersonService;
import com.kuxue.service.adviser.AdviserService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseParticipateAuthService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.log.LogAppService;
import com.kuxue.service.log.LogLoginService;
import com.kuxue.service.log.LogLogoutService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.service.studyTack.StudyTrackService;
import com.kuxue.utils.DateUtil;
import com.kuxue.utils.ShiroUtils;
import com.kuxue.view.model.VCheckTaskData;
import com.kuxue.view.model.VExamOpinion;
import com.kuxue.view.model.VExamPaperData;
import com.kuxue.view.model.VExamQuestion;
import com.kuxue.view.model.VLogData;
import com.kuxue.view.model.VMaterialData;
import com.kuxue.view.model.VStudyTrack;
import com.kuxue.view.model.VTask;
import com.kuxue.view.model.VTaskPaperAuth;
import com.kuxue.view.model.VTaskPaperData;
import com.kuxue.view.model.VUserData;


/**
 * excel导出
 * 
 * @author zq
 *
 */
@Controller
@RequestMapping("/common/exp")
public class ExpController {

	private static final Logger log = LoggerFactory.getLogger(ExpController.class);

    @Autowired
    private AttMainService attMainService;
	
	@Autowired
	private StudyTrackService studyTrackService;
	
    @Autowired
    private BamCourseService bamCourseService;
    
    @Autowired
    private AccountService accountService;
	
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private AdviserService adviserService;
    
    @Autowired
    private MaterialService materialService;
    
    @Autowired
    private SysOrgPersonService sysOrgPersonService;
    
    @Autowired
    private LogLoginService logLoginService;
    
    @Autowired
    private LogLogoutService logLogoutService;
    
    @Autowired
    private LogAppService logAppService;
    
    @Autowired
	private CourseParticipateAuthService courseParticipateAuthService;
    
    public SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd h:m:s a");
    
    @RequestMapping(value = "/getExpExamPaper/{id}")
    public String getExpExamPaper(@PathVariable("id") String id,
    		HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	MaterialInfo info = materialService.get(id);
    	List<VExamPaperData> examPaperList = new ArrayList<VExamPaperData>();	
    	VExamPaperData data = new VExamPaperData();
    	data.setFdAuthor(info.getFdAuthor());//作者
    	data.setFdAuthorDescription(info.getFdAuthorDescription());//作者简介
    	data.setFdStudyTime(info.getFdStudyTime());//建议学习时间
    	data.setFdName(info.getFdName());//作业包名
    	data.setFdDescription(info.getFdDescription()==null?"":info.getFdDescription());//作业包简介
    	data.setFdCreateTime(sdf.format(info.getFdCreateTime()));//创建时间
    	data.setCreatorName(info.getCreator().getRealName());//创建者
    	List<VExamQuestion> vExamList = new ArrayList<VExamQuestion>();
    	List<ExamQuestion> examList = info.getQuestions();
    	ArrayUtils.sortListByProperty(examList, "fdOrder", SortType.HIGHT); 
    	Integer totalScore = 0;
    	for (ExamQuestion examQuestion : examList) {
    		VExamQuestion question = new VExamQuestion();
    		question.setFdStandardScore(examQuestion.getFdStandardScore().intValue());//标准分
    		totalScore += examQuestion.getFdStandardScore().intValue();//总分
    		question.setFdSubject(examQuestion.getFdSubject());//简介
    		question.setFdOrder(examQuestion.getFdOrder()+1);
    		Integer fdType = examQuestion.getFdType();
    		if(fdType.equals(Constant.EXAM_QUESTION_SINGLE_SELECTION)){
    			question.setFdType("单选题");
    			question.setFdQuestion("");
    		}else if(fdType.equals(Constant.EXAM_QUESTION_MULTIPLE_SELECTION)){
    			question.setFdType("多选题");
    			question.setFdQuestion("");
    		}else if(fdType.equals(Constant.EXAM_QUESTION_CLOZE)){
    			question.setFdType("填空题");
    			question.setFdQuestion(examQuestion.getFdQuestion());
    		}
    		List<VExamOpinion> vOpinionList = new ArrayList<VExamOpinion>();
			List<ExamOpinion> opinion = examQuestion.getOpinions();
    		ArrayUtils.sortListByProperty(opinion, "fdOrder", SortType.HIGHT); 
    		for (ExamOpinion examOpinion : opinion) {
    			VExamOpinion vOpinion = new VExamOpinion();
    			vOpinion.setIsAnswer(examOpinion.getIsAnswer()==true?"是":"否");
    			vOpinion.setOpinion(examOpinion.getOpinion());
    			vOpinionList.add(vOpinion);
			}
    		question.setOpinions(vOpinionList);	
    		vExamList.add(question);
		}
    	data.setQuestions(vExamList);//设置试题
    	data.setTotalScore(totalScore);//总分
    	data.setTotalExam(examList.size());//题数
    	data.setPassScore(info.getFdScore().intValue());//及格分
    	List<VTaskPaperAuth> taskPaperAuthList = new ArrayList<VTaskPaperAuth>();
    	List<MaterialAuth> authList = info.getAuthList();//权限列表
    	data.setAuthCategory(info.getIsPublish()==true?"公开":"加密");//权限
    	for (MaterialAuth materialAuth : authList) {
    		VTaskPaperAuth auth = new VTaskPaperAuth();
    		auth.setFdName(materialAuth.getFdUser().getRealName());
    		auth.setDept(materialAuth.getFdUser().getDeptName());
    		auth.setIsEditer(materialAuth.getIsEditer()==true?"是":"否");
    		auth.setIsReader(materialAuth.getIsReader()==true?"是":"否");
    		taskPaperAuthList.add(auth);
		}
    	if(taskPaperAuthList!=null&&!taskPaperAuthList.isEmpty()){
    		data.setTaskPaperAuth(taskPaperAuthList);
    	}
    	examPaperList.add(data);
    	if(info.getIsPublish()){
    		exportExcel(examPaperList, "examPaperDetail.xls", response,info.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}else{
    		exportExcel(examPaperList, "examPaperEncrypt.xls", response,info.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}
    	
    	return null;
    }
    /**
     * 导出单个作业包信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpTaskPaper/{id}")
    public String getExpTaskPaper(@PathVariable("id") String id,
    		HttpServletRequest request,HttpServletResponse response){
    	MaterialInfo info = materialService.get(id);
    	Date date = new Date();
    	List<VTaskPaperData> studyTrackList = new ArrayList<VTaskPaperData>();	
    	VTaskPaperData data = new VTaskPaperData();
    	data.setFdAuthor(info.getFdAuthor());//作者
    	data.setFdAuthorDescription(info.getFdAuthorDescription());//作者简介
    	data.setFdStudyTime(info.getFdStudyTime());//建议学习时间
    	data.setTaskPaperName(info.getFdName());//作业包名
    	data.setTaskPaperDescription(info.getFdDescription()==null?"":info.getFdDescription());//作业包简介
    	data.setFdCreateTime(sdf.format(info.getFdCreateTime()));//创建时间
    	data.setCreatorName(info.getCreator().getRealName());//创建者
    	List<VTask> vtaskList = new ArrayList<VTask>();
    	List<Task> taskList = info.getTasks();
    	ArrayUtils.sortListByProperty(taskList, "fdOrder", SortType.HIGHT); 
    	for (Task task : taskList) {
			VTask vtask = new VTask();
			vtask.setFdName(task.getFdName());
			vtask.setFdOrder(task.getFdOrder()+1);
			vtask.setFdSubject(task.getFdSubject());
			vtask.setFdStandardScore(task.getFdStandardScore());
			if(task.getFdType().equals(Constant.TASK_TYPE_UPLOAD)){
				vtask.setFdType("上传作业");
			}else{
				vtask.setFdType("在线作答");
			}
			vtaskList.add(vtask);
		}
    	data.setTasks(vtaskList);//作业list
    	Integer totalScore = 0;
    	for (Task task : taskList) {
    		totalScore += task.getFdStandardScore().intValue();
		}
    	data.setTotalScore(totalScore);//总分
    	data.setTotalTask(taskList.size());//题数
    	data.setPassScore(info.getFdScore().intValue());//及格分
    	List<VTaskPaperAuth> taskPaperAuthList = new ArrayList<VTaskPaperAuth>();
    	List<MaterialAuth> authList = info.getAuthList();//权限列表
    	data.setAuthCategory(info.getIsPublish()==true?"公开":"加密");//权限
    	for (MaterialAuth materialAuth : authList) {
    		VTaskPaperAuth auth = new VTaskPaperAuth();
    		auth.setFdName(materialAuth.getFdUser().getRealName());
    		auth.setDept(materialAuth.getFdUser().getDeptName());
    		auth.setIsEditer(materialAuth.getIsEditer()==true?"是":"否");
    		auth.setIsReader(materialAuth.getIsReader()==true?"是":"否");
    		taskPaperAuthList.add(auth);
		}
    	if(taskPaperAuthList!=null&&!taskPaperAuthList.isEmpty()){
    		data.setTaskPaperAuth(taskPaperAuthList);
    	}
    	studyTrackList.add(data);
    	if(info.getIsPublish()){
    		exportExcel(studyTrackList, "taskPaperDetail.xls", response,"素材作业包-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}else{
    		exportExcel(studyTrackList, "taskPaperEncrypt.xls", response,"素材作业包-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}
    	return null;
    }
    @RequestMapping(value = "/getExpAllCourseAuth")
	public String getExpAllCourseAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String fdTitle = request.getParameter("fdTitle");
    	String order = request.getParameter("order");
    	Pagination page = courseService.findCourseInfosByName(fdTitle,
    			Integer.toString(1), order, Constant.COUSER_AUTH_MANAGE,500);
    	if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
			List list = courseService.findAllCourseInfoAuth(page.getList());
			exportExcel(list, "courseAllAuth.xls", response,"全部课程授权统计报表-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
		}else if(page.getTotalPage()>1){
			String [] attMainIds= new String[page.getTotalPage()];
			for(int i=1;i<=page.getTotalPage();i++){
				Pagination pageZip = courseService.findCourseInfosByName(fdTitle,
		    			Integer.toString(i), order, Constant.COUSER_AUTH_MANAGE,500);
				AttMain attMain = AbsExportExcel.exportExcels(courseService.findAllCourseInfoAuth(pageZip.getList()), "courseAllAuth.xls");
				attMainService.save(attMain);
				attMainIds[i-1] = attMain.getFdId();
			}
			try {
				downloadZipsByArrayIds(attMainIds, "courseAllAuth.xls", request, response);
			} catch (UnsupportedEncodingException e) {
				  log.error("export excleZip error!", e);
			}
			//删除下载后的无用附件
			attMainService.deleteAttMainByIds(attMainIds);
		}
    	return null;
    }
    
    /**
     * 按新教师导出全部授权列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpAllTeacherAuth")
	public String getExpAllTeacherAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String fdTitle = request.getParameter("fdTitle");
    	String order = request.getParameter("order");
    	Pagination page = courseService.findTeachersByName(fdTitle,
    			Integer.toString(1), order,500);
    	if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
			List list = courseService.findAllTeacherAuth(page.getList());
			exportExcel(list, "teacherAllAuth.xls", response,"按新教师全部授权学习统计报表-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
		}else if(page.getTotalPage()>1){
			String [] attMainIds= new String[page.getTotalPage()];
			for(int i=1;i<=page.getTotalPage();i++){
				Pagination pageZip = courseService.findTeachersByName(fdTitle,
						Integer.toString(i), order, 500);
				AttMain attMain = AbsExportExcel.exportExcels(courseService.findAllTeacherAuth(pageZip.getList()), "teacherAllAuth.xls");
				attMainService.save(attMain);
				attMainIds[i-1] = attMain.getFdId();
			}
			try {
				downloadZipsByArrayIds(attMainIds, "teacherAllAuth.xls", request, response);
			} catch (UnsupportedEncodingException e) {
				  log.error("export excleZip error!", e);
			}
			//删除下载后的无用附件
			attMainService.deleteAttMainByIds(attMainIds);
		}
    	return null;
    }
    
    /**
     * 按导师导出全部授权列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpAllTutorAuth")
	public String getExpAllTutorAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String fdTitle = request.getParameter("fdTitle");
    	String order = request.getParameter("order");
    	Pagination page = courseService.findTutorsByName(fdTitle,
    			Integer.toString(1), order,500);
    	if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
			List list = courseService.findAllTutorAuth(page.getList());
			exportExcel(list, "tutorAllAuth.xls", response,"按导师全部授权学习统计报表-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
		}else if(page.getTotalPage()>1){
			String [] attMainIds= new String[page.getTotalPage()];
			for(int i=1;i<=page.getTotalPage();i++){
				Pagination pageZip = courseService.findTutorsByName(fdTitle,
						Integer.toString(i), order, 500);
				AttMain attMain = AbsExportExcel.exportExcels(courseService.findAllTutorAuth(pageZip.getList()), "tutorAllAuth.xls");
				attMainService.save(attMain);
				attMainIds[i-1] = attMain.getFdId();
			}
			try {
				downloadZipsByArrayIds(attMainIds, "tutorAllAuth.xls", request, response);
			} catch (UnsupportedEncodingException e) {
				  log.error("export excleZip error!", e);
			}
			//删除下载后的无用附件
			attMainService.deleteAttMainByIds(attMainIds);
		}
    	return null;
    }
    
    /**
     * 导出授权列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpCourseAuth")
	public String getExpCourseAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String currentPage = request.getParameter("currentPage");
    	String order = request.getParameter("order");
    	String courseId = request.getParameter("courseId");
    	String isAll = request.getParameter("isAll");
    	CourseInfo info = courseService.get(courseId);
    	List list = new ArrayList();
    	if(StringUtil.isBlank(isAll)){
    		Pagination page = courseParticipateAuthService
        			.findSingleCourseAuthList(courseId, order, Integer.parseInt(currentPage),SimplePage.DEF_COUNT, null);
    		list = courseService.findCourseInfoAuth(page.getList());
    		exportExcel(list, "courseAuth.xls", response,"课程授权统计报表-"+info.getFdTitle()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}else{
    		Pagination page = courseParticipateAuthService
        			.findSingleCourseAuthList(courseId, order, 1,5000, null);
    		if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
    			list = courseService.findCourseInfoAuth(page.getList());
    			exportExcel(list, "courseAuth.xls",response, "课程授权统计报表-"+info.getFdTitle()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    		}else if(page.getTotalPage()>1){
    			String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip = courseParticipateAuthService
		        			.findSingleCourseAuthList(courseId, order, i,5000, null);
					AttMain attMain = AbsExportExcel.exportExcels(courseService.findCourseInfoAuth(pageZip.getList()), "courseAuth.xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "courseAuth.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
    		}
    	}
    	
    	return null;
	}
    
    
    /**
     * 导出按新教师授权的列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpTeacherAuth")
	public String getExpTeacherAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String currentPage = request.getParameter("currentPage");
    	String order = request.getParameter("order");
    	String teacherId = request.getParameter("teacherId");
    	String isAll = request.getParameter("isAll");
    	SysOrgPerson teacher = accountService.load(teacherId);
    	List list = new ArrayList();
    	if(StringUtil.isBlank(isAll)){
    		Pagination page = courseParticipateAuthService
        			.findSingleTeacherAuthList(teacherId, order, Integer.parseInt(currentPage),SimplePage.DEF_COUNT, null);
    		list = courseService.findCourseInfoAuth(page.getList());
    		exportExcel(list, "teacherAuth.xls", response,"按新教师授权学习统计报表-"+teacher.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}else{
    		Pagination page = courseParticipateAuthService
        			.findSingleTeacherAuthList(teacherId, order, 1,5000, null);
    		if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
    			list = courseService.findCourseInfoAuth(page.getList());
    			exportExcel(list, "teacherAuth.xls",response, "按新教师授权学习统计报表-"+teacher.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    		}else if(page.getTotalPage()>1){
    			String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip = courseParticipateAuthService
		        			.findSingleTeacherAuthList(teacherId, order, i,5000, null);
					AttMain attMain = AbsExportExcel.exportExcels(courseService.findCourseInfoAuth(pageZip.getList()), "teacherAuth.xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "teacherAuth.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
    		}
    	}
    	
    	return null;
	}
    
    /**
     * 导出按导师授权的列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getExpTutorAuth")
	public String getExpTutorAuth(HttpServletRequest request,HttpServletResponse response){
    	Date date = new Date();
    	String currentPage = request.getParameter("currentPage");
    	String order = request.getParameter("order");
    	String teacherId = request.getParameter("teacherId");
    	String isAll = request.getParameter("isAll");
    	SysOrgPerson teacher = accountService.load(teacherId);
    	List list = new ArrayList();
    	if(StringUtil.isBlank(isAll)){
    		Pagination page = courseParticipateAuthService
        			.findSingleTutorAuthList(teacherId, order, Integer.parseInt(currentPage),SimplePage.DEF_COUNT, null);
    		list = courseService.findCourseInfoAuth(page.getList());
    		exportExcel(list, "tutorAuth.xls", response,"按导师授权学习统计报表-"+teacher.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    	}else{
    		Pagination page = courseParticipateAuthService
        			.findSingleTutorAuthList(teacherId, order, 1,5000, null);
    		if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
    			list = courseService.findCourseInfoAuth(page.getList());
    			exportExcel(list, "tutorAuth.xls",response, "按导师授权学习统计报表-"+teacher.getFdName()+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
    		}else if(page.getTotalPage()>1){
    			String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip = courseParticipateAuthService
		        			.findSingleTutorAuthList(teacherId, order, i,5000, null);
					AttMain attMain = AbsExportExcel.exportExcels(courseService.findCourseInfoAuth(pageZip.getList()), "tutorAuth.xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "tutorAuth.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
    		}
    	}
    	
    	return null;
	}
    
    /**
	 * 导出学习跟踪excel方法
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getExpStudyTrack")
	public String getExpStudyTrack(HttpServletRequest request,HttpServletResponse response){
		String selectType = request.getParameter("selectType");
		String orderType = request.getParameter("order");
		String key = request.getParameter("key");
		String isAll = request.getParameter("isAll");
		Pagination page = studyTrackService.getStudyTrack(selectType, ShiroUtils.getUser().getId(), 1, 5000, orderType, key);
		if("noPage".equals(isAll)){//根据bamId进行导出
			String [] modeiIds = request.getParameter("modelIds").split(",");
			List<VStudyTrack> list = studyTrackService.buildStudyTrackList(modeiIds);
			String userName = ((SysOrgPerson)accountService.load(ShiroUtils.getUser().getId())).getFdName();
			Date date = new Date();
			
			exportExcel(list, "studyTrack.xls", response,"课程学习跟踪统计报表-"+userName+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
		}else{//全部导出
			if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
				List<VStudyTrack> list = studyTrackService.buildStudyTrackList(page.getList());
				String userName = ((SysOrgPerson)accountService.load(ShiroUtils.getUser().getId())).getFdName();
				Date date = new Date();
				exportExcel(list, "studyTrack.xls", response,"课程学习跟踪统计报表-"+userName+"-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
			}else if(page.getTotalPage()>1){//全部导出（导出多个模板，需要打包）
				String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip =studyTrackService.getStudyTrack(selectType, ShiroUtils.getUser().getId(), i, 5000, orderType, key);
					AttMain attMain = AbsExportExcel.exportExcels(studyTrackService.buildStudyTrackList(pageZip.getList()), "studyTrack.xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "studyTrack.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
			}
		}
		return null;
	}
	/**
	 * 导出作业包xls（导出）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getExportAdviserTask")
    public String getExportAdviserTask(HttpServletRequest request,HttpServletResponse response){
		String isAll = request.getParameter("isAll");
		String fdType = request.getParameter("fdType");
		String fdName = request.getParameter("fdName");
		String order = request.getParameter("order");
		String name = "";
		if(fdType.equals("checked")){
			name = "已批改";
		}else{
			name = "未批改";
		}
		if("noPage".equals(isAll)){
			String [] modelIds = request.getParameter("modelIds").split(",");
			List<VCheckTaskData> adviserList = adviserService.findCheckDataList(modelIds, fdType);
			AbsExportExcel.exportExcel(adviserList, fdType+"Data.xls", response,"我审批的作业统计表-"+name+".xls");
		} else {
			Pagination page = adviserService.findAdivserCouserList(fdType, 1, 5000, fdName, order);
			if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
				List<VCheckTaskData> adviserList = adviserService.findCheckDataByPageList(page.getList());
				AbsExportExcel.exportExcel(adviserList, fdType+"Data.xls", response,"我审批的作业统计表-"+name+".xls");
			}else if(page.getTotalPage()>1){//全部导出（导出多个模板，需要打包）
				String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip = adviserService.findAdivserCouserList(fdType, 1, 5000, fdName, order);
					AttMain attMain = AbsExportExcel.exportExcels(adviserService.findCheckDataByPageList(pageZip.getList()), "我审批的作业统计表-"+name+".xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, fdType+"Data.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
			}
		}
    	return null;
    }
	
	/**
	 * 导出日志xls（导出）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getExpLog")
    public String getExpLog(HttpServletRequest request,HttpServletResponse response){
		String fdType = request.getParameter("fdType");
		String [] ids =request.getParameterValues("ids");
		String fdKey = request.getParameter("fdKey");
		String isAll = request.getParameter("selectCheckbox");
		if(isAll!=null&&isAll.equals("all")){//导出全部
			Pagination page = null;
			if(fdType.equals("LogLogin")){
				page = logLoginService.findVLogDataPagination(fdKey, 1, 5000);
			}else if(fdType.equals("LogLogout")){
				page = logLogoutService.findVLogDataPagination(fdKey, 1, 5000);
			}else if(fdType.equals("LogApp")){
				page = logAppService.findVLogDataPagination(fdKey, 1, 5000);
			}
			if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
				List<VLogData> logDatas = null;
				if(fdType.equals("LogLogin")){
					logDatas = logLoginService.findVLogDataByPagination(page);
				}else if(fdType.equals("LogLogout")){
					logDatas = logLogoutService.findVLogDataByPagination(page);
				}else if(fdType.equals("LogApp")){
					logDatas = logAppService.findVLogDataByPagination(page);
				}
				AbsExportExcel.exportExcel(logDatas, "log.xls", response,"日志导出表.xls");
			}else if(page.getTotalPage()>1){//全部导出（导出多个模板，需要打包）
				String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					List<VLogData> logDatas = null;
					if(fdType.equals("LogLogin")){
						page = logLoginService.findVLogDataPagination(fdKey, i, 5000);
						logDatas = logLoginService.findVLogDataByPagination(page);
					}else if(fdType.equals("LogLogout")){
						page = logLogoutService.findVLogDataPagination(fdKey, i, 5000);
						logDatas = logLogoutService.findVLogDataByPagination(page);
					}else if(fdType.equals("LogApp")){
						page = logAppService.findVLogDataPagination(fdKey, i, 5000);
						logDatas = logAppService.findVLogDataByPagination(page);
					}
					AttMain attMain = AbsExportExcel.exportExcels(logDatas, "log.xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "日志导出表.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
			}
		}else{//导出ids
			List<VLogData> logDatas=null;
			if(fdType.equals("LogLogin")){
				logDatas = logLoginService.findVLogData(ids);
			}else if(fdType.equals("LogLogout")){
				logDatas = logLogoutService.findVLogData(ids);
			}else if(fdType.equals("LogApp")){
				logDatas = logAppService.findVLogData(ids);
			}
			AbsExportExcel.exportExcel(logDatas, "log.xls", response,"日志导出表.xls");
		}
		return null;
	}
	
	/**
	 * 用户xls（导出）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getExpuser")
    public String getExpuser(HttpServletRequest request,HttpServletResponse response){
		String[] ids = request.getParameterValues("ids");
		String param = request.getParameter("fdKey");
		String fdType = request.getParameter("fdType");
		String selectAll = request.getParameter("selectAll");
		if(StringUtils.isBlank(selectAll) && org.apache.commons.lang3.ArrayUtils.isNotEmpty(ids)){//导出ids
			List<VUserData> vUserDatas = sysOrgPersonService.getVUserDatas(ids);
			AbsExportExcel.exportExcel(vUserDatas, "user.xls", response, "用户导出表.xls");
		}else if(StringUtils.isNotBlank(selectAll)){//导出全部
			Pagination page = sysOrgPersonService.getVUserDatasPage(fdType, 1, 2000, param);
			if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
				List<VUserData> vUserDatas = sysOrgPersonService.findVUserDatasByPageList(page.getList());
				AbsExportExcel.exportExcel(vUserDatas, "user.xls", response, "用户导出表.xls");
			}else if(page.getTotalPage()>1){//全部导出（导出多个模板，需要打包）
				String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					page = sysOrgPersonService.getVUserDatasPage(fdType, i, 2000, param);
					AttMain attMain = AbsExportExcel.exportExcels(sysOrgPersonService.findVUserDatasByPageList(page.getList()), "user.xls");
					attMainService.saveOnInit(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "user.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				//attMainService.deleteAttMainByIds(attMainIds);
			}
		}
		return null;
	}
     
	@RequestMapping(value = "/getExportMaterialList")
    public String getExportMaterialList(HttpServletRequest request,HttpServletResponse response){
		String isAll = request.getParameter("isAll");
		String fdType = request.getParameter("fdType");
		String fdName = request.getParameter("fdName");
		String order = request.getParameter("order");
		Date date = new Date();
		if("noPage".equals(isAll)){
			String [] modelIds = request.getParameter("modelIds").split(",");
			List<VMaterialData> materialList = materialService.findExportMaterialList(modelIds, fdType);
			AbsExportExcel.exportExcel(materialList, "materialData.xls", response,"课程素材库-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
		}else{
			Pagination page = materialService.findMaterialList(fdType, 1, 5000, fdName, order);
			if(page.getTotalPage()==1){//全部导出（只导出一个模板，不需要打包）
				List<VMaterialData> materialList = materialService.findExportMaterialByPageList(page.getList());
				AbsExportExcel.exportExcel(materialList, "materialData.xls", response,"课程素材库-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
			}else if(page.getTotalPage()>1){//全部导出（导出多个模板，需要打包）
				String [] attMainIds= new String[page.getTotalPage()];
				for(int i=1;i<=page.getTotalPage();i++){
					Pagination pageZip = materialService.findMaterialList(fdType, 1, 5000, fdName, order);
					AttMain attMain = AbsExportExcel.exportExcels(
							materialService.findExportMaterialByPageList(pageZip.getList()), "课程素材库-"+DateUtil.convertDateToString(date, "yyyyMMddHHmmss")+".xls");
					attMainService.save(attMain);
					attMainIds[i-1] = attMain.getFdId();
				}
				try {
					downloadZipsByArrayIds(attMainIds, "materialData.xls", request, response);
				} catch (UnsupportedEncodingException e) {
					  log.error("export excleZip error!", e);
				}
				//删除下载后的无用附件
				attMainService.deleteAttMainByIds(attMainIds);
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据attId打包下载附件
	 * 
	 * @param ids
	 * @param zipname
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    private String downloadZipsByArrayIds(String [] ids, @PathVariable("zipname") String zipname,
                                         HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        DownloadHelper dh = new DownloadHelper();
        dh.setRequest(request);
        List<AttMain> attMains = attMainService.getAttsByIds(ids);
        String agent = request.getHeader("USER-AGENT");
        downloadAttMain(attMains, agent, zipname, response);
        return null;
    }
    
    /**
     * 
     * 打包下载附件
     * 
     * @param attMains
     * @param agent
     * @param zipname
     * @param response
     * @throws UnsupportedEncodingException
     */
    private void downloadAttMain(List<AttMain> attMains, String agent, String zipname, HttpServletResponse response) throws UnsupportedEncodingException {
        if (attMains != null && !attMains.isEmpty()) {
            String temp = "";
            // 设置文件头，文件名称或编码格式
            if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
                temp = java.net.URLEncoder.encode(zipname, "UTF-8");
            } else {
                temp = new String(zipname.getBytes("UTF-8"), "ISO8859-1");
            }
            List<Zipper.FileEntry> fileEntrys = new ArrayList<Zipper.FileEntry>();
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("Content-disposition", "filename=" + temp + ".zip");

            for (AttMain attMain : attMains) {
                File file = new File(attMain.getFdFilePath());
                fileEntrys.add(new Zipper.FileEntry(attMain.getFdFileName(), "", file));
            }
            try {
                Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
            } catch (IOException e) {
                log.error("export db error!", e);
            }
        }
    }
    
    /**
     * 
     * 根据Vmodel导出数据
     * 
     * @param list
     * @param templateName
     * @param response
     */
    private void exportExcel(List<?> list, String templateName, HttpServletResponse response,String expFileName){
    	AbsExportExcel.exportExcel(list, templateName, response ,expFileName);
    }
	
}

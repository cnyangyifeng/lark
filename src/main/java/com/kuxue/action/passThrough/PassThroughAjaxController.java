package com.kuxue.action.passThrough;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.utils.array.ArrayUtils;
import com.kuxue.common.utils.array.SortType;
import com.kuxue.model.bam.BamCourse;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseContent;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.course.CourseParticipateAuth;
import com.kuxue.model.course.Visitor;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.message.Message;
import com.kuxue.model.message.MessageReply;
import com.kuxue.model.organization.RoleEnum;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.SysOrgPersonService;
import com.kuxue.service.UserRoleService;
import com.kuxue.service.bam.BamCourseService;
import com.kuxue.service.bam.BamMaterialService;
import com.kuxue.service.bam.process.SourceNodeService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseParticipateAuthService;
import com.kuxue.service.course.CourseService;
import com.kuxue.service.course.VisitorService;
import com.kuxue.service.letter.PrivateLetterService;
import com.kuxue.service.log.LogLoginService;
import com.kuxue.service.log.LogOnlineService;
import com.kuxue.service.material.ExamQuestionService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.service.message.MessageReplyService;
import com.kuxue.service.message.MessageService;
import com.kuxue.service.studyTack.StudyTrackService;
import com.kuxue.utils.DateUtil;
import com.kuxue.utils.ShiroUtils;


@Controller
@RequestMapping(value = "/ajax/passThrough")
@Scope("request")
public class PassThroughAjaxController {
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BamMaterialService bamMaterialService;
	
	@Autowired
	private SourceNodeService sourceNodeService;
	
	@Autowired
	private MaterialService materialService;

	@Autowired
	private ExamQuestionService examQuestionService;
	
	@Autowired
	private CourseParticipateAuthService courseParticipateAuthService;
	
	@Autowired
	private SysOrgPersonService sysOrgPersonService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageReplyService messageReplyService;
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PrivateLetterService privateLetterService;
	/**
	 * 检查当前课程 当前登录是否有权限进入
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "checkCoursePwd/{courseId}")
	@ResponseBody
	public String checkCoursePwd(@PathVariable("courseId") String courseId){
		Map<String,String> map = new HashMap<String,String>();
		CourseInfo course = courseService.get(courseId);
		if(!course.getIsPublish()){
			if(StringUtil.isNotBlank(course.getFdPassword())){
				map.put("flag", "0");//代表是有密码的加密课程
			} else {
				boolean canStudy= courseParticipateAuthService
						.findCouseParticipateAuthById(courseId,ShiroUtils.getUser().getId());
				if(canStudy){//无权学习的
					map.put("flag", "0");//无权学习
				} else {
					map.put("flag", "1");//有权学习
				}
			}
		} else {
			if(courseParticipateAuthService.findAuthByCourseIdAndUserId(courseId,ShiroUtils.getUser().getId())){
				map.put("flag", "1");//有权学习
			}else{
				map.put("flag", "0");
			}
		}
		
		return JsonUtils.writeObjectToJson(map);
	}
	
	@RequestMapping(value = "verifyPwd")
	@ResponseBody
	public String verifyPwd(HttpServletRequest request){
		String courseId = request.getParameter("courseId");
		String userPwd = request.getParameter("userPwd");
		Map<String,String> map = new HashMap<String,String>();
		CourseInfo course = courseService.get(courseId);
		boolean canS = courseParticipateAuthService.findAuthByCourseIdAndUserId(courseId,ShiroUtils.getUser().getId());
		if(StringUtil.isNotBlank(userPwd)&&userPwd.equals(course.getFdPassword())&&canS){
			map.put("flag", "1");
		}else{
			if(userPwd.equals(course.getFdPassword())){
				map.put("flag", "00");
			}else{
				map.put("flag", "01");
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}
	
	
	/**
	 * 点击学习通过，更改节的学习状态
	 */
	@RequestMapping(value = "updateCatalogThrough")
	@ResponseBody
	public void updateCatalogThrough(HttpServletRequest request){
		String catalogId = request.getParameter("catalogId");
		String bamId = request.getParameter("bamId");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		bamCourseService.updateCatalogThrough(bamCourse, catalogId);
	}
	
	/**
	 * 最新课程列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getNewCourseList")
	@ResponseBody
	public String getNewCourseList(HttpServletRequest request) {
		List result = new ArrayList();
		Pagination page = courseService.discoverCourses(1,5);
		if(page.getTotalCount()>0){
			List list = page.getList();
			for(int i=0;i<list.size();i++){
				Map course = new HashMap();
				course = (Map)list.get(i);
				AttMain attMain = attMainService.getByModelIdAndModelName((String)course.get("FDID"), CourseInfo.class.getName());
				course.put("ATTID", attMain==null?"":attMain.getFdId());
				result.add(course);
			}
		}
		return JsonUtils.writeObjectToJson(result);
	}
	
	/**
	 * 正在学习课程的教师列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPageByLeaningTeacher")
	@ResponseBody
	public String getPageByLeaningTeacher(HttpServletRequest request) {
		String pageNoStr = request.getParameter("pageNo");
		String courseId = request.getParameter("courseId");
		int pageNo;
		if (StringUtil.isNotBlank(pageNoStr)) {
			pageNo = Integer.parseInt(pageNoStr);
		} else {
			pageNo = 1;
		}
		List result = new ArrayList();
		Finder finder = Finder.create(" select preTeachId  from IXDF_NTP_BAM_SCORE b where b.courseId = :courseId order by startDate desc  ");//随机排序用 dbms_random.value
		finder.setParam("courseId", courseId);
		Pagination page = bamCourseService.getPageBySql(finder, pageNo, 15);
		if(page.getTotalCount()>0){
			List list = page.getList();
			for(int i=0;i<list.size();i++){
				Map bam = (Map)list.get(i);
				SysOrgPerson person = accountService.load((String)bam.get("PRETEACHID"));
				Map people = new HashMap();
				people.put("id", person.getFdId());
				people.put("name", person.getRealName());
				people.put("photoUrl", person.getPoto());
				result.add(people);
			}
		}
		Map map = new HashMap();
		map.put("totalPage", page.getTotalPage());
		map.put("currentPage", pageNo);
		map.put("list", result);
		map.put("totalCount", page.getTotalCount());
		return JsonUtils.writeObjectToJson(map);
	}
	
    /**
     * 根据课程id取出当前课程最新学习的的一百个人员（应用于侧边栏）
     * @param request
     * @return
     */
	@RequestMapping(value = "getLeaningTeacherTop")
	@ResponseBody
	public String getLeaningTeacherTop(HttpServletRequest request) {
		String courseId = request.getParameter("fdCourseId"); 
		String fdId = ShiroUtils.getUser().getId();
		Map returnMap = new HashMap();
		Finder finder = Finder.create(" select preTeachId  from IXDF_NTP_BAM_SCORE b ");
		finder.append(" where b.courseId = :courseId");
		finder.setParam("courseId", courseId);
		finder.append(" order by startDate desc");
		Pagination page = bamCourseService.getPageBySql(finder, 1, 100);
		List listLearner = new ArrayList();
		if(page.getTotalCount()>0){
			List list = page.getList();
			for(int i=0;i<list.size();i++){
				Map bam = (Map)list.get(i);
				SysOrgPerson person = accountService.load((String)bam.get("preTeachId"));
				Map userMap = new HashMap();
				if(fdId.equals(person.getFdId())){
					userMap.put("isMe", true);
				}else{
					userMap.put("isMe", false);
				}
				userMap.put("id", person.getFdId());
				userMap.put("name", person.getRealName());
				userMap.put("imgUrl", person.getPoto());
				boolean isShow = privateLetterService.getIsUnRead(person.getFdId(), fdId);
				userMap.put("isShow", isShow);
				listLearner.add(userMap);
			}
		}
		returnMap.put("listLearner", listLearner);
		returnMap.put("author", getAuthorByCourseId(courseId,fdId));
		returnMap.put("mentor", getAdviser(courseId,fdId));
		returnMap.put("admin", getManager(fdId));
		return JsonUtils.writeObjectToJson(returnMap);
	}
	/**
	 * 平台运营
	 * @param fdId
	 * @return
	 */
	private Map getManager(String fdId){
		Map admin = new HashMap();//作者
		String fdEmail = "yangyifeng@xdf.cn";
		String fdIdentityCard = "371121198601100217";
		SysOrgPerson person = accountService.findPersonByEmailAndIdCard(fdEmail,fdIdentityCard);
		if(fdId.equals(person.getFdId())){
			admin.put("isMe", true);
		}else{
			admin.put("isMe", false);
		}
	    admin.put("id",person.getFdId());
	    admin.put("name",person.getRealName());
	    boolean isShow = privateLetterService.getIsUnRead(person.getFdId(), fdId);
	    admin.put("isShow", isShow);
		return admin;
	}
	/**
	 * 得到课程创建者map
	 * @param fdId
	 * @return
	 */
	private Map getAuthorByCourseId(String courseId,String fdId){
		Map author = new HashMap();//作者
		CourseInfo info = courseService.get(courseId);
		String fdAuthorId = info.getFdAuthorId();
		SysOrgPerson person ;
		if(StringUtil.isNotBlank(fdAuthorId)){
			person = accountService.get(fdAuthorId);
		}else{
			person = info.getCreator();
		}
		if(fdId.equals(person.getFdId())){
			author.put("isMe", true);
		}else{
			author.put("isMe", false);
		}
		author.put("id",person.getFdId());
		author.put("name",person.getRealName());
		boolean isShow = privateLetterService.getIsUnRead(person.getFdId(), fdId);
		author.put("isShow", isShow);
		return author;
	}
	/**
     * 根据课程id取出当前课程导师（应用于侧边栏）
     * @return
     */
	private Map getAdviser(String courseId,String fdId){
		Map mentor = new HashMap();//导师
		CourseParticipateAuth auth = courseParticipateAuthService.findAuthByCourseIdandUserId(courseId,fdId);
		if(auth==null||auth.getFdTeacher()==null){
			mentor.put("isMe", true);
			return mentor;
		}else{
			String teacherId = auth.getFdTeacher().getFdId();
			if(teacherId.equals(fdId)){
				mentor.put("isMe", true);
			}else{
				mentor.put("isMe", false);
			}
			mentor.put("id", teacherId);
			mentor.put("name", auth.getFdTeacher().getRealName());
			boolean isShow = privateLetterService.getIsUnRead(teacherId, fdId);
			mentor.put("isShow", isShow);
		}
		return mentor;
	}
	
	/**
	 * 根据课程学习进程获取章节目录树
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getBamCatalogTree")
	@ResponseBody
	public String getBamCatalogTree(HttpServletRequest request) {
		//获取进程ID
		String bamId = request.getParameter("bamId");
		Map map = new HashMap();
		if(StringUtil.isNotBlank(bamId)){
			BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
			if(bamCourse!=null && bamCourse.getCatalogs()!=null){
				Boolean isOrder=bamCourse.getCourseInfo().getIsOrder();
				List<CourseCatalog> catalogs = bamCourse.getCatalogs();
				if(catalogs!=null){
					ArrayUtils.sortListByProperty(catalogs, "fdTotalNo", SortType.HIGHT);
				}
				Map catalogMap = new HashMap();
				List<Map> chapter = new ArrayList();
				List<Map> lecture = new ArrayList();
				Boolean currentCatalog = true;//设置标识，记录上一节是否通过
				for(CourseCatalog catalog : catalogs){
					Map tmp = new HashMap();
					if(Constant.CATALOG_TYPE_CHAPTER==catalog.getFdType()){
						tmp.put("index", catalog.getFdTotalNo());
						tmp.put("num", catalog.getFdNo());
						tmp.put("name", catalog.getFdName());
						chapter.add(tmp);
					}else{
						tmp.put("id", catalog.getFdId());
						tmp.put("index", catalog.getFdTotalNo());
						tmp.put("num", catalog.getFdNo());
						tmp.put("type", catalog.getMaterialType());
						tmp.put("baseType", catalog.getFdMaterialType());
						tmp.put("name", catalog.getFdName());
						tmp.put("intro", catalog.getFdDescription());
						if(!isOrder){//无序
							tmp.put("fdStatus", "true");
						}else{//有序
							if(currentCatalog!=null){
								if(currentCatalog == true){
									tmp.put("fdStatus", "true");
								}else{
									tmp.put("fdStatus", "false");
								}
							}else{
								tmp.put("fdStatus", "false");
							}
						}
						if(catalog.getThrough()==null){
								tmp.put("status", "untreated");
						}else if(catalog.getThrough()==false){
								tmp.put("status", "doing");
						}else if(catalog.getThrough()==true){
								tmp.put("status", "pass");
						}
						currentCatalog = catalog.getThrough();
						lecture.add(tmp);
					}
				}
				catalogMap.put("chapter", chapter);
				catalogMap.put("lecture", lecture);
				catalogMap.put("isOrder", isOrder);
				map.put("sidenav", catalogMap);
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 根据课程学习进程获取章节目录树
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCourseContent")
	@ResponseBody
	public String getCourseContent(WebRequest request) {
		//获取进程ID
		String bamId = request.getParameter("bamId");
		//获取节ID
		String catalogId = request.getParameter("catalogId");
        bamCourseService.updateCourseCatalogStartTime(bamId,catalogId);
		//获取节内容类型
		String sourceType = request.getParameter("fdMtype");
		///点击播放当前素材的id
		String materialId = request.getParameter("materialId");
		Map map = new HashMap();
		if(StringUtil.isNotBlank(bamId)){
			BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
			if(bamCourse!=null && bamCourse.getCatalogs()!=null){
				List<CourseCatalog> catalogs = bamCourse.getCatalogs();
				for(CourseCatalog catalog : catalogs){
					if(catalog.getFdId().equals(catalogId)){
						//设置节信息
						Map prenext=getCurrentCatalog(bamCourse,catalog);
						map.putAll(prenext);
						map.put("type", catalog.getMaterialType());
						if(catalog.getThrough()==null){
							map.put("status", "unfinish");
						}else if(catalog.getThrough()==false){
							map.put("status", "doing");
						}else if(catalog.getThrough()==true){
							map.put("status", "pass");
						}
						map.put("courseName", bamCourse.getCourseInfo().getFdTitle());
						map.put("lectureName", catalog.getFdName());
						map.put("lectureIntro", catalog.getFdDescription());
						map.put("num", catalog.getFdNo());
						map.put("isOptional", catalog.getFdPassCondition()!=null && catalog.getFdPassCondition()==0?true:false);
						//根据素材类型设置节中内容详细信息
						Map returnMap = (Map)bamMaterialService.findMaterialDetailInfo(sourceType, bamCourse, catalog, materialId);
						if(returnMap!=null){
							map.putAll(returnMap);
						}
						break;
					}
				}
			}
		}
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 根据测试id获取测试信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSubInfoByMaterialId")
	@ResponseBody
	public String getSubInfoByMaterialId(WebRequest request) {
		String materialId = request.getParameter("materialId");
		String catalogId = request.getParameter("catalogId");
		String sourceType = request.getParameter("sourceType");
		String bamId = request.getParameter("bamId");
		BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
		MaterialInfo materialInfo = materialService.get(materialId); 
		//根据素材类型获取素材子表信息
		List listExam = (List)bamMaterialService.findSubInfoByMaterial(sourceType, request);
		List<CourseContent> courseContents = bamCourse.getCourseContents();
        if (courseContents != null){
        	for (CourseContent content : courseContents) {
	            if (content.getMaterial().getFdId().equals(materialId)&&content.getCatalog().getFdId().equals(catalogId)) {
	            	materialInfo = content.getMaterial();
	            	break;
	            }
	        }
        }
		Map map = new HashMap();
		map.put("id", materialInfo.getFdId());
		map.put("name", materialInfo.getFdName());
		map.put("fullScore", materialService.getTotalSorce(materialId).get("totalscore"));
		map.put("examPaperTime", materialInfo.getFdStudyTime());
		map.put("examPaperIntro", materialInfo.getFdDescription());
		map.put("examPaperStatus", sourceNodeService.getStatus(materialInfo, catalogId, ShiroUtils.getUser().getId()));
		
		map.put("listExam", listExam);
		return JsonUtils.writeObjectToJson(map);
	}
	/**
	 *获取当前选中节的上一节点和下一节点,节点id,是否通过学习
	 * @param catalogs  节列表
	 * @param currentCatalog  当前节
	 * @return 当前节点的上下节点
	 */
		private Map getCurrentCatalog(BamCourse bamCourse,CourseCatalog currentCatalog){
			List<CourseCatalog> catalogs = bamCourse.getCatalogs();
			//分离章节集合中的节
			CourseCatalog prevCatalog=null;
			CourseCatalog nextCatalog=null;
			Map pn=new HashMap();
			if(currentCatalog.getFdNo()==1){//当前节是节1的情况
				pn.put("prevc", "0");
				pn.put("pstatus", false);
				nextCatalog=getpnCatalog(catalogs,currentCatalog.getFdNo()+1);
				if(nextCatalog!=null){//总节数>2节
					pn.put("nextc", nextCatalog.getFdId());
					if(bamCourse.getCourseInfo().getIsOrder()){
						if(null!=currentCatalog.getThrough()&&true==currentCatalog.getThrough()){
							pn.put("nstatus", true);
						}else{
							pn.put("nstatus", false);
						}
					}else{
						pn.put("nstatus", true);
					}
					pn.put("nextBaseType", nextCatalog.getFdMaterialType());
				}else{//不大于2 则当前节是节首也是节尾
					pn.put("nextc", "0");
					pn.put("nstatus", false);
				}
			}
			if(currentCatalog.getFdNo()>1){//节编号大于1说明有上一节点 
				prevCatalog=getpnCatalog(catalogs,currentCatalog.getFdNo()-1);
				pn.put("prevc", prevCatalog.getFdId());
				pn.put("pstatus", true);
				pn.put("prevBaseType", prevCatalog.getFdMaterialType());
				nextCatalog=getpnCatalog(catalogs,currentCatalog.getFdNo()+1);
				if(nextCatalog!=null){//当前节是节尾
					pn.put("nextc", nextCatalog.getFdId());
					if(bamCourse.getCourseInfo().getIsOrder()){
						if(null!=currentCatalog.getThrough()&&true==currentCatalog.getThrough()){
							pn.put("nstatus", true);
						}else{
							pn.put("nstatus", false);
						}
					}else{
						pn.put("nstatus", true);
					}
					pn.put("nextBaseType", nextCatalog.getFdMaterialType());
				}else{
					pn.put("nextc", "0");
					pn.put("nstatus", false);
				}
			}
			int currentCatalogCount=0;
			for(int i=0;i<catalogs.size();i++){
				if(catalogs.get(i).getFdType()==1){
					currentCatalogCount++;
				}
			}
			if(currentCatalog.getFdNo()==currentCatalogCount){
				pn.put("isLast", true);
				pn.put("isThrough", bamCourse.getThrough());
			}else{
				pn.put("isLast", false);
			}
			return pn;
		}
		/**
		 * 根据节号抽去节
		 * @param catalogs 章节集合
		 * @param no       节号
		 * @return   节信息
		 */
		private CourseCatalog getpnCatalog(List<CourseCatalog> catalogs,Integer no){
			for(CourseCatalog courseCatalog:catalogs){
				if(Constant.CATALOG_TYPE_CHAPTER!=courseCatalog.getFdType()&&courseCatalog.getFdNo()==no){
					return courseCatalog;
				}
			}
			return null;
		}
		
		/**
		 * 根据bamId获取结业证书信息(当前用户)
		 * 
		 * 
		 */
		@RequestMapping(value = "getCourseOverByBamId")
		@ResponseBody
		public String getCourseOverByBamId(HttpServletRequest request){
			String bamId = request.getParameter("bamId");
			BamCourse bamCourse = bamCourseService.get(BamCourse.class, bamId);
			if(bamCourse.getThrough()==false){
				return "notThrough";
			}else{
				Map map = new HashMap();
				Map user = new HashMap();
				SysOrgPerson orgPerson = accountService.load(ShiroUtils.getUser().getId());
				user.put("name",orgPerson.getRealName());
				user.put("enName", orgPerson.getLoginName());
				user.put("imgUrl", orgPerson.getPoto());
				user.put("link", "#");
				CourseInfo courseInfo =((CourseInfo)courseService.get(bamCourse.getCourseId()));
				SysOrgPerson person = courseInfo.getCreator();
				map.put("courseName", courseInfo.getFdTitle() );
				map.put("passTime", DateUtil.convertDateToString(bamCourse.getEndDate()));
				String aId = courseInfo.getFdAuthorId();
				if(StringUtil.isEmpty(aId)){
					map.put("issuer", person.getDeptName());
				}else{
					SysOrgPerson person2 = accountService.get(aId);
					map.put("issuer", person2.getDeptName());
				}
				
				map.put("user", user);
				List<CourseCatalog> catalogs = bamCourse.getCatalogs();
				List<CourseCatalog> catalog = new ArrayList<CourseCatalog>();
				for (CourseCatalog courseCatalog : catalogs) {
					if(courseCatalog.getFdType()==1){
						catalog.add(courseCatalog);
					}
				}
				ArrayUtils.sortListByProperty(catalog, "fdNo", SortType.HIGHT);
				map.put("firstCId", catalog.get(0).getFdId());
				map.put("firstCType", catalog.get(0).getFdMaterialType());
				map.put("listCId", catalog.get(catalog.size()-1).getFdId());
				map.put("listCType", catalog.get(catalog.size()-1).getFdMaterialType());
				return JsonUtils.writeObjectToJson(map);
			}
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
			String courseId = request.getParameter("courseId");
			if(userId.equals(ShiroUtils.getUser().getId())){
				returnMap.put("isme", true);
			}else{
				returnMap.put("isme", false);
			}
			SysOrgPerson orgPerson = accountService.load(userId);
			returnMap.put("userid", orgPerson.getFdId());
			returnMap.put("name", orgPerson.getRealName());
			returnMap.put("img", orgPerson.getPoto());
			returnMap.put("sex", orgPerson.getFdSex());
			returnMap.put("org", orgPerson.getHbmParent()==null?"不详":orgPerson.getHbmParent().getHbmParentOrg().getFdName());
			returnMap.put("dep", orgPerson.getDeptName()==null?"不详":orgPerson.getDeptName());
			returnMap.put("tel", orgPerson.getFdWorkPhone()==null?"不详":orgPerson.getFdWorkPhone());
			returnMap.put("bird", orgPerson.getFdBirthDay()==null?"不详":orgPerson.getFdBirthDay());
			Map userMap = sysOrgPersonService.getUserInfo(userId);
			returnMap.put("bool", orgPerson.getFdBloodType()==null?"不详":orgPerson.getFdBloodType());
			returnMap.put("selfIntroduction", orgPerson.getSelfIntroduction()==null?"":orgPerson.getSelfIntroduction());
			CourseInfo courseInfo = courseService.get(courseId);
			List<AttMain> attMains = attMainService.getAttMainsByModelIdAndModelName(courseInfo.getFdId(), CourseInfo.class.getName());
			returnMap.put("courseName",courseInfo.getFdTitle());
			returnMap.put("courseAuther", courseInfo.getFdAuthor());
			returnMap.put("courseImg",attMains.size()==0?"":(attMains.get(0).getFdId()+"?n="+new Random().nextInt(100)));
			return JsonUtils.writeObjectToJson(returnMap);
		}
		
		/**
		 * 获取备课心情
		 * 
		 * 
		 */
		@RequestMapping(value="getMessageFeeling")
		@ResponseBody
		private String getMessageFeeling(HttpServletRequest request) {
			String userId = request.getParameter("userId");
			String courseId = request.getParameter("courseId");
			BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseId);
			Map returnMap = new HashMap();
			if(bamCourse==null){
				returnMap.put("list", new ArrayList());
				return JsonUtils.writeObjectToJson(returnMap);
			}else{
				List<Map> list =  new ArrayList<Map>();
				Finder finder = Finder.create("");
				finder.append("from Message m where (m.fdType=:fdType1 or m.fdType=:fdType2) and m.fdModelName=:fdModelName and m.fdModelId=:fdModelId order by m.fdCreateTime desc ");
				finder.setParam("fdType1",Constant.MESSAGE_TYPE_MOOD);
				finder.setParam("fdType2",Constant.MESSAGE_TYPE_SYS);
				finder.setParam("fdModelName",BamCourse.class.getName());
				finder.setParam("fdModelId",bamCourse.getFdId());
				List<Message> messages = messageService.find(finder);
				if(messages.size()==0){
					returnMap.put("list", new ArrayList());
					return JsonUtils.writeObjectToJson(returnMap);
				}
				List<String> dateList = new ArrayList<String>();
				//Set<String> dateSet = new HashSet<String>();
				for ( int i=0;i<messages.size();i++) {
					Message message = messages.get(i);
					SimpleDateFormat sim = new SimpleDateFormat("MM dd yyyy");
					String date = sim.format(message.getFdCreateTime().getTime());
					if(!dateList.contains(date)){
						dateList.add(date);
					}
				}
				for(int j=0;j<dateList.size();j++){
					String string = dateList.get(j);
				//for (String string : dateSet) {
					Map map = new HashMap();
					map.put("date", string);
					List<Map> items = new ArrayList<Map>();
					for(int i=0;i<messages.size();i++){
						SimpleDateFormat sim = new SimpleDateFormat("MM dd yyyy");
						String c2s = sim.format(messages.get(i).getFdCreateTime().getTime()); 
						if(string.equals(c2s)){
							Map item = new HashMap();
							item.put("id", messages.get(i).getFdId());
							item.put("mood",  messages.get(i).getFdContent());
							Map praise = new HashMap();
							praise.put("count", messageService.getSupportCount(messages.get(i).getFdId()));
							praise.put("did", messageReplyService.isSupportMessage(ShiroUtils.getUser().getId(), messages.get(i).getFdId())!=null);
							item.put("praise", praise);
							Map weak = new HashMap();
							weak.put("count", messageService.getOpposeCount(messages.get(i).getFdId()));
							weak.put("did", messageReplyService.isOpposeMessage(ShiroUtils.getUser().getId(), messages.get(i).getFdId())!=null);
							item.put("weak", weak);
							if(messages.get(i).getFdUser()==null){
								item.put("canDelete", false);
							}else{
								if((messages.get(i).getFdUser().getFdId().equals(ShiroUtils.getUser().getId()) || !userRoleService.isEmptyPerson(ShiroUtils.getUser().getId(), RoleEnum.admin))&&(!Constant.MESSAGE_TYPE_SYS.equals(messages.get(i).getFdType()))){
									item.put("canDelete", true);
								}else{
									item.put("canDelete", false);
								}
							}
							Map comment = new HashMap();
							List<MessageReply> messageReplies = messageReplyService.findByCriteria(MessageReply.class,
									Value.eq("message.fdId", messages.get(i).getFdId()), Value.eq("fdType", "03"));
							comment.put("count", messageReplies.size());
							List<Map> messageRepliesMap = new ArrayList<Map>();
							for (MessageReply messageReply : messageReplies) {
								Map messageReplyMap = new HashMap();
								SysOrgPerson orgPerson = messageReply.getFdUser();
								Map userMap = new HashMap();
								userMap.put("imgUrl", orgPerson.getPoto());
								userMap.put("link", "/course/courseIndex?userId="+orgPerson.getFdId());
								userMap.put("name", orgPerson.getRealName());
								userMap.put("mail", orgPerson.getFdEmail()==null?"不详":orgPerson.getFdEmail());
								userMap.put("org", orgPerson.getDeptName()==null?"不详":orgPerson.getDeptName());
								messageReplyMap.put("issuer", userMap);
								messageReplyMap.put("comment", messageReply.getFdContent());
								messageReplyMap.put("msaageeRId", messageReply.getFdId());
								messageReplyMap.put("time", DateUtil.getInterval(DateUtil.convertDateToString(messageReply.getFdCreateTime()), "yyyy/MM/dd hh:mm aa"));
								if(messageReply.getFdUser()==null){
									messageReplyMap.put("canDeleteMr", false);
								}else{
									if((messageReply.getFdUser().getFdId().equals(ShiroUtils.getUser().getId()) || !userRoleService.isEmptyPerson(ShiroUtils.getUser().getId(), RoleEnum.admin))){
										messageReplyMap.put("canDeleteMr", true);
									}else{
										messageReplyMap.put("canDeleteMr", false);
									}
								}
								messageRepliesMap.add(messageReplyMap);
							}
							ArrayUtils.sortListByProperty(messageRepliesMap, "time", SortType.LOW); 
							comment.put("list", messageRepliesMap);
							item.put("comment", comment);
							item.put("time", DateUtil.getInterval(DateUtil.convertDateToString(messages.get(i).getFdCreateTime()), "yyyy/MM/dd hh:mm aa"));
							items.add(item);
						}
					}
					map.put("items", items);
					list.add(map);
				}
				returnMap.put("list",list);
				return JsonUtils.writeObjectToJson(returnMap);
			}
		}
		
		@Autowired
		private LogLoginService logLoginService;
		
		@Autowired
		private LogOnlineService logOnlineService;
		
		@Autowired
		private StudyTrackService studyTrackService;
		
		/**
		 * 学习心情页面，活跃天数
		 * @param request
		 */
		@RequestMapping(value = "getCourseFeelingActive")
		@ResponseBody
		public String getCourseFeelingActive(HttpServletRequest request) {
			String userId = request.getParameter("userId");
			String courseId= request.getParameter("courseId");
			SysOrgPerson orgPerson = accountService.load(userId);
			Map map = new HashMap();
			map.put("lastTime", logLoginService.getNewLoginDate(userId));
			map.put("onlineDay",logOnlineService.getOnlineByUserId(userId).getLoginDay());
			BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseId);
			Map passMap = studyTrackService.passInfoByBamId(bamCourse.getFdId());
			String currLecture="";
			//String nextCatalog="";
			if(passMap.size()==0){
				currLecture="0%";
			}else{
				if(passMap.get("coursePass")==null){
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
					currLecture=(finishSum*100/sum)+"%";
				}else{
					currLecture = "100%";
				}
			}
			int messageCount = messageService.findByCriteria(Message.class,
	                Value.eq("fdModelId", bamCourse.getFdId()),
	                Value.eq("fdModelName", BamCourse.class.getName()),Value.eq("fdType", "02")).size();
			map.put("messageCount", messageCount);
			map.put("currLecture", currLecture);
			return JsonUtils.writeObjectToJson(map);
		}
		
		
		/**
		 * 学习心情页面，进度条
		 * @param request
		 */
		@RequestMapping(value = "getCourseFeelingSchedule")
		@ResponseBody
		public String getCourseFeelingSchedule(HttpServletRequest request) {
			String userId = request.getParameter("userId");
			String courseId= request.getParameter("courseId");
			BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseId);
			Map map = new HashMap();
			Map passMap = studyTrackService.passInfoByBamId(bamCourse.getFdId());
			int sums;//共完成数
			String nextCatalog="";
			String width="";//百分比
			if(passMap.size()==0){
				List<CourseCatalog> catalogs =bamCourse.getCatalogs();
				for (int i=0;i< catalogs.size();i++) {
					CourseCatalog courseCatalog = catalogs.get(i);
					if(Constant.CATALOG_TYPE_LECTURE == courseCatalog.getFdType()){
						nextCatalog = courseCatalog.getFdName();
						break;
					}
				}
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
						//取出当前环节的下一节
						if(courseCatalog.getFdId().equals(catalog.getFdId())){
							try {
								nextCatalog = catalogs.get(i).getFdName();
							} catch (Exception e) {
								nextCatalog = "查看课程结业证书";
							}
						}
					}
					sums=finishSum;
					width=(finishSum*100/sum)+"%";
				}else{
					sums=0;
					List<CourseCatalog> catalogs =bamCourse.getCatalogs();
					for (int i=0;i< catalogs.size();i++) {
						CourseCatalog courseCatalog = catalogs.get(i);
						if(Constant.CATALOG_TYPE_CHAPTER == courseCatalog.getFdType()){
							continue;
						}else{
							sums++;
						}
					}
					width="100%";
					nextCatalog = "查看课程结业证书";
				}
			}
			map.put("sums", sums);
			map.put("width", width);
			map.put("nextCatalog", nextCatalog);
			return JsonUtils.writeObjectToJson(map);
		}
		
		/**
		 * 学习心情页面，进度条
		 * @param request
		 */
		@RequestMapping(value = "getVisitorsInfo")
		@ResponseBody
		public String getVisitorsInfo(HttpServletRequest request) {
			String userId = request.getParameter("userId");
			String courseId= request.getParameter("courseId");
			String pageNo= request.getParameter("pageNo");
			BamCourse bamCourse = bamCourseService.getCourseByUserIdAndCourseId(userId, courseId);
			Finder finder = Finder.create("");
			finder.append("from Visitor v where v.bamCourse.fdId=:bamId order by v.fdTime desc");
			finder.setParam("bamId", bamCourse.getFdId());
			Pagination pagination = visitorService.getPage(finder, new Integer(pageNo), 15);
			List<Visitor> visitors = (List<Visitor>) pagination.getList();
			Map returnMap = new HashMap();
			List<Map> list = new ArrayList<Map>();
			for (int i = 0; i < visitors.size(); i++) {
				Visitor visitor =  visitors.get(i);
				Map map = new HashMap();
				map.put("userName", visitor.getFdUser().getFdName());
				map.put("userId", visitor.getFdUser().getFdId());
				map.put("img", visitor.getFdUser().getPoto());
				list.add(map);
			}
			returnMap.put("list", list);
			returnMap.put("pageOver", (new Integer(pageNo)==1)?-1:(new Integer(pageNo)-1));
			returnMap.put("pageNext", (new Integer(pageNo)>=pagination.getTotalPage())?-1:new Integer(pageNo)+1);
			return JsonUtils.writeObjectToJson(returnMap);
		}
		
		
		
}

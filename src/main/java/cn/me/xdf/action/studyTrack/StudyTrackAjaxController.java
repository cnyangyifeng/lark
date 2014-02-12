package cn.me.xdf.action.studyTrack;

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
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.course.CourseInfo;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.organization.RoleEnum;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.UserRoleService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.course.CourseService;
import cn.me.xdf.service.log.LogLoginService;
import cn.me.xdf.service.log.LogOnlineService;
import cn.me.xdf.service.message.MessageService;
import cn.me.xdf.service.studyTack.StudyTrackService;
import cn.me.xdf.utils.DateUtil;
import cn.me.xdf.utils.ShiroUtils;

/**
 * 学习跟踪的ajax
 * 
 * @author
 * 
 */
@Controller
@RequestMapping(value = "/ajax/studyTrack")
@Scope("request")
public class StudyTrackAjaxController {
	
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private StudyTrackService studyTrackService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private BamCourseService bamCourseService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private LogOnlineService logOnlineService;
	
	@Autowired
	private LogLoginService logLoginService;
	
	@Autowired
	private AttMainService attMainService;

	
	/**
	 * 获取当前页学习跟踪信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getTracks")
	@ResponseBody
	public String getTracks(HttpServletRequest request){
		String selectType = request.getParameter("selectType");
		int pageNo = new Integer( request.getParameter("pageNo"));
		int pageSize = new Integer( request.getParameter("pageSize"));
		String orderType = request.getParameter("order");
		String key = request.getParameter("key");
		Pagination pagination =  studyTrackService.getStudyTrack(selectType, ShiroUtils.getUser().getId(), pageNo, pageSize,orderType, key);
		List<Map> bamCourses = (List<Map>) pagination.getList();
		List<Map> list = new ArrayList<Map>();
		for (Map bamCourse : bamCourses) {
			Map map = new HashMap();
			map.put("id", (String)bamCourse.get("BAMID"));
			SysOrgPerson person = ((SysOrgPerson)accountService.load((String)bamCourse.get("PREID")));
			Map user = new HashMap();
			user.put("userId", person.getFdId());
			user.put("name", person.getRealName());
			user.put("imgUrl", person.getPoto());
			user.put("org", person.getHbmParent()==null?"":person.getHbmParent().getHbmParentOrg()==null?"":person.getHbmParent().getHbmParentOrg().getFdName());
			user.put("department", person.getDeptName());
			user.put("phone", person.getFdWorkPhone());
			user.put("mail", person.getFdEmail());
			user.put("link", "#");
			map.put("user", user);
			map.put("canDel", !userRoleService.isEmptyPerson(ShiroUtils.getUser().getId(),RoleEnum.admin));
			map.put("courseName", ((CourseInfo)courseService.get((String)bamCourse.get("COURSEID"))).getFdTitle());
			String guideTeachName;
			if(StringUtil.isEmpty((String)bamCourse.get("GUIID"))){
				guideTeachName="没有导师";
			}else{
				guideTeachName = ((SysOrgPerson)accountService.load((String)bamCourse.get("GUIID"))).getRealName();
			}
			map.put("mentor", guideTeachName);
			Map passMap = studyTrackService.passInfoByBamId((String)bamCourse.get("BAMID"));
			String currLecture="";
			if(passMap.size()==0){
				currLecture="尚未开始学习";
			}else{
				if(passMap.get("coursePass")==null){
					CourseCatalog catalog = (CourseCatalog)passMap.get("courseCatalogNow");
					MaterialInfo materialInfo = (MaterialInfo) passMap.get("materialInfoNow");
					currLecture = catalog.getFdName()+"  ,  "+materialInfo.getFdName();
				}else{
					currLecture = "课程已全部通过";
				}
				
			}
			map.put("currLecture", currLecture);
			Map map2 = studyTrackService.getMessageInfoByBamId((String)bamCourse.get("BAMID"));
			if(map2.size()==0){
				map.put("passMsg","没有学习记录");
				map.put("passTime", "0000-00-00 00:00:00");
			}else{
				map.put("passMsg",map2.get("cot"));
				map.put("passTime", DateUtil.convertDateToString((Date)map2.get("time")));
			}
			list.add(map);
		}
		Map returnMap = new HashMap();
		returnMap.put("list", list);
		returnMap.put("pageInfo", getPageInfo(pagination));
		
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	/**
	 * 获取分页信息
	 * 
	 * @param pagination
	 * @return
	 */
	private Map getPageInfo(Pagination pagination) { 
		int pageNo = pagination.getPageNo();
		int pageSize = pagination.getPageSize();
		int totalSize = pagination.getTotalCount();
		int startLine = (pageNo-1)*(pageSize)+1;
		int totalPage = pagination.getTotalPage();
		int endLine = 0;
		if(totalPage==pageNo){
			if(totalSize==0){
				startLine=0;
				endLine=0;
			}else if(totalSize%pageSize==0){
				endLine = startLine + pageSize-1;
			}else{
				endLine = startLine + totalSize%pageSize-1;
			}
		}else{
			endLine = startLine + pageSize-1;
		}
		Map map = new HashMap();
		map.put("totalSize", totalSize);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("startLine",startLine);
		map.put("endLine", endLine);
		map.put("totalPage", totalPage);
		map.put("startPage", pagination.getStartPage());
		map.put("endPage", pagination.getEndPage());
		return map;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPerson")
	@ResponseBody
	public String getPerson(HttpServletRequest request){
		SysOrgPerson orgPerson = accountService.load(ShiroUtils.getUser().getId());
		Map map = new HashMap();
		map.put("userId", orgPerson.getFdId());
		map.put("name", orgPerson.getRealName());
		map.put("url", orgPerson.getPoto());
		map.put("org", orgPerson.getHbmParent()==null?"":orgPerson.getHbmParent().getHbmParentOrg().getFdName());
		map.put("dep", orgPerson.getDeptName());
		map.put("sex", orgPerson.getFdSex());
		map.put("lastTime", logLoginService.getNewLoginDate(orgPerson.getFdId()).equals("0")?"首次登录":"最近登录 "+logLoginService.getNewLoginDate(orgPerson.getFdId()));
		map.put("onlineDay",logOnlineService.getOnlineByUserId(ShiroUtils.getUser().getId()).getLoginDay());
		return JsonUtils.writeObjectToJson(map);
	}

	
	/**
	 * 删除备课信息
	 * @param request
	 */
	@RequestMapping(value = "deleBam")
	@ResponseBody
	public String deleBam(HttpServletRequest request) {
		String bamId=request.getParameter("bamId");
		String userId=request.getParameter("userId");
		studyTrackService.deleteBam(bamId);
		return "";
	}
}

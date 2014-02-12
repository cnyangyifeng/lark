package cn.me.xdf.action;

import java.util.ArrayList;
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

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.system.PageConfig;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.SysOrgDepartService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.system.SysPageConfigService;

@Controller
@RequestMapping(value = "/ajax/head")
@Scope("request")
public class HeadPageAjaxContoller {
	
	 @Autowired
	 private SysOrgDepartService departService;
	
	 @Autowired
	 private SysPageConfigService pageConfigService;
	 
	 @Autowired
	 private AccountService accountService;
	 
	 @Autowired
	 private AttMainService attMainService;
	 /**
	  * 
	  * @param request
	  * @return
	  */
	@RequestMapping(value = "getSchoolInfo")
	@ResponseBody
	public String getSchoolInfo(HttpServletRequest request) {
		String sql = "select count(*) c from sys_org_element  o where fd_parentid in (select fdid from sys_org_element where  fd_no='100') ";// 学校数量
		String sql1 = "select count(*) c from sys_user_role where person_role='guidance'"; // 导师数量
		String sql2 = "select count(distinct preteachid) c from ixdf_ntp_bam_score ";// --新教师数量
		String sql3 = "select count(*) c from ixdf_ntp_course c where c.isavailable='Y' ";// --课程数量
		List list=departService.findBySQL(sql, null, null);
		List list1=departService.findBySQL(sql1, null, null);
		List list2=departService.findBySQL(sql2, null, null);
		List list3=departService.findBySQL(sql3, null, null);
		Map schoolinfo=new HashMap();
		schoolinfo.put("schoolNum", list.get(0).toString());
		schoolinfo.put("mentorNum", list1.get(0).toString());
		schoolinfo.put("newTeacherNum", list2.get(0).toString());
		schoolinfo.put("courseNum", list3.get(0).toString());
		return JsonUtils.writeObjectToJson(schoolinfo);
	}
	/**
	 * 随机获取4位教师的寄语
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getTeacherWord")
	@ResponseBody
	public String getTeacherWord(HttpServletRequest request) {
		Finder finder=Finder.create( " select * from SYS_PAGE_CONFIG page ");
		finder.append(" where page.fdtype='01' ");
		finder.append("   order by  dbms_random.value ");
//		Pagination  page1=pageConfigService.getPageBySql(finder, 1, 4);
		List pages=pageConfigService.getPageBySql(finder, 1, 4).getList();
		List<Map> teachers=new ArrayList<Map>();
		if(pages!=null&&pages.size()>0){
			for(int i=0;i<pages.size();i++){
				Map page=(Map) pages.get(i);
				Map teacher=new HashMap();
				SysOrgPerson orgPerson = accountService.findById(page.get("FDELEMENTID").toString());
				teacher.put("tname", orgPerson.getFdName());
				teacher.put("content",page.get("FDCONTENT"));
				teacher.put("headimg", orgPerson.getPoto());
				teacher.put("dep", orgPerson.getDeptName()==null?"不详":orgPerson.getDeptName());
				teachers.add(teacher);
			}
		}
		return JsonUtils.writeObjectToJson(teachers) ;
	}
	
	/**
	 * 列表显示学校宣言
	 */
	@RequestMapping(value="getSchoolWord")
	@ResponseBody
	public String getSchoolWord(HttpServletRequest request){
		String pageNoStr=request.getParameter("pageNoStr");
		Map returnMap = new HashMap();
		int pageNo=1;
		if(StringUtil.isNotEmpty(pageNoStr)){
			pageNo=Integer.parseInt(pageNoStr);
		}
		Pagination pagination = getShoolList(pageNo);
		List<PageConfig>  pages = (List<PageConfig>) pagination.getList();
		List<Map> schools=new ArrayList<Map>();
		if(pages!=null&&pages.size()>0){
			for(PageConfig page:pages){
				Map school=new HashMap();
				school.put("sname", departService.getSysOrgElementById(page.getFdElementId()).getFdName());
				school.put("content", page.getFdContent());
				//学校图片
				AttMain attMain=attMainService.getByModelIdAndModelName(page.getFdId(), PageConfig.class.getName());
				school.put("simg", attMain==null?"":attMain.getFdId());
				
				schools.add(school);
				
			}
		}
		returnMap.put("schools", schools);
		returnMap.put("pageNo", pagination.getPageNo());
		returnMap.put("pageTotal", pagination.getTotalPage());
		return JsonUtils.writeObjectToJson(returnMap);
	}
	
	@RequestMapping(value="getStatistics")
	@ResponseBody
	public String getStatistics(HttpServletRequest request){
		String pageNoStr=request.getParameter("pageNoStr");
		Map returnMap = new HashMap();
		int pageNo=1;
		if(StringUtil.isNotEmpty(pageNoStr)){
			pageNo=Integer.parseInt(pageNoStr);
		}
		Finder finder=Finder.create(" from PageConfig page ");
		finder.append(" where page.fdType='02' ");
		finder.append(" order by page.fdOrder ");
		Pagination pagination = pageConfigService.getPage(finder, pageNo,5);
		List<PageConfig>  pages = (List<PageConfig>) pagination.getList();
		List<Map> schoolsNum=new ArrayList<Map>();
		if(pages!=null&&pages.size()>0){
			for(PageConfig page:pages){
				Map school=new HashMap();
				//统计学校的新教师  导师
				List deptIds = new ArrayList();
				getDeptIds(page.getFdElementId(),deptIds);
				int tnum=getNewTeacherNum(deptIds);
				school.put("tnum", tnum);
				//导师
				int mnum=getMentorNum(deptIds);
				school.put("mnum", mnum);
				schoolsNum.add(school);
				
			}
		}

		return JsonUtils.writeObjectToJson(schoolsNum);
	}
	private void getDeptIds(String id,List deptIds){
		String sql="select fdid from sys_org_element  where fd_parentid='"+id+"'  and fd_org_type='2' ";
		List list=departService.findBySQL(sql, null, null);
		if(list==null || list.size()==0){
			return;
		}
		for(int i=0;i<list.size();i++){
			Object obj=list.get(i);
			deptIds.add(list.get(i));
			getDeptIds(obj.toString(),deptIds);
		}
	}
	//某学校教师数量
	private int getNewTeacherNum(List depart){
		String sql=" select count(distinct preteachid) c from ixdf_ntp_bam_score  where preteachid  in (select fdid from sys_org_element where fd_parentid in( ";
		String sqlstr="";
		for(int i=0;i<depart.size();i++){
			Object obj=depart.get(i);
			sqlstr+="'"+obj+"',";
		}
		if(sqlstr.length()>0){
			sqlstr = sqlstr.substring(0,sqlstr.length()-1);
			sql=sql+sqlstr+"))";
			List newTeachers=departService.findBySQL(sql, null, null);
			return Integer.parseInt(newTeachers.get(0).toString());
		}else{
			return 0;
		}
	}
	//某学校导师数量
	private int getMentorNum(List depart){
		String sql=" select count(*) c from sys_user_role where person_role='guidance'  and person_id in (select fdid from sys_org_element where fd_parentid in ( ";
		String sqlstr="";
		for(int i=0;i<depart.size();i++){
			Object obj=depart.get(i);
			sqlstr+="'"+obj+"',";
		}
		if(sqlstr.length()>0){
			sqlstr = sqlstr.substring(0,sqlstr.length()-1);
			sql=sql+sqlstr+"))";
			List mentors=departService.findBySQL(sql, null, null);
			return Integer.parseInt(mentors.get(0).toString());
		}else{
			return 0;
		}
	
	}
	
	private Pagination getShoolList(int pageNo){
		
		Finder finder=Finder.create(" from PageConfig page ");
		finder.append(" where page.fdType='02' ");
		finder.append(" order by page.fdOrder ");
		return  pageConfigService.getPage(finder, pageNo,5);
	}
}

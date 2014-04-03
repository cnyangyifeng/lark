package com.kuxue.action.base;

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

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.course.CourseInfo;
import com.kuxue.model.course.CourseParticipateAuth;
import com.kuxue.model.course.CourseTag;
import com.kuxue.model.course.TagInfo;
import com.kuxue.model.organization.RoleEnum;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.model.score.ScoreStatistics;
import com.kuxue.service.course.CourseTagService;
import com.kuxue.service.course.TagInfoService;
import com.kuxue.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/tag")
@Scope("request")
public class TagAjaxController {

	@Autowired
	private TagInfoService tagInfoService;

	@Autowired
	private CourseTagService courseTagService;
	/**
	 * 添加或更新标签
	 */
	@RequestMapping(value="saveTagInfo")
	@ResponseBody
	public String saveTagInfo(HttpServletRequest request){
		String tagInfoId=request.getParameter("fdId");
		String tagInfoName=request.getParameter("fdName");
		String tagInfoDesc=request.getParameter("fdDescription");
		TagInfo tagInfo=null;
		if(StringUtil.isNotBlank(tagInfoId)){
			tagInfo=tagInfoService.get(tagInfoId);
		}else{
			tagInfo=new TagInfo();
		}
		tagInfo.setFdName(tagInfoName);
		tagInfo.setFdDescription(tagInfoDesc);
		tagInfoService.save(tagInfo);
		Map map=new HashMap();
		map.put("id", tagInfo.getFdId());
		return JsonUtils.writeObjectToJson(map);
	    
	}
	
	@RequestMapping(value="getTagInfoOfcourses")
	@ResponseBody
	public String getTagInfoOfcourses(HttpServletRequest request){
		String tagId=request.getParameter("fdId");
		String pageNostr=request.getParameter("pageNo");
		String keyword=request.getParameter("keyword");
		int pageNo;
		if (StringUtil.isNotBlank(pageNostr)) {
			pageNo = Integer.parseInt(pageNostr);
		} else {
			pageNo = 1;
		}
		List coursetags=new ArrayList();
		Map map=new HashMap();
		Pagination page=null;
		TagInfo tag=null;
		if(StringUtil.isNotBlank(tagId)){
			tag=tagInfoService.get(tagId);
			page=courseTagService.findCourseByTag(tagId, keyword,pageNo);
			if(page.getTotalCount()>0){
				List list = page.getList();
				for(int i=0;i<list.size();i++){
					CourseTag courseTag =(CourseTag)list.get(i);
					Map mcpa=new HashMap();//
					mcpa.put("id", courseTag.getCourses().getFdId());
					mcpa.put("name", courseTag.getCourses().getFdTitle());
					mcpa.put("author",courseTag.getCourses().getFdAuthor()==""||courseTag.getCourses().getFdAuthor()==null?courseTag.getCourses().getCreator().getFdName():courseTag.getCourses().getFdAuthor());
					coursetags.add(mcpa);
				}
			}
			
		}
		map.put("totalPage", page==null?0:page.getTotalPage());
		map.put("currentPage", pageNo);
		map.put("list", coursetags);
		map.put("totalCount", page==null?0:page.getTotalCount());
		map.put("StartPage", page==null?0:page.getStartPage());
		map.put("EndPage",page==null?0:page.getEndPage());
		map.put("StartOperate", page==null?0:page.getStartOperate());
		map.put("EndOperate", page==null?0:page.getEndOperate());
		map.put("startNum",page==null?0:page.getStartNum());;
		map.put("endNum", page==null?0:page.getEndNum());
		map.put("fdId",tag==null?"":tag.getFdId());
		map.put("fdName",tag==null?"":tag.getFdName());
		map.put("fdDesc",tag==null?"":tag.getFdDescription());
		return JsonUtils.writeObjectToJson(map);
	}
	/**
	 * 标签删除验证
	 * @param request
	 * @return
	 */
	@RequestMapping(value="isExsitTagOfcourse")
	@ResponseBody
	public boolean isExsitTagOfcourse(HttpServletRequest request){
		String idlist =request.getParameter("ids");
		String fdKey=request.getParameter("fdKey");
		Finder finder=Finder.create("from CourseTag courseTag");
		List list=new ArrayList();
		String [] ids=idlist.split(",");
		if(ids!=null&&ids.length>0){
			StringBuffer sbf=new StringBuffer();
			finder.append("where courseTag.tag.fdId in(");
			for(int i=0;i<ids.length;i++){
				sbf.append("'"+ids[i]+"',");
			}
			String str=sbf.substring(0, sbf.lastIndexOf(","));
			finder.append(str+")");
			if(StringUtil.isNotBlank(fdKey)){
				finder.append(" and courseTag.tag.fdName like :fdkey");
				finder.setParam("fdkey", "%"+fdKey+"%");
			}
			list=courseTagService.find(finder);
		}
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		} 
	}
	
}

package cn.me.xdf.action.base;

import java.text.SimpleDateFormat;
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

import cn.me.xdf.common.json.JsonUtils;
import cn.me.xdf.model.course.CourseCatalog;
import cn.me.xdf.model.organization.SysOrgElement;
import cn.me.xdf.model.system.PageConfig;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.SysOrgDepartService;
import cn.me.xdf.service.system.SysPageConfigService;

@Controller
@RequestMapping(value = "/ajax/page")
@Scope("request")
public class PageAjaxController {

	@Autowired
	private SysPageConfigService pageConfigService;

	@Autowired
	private SysOrgDepartService sysOrgDepartService;

	/**
	 * 配置列表
	 */
	 public SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd h:m:s a");
	@RequestMapping(value = "getPages")
	@ResponseBody
	public String getPages(HttpServletRequest request) {
		String ptype = request.getParameter("ptype");
		String fdkey = request.getParameter("fdkey");
		List<PageConfig> list = pageConfigService.getPageConfigs(ptype, fdkey);
		@SuppressWarnings("rawtypes")
		List<Map> maps = new ArrayList<Map>();
		if(list!=null&&list.size()>0){
			for (PageConfig page : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", page.getFdId());
				SysOrgElement element = sysOrgDepartService
						.getSysOrgElementById(page.getFdElementId());
				map.put("name", element.getFdName());
				map.put("content", page.getFdContent());
				map.put("order", page.getFdOrder().toString());
				map.put("type", page.getFdType());
				map.put("createtime", sdf.format(page.getFdCreateTime()));
				if("01".equals(ptype)){
					map.put("org", element.getHbmParent().getFdName());//老师寄语 加部门信息
				}
				maps.add(map);
			}
		}
		Map<String, Object> mapAll = new HashMap<String, Object>();
		mapAll.put("list", maps);
		mapAll.put("fdkey", fdkey);
		mapAll.put("ptype", ptype);
		return JsonUtils.writeObjectToJson(mapAll);
	}

	/**
	 * 更新顺序
	 */
	@RequestMapping(value="updatePageOrder")
	public void updatePageOrder(HttpServletRequest request){
		String lecture=request.getParameter("lecture");
		List<Map> lectures = JsonUtils.readObjectByJson(lecture, List.class);
		if(lectures!=null && lectures.size()>0){
			for(Map lectureMap:lectures){
				String pId = (String)lectureMap.get("id");
				if(StringUtil.isNotEmpty(pId)){
					PageConfig page = pageConfigService.get(pId);
					page.setFdOrder((Integer)lectureMap.get("order"));
					pageConfigService.save(page);
				}
			}
		}
	}

	/**
	 * 删除某个
	 */
	@RequestMapping(value="deleteOnePage")
	@ResponseBody
	public void deleOnePage(HttpServletRequest request) {
		String pIds = request.getParameter("pId");
		String ptype=request.getParameter("ptype");
		if (StringUtil.isNotEmpty(pIds)) {
			String[] pIdlist = pIds.split(",");
			String pageId = "";
			for (int i = 0; i < pIdlist.length; i++) {
				pageId = pIdlist[i];
				pageConfigService.delete(pageId);
			}
		}
		//updatePageOrder(ptype);

	}

	/**
	 * 删除所有
	 */
	@RequestMapping(value="deleAllPages")
	@ResponseBody
	public void deleAllPages(HttpServletRequest request) {
		String fdkey = request.getParameter("fdkey");
		String ptype = request.getParameter("ptype");
		if (StringUtil.isNotEmpty(fdkey)) {// 有搜索关键字
			List<PageConfig> pages = pageConfigService.getPageConfigs(ptype,
					fdkey);
			if (pages != null && pages.size() > 0) {
				for (PageConfig page : pages) {
					pageConfigService.delete(page.getFdId());
				}
			}
		} else {// 无搜索关键字
			List<PageConfig> pages = pageConfigService.getPageConfigs(ptype,
					"");
			if (pages != null && pages.size() > 0) {
				for (PageConfig page : pages) {
					pageConfigService.delete(page.getFdId());
				}
			}
		}
		//updatePageOrder(ptype);

	}
	//更新顺序
	private void updatePageOrder(String ptype){
		List<PageConfig> pages=pageConfigService.getPageConfigs(ptype,"");
		if(pages!=null&&pages.size()>0){
			for(int i=0;i<pages.size();i++){
				PageConfig page=pages.get(i);
				page.setFdOrder(i+1);
				pageConfigService.save(page);
			}
		}
		
	}
	//验证是否已添加过该对象
	@RequestMapping(value = "checkUnique")
	@ResponseBody
	public String checkUnique(HttpServletRequest request){
		String fdname=request.getParameter("fdname");
		String elementId=request.getParameter("fdElementId");
		Map map=new HashMap();
		boolean isnot=pageConfigService.checkIdAndName(elementId, fdname);//判断输入名和id是否匹配
		if(isnot){//表示匹配,然后验证是否唯一
			map.put("match",true);
			map.put("ispermit", pageConfigService.checkUnique(elementId));	
		}else{
			map.put("match",false);
		}
		return JsonUtils.writeObjectToJson(map);
	}
}

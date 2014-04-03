package com.kuxue.action.material;

import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kuxue.common.json.JsonUtils;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.base.AttMain;
import com.kuxue.model.base.Constant;
import com.kuxue.model.course.CourseCatalog;
import com.kuxue.model.course.CourseContent;
import com.kuxue.model.material.MaterialAuth;
import com.kuxue.model.material.MaterialInfo;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.service.AccountService;
import com.kuxue.service.base.AttMainService;
import com.kuxue.service.course.CourseCatalogService;
import com.kuxue.service.course.CourseContentService;
import com.kuxue.service.material.MaterialAuthService;
import com.kuxue.service.material.MaterialDiscussInfoService;
import com.kuxue.service.material.MaterialService;
import com.kuxue.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/ajax/material")
@Scope("request")
public class MaterialAjaxController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialAuthService materialAuthService;

	@Autowired
	private AttMainService attMainService;

	@Autowired
	private CourseContentService courseContentService;

	@Autowired
	private CourseCatalogService courseCatalogService;
	
	@Autowired
	private MaterialDiscussInfoService materialDiscussInfoService;

	/**
	 * 删除素材相关信息==>素材==>课程内容==>节
	 * 
	 * @param request
	 */
	@RequestMapping(value = "deleteMaterial")
	@ResponseBody
	public void deleteMaterial(HttpServletRequest request) {
		String materialId = request.getParameter("materialId");
		// 删除素材
		deleteMaterialData(materialId);
	}
	
	
	/**
	 * 批量删除素材的方法
	 * 
	 * @param request
	 */
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public void batchDelete(HttpServletRequest request) {
		String fdIds = request.getParameter("materialIds");
		if (StringUtil.isNotEmpty(fdIds)) {
			String[] materialId = fdIds.split(",");
			String fdId = "";
			for (int i = 0; i < materialId.length; i++) {
				fdId = materialId[i];
				deleteMaterialData(fdId);
			}
		}
	}
	@RequestMapping(value = "prepareDelete")
	@ResponseBody
	public List<String> prepareDelete(HttpServletRequest request){
		String fdIds = request.getParameter("materialIds");
		List<String> list=new ArrayList<String>();
		if (StringUtil.isNotEmpty(fdIds)) {
			String[] materialId = fdIds.split(",");
			String fdId = "";
			String auth = "";
			for (int i = 0; i < materialId.length; i++) {
				fdId = materialId[i];
				auth = findEditAuth(fdId);
				if(StringUtil.isBlank(auth)){
					continue;
				}else{
					list.add(auth);
				}
			}
		}
		return list;
	}
	
	private String findEditAuth(String materialId){
		MaterialInfo materialInfo = materialService.load(materialId);
		if(materialInfo.getCreator().getFdId().equals(ShiroUtils.getUser().getId())){
			return materialInfo.getFdId();
		}
		if(materialInfo.getIsPublish()==false){
		    MaterialAuth auth = materialAuthService
				  .findByMaterialIdAndUserId(materialId,ShiroUtils.getUser().getId());
		    if(auth.getIsEditer()==true){
		    	return materialInfo.getFdId();
		    }
		}
		return null;
		   
	}

	@RequestMapping(value = "deleteAllMaterial")
	@ResponseBody
	public void deleteAllMaterial(HttpServletRequest request) {
		String fdType = request.getParameter("fdType");
		String fdName = request.getParameter("fdName");
		String order = request.getParameter("order");
		Pagination page = materialService.findMaterialList(fdType, 1,
				SimplePage.DEF_COUNT, fdName, order);
		int i = page.getTotalPage();
		if (i > 0) {
			for (int j = 0; j <= i; j++) {
				page = materialService.findMaterialList(fdType, j+1, SimplePage.DEF_COUNT, fdName,order);
				List list = page.getList();
				if (list != null && list.size() > 0) {
					for (Object obj : list) {
						Map map = (Map) obj;
						String materialId = (String) map.get("FDID");
						if(ShiroUtils.isAdmin()){
							deleteMaterialData(materialId);
						} else{
							if(StringUtil.isNotBlank(findEditAuth(materialId))){
								deleteMaterialData(materialId);
							}
						}
					}
				}
			}
		}

	}

	public void deleteMaterialData(String materialId) {
		List<CourseContent> courseList = courseContentService.findByProperty(
				"material.fdId", materialId);
		if (courseList != null && courseList.size() > 0) {
			for (CourseContent courseContent : courseList) {
				CourseCatalog catalog = courseContent.getCatalog();
				if (catalog != null && catalog.getFdTotalContent() != null) {
					catalog.setFdTotalContent(catalog.getFdTotalContent() - 1);
					courseCatalogService.save(catalog);
				}
				courseContentService.delete(courseContent.getFdId());
			}
		}
		MaterialInfo material = materialService.load(materialId);
		material.setIsAvailable(false);
		materialService.save(material);
	}
	@RequestMapping(value = "updateDownloadNum")
	@ResponseBody
	public Integer updateDownloadNum(HttpServletRequest request){
		String fdId = request.getParameter("materialId");
		materialDiscussInfoService.updateMaterialDiscussInfo(Constant.MATERIALDISCUSSINFO_TYPE_DOWNLOAD, fdId);
	    MaterialInfo info = materialService.load(fdId);
	    return info.getFdDownloads();
	}
	
	/**
	 * 保存赞
	 * @param materialId
	 */
	@RequestMapping(value = "saveLaud")
	@ResponseBody
	public Integer saveLaud(String materialId){
		materialDiscussInfoService.updateMaterialDiscussInfo(Constant.MATERIALDISCUSSINFO_TYPE_LAUD, materialId);
	    MaterialInfo info = materialService.load(materialId);
		return info.getFdLauds();
	}


	/**
	 * ajax找出素材列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findList")
	@ResponseBody
	public ModelAndView findList(Model model, HttpServletRequest request) {
		String fdType = request.getParameter("fdType");
		String pageNoStr = request.getParameter("pageNo");
		String fdName = request.getParameter("fdName");
		String order = request.getParameter("order");
		int pageNo;
		if (StringUtil.isNotBlank(pageNoStr)
				&& StringUtil.isNotEmpty(pageNoStr)) {
			pageNo = Integer.parseInt(pageNoStr);
		} else {
			pageNo = 1;
		}
		if (StringUtil.isNotBlank(fdType) && StringUtil.isNotEmpty(fdType)) {
			Pagination page = materialService.findMaterialList(fdType, pageNo,
				SimplePage.DEF_COUNT, fdName, order);
			model.addAttribute("page", page);
		}
		if(Constant.MATERIAL_TYPE_TEST.equals(fdType)){
			return new ModelAndView(
					"forward:/WEB-INF/views/material/divMatQuestList.jsp");	
		}else if(Constant.MATERIAL_TYPE_JOBPACKAGE.equals(fdType)){
			return new ModelAndView(
					"forward:/WEB-INF/views/material/divMatTaskList.jsp");	
		}else{
			return new ModelAndView(
					"forward:/WEB-INF/views/material/divMatList.jsp");
		}
	}

	@RequestMapping(value = "getMaterialBykey")
	@ResponseBody
	public List<Map> getMaterialBykey(HttpServletRequest request) {
		String key = request.getParameter("q");
		String type = request.getParameter("type");
		return materialService.getMaterialsTop10Bykey(key, type);
	}

	@RequestMapping(value = "saveMaterial")
	@ResponseBody
	public Map saveMaterial(HttpServletRequest request) {
		String type = request.getParameter("type");
		String fileName = request.getParameter("fileName");
		String attId = request.getParameter("attId");
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setFdName(fileName);
		materialInfo.setFdType(type);
		materialInfo.setIsAvailable(true);
		materialInfo.setIsPublish(true);
		materialInfo.setIsDownload(true);
		materialInfo.setFdCreateTime(new Date());
		SysOrgPerson creator = accountService
				.load(ShiroUtils.getUser().getId());
		materialInfo.setCreator(creator);
		materialInfo.setFdAuthor(creator.getFdName());
		materialInfo.setFdAuthorDescription(creator.getSelfIntroduction());
		materialService.save(materialInfo);
		saveAtt(attId,materialInfo.getFdId());
		Map map = new HashMap();
		map.put("id", materialInfo.getFdId());
		map.put("name", materialInfo.getFdName());
		return map;
	}

	/**
	 * 更新或保存素材(共用)
	 * 
	 * @param materialInfo
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdateVideo")
	@ResponseBody
	public void saveOrUpdateVideo(HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		String permission = request.getParameter("permission");
		String kingUser = request.getParameter("kingUser");
		String attId = request.getParameter("attId");
		String richText = request.getParameter("richText");//富文本内容
		String isDownload = request.getParameter("isDownload");
		MaterialInfo info;
		if (StringUtil.isBlank(fdId)) {
			info = new MaterialInfo();
			info.setFdType(request.getParameter("fdType"));
			SysOrgPerson creator = accountService.load(ShiroUtils.getUser().getId());
			info.setCreator(creator);
			info.setFdCreateTime(new Date());
			info.setIsAvailable(true);
		}else{
			info = materialService.load(fdId);
		}
		AttMain main = attMainService.getByModelId(info.getFdId());
		if(StringUtil.isNotBlank(attId)){
			if(main==null){
				saveAtt(attId, info.getFdId());
			}else{
				if(!main.getFdId().equals(attId)){
				  attMainService.deleteAttMainByModelId(info.getFdId());
				  saveAtt(attId, info.getFdId());
				}
		    }
		}else{
			attMainService.deleteAttMainByModelId(info.getFdId());
		}
		info.setIsPublish("open".equals(permission)?true:false);
		info.setIsDownload("yes".equals(isDownload)?true:false);
		info.setFdAuthor(request.getParameter("author"));
		info.setFdAuthorDescription(request.getParameter("authorIntro"));
		info.setFdLink(request.getParameter("videoUrl"));
		info.setFdName(request.getParameter("videoName"));
		info.setFdDescription(request.getParameter("videoIntro"));
		info.setRichContent(richText);
		String fileNetId=request.getParameter("fileNetId");
		String fileNetName=request.getParameter("fileNetName");
		info.setMfileNetId(fileNetId);
		info.setMfileNetName(fileNetName);
		materialService.save(info);
		if (!permission.equals("open")) {
			materialService.saveMaterAuth(kingUser, info.getFdId(),info.getCreator().getFdId());
		}
	}

	/**
	 * 保存附件关系
	 * 
	 * @param attId
	 * @param modelId
	 */
	public void saveAtt(String attId, String modelId) {
		AttMain att = attMainService.get(attId);
		att.setFdModelId(modelId);
		att.setFdModelName(MaterialInfo.class.getName());
		attMainService.save(att);
	}

	/**
	 * 得到指定素材的权限信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAuthInfoByMaterId")
	@ResponseBody
	public String getAuthInfoByMaterId(HttpServletRequest request) {
		// 获取课程ID
		String MaterialId = request.getParameter("MaterialId");
		List<Map> list = materialService.findAuthInfoByMaterialId(MaterialId);
		Map map = new HashMap();
		map.put("user", list);
		return JsonUtils.writeObjectToJson(map);
	}

	/**
	 * 得到测试信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getMaterial")
	@ResponseBody
	public String getMaterial(HttpServletRequest request){
		// 获取课程ID
		String materialId = request.getParameter("materialId");
		MaterialInfo info = materialService.findUniqueByProperty("fdId", materialId);
		Map map = new HashMap();
		map.put("fdName",info.getFdName());
		map.put("description", info.getFdDescription());
		map.put("time", info.getFdStudyTime()==null?"0":info.getFdStudyTime());
		map.put("score", info.getFdScore()==null?"0":info.getFdScore());
		map.put("fdAuthor", info.getFdAuthor());
		map.put("fdAuthorDescription", info.getFdAuthorDescription());
		map.put("isPublish", info.getIsPublish());
		map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:MM aa").format(info.getFdCreateTime()));
		
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 得到指定素材的试题信息（只针对测试）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getExamQuestionByMaterId")
	@ResponseBody
	public String getExamQuestionByMaterId(HttpServletRequest request) {
		// 获取课程ID
		String MaterialId = request.getParameter("materialId");
		MaterialInfo info = materialService.get(MaterialId);
		if(info==null){
			return null;
		}
		List<Map> list = materialService.getExamQuestionByMaterId(info);
		Map map = new HashMap();
		map.put("qusetions", list);
		map.put("name", info.getFdName());
		map.put("score", info.getFdScore());
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 得到指定素材的试题信息（只针对测试）加密
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getExamQuestionSrcByMaterId")
	@ResponseBody
	public String getExamQuestionSrcByMaterId(HttpServletRequest request) {
		// 获取课程ID
		String MaterialId = request.getParameter("materialId");
		MaterialInfo info = materialService.get(MaterialId);
		if(info==null){
			return null;
		}
		List<Map> list = materialService.getExamQuestionSrcByMaterId(info);
		Map map = new HashMap();
		map.put("qusetions", list);
		map.put("name", info.getFdName());
		return JsonUtils.writeObjectToJson(map);
	}
	
	/**
	 * 获得创建者
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCreater")
	@ResponseBody
	public String getCreater(HttpServletRequest request) {
		// 获取课程ID
		String MaterialId = request.getParameter("materialId");
		SysOrgPerson person;
		if(StringUtil.isEmpty(MaterialId)){
			person= accountService.load(ShiroUtils.getUser().getId());
		}else{
			MaterialInfo info = materialService.get(MaterialId);
			person = info.getCreator();
		}
		Map map = new HashMap();
		map.put("fdId", person.getFdId());
		map.put("name", person.getRealName());
		map.put("email", person.getFdEmail());
		map.put("dept", person.getDeptName());
		map.put("url", person.getPoto());
		return JsonUtils.writeObjectToJson(map);
		
	}
	
	/**
	 * 获得资源状态（是否转换成功）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getMaterialStu")
	@ResponseBody
	public String getMaterialStu(HttpServletRequest request) {
		// 获取课程ID
		String MaterialId = request.getParameter("materialId");
		MaterialInfo info = materialService.get(MaterialId);
		List<AttMain> attMains = info.getAttMains();
		if(attMains==null||attMains.size()==0){
			return JsonUtils.writeObjectToJson("ok");
		}else{
			if(attMains.get(0).getFlag()==null||attMains.get(0).getFlag()==0){
				return JsonUtils.writeObjectToJson("noOk");
			}else{
				return JsonUtils.writeObjectToJson("ok");
			}
		}
		
	}
}

package cn.me.xdf.action.material;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.me.xdf.common.page.Pagination;
import cn.me.xdf.common.page.SimplePage;
import cn.me.xdf.model.base.AttMain;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.material.MaterialAuth;
import cn.me.xdf.model.material.MaterialInfo;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.score.ScoreStatistics;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.material.MaterialAuthService;
import cn.me.xdf.service.material.MaterialService;
import cn.me.xdf.service.score.ScoreStatisticsService;
import cn.me.xdf.utils.ShiroUtils;

/**
 * 材料信息
 * @author yuhuizhe
 */
@Controller
@RequestMapping(value = "/material")
@Scope("request")
public class MaterialController {
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private MaterialAuthService materialAuthService;
	
	@Autowired
	private ScoreStatisticsService scoreStatisticsService;
	
	@Autowired
	private AttMainService attMainService;
	
	@Autowired
	private AccountService accountService;
	
	
	/**
	 * 返回编辑，查看素材页面
	 */
	@RequestMapping(value="materialFoward")
	public String materialFoward(Model model ,HttpServletRequest request){
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNotBlank(fdId)){
			MaterialInfo materialInfo = materialService.get(fdId);
			model.addAttribute("materialInfo", materialInfo);
			ScoreStatistics score = scoreStatisticsService.findScoreStatisticsByModelNameAndModelId
					           (MaterialInfo.class.getName(),materialInfo.getFdId());
			if(score!=null){
				model.addAttribute("score", score);
			}
		    AttMain main = attMainService.getByModelId(fdId);
		    if(main!=null){
		    	model.addAttribute("main", main);
		    }
		    String creatorId = materialInfo.getCreator().getFdId();
		    String loginUserId = ShiroUtils.getUser().getId();
		    if(creatorId.equals(loginUserId)||ShiroUtils.isAdmin()){
		    	return fowardEdit(materialInfo.getFdType());//返回的是素材edit页面
		    }
		    if(materialInfo.getIsPublish()==false){
		    	MaterialAuth auth = materialAuthService.findByMaterialIdAndUserId(fdId,ShiroUtils.getUser().getId());
			    if(auth.getIsEditer()==true){
			    	return fowardEdit(materialInfo.getFdType());
				}else{
					return fowardView(materialInfo.getFdType());
				}
		    }else{
				return fowardView(materialInfo.getFdType());
			}
		}
		return null;
	}
	
	/**
	 * 返回添加素材的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="materialAddFoward")
	public String materialAddFoward(HttpServletRequest request,Model model){
		String fdType = request.getParameter("fdType");
		SysOrgPerson person = accountService.get(ShiroUtils.getUser().getId());
		model.addAttribute("person",person);
		return fowardEdit(fdType);
	}
	//返回的是view页面
	private String fowardView(String fdType){
		//测试
    	if(fdType.equals(Constant.MATERIAL_TYPE_TEST)){
    		return "/material/exam_view";
    	}
    	//作业包
    	if(fdType.equals(Constant.MATERIAL_TYPE_JOBPACKAGE)){
    		return "/material/viewExamPaper";
    	}//富文本
    	if(fdType.equals(Constant.MATERIAL_TYPE_RICHTEXT)){
    		return "/material/richtext_view";
    	}else{
    		//其它素材种类
    		return "/material/viewVideo";
    	}
	}
	private String fowardEdit(String fdType){
		//测试
    	if(fdType.equals(Constant.MATERIAL_TYPE_TEST)){
    		return "/material/exam_add";
    	}
    	//作业包
    	if(fdType.equals(Constant.MATERIAL_TYPE_JOBPACKAGE)){
    		return "/material/addExamPaper";
    	}
    	//富文本
    	if(fdType.equals(Constant.MATERIAL_TYPE_RICHTEXT)){
    		return "/material/richtext_add";
    	}else{
    		//其它素材种类
    		return "/material/editVideo";
    	}
	}

	/**
	 * 查找素材列表
	 * fdType 类型 01视频 02音频 03图片 04文档等
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findList", method = RequestMethod.GET)
	public String findList(Model model , HttpServletRequest request) {
		String fdType = request.getParameter("fdType");
		String pageNoStr = request.getParameter("pageNo");
		String fdName = request.getParameter("fdName");
		String order = request.getParameter("order");
		int pageNo;
		if(StringUtil.isNotBlank(pageNoStr)&&StringUtil.isNotEmpty(pageNoStr)){
			pageNo = Integer.parseInt(pageNoStr);
		} else {
			pageNo = 1;
		}
		if(StringUtil.isNotBlank(fdType)&&StringUtil.isNotEmpty(fdType)){
			Pagination page = materialService.findMaterialList(fdType, pageNo, SimplePage.DEF_COUNT,fdName,order);
			model.addAttribute("page", page);
		}
		return "/material/materialList";
	}

	
}

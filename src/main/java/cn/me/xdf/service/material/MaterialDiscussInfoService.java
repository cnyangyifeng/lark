package cn.me.xdf.service.material;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.base.Constant;
import cn.me.xdf.model.material.MaterialDiscussInfo;
import cn.me.xdf.service.AccountService;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.utils.ShiroUtils;
import cn.me.xdf.model.material.MaterialInfo;
/**
 * 素材对应赞，播放次数和下载次数详细的serivce
 * @author hpnn
 *
 */
@Service
@Transactional(readOnly = true)
public class MaterialDiscussInfoService extends BaseService{
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MaterialService materialService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<MaterialDiscussInfo> getEntityClass() {
		return MaterialDiscussInfo.class;
	}
	/**
	 * 保存人员播放 赞 下载 信息
	 * @param fdType
	 * @param materialId
	 */
	@Transactional(readOnly = false)
	public void updateMaterialDiscussInfo(String fdType,String fdId){
		
		MaterialInfo materialInfo = materialService.get(fdId);
		/////////////操作类型(下载:01、播放:02、攒:03)
		if(fdType.equals(Constant.MATERIALDISCUSSINFO_TYPE_DOWNLOAD)){
			if(materialInfo.getFdDownloads()==null){
				materialInfo.setFdDownloads(1);
			}else{
				materialInfo.setFdDownloads(materialInfo.getFdDownloads()+1);
			}
			materialService.save(materialInfo);
		}
		if(fdType.equals(Constant.MATERIALDISCUSSINFO_TYPE_PLAY)){
			if(materialInfo.getFdPlays()==null){
				materialInfo.setFdPlays(1);
			}else{
				materialInfo.setFdPlays(materialInfo.getFdPlays()+1);
			}
			materialService.save(materialInfo);
		}
		if(fdType.equals(Constant.MATERIALDISCUSSINFO_TYPE_LAUD)){
			if(materialInfo.getFdLauds()==null){
				materialInfo.setFdLauds(1);
			}else{
				materialInfo.setFdLauds(materialInfo.getFdLauds()+1);
			}
			materialService.save(materialInfo);
		}
		MaterialDiscussInfo discussInfo = new MaterialDiscussInfo();
		discussInfo.setMaterialInfo(materialInfo);
		discussInfo.setFdType(fdType);
		discussInfo.setCreatTime(new Date());
		discussInfo.setOrgPerson(accountService.findById(ShiroUtils.getUser().getId()));
		save(discussInfo);
	}
	/**
	 * 查看当前用户是否具有赞权利
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public boolean isCanLaud(String materialId){
		Finder finder = Finder.create(" from MaterialDiscussInfo info where info.fdType=:fdType and orgPerson.fdId=:personId and materialInfo.fdId=:materialId");
		finder.setParam("fdType", Constant.MATERIALDISCUSSINFO_TYPE_LAUD);
		finder.setParam("personId", ShiroUtils.getUser().getId());
		finder.setParam("materialId", materialId);
		List<MaterialDiscussInfo> discussInfoList = find(finder);
		if(discussInfoList==null||discussInfoList.isEmpty()){
			return true;//可以赞
		}else{
			return false;
		}
	}

}

package cn.me.xdf.service.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.common.hibernate4.Finder;
import cn.me.xdf.model.course.CourseAuth;
import cn.me.xdf.model.material.MaterialAuth;
import cn.me.xdf.model.organization.SysOrgPerson;
import cn.me.xdf.model.organization.User;
import cn.me.xdf.service.BaseService;
/**
 * 
 * 资源权限service
 * 
 * @author
 * 
 */
@Service
@Transactional(readOnly = false)
public class MaterialAuthService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public  Class<MaterialAuth> getEntityClass() {
		return MaterialAuth.class;
	}
	
	/**
	 * 删除资源权限
	 * 
	 */
	@Transactional(readOnly = false)
	public void deleMaterialAuth(MaterialAuth materialAuth){
		delete(materialAuth);
	}
	/**
	 * 查找资源权限
	 * 
	 */
	@Transactional(readOnly = true)
	public MaterialAuth findByMaterialIdAndUserId(String materialId,String userId){
		Finder finder = Finder
				.create("from MaterialAuth anth ");
		finder.append("where anth.material.fdId = :materialId and anth.fdUser.fdId = :userId");
		finder.setParam("materialId", materialId);
		finder.setParam("userId", userId);
		return (MaterialAuth) super.find(finder).get(0);
	}

	/**
	 * 根据资源Id删除所有资源权限
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void deleMaterialAuthByMaterialId(String materialId){
		Finder finder = Finder
				.create("from MaterialAuth anth ");
		finder.append("where anth.material.fdId = :materialId");
		finder.setParam("materialId", materialId);
		List<MaterialAuth> list = super.find(finder);
		for (MaterialAuth materialAuth : list) {
			delete(materialAuth.getFdId());
		}
	}

}

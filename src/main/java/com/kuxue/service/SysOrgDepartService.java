package com.kuxue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.hibernate4.Value;
import com.kuxue.model.organization.SysOrgDepart;
import com.kuxue.model.organization.SysOrgElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class SysOrgDepartService extends BaseService {

	/**
	 * 根据机构编码获取机构下的所有部门，包括本机构
	 * 
	 * @param orgId
	 * @return
	 */
	public List<SysOrgElement> findAllChildrenById(String orgId) {
		List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
        SysOrgElement element = load(orgId);
		if (element != null) {
			elements.add(element);
			loadAllChildren(element.getFdChildren(), elements);
		}
		return elements;
	}
	
	/**
	 * 根据类型查询部门信息
	 * @param type
	 * @return
	 */
	public List<SysOrgDepart> findAllOrgByType(int type){

        return findByCriteria(SysOrgDepart.class, Value.eq("fdOrgType", type));
	}
	
	@SuppressWarnings("unchecked")
	public List<SysOrgDepart> findTypeis1(){
		Finder finder = Finder
				//TODO wg mysql:function nlssort does not exists.
//				.create("from SysOrgElement e where e.hbmParent is not null and e.fdOrgType=1  order by NLSSORT(e.fdName, 'NLS_SORT=SCHINESE_PINYIN_M')");
		.create("from SysOrgElement e where e.hbmParent is not null and e.fdOrgType=1 ");
		return find(finder);
	}
	
	@SuppressWarnings("unchecked")
	public SysOrgElement getSysOrgElementById(String id){
		Finder finder = Finder
				.create("from SysOrgElement e where e.fdId=:id");
		finder.setParam("id", id);
		return findUnique(finder);
	}
	
	public List<SysOrgDepart> findElement(String fdId){
		
		return null;
	}



	private void loadAllChildren(List<SysOrgElement> elements,
			List<SysOrgElement> targetEelements) {
		for (SysOrgElement e : elements) {
			targetEelements.add(e);
			if (e.getHbmChildren() != null) {
				loadAllChildren(e.getFdChildren(), targetEelements);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<SysOrgDepart> getEntityClass() {
		return SysOrgDepart.class;
	}
    
	public List<SysOrgElement> getSchools(String key){
		Finder finder=Finder.create(" from SysOrgElement o where o.hbmParent.fdNo='100' and o.fdName like :key ");
		finder.setParam("key", "%"+key+"%");
		return find(finder);
	}
}

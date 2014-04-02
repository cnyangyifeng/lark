package com.kuxue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.model.organization.SysOrgPerson;
import com.kuxue.view.model.VUserData;

@Service
@Transactional(readOnly = true)
public class SysOrgPersonService extends BaseService{

	@SuppressWarnings("unchecked")
	@Override
	public Class<SysOrgPerson> getEntityClass() {
		return SysOrgPerson.class; 
	}
	
	/**
	 * 根据登录名称和姓名模糊查询用户(前10条)
	 * 
	 * @param key
	 * @return
	 */
	public List<SysOrgPerson> findUserByLinkLoginAndRealNameTop10(String key){
		Finder finder = Finder
				.create("from SysOrgPerson p ");
		finder.append("where (lower(p.loginName) like :key1  or lower(p.fdName) like :key2 or lower(p.hbmParent.fdName) like :key3 or lower(p.fdEmail) like :key4 ) and (p.loginName <> :admin  or p.loginName is null) and  (p.fdAvailable <> '00' or p.fdAvailable is null )");
		finder.setParam("key1", "%"+key+"%");
		finder.setParam("key2", "%"+key+"%");
		finder.setParam("key3", "%"+key+"%");
		finder.setParam("key4", "%"+key+"%");
		finder.setParam("admin", "admin");
		return (List<SysOrgPerson>)getPage(finder, 1,10).getList();
		
	}
	
	public Map getUserInfo(String userId){
		Map map = new HashMap();
		Finder finder = Finder
				.create("select p.fdBloodType from SysOrgPersonTemp p ");
		finder.append("where p.fdId=:userId ");
		finder.setParam("userId", userId);
		String fdBloodType = findUnique(finder);
		map.put("fdBloodType",fdBloodType==null?"不详":fdBloodType);
		Finder finder1 = Finder
				.create("select p.selfIntroduction from SysOrgPersonTemp p ");
		finder1.append("where p.fdId=:userId ");
		finder1.setParam("userId", userId);
		String selfIntroduction = findUnique(finder);
		map.put("selfIntroduction", selfIntroduction==null?"":selfIntroduction);
		return map;
	}
	
	public List<VUserData> getVUserDatas(String [] ids){
		List<VUserData> datas = new ArrayList<VUserData>();
		for(String id:ids){
			SysOrgPerson person = get(id);
			VUserData vUserData = new VUserData();
			vUserData.setBirthday(person.getFdBirthDay());
			vUserData.setBlood(person.getFdBloodType());
			vUserData.setDept(person.getHbmParent()==null?"":person.getHbmParent().getFdName());
			vUserData.setEmail(person.getFdEmail());
			vUserData.setIdcard(person.getFdIdentityCard());
			vUserData.setIntroduction(person.getSelfIntroduction());
			vUserData.setName(person.getFdName());
			try {
				vUserData.setOrg(person.getHbmParent().getHbmParent().getFdName());
			} catch (Exception e) {
				vUserData.setOrg("");
			}
			vUserData.setSex(person.getFdSex());
			vUserData.setTel(person.getFdWorkPhone());
			datas.add(vUserData);
		}
		return datas;
	}
	
	
	public Pagination getVUserDatasPage(String fdType,  int pageNo, int pageSize, String param){
		Finder finder = Finder.create(" select p.fdId id from SYS_ORG_PERSON p ");
		finder.append(" where 1 = 1 ");
		if(StringUtils.isNotBlank(fdType)){
			finder.append(" and p.fd_is_emp = :isEmp ").setParam("isEmp", fdType);
		}
		if (StringUtils.isNotBlank(param)) {
			finder.append(" and (lower(p.FD_LOGIN_NAME) like '%"+param+"%' or p.fdid in (select e.fdid ");
			finder.append(" from SYS_ORG_ELEMENT e ");
			finder.append(" where e.fd_name like ");
			finder.append(" '%"+param+"%') or p.fdid in (select tem.fdid ");
			finder.append(" from SYS_ORG_ELEMENT tem ");
			finder.append(" where tem.fd_parentid in ");
			finder.append(" (select e1.fdid ");
			finder.append("  from SYS_ORG_ELEMENT e1 ");
			finder.append("  where e1.fd_name like '%"+param+"%'))) ");
		}
		return getPageBySql(finder, pageNo, pageSize);
	}
	
	public List<VUserData> findVUserDatasByPageList(List list){
		List<Map> orgPersons = (List<Map>) list;
		List<VUserData> datas = new ArrayList<VUserData>();
		for (int i = 0; i < orgPersons.size(); i++) {
			SysOrgPerson person = get((String)orgPersons.get(i).get("ID"));
			VUserData vUserData = new VUserData();
			vUserData.setBirthday(person.getFdBirthDay());
			vUserData.setBlood(person.getFdBloodType());
			vUserData.setDept(person.getHbmParent()==null?"":person.getHbmParent().getFdName());
			vUserData.setEmail(person.getFdEmail());
			vUserData.setIdcard(person.getFdIdentityCard());
			vUserData.setIntroduction(person.getSelfIntroduction());
			vUserData.setName(person.getFdName());
			try {
				vUserData.setOrg(person.getHbmParent().getHbmParent().getFdName());
			} catch (Exception e) {
				vUserData.setOrg("");
			}
			vUserData.setSex(person.getFdSex());
			vUserData.setTel(person.getFdWorkPhone());
			datas.add(vUserData);
		}
		return datas;
	}

}

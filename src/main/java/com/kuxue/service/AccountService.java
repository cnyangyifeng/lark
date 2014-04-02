package com.kuxue.service;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import org.apache.commons.lang3.ObjectUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.springframework.util.CollectionUtils;import com.kuxue.common.hibernate4.Finder;import com.kuxue.common.utils.security.PasswordEncoder;import com.kuxue.dao.UserDao;import com.kuxue.model.organization.RoleEnum;import com.kuxue.model.organization.SysOrgElement;import com.kuxue.model.organization.SysOrgPerson;import com.kuxue.model.organization.UserRole;@Service@Transactional(readOnly = true)public class AccountService extends BaseService {	private static final Logger log = LoggerFactory			.getLogger(AccountService.class);	@Autowired	private PasswordEncoder passwordEncoder;	@Autowired	private UserRoleService userRoleService;	@Autowired	private SysOrgDepartService SysOrgDepartService;	/**	 * 根据登录名称和姓名模糊查询用户	 * 	 * @param key	 * @return	 */	@SuppressWarnings({ "rawtypes", "unchecked" })	public List<SysOrgPerson> findUserByLinkLoginAndRealName(String key) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		List<Map> values = findByNamedQuery("findUserByLinkLoginAndRealName",				map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}		public SysOrgPerson findPersonByEmailAndIdCard(String fdEmail,String fdIdentityCard){		Finder finder = Finder.create(" from SysOrgPerson t ");		finder.append("where t.fdEmail =:fdEmail");		finder.append("and t.fdIdentityCard=:fdIdentityCard");		finder.setParam("fdEmail", fdEmail);		finder.setParam("fdIdentityCard", fdIdentityCard);		List<SysOrgPerson> list = find(finder);		if(CollectionUtils.isEmpty(list)){        	return null;		}		return list.get(0);	}	// findUserByLinkLoginOrRealNameAndRole	/**	 * 根据登录名或者真实姓名和角色查询数据	 * 	 * @param key	 * @return	 */	@SuppressWarnings({ "rawtypes", "unchecked" })	public List<SysOrgPerson> findUserByLinkLoginOrRealNameAndRole(String key,			RoleEnum roleEnum) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		map.put("role", roleEnum.toString());		List<Map> values = findByNamedQuery(				"findUserByLinkLoginOrRealNameAndRole", map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}	/**	 * 根据登录名称和姓名模糊查询用户	 * 	 * @param key	 * @return	 */	@SuppressWarnings({ "rawtypes", "unchecked" })	public List<SysOrgPerson> findUserByLinkLoginAndRealNameAndDetp(String key,			String detpId) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		map.put("detpId", detpId);		List<Map> values = findByNamedQuery(				"findUserByLinkLoginAndRealNameAndDept", map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}	/**	 * 根据登录名称和姓名模糊查询用户	 *	 * @param key	 * @return	 */	@SuppressWarnings({ "unchecked" })	public List<SysOrgPerson> findUserByLinkLoginAndRealNameAndOrg(String key,			String orgId) {		List<SysOrgElement> elements = SysOrgDepartService				.findAllChildrenById(orgId);		if (CollectionUtils.isEmpty(elements)) {			return new ArrayList<SysOrgPerson>();		}		List<String> deptIds = new ArrayList<String>();		for (SysOrgElement e : elements) {			deptIds.add(e.getFdId());		}		Finder finder = Finder				.create("from SysOrgPerson person left join fetch person.hbmParent parent");		finder.append("where (person.realName like :realName or person.fdInitPassword like :name)  and parent.fdId in(:fids)");		finder.setParam("realName", '%' + key + '%');		finder.setParam("name", '%' + key + '%');		finder.setParamList("fids", deptIds.toArray());		return find(finder);	}	/**	 * 根据用户id获取用户	 * 	 * @param id	 * @return	 */	@SuppressWarnings({ "rawtypes", "unchecked" })	public SysOrgPerson findById(String id) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("id", id);        SysOrgPerson person = get(SysOrgPerson.class,id);		return person;	}	private SysOrgPerson builderUserByMap(Map<String, Object> value) {		if (CollectionUtils.isEmpty(value)) {			return null;		}		SysOrgPerson person = new SysOrgPerson();		person.setFdId(value.get("FDID").toString());		person.setLoginName(ObjectUtils.toString((value.get("FD_LOGIN_NAME")==null?"":value.get("FD_LOGIN_NAME"))				.toString()));        person.setFdMobileNo(ObjectUtils.toString(value.get("FD_MOBILE_NO")));        person.setFdEmail(ObjectUtils.toString(value.get("FD_EMAIL")));        person.setFdName(ObjectUtils.toString(value.get("REALNAME")));		person.setDeptName(value.get("FD_NAME") == null ? "" : value.get(				"FD_NAME").toString());		if (value.get("FD_PHOTO_URL") != null) {			person.setFdPhotoUrl(value.get("FD_PHOTO_URL").toString());		}		if (value.get("FD_IS_EMP") != null) {			person.setFdIsEmp((value.get("FD_IS_EMP")==null?"":value.get("FD_IS_EMP")).toString());		}		return person;	}	@SuppressWarnings("unchecked")	public SysOrgPerson findUserByLoginName(String loginName) {		try {			SysOrgPerson user = userDao.findByLoginName(loginName);			if (user != null) {				// 查询用户角色信息				Finder finder = Finder						.create("SELECT r from  UserRole r where r.sysOrgPerson.fdId=:fdId");				finder.setParam("fdId", user.getFdId());				List<UserRole> userRoles = userRoleService.find(finder);				user.setUserRoles(userRoles);			}			return user;		} catch (Exception e) {			log.error(e.toString());			throw new RuntimeException(e);		}	}	@Transactional(readOnly = false)	public void registerUser(SysOrgPerson user) {		entryptPassword(user);		userDao.save(user);	}	/**	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash	 */	private void entryptPassword(SysOrgPerson user) {		user.setPassword(passwordEncoder.encodePassword(				user.getPlainPassword(), null));	}	private UserDao userDao;	@Autowired	public void setUserDao(UserDao userDao) {		this.userDao = userDao;	}	@SuppressWarnings("unchecked")	@Override	public Class<SysOrgPerson> getEntityClass() {		return SysOrgPerson.class;	}}
package cn.me.xdf.dao;

import cn.me.xdf.model.organization.SysOrgPerson;


public interface UserDao {

	SysOrgPerson findByLoginName(String loginName);

	SysOrgPerson save(SysOrgPerson entity);

}

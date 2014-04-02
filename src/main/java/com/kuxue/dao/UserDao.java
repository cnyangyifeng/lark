package com.kuxue.dao;

import com.kuxue.model.organization.SysOrgPerson;


public interface UserDao {

	SysOrgPerson findByLoginName(String loginName);

	SysOrgPerson save(SysOrgPerson entity);

}

package com.kuxue.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.hibernate4.HibernateSimpleDao;
import com.kuxue.dao.UserDao;
import com.kuxue.model.organization.SysOrgPerson;

public class UserDaoImpl extends HibernateSimpleDao implements UserDao {

	@SuppressWarnings("rawtypes")
	@Override
	public SysOrgPerson findByLoginName(String loginName) {
		Finder finder = Finder
				.create("from SysOrgPerson where fdAvailable is null and (loginName=:fdname or fdEmail=:emailName)");
		finder.setParam("fdname", loginName);
		finder.setParam("emailName", loginName);
		List lists = find(finder);
		if (!CollectionUtils.isEmpty(lists)) {
			return (SysOrgPerson) lists.get(0);
		}
		return null;
	}

	public SysOrgPerson save(SysOrgPerson entity) {
		Assert.notNull(entity);
		getSession().merge(entity);
		return entity;
	}

	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(SysOrgPerson.class);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

}

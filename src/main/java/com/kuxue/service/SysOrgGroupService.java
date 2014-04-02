package com.kuxue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.model.organization.SysOrgDepart;
import com.kuxue.model.organization.SysOrgGroup;

@Service
@Transactional(readOnly = true)
public class SysOrgGroupService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<SysOrgGroup> getEntityClass() {
		return SysOrgGroup.class;
	}
	
	
	public List<SysOrgGroup> findGroupTop10ByKey(String key){
		Finder finder = Finder.create("");
		finder.append("from SysOrgGroup g where g.fdName like '%"+key+"%' ");
		return (List<SysOrgGroup>) getPage(finder, 1,10).getList();
	}
}

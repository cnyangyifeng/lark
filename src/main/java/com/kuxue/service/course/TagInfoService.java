package com.kuxue.service.course;

import java.util.List;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuxue.common.hibernate4.Finder;
import com.kuxue.common.page.Pagination;
import com.kuxue.common.page.SimplePage;
import com.kuxue.model.course.TagInfo;
import com.kuxue.service.BaseService;

@Service
@Transactional(readOnly = true)
public class TagInfoService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<TagInfo> getEntityClass() {
		return TagInfo.class;
	}

	/**
	 * 根据key模糊查找TagInfo
	 */
	@Transactional(readOnly = true)
	public List<TagInfo> findTagInfosByKey(String key) {
		Finder finder = Finder.create("from TagInfo tagInfo ");
		finder.append("where lower(tagInfo.fdName) like :key");
		finder.setParam("key", '%' + key + '%');
		return (List<TagInfo>) getPage(finder, 1, 10).getList();
	}

	/**
	 * 根据标签名称查找TagInfo
	 * 
	 * @param tagName
	 *            标签名
	 * @return TagInfo 标签信息
	 */
	@Transactional(readOnly = true)
	public TagInfo getTagByName(String tagName) {
		Finder finder = Finder.create("from TagInfo tagInfo ");
		finder.append("where tagInfo.fdName = :key");
		finder.setParam("key", tagName);
		return findUnique(finder);
	}

	/**
	 * 模糊查询
	 */
	public Pagination findTagsByKey(String key, int pageNo) {
		Finder finder = Finder.create("from TagInfo tagInfo ");
		if (StringUtil.isNotBlank(key)) {
			finder.append("where tagInfo.fdName like :key");
			finder.setParam("key", '%' + key + '%');
		}
		Pagination pagination = getPage(finder, pageNo, SimplePage.DEF_COUNT);
		return pagination;
	}

	
}

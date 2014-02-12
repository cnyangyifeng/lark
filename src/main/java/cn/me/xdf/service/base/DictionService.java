package cn.me.xdf.service.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.me.xdf.common.hibernate4.Value;
import cn.me.xdf.common.utils.array.ArrayUtils;
import cn.me.xdf.common.utils.array.SortType;
import cn.me.xdf.model.base.Diction;
import cn.me.xdf.service.BaseService;
import cn.me.xdf.service.ShiroDbRealm.ShiroUser;



/**
 * 基本类型字典
 * 
 * @author jiaxj
 * 
 */
@Service
@Transactional(readOnly = true)
public class DictionService extends BaseService {

	/**
	 * 项目
	 */
	public List<Diction> findProject() {
		List<Diction> lists = findByProperty("fdType", 1);
		ArrayUtils.sortListByProperty(lists, "fdSeqNum", SortType.HIGHT);
		return lists;
	}

	/**
	 * 课程分项
	 * 
	 * @return
	 */
	public Diction findCoursePartials() {
		List<Diction> courses = findCourse();// 课程
		if (!CollectionUtils.isEmpty(courses)) {
			Object obj = courses.get(0);
			Diction course = (Diction) obj;
			course.setChildrens(findPartial());
			return course;
		}
		return null;
	}

	/**
	 * 课程
	 * 
	 * @return
	 */
	public List<Diction> findCourse() {
		List<Diction> lists= findByProperty("fdType", 2);
		ArrayUtils.sortListByProperty(lists, "fdSeqNum", SortType.HIGHT);
		return lists;
	}

	/**
	 * 分项
	 * 
	 * @return
	 */
	public List<Diction> findPartial() {
		List<Diction> lists=findByProperty("fdType", 3);
		ArrayUtils.sortListByProperty(lists, "fdSeqNum", SortType.HIGHT);
		return lists;
	}

	/**
	 * 阶段
	 * 
	 * @return
	 */
	public List<Diction> findStage() {
		List<Diction> lists=findByProperty("fdType", 4);
		ArrayUtils.sortListByProperty(lists, "fdSeqNum", SortType.HIGHT);
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Diction> getEntityClass() {
		return Diction.class;
	}

	/**
	 * 根据字典类型和字典名称查询数据是否重复
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public boolean hasValueByNameAndType(String type, String name) {
		List<Diction> dictions = findByCriteria(Diction.class,
				Value.eq("fdName", name),
				Value.eq("fdType", NumberUtils.createInteger(type)));
		return !(CollectionUtils.isEmpty(dictions));
	}
	
	/**
	 * 根据字典类型和字典名称查询数据是否重复
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public boolean hasValueByIdAndNameAndType(String fdId,String type, String name) {
		List<Diction> dictions = findByCriteria(Diction.class,
				Value.ne("fdId", fdId),
				Value.eq("fdName", name),
				Value.eq("fdType", NumberUtils.createInteger(type)));
		return !(CollectionUtils.isEmpty(dictions));
	}

	@Transactional(readOnly = false)
	public Diction saveDiction(Diction diction) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		diction.setFdCreatorId(uId);
		diction.setFdDateCreated(new Date());
		diction.setFdStatus(true);
		return super.save(diction);
	}
}

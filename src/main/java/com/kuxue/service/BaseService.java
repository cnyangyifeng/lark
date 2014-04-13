package com.kuxue.service;import java.io.Serializable;import java.util.List;import org.hibernate.criterion.Criterion;import org.springframework.transaction.annotation.Transactional;import com.kuxue.common.hibernate4.OrderBy;/** * 继承此类后，需要实现 . * <p> * public Class<Model> getEntityClass(){ return Model.class(); } * </p> * <p> * 此类里有一些简单的增删改查，也可以调用getBaseDao()对方法进行扩展。 * </p> *  * @author xiaobin */@Transactional(readOnly = true)public abstract class BaseService extends SimpleService {	/*private static final Logger log = LoggerFactory			.getLogger(BaseService.class);*/	/**	 * 桉属性查找数据	 * 	 * @param property	 * @param value	 * @return	 */	@SuppressWarnings("unchecked")	@Transactional(readOnly = true)	public <T> List<T> findByProperty(String property, Object value) {		return (List<T>) getBaseDao().findByProperty(getEntityClass(),				property, value);	}	/**	 * 根据属性值获取唯一的数据	 * 	 * @param property	 * @param value	 * @return	 */	@Transactional(readOnly = true)	public <T> T findUniqueByProperty(String property, Object value) {		Class<T> clazz = getEntityClass();		return findUniqueByProperty(clazz, property, value);	}	@Transactional(readOnly = true)	public <T> List<T> findByCriteria(OrderBy order, Criterion... criterion) {		Class<T> clazz = getEntityClass();		return findByCriteria(clazz, OrderBy.asOrders(order), criterion);	}	/**	 * 用法: .findByCriteria(T.class, Value.eq("fdId", id));	 * <p>	 * Value提供了非常多的查询表达式	 * </p>	 * 	 * @param order	 * @param criterion	 * @return	 */	@Transactional(readOnly = true)	public <T> List<T> findByCriteria(OrderBy[] order, Criterion... criterion) {		Class<T> clazz = getEntityClass();		return findByCriteria(clazz, order, criterion);	}	/**	 * get Object	 * 	 * @param id	 * @return	 */	@Transactional(readOnly = false)	@SuppressWarnings("unchecked")	public <T> T get(Serializable id) {		return (T) getBaseDao().get(getEntityClass(), id);	}	/**	 * load Object	 * 	 * @param id	 * @return	 */	@Transactional(readOnly = true)	public <T> T load(Serializable id) {		Class<T> clazz = getEntityClass();		return load(clazz, id);	}	@Transactional(readOnly = false)	public void delete(Serializable id) {		delete(getEntityClass(), id);	}	@Transactional(readOnly = false)	public void delete(Serializable[] ids) {		if (ids == null)			return;		for (Serializable id : ids) {			delete(getEntityClass(), id);		}	}	public abstract <T> Class<T> getEntityClass();}
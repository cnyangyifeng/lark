package cn.me.xdf.common.hibernate4.dynamic;

import java.util.List;
import java.util.Map;

import cn.me.xdf.common.page.Pagination;

public interface DynamicBaseDao {

	/**
	 * 查询在xxx.dynamic.xml中配置的查询语句
	 * <p>
	 * 可查询sql或者hql占位符的语句,也可以直接写sql或者hql
	 * </p>
	 * 
	 * @param queryName
	 * @param parameters
	 * @return
	 */
	<X> List<X> findByNamedQuery(final String queryName,
			final Map<String, Object> parameters, Class<X> clazz);

	/**
	 * 查询在xxx.dynamic.xml中配置的查询语句
	 * <p>
	 * 可查询sql或者hql占位符的语句,也可以直接写sql或者hql
	 * </p>
	 * 
	 * @param queryName
	 * @param parameters
	 * @return
	 */
	Pagination findByNamedPage(final String queryName,
			final Map<String, Object> parameters, Class<?> clazz, int pageNo);

	/**
	 * 更新在xxx.dynamic.xml中配置的更新语句
	 * <p>
	 * 可更新sql或者hql占位符的语句,也可以直接写sql或者hql
	 * </p>
	 * 
	 * @param queryName
	 *            query名称
	 * @param parameters
	 *            参数
	 * @return
	 */
	int updateByNamedQuery(final String queryName,
			final Map<String, ?> parameters);
}

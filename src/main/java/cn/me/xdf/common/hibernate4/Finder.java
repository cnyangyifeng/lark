package cn.me.xdf.common.hibernate4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 * Hibernate查询组装器
 * 
 * @author xiaobin
 * 
 */
public class Finder {

	protected Finder() {
		hqlBuilder = new StringBuilder();
	}

	protected Finder(String hql) {
		hqlBuilder = new StringBuilder(hql);
	}

	public static Finder create() {
		return new Finder();
	}

	public static Finder create(String hql) {
		return new Finder(hql);
	}

	public Finder append(String hql) {
		hqlBuilder.append(" ").append(hql);
		return this;
	}

	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */
	public String getOrigHql() {
		return hqlBuilder.toString();
	}

	/**
	 * 获得查询数据库记录数的hql语句。
	 * 
	 * @return
	 */
	public String getRowCountHql() {

		if (StringUtils.isNotBlank(rowCountSql)) {
			return rowCountSql;
		}
		String hql = hqlBuilder.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		//String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(ORDER_BY);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		//return wrapProjection(projectionHql) + rowCountHql;
		return ROW_COUNT + rowCountHql;
	}

	private String rowCountSql;

	public String getRowCountSql() {
		if (StringUtils.isNotBlank(rowCountSql)) {
			return rowCountSql;
		}
		String sql = hqlBuilder.toString();
		return ROW_COUNT + " from ("+ sql +")";
	}

	public void setRowCountSql(String rowCountSql) {
		this.rowCountSql = rowCountSql;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * 是否使用查询缓存
	 * 
	 * @return
	 */
	public boolean isCacheable() {
		return cacheable;
	}

	/**
	 * 设置是否使用查询缓存
	 * 
	 * @param cacheable
	 * @see Query#setCacheable(boolean)
	 */
	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	/**
	 * 设置参数
	 * 
	 * @param param
	 * @param value
	 * @return
	 * @see Query#setParameter(String, Object)
	 */
	public Finder setParam(String param, Object value) {
		return setParam(param, value, null);
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param param
	 * @param value
	 * @param type
	 * @return
	 * @see Query#setParameter(String, Object, Type)
	 */
	public Finder setParam(String param, Object value, Type type) {
		getParams().add(param);
		getValues().add(value);
		getTypes().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param paramMap
	 * @return
	 * @see Query#setProperties(Map)
	 */
	public Finder setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @param type
	 * @return
	 * @see Query#setParameterList(String, Collection, Type))
	 */
	public Finder setParamList(String name, Collection<Object> vals, Type type) {
		getParamsList().add(name);
		getValuesList().add(vals);
		getTypesList().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Collection)
	 */
	public Finder setParamList(String name, Collection<Object> vals) {
		return setParamList(name, vals, null);
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @param type
	 * @return
	 * @see Query#setParameterList(String, Object[], Type)
	 */
	public Finder setParamList(String name, Object[] vals, Type type) {
		getParamsArray().add(name);
		getValuesArray().add(vals);
		getTypesArray().add(type);
		return this;
	}

	/**
	 * 设置参数。与hibernate的Query接口一致。
	 * 
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Object[])
	 */
	public Finder setParamList(String name, Object[] vals) {
		return setParamList(name, vals, null);
	}

	public Finder setParamList2(String name, Object... vals) {
		return setParamList(name, vals, null);
	}

	public Finder where() {
		hqlBuilder.append(" where ");
		return this;
	}

	public Finder where(String sql) {
		hqlBuilder.append(" where ").append(sql).append(" ");
		return this;
	}

	public Finder and() {
		hqlBuilder.append(" and ");
		return this;
	}

	public Finder and(String sql) {
		hqlBuilder.append(" and ").append(sql).append(" ");
		return this;
	}

	public Finder or() {
		hqlBuilder.append(" or ");
		return this;
	}

	public Finder or(String sql) {
		hqlBuilder.append(" or ").append(sql).append(" ");
		return this;
	}

	/**
	 * 相等
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder eq(Object value) {
		if (value instanceof String) {
			value = ((String) value);
		}
		String param = setWheres(value);
		hqlBuilder.append(" =:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不相等
	 * 
	 * @param value
	 * @return
	 */
	public Finder noteq(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" !=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 大于等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder ge(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" >=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 大于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder gt(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" >:").append(param);
		return setParam(param, value);
	}

	/**
	 * 小于等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder le(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" <=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 小于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder lt(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" <:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder ne(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" !=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不为空
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder isNotNull() {
		hqlBuilder.append(" is not null");
		return this;
	}

	/**
	 * 为空
	 * 
	 * @return
	 */
	public Finder isNull() {
		hqlBuilder.append(" is null");
		return this;
	}

	/**
	 * in
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder in(List<?> value) {
		String param = setWheres(value);
		hqlBuilder.append(" in(:").append(param).append(")");
		// return setParam(param, value);
		return setParamList(param, value.toArray());
	}

	/**
	 * in
	 * 
	 * @param sql
	 * @return
	 */
	public Finder in(String sql) {
		hqlBuilder.append(" in(").append(sql).append(")");
		return this;
	}

	public Finder like(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" like :").append(param);
		return setParam(param, "%" + value + "%");
	}

	public Finder llike(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" like :").append(param);
		return setParam(param, "%" + value);
	}

	public Finder rlike(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" like :").append(param);
		return setParam(param, value + "%");
	}

	/**
	 * not in
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Finder notIn(List<?> value) {
		String param = setWheres(value);
		hqlBuilder.append(" not in(:").append(param).append(")");
		return setParam(param, value);
	}

	private String setWheres(Object where) {
		String param = "param" + getWheres().size();
		getWheres().add(where);
		return param;
	}

	private List<Object> getWheres() {
		if (wheres == null) {
			wheres = new ArrayList<Object>();
		}
		return wheres;
	}

	/**
	 * not in
	 * 
	 * @param sql
	 * @return
	 */
	public Finder notIn(String sql) {
		hqlBuilder.append(" not in(").append(sql).append(")");
		return this;
	}

	public Finder search(String key, Object value, SearchType type) {
		if (StringUtils.isBlank(key) || value == null)
			return this;
		if (value instanceof String) {
			String v = (String) value;
			if (StringUtils.isBlank(v)) {
				return this;
			}
		} else if (value instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) value;
			if (collection.size() < 1)
				return this;
		}
		if (type == SearchType.EQ) {
			return and(key).eq(value);
		} else if (type == SearchType.NEQ) {
			return and(key).noteq(value);
		} else if (type == SearchType.GE) {
			return and(key).ge(value);
		} else if (type == SearchType.GT) {
			return and(key).gt(value);
		} else if (type == SearchType.LE) {
			return and(key).le(value);
		} else if (type == SearchType.LT) {
			return and(key).lt(value);
		} else if (type == SearchType.LIKE) {
			return and(key).like(value);
		} else if (type == SearchType.LLIKE) {
			return and(key).llike(value);
		} else if (type == SearchType.RLIKE) {
			return and(key).rlike(value);
		}

		else if (type == SearchType.IN) {
			if (value instanceof List) {
				List<?> vs = (List<?>) value;
				if (vs.size() > 0) {
					return and(key).in((List<?>) value);
				} else {
					return this;
				}
			} else {
				throw new RuntimeException("value is not List type");
			}
		}
		throw new RuntimeException("不支持的搜索类型");
	}

	/**
	 * 将finder中的参数设置到query中。
	 * 
	 * @param query
	 */
	public Query setParamsToQuery(Query query) {
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				if (types.get(i) == null) {
					query.setParameter(params.get(i), values.get(i));
				} else {
					query.setParameter(params.get(i), values.get(i),
							types.get(i));
				}
			}
		}
		if (paramsList != null) {
			for (int i = 0; i < paramsList.size(); i++) {
				if (typesList.get(i) == null) {
					query.setParameterList(paramsList.get(i), valuesList.get(i));
				} else {
					query.setParameterList(paramsList.get(i),
							valuesList.get(i), typesList.get(i));
				}
			}
		}
		if (paramsArray != null) {
			for (int i = 0; i < paramsArray.size(); i++) {
				if (typesArray.get(i) == null) {
					query.setParameterList(paramsArray.get(i),
							valuesArray.get(i));
				} else {
					query.setParameterList(paramsArray.get(i),
							valuesArray.get(i), typesArray.get(i));
				}
			}
		}
		return query;
	}

	public Query createQuery(Session s) {
		Query query = setParamsToQuery(s.createQuery(getOrigHql()));
		if (getFirstResult() > 0) {
			query.setFirstResult(getFirstResult());
		}
		if (getMaxResults() > 0) {
			query.setMaxResults(getMaxResults());
		}
		if (isCacheable()) {
			query.setCacheable(true);
		}
		return query;
	}

	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}

	private List<String> getParams() {
		if (params == null) {
			params = new ArrayList<String>();
		}
		return params;
	}

	private List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	private List<Type> getTypes() {
		if (types == null) {
			types = new ArrayList<Type>();
		}
		return types;
	}

	private List<String> getParamsList() {
		if (paramsList == null) {
			paramsList = new ArrayList<String>();
		}
		return paramsList;
	}

	private List<Collection<Object>> getValuesList() {
		if (valuesList == null) {
			valuesList = new ArrayList<Collection<Object>>();
		}
		return valuesList;
	}

	private List<Type> getTypesList() {
		if (typesList == null) {
			typesList = new ArrayList<Type>();
		}
		return typesList;
	}

	private List<String> getParamsArray() {
		if (paramsArray == null) {
			paramsArray = new ArrayList<String>();
		}
		return paramsArray;
	}

	private List<Object[]> getValuesArray() {
		if (valuesArray == null) {
			valuesArray = new ArrayList<Object[]>();
		}
		return valuesArray;
	}

	private List<Type> getTypesArray() {
		if (typesArray == null) {
			typesArray = new ArrayList<Type>();
		}
		return typesArray;
	}

	private StringBuilder hqlBuilder;

	private List<String> params;
	private List<Object> values;
	private List<Type> types;

	private List<String> paramsList;
	private List<Collection<Object>> valuesList;
	private List<Type> typesList;

	private List<String> paramsArray;
	private List<Object[]> valuesArray;
	private List<Type> typesArray;

	private List<Object> wheres;

	private int firstResult = 0;

	private int maxResults = 0;

	private boolean cacheable = false;

	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String DISTINCT = "distinct";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";

	public static void main(String[] args) {
		Finder find = Finder
				.create("select distinct p FROM BookType join fetch p");
		System.out.println(find.getRowCountHql());

		System.out.println(find.getOrigHql());
	}

	public static enum SearchType {
		/**
		 * 等于
		 */
		EQ,
		/**
		 * 不等于
		 */
		NEQ,
		/**
		 * >
		 */
		GE,
		/**
		 * <
		 */
		GT,
		/**
		 * >=
		 */
		LE,
		/**
		 * <=
		 */
		LT,
		/**
		 * like '%s%'
		 */
		LIKE,
		/**
		 * like '%s'
		 */
		LLIKE,
		/**
		 * like 's%'
		 */
		RLIKE,
		/**
		 * in
		 */
		IN
	}

}

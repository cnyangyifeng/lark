package cn.me.xdf.common.hibernate4.dynamic;

import java.util.HashMap;

public class SqlValueMap<K, V> extends HashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6913524279672477494L;

	public V add(K key, V value) {

		return super.put(key, value);
	}

	public void addExcludeNullValue(K key, V value) {
		if (value != null)
			super.put(key, value);
	}

	public void addIncludeNullValue(K key, V value) {
		super.put(key, value);
	}

}

package cn.me.xdf.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class ModelUtil {
	
	
	/**
	 * 检查树结构的域模型中是否出现了循环嵌套，校验失败后抛出TreeCycleException异常
	 * 
	 * @param treeModel
	 *            树的域模型
	 * @param parent
	 *            即将要设置父节点
	 * @param parentProperty
	 *            父节点的属性名称
	 * @throws TreeCycleException
	 */
	@SuppressWarnings("unchecked")
	public static <T> void checkTreeCycle(T treeModel, T parent,
			String parentProperty) {
		List<T> parentList = new ArrayList<T>();
		parentList.add(treeModel);
		try {
			for (T curNode = parent; curNode != null; curNode = (T) PropertyUtils
					.getProperty(curNode, parentProperty)) {
				if (parentList.contains(curNode)) {
					throw new RuntimeException("errors.treeCycle");
				}
				parentList.add(curNode);
			}
		} catch (Exception e) {
			throw new RuntimeException("errors.treeCycle");
		}
	}

}

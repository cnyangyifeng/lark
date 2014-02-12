package cn.me.xdf.common.utils.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.CollectionUtils;

import cn.me.xdf.common.utils.MyBeanUtils;
import cn.me.xdf.common.utils.ObjectUtils;

/**
 * 集合常用工具类
 * 
 * @author xiaobin
 * 
 */
public class ArrayUtils {

	public static <T> void setListProperty(List<T> lists,
			final String property, Object value) {
		if (lists == null || lists.isEmpty())
			return;
		for (T t : lists) {
			MyBeanUtils.setFieldValue(t, property, value);
		}
	}

	/**
	 * 对集合重新排序
	 * 
	 * @param lists
	 *            list集合
	 * @param property
	 *            排序基准
	 * @param sortType
	 *            排序类型
	 */
	public static <T> void sortListByProperty(List<T> lists,
			final String property, final SortType sortType) {
		Collections.sort(lists, new Comparator<T>() {
			public int compare(T a, T b) {
				Object obj1 = MyBeanUtils.getGetFieldValue(a, property);
				Object obj2 = MyBeanUtils.getGetFieldValue(b, property);
				if (sortType == SortType.HIGHT) {
					return ObjectUtils.compare(obj1, obj2);
				} else {
					return ObjectUtils.compare(obj2, obj1);
				}
			}
		});
	}

	public static <T> List<T> getListByPropertyValue(List<T> lists,
			String property, Object value) {
		if (CollectionUtils.isEmpty(lists))
			return lists;
		List<T> targetList = new ArrayList<T>();
		for (T t : lists) {
			if (MyBeanUtils.getGetFieldValue(t, property).equals(value)) {
				targetList.add(t);
			}
		}
		return targetList;
	}


    /**
     * 判断list是否为null或empty
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null)
            return true;
        return list.isEmpty();
    }


}

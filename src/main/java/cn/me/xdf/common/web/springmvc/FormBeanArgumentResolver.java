package cn.me.xdf.common.web.springmvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import cn.me.xdf.common.web.springmvc.annotation.FormBean;

/**
 * <p>
 * BeanArgumentResolver
 * </p>
 * 是从WebRequest中提取FormBean指定前缀的数据到方法参数中
 */
public class FormBeanArgumentResolver implements HandlerMethodArgumentResolver {

	public static final String BINDING_RESULT_LIST_NAME = "_bindingResultList_";

	private static final String DEFAULT_SEPARATOR = ".";
	private String separator = DEFAULT_SEPARATOR;

	private WebBindingInitializer webBindingInitializer;

	/**
	 * Setter to configure the separator between prefix and actual property
	 * value. Defaults to {@link #DEFAULT_SEPARATOR}.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
	}

	@Autowired
	public void setWebBindingInitializer(
			WebBindingInitializer webBindingInitializer) {
		this.webBindingInitializer = webBindingInitializer;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(FormBean.class)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		ServletRequest servletRequest = (ServletRequest) webRequest
				.getNativeRequest();

		PropertyValues pvs = null;
		Object bindObject = null;

		String prefix = getPrefix(parameter);

		Class<?> paramType = parameter.getParameterType();

		if (Collection.class.isAssignableFrom(paramType) || paramType.isArray()) {
			Class<?> genericClass = null;
			if (paramType.isArray()) {
				genericClass = paramType.getComponentType();
			} else {
				Type type = parameter.getGenericParameterType();
				if (type instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) type;
					genericClass = (Class<?>) pt.getActualTypeArguments()[0];
				}
			}
			if (genericClass != null) {
				Map<String, Object> mappedValues = createMappedValues(
						genericClass, webRequest, parameter, prefix);
				if (!mappedValues.isEmpty()) {
					List<Object> targetObject = new ArrayList<Object>(
							mappedValues.values());
					WebDataBinder binder = new WebDataBinder(null);
					if (webBindingInitializer != null) {
						webBindingInitializer.initBinder(binder, webRequest);
					}
					bindObject = binder.convertIfNecessary(targetObject,
							paramType);
				}
			}
		} else if (Map.class.isAssignableFrom(paramType)) {
			Class<?> genericClass = null;
			Type type = parameter.getGenericParameterType();
			if (type instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type;
				genericClass = (Class<?>) pt.getActualTypeArguments()[1];
			}

			if (genericClass != null) {
				Map<String, Object> mappedValues = createMappedValues(
						genericClass, webRequest, parameter, prefix);
				if (!mappedValues.isEmpty()) {
					Map<String, Object> targetObject = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : mappedValues
							.entrySet()) {
						String key = entry.getKey();
						key = key.substring(key.indexOf("['") + 2,
								key.indexOf("']"));
						targetObject.put(key, entry.getValue());
					}
					WebDataBinder binder = new WebDataBinder(null);
					if (webBindingInitializer != null) {
						webBindingInitializer.initBinder(binder, webRequest);
					}
					bindObject = binder.convertIfNecessary(targetObject,
							paramType);
				}
			}

		} else {
			pvs = new ServletRequestParameterPropertyValues(servletRequest,
					prefix, separator);

			bindObject = convertIfDomainClass(webRequest, pvs, paramType,
					prefix);

			if (null == bindObject) {
				bindObject = BeanUtils.instantiateClass(paramType);
			}

			WebDataBinder binder = new WebDataBinder(bindObject, prefix);
			if (webBindingInitializer != null) {
				webBindingInitializer.initBinder(binder, webRequest);
			}

			// binder.initDirectFieldAccess();
			binder.bind(pvs);

			// 如果有校验注解@Valid，则校验绑定，通过Request传递校验结果
			if (isValid(parameter)) {
				binder.validate();
				BindingResult bindingResult = binder.getBindingResult();

				List<BindingResult> list = (List<BindingResult>) webRequest
						.getAttribute(BINDING_RESULT_LIST_NAME, 0);
				if (null == list) {
					list = new ArrayList<BindingResult>();
					webRequest.setAttribute(BINDING_RESULT_LIST_NAME, list, 0);
				}
				list.add(bindingResult);
			}
		}

		return bindObject;
	}

	/**
	 * Resolves the prefix to use to bind properties from. Will prepend a
	 * possible {@link FormBean} if available or return the configured prefix
	 * otherwise.
	 * 
	 * @param parameter
	 */
	private String getPrefix(MethodParameter parameter) {
		for (Annotation annotation : parameter.getParameterAnnotations()) {
			if (annotation instanceof FormBean) {
				return ((FormBean) annotation).value();
			}
		}
		return null;
	}

	/*
	 * private FormBean getFormBeanAnnotation(MethodParameter parameter) { for
	 * (Annotation annotation : parameter.getParameterAnnotations()) { if
	 * (annotation instanceof FormBean) { return ((FormBean) annotation); } }
	 * return null; }
	 */

	private boolean isValid(MethodParameter parameter) {
		for (Annotation annotation : parameter.getParameterAnnotations()) {
			if (annotation instanceof Valid) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 生成指定前缀的数据Map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> createMappedValues(Class<?> genericClass,
			NativeWebRequest webRequest, MethodParameter parameter,
			String prefix) {
		ServletRequest servletRequest = (ServletRequest) webRequest
				.getNativeRequest();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		WebDataBinder binderHelper = new WebDataBinder(null);
		if (webBindingInitializer != null) {
			webBindingInitializer.initBinder(binderHelper, webRequest);
		}
		// 将数组提取为一个一个的KEY，这里是集合必须要有prefix + '['
		Set<String> keySet = getSortedKeySet(servletRequest, prefix + '[');
		for (String key : keySet) {
			Object genericObj = null;
			if (key.endsWith(separator)) {
				ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(
						servletRequest, key, StringUtils.EMPTY);

				String realKey = key.substring(0, key.length() - 1);
				genericObj = convertIfDomainClass(webRequest, pvs,
						genericClass, realKey);
				if (null == genericObj) {
					genericObj = BeanUtils.instantiateClass(genericClass);
				}

				WebDataBinder objectBinder = new WebDataBinder(genericObj,
						realKey);
				if (webBindingInitializer != null) {
					webBindingInitializer.initBinder(objectBinder, webRequest);
				}
				objectBinder.bind(pvs);

				// 如果有校验注解@Valid，则校验绑定，通过Request传递校验结果
				if (isValid(parameter)) {
					objectBinder.validate();
					BindingResult bindingResult = objectBinder
							.getBindingResult();

					List<BindingResult> list = (List<BindingResult>) webRequest
							.getAttribute(BINDING_RESULT_LIST_NAME, 0);
					if (null == list) {
						list = new ArrayList<BindingResult>();
						webRequest.setAttribute(BINDING_RESULT_LIST_NAME, list,
								0);
					}
					list.add(bindingResult);
				}
			} else {
				Map<String, Object> paramValues = WebUtils
						.getParametersStartingWith(servletRequest, key);
				if (!paramValues.isEmpty()) {
					if (Collection.class.isAssignableFrom(genericClass)) {
						genericObj = binderHelper.convertIfNecessary(
								paramValues.values(), genericClass);
					} else {
						genericObj = binderHelper.convertIfNecessary(
								paramValues.values().iterator().next(),
								genericClass);
					}
				}
			}
			if (genericObj != null) {
				resultMap.put(key, genericObj);
			}
		}
		return resultMap;
	}

	/*
	 * 获取指定前缀的KEY值
	 */
	@SuppressWarnings("unchecked")
	private Set<String> getSortedKeySet(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(prefix, "Prefix must not be null");
		Enumeration<String> paramNames = request.getParameterNames();
		Set<String> keySet = new TreeSet<String>(ComparatorImpl.INSTANCE);
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(prefix)) {
				String key = paramName;
				int lastScopeIndex = paramName.indexOf(']');
				int firstSeparator = paramName.indexOf(separator);
				if (firstSeparator > lastScopeIndex) {
					// 这里把separator也加上，用来判断是简单数据类型还是复杂类型
					key = paramName.substring(0, firstSeparator + 1);
				}
				if (!keySet.contains(key)) {
					keySet.add(key);
				}
			}
		}
		return keySet;
	}

	private static final class ComparatorImpl implements Comparator<String> {
		public static final ComparatorImpl INSTANCE = new ComparatorImpl();

		@Override
		public int compare(String left, String right) {
			int lengthCompare = left.length() - right.length();
			return lengthCompare != 0 ? lengthCompare : left.compareTo(right);
		}
	}

	/*
	 * 如果是Domain Class，则根据是否有ID属性来自动查询实体数据，再行绑定
	 */
	private Object convertIfDomainClass(WebRequest webRequest,
			PropertyValues pvs, Class<?> paramType, String prefix) {
		// 如果参数是Domain Class，则看看是否有ID，有就根据ID读取数据
		if (Persistable.class.isAssignableFrom(paramType)) {
			PropertyValue idValue = pvs.getPropertyValue("id");
			if (null != idValue) {
				String idString = (String) idValue.getValue();
				if (StringUtils.isNotEmpty(idString)) {
					WebDataBinder binder = new WebDataBinder(null, prefix
							+ separator + "id");
					if (webBindingInitializer != null) {
						webBindingInitializer.initBinder(binder, webRequest);
					}
					return binder.convertIfNecessary(idString, paramType);
				}
			}
		}
		return null;
	}
}

package cn.me.xdf.common.utils;

import java.beans.Introspector;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

/**
 * @see AnnotationBeanNameGenerator
 * @author xiaobin
 * 
 */
public class MyNameGenerator extends AnnotationBeanNameGenerator {

	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		if (definition instanceof AnnotatedBeanDefinition) {
			String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
			if (org.springframework.util.StringUtils.hasText(beanName)) {
				// Explicit bean name found.
				return beanName;
			}
		}
		// Fallback: generate a unique default bean name.
		return buildDefaultBeanName(definition);
	}

	protected String buildDefaultBeanName(BeanDefinition definition) {
		String shortClassName = ClassUtils.getShortName(definition
				.getBeanClassName());
		String beanName = Introspector.decapitalize(shortClassName);
		return StringUtils.removeEnd(beanName, "Impl");
	}

}

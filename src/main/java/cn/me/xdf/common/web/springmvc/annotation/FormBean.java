package cn.me.xdf.common.web.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * <b>FormBean</b> 是用于Spring MVC的Controller中的方法参数的注解，标识与HTML页面中Form
 * 的对应关系，该标注会自动绑定页面Form传递进来的参数
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface FormBean {

	String value() default "";

	/** 是否校验表单重复提交 */
	boolean valid() default false;
}

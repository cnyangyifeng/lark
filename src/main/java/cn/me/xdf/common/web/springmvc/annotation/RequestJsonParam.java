package cn.me.xdf.common.web.springmvc.annotation;

import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-29
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 * 用法
 *
 *
 * URL:/demo/user.do?user={name: 'demo', email: 'demo@126.com'}
 *
 * Arg: @RequestJsonParam("user") User user
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonParam {


    String value() default "";


    boolean required() default true;


    String defaultValue() default ValueConstants.DEFAULT_NONE;

}
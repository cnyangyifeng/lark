package com.kuxue.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-23
 * Time: 上午11:59
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AttMainMachine {

    AttValues[] value();

    String modelId() default "fdId";

    String modelName();
}

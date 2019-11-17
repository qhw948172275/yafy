package com.yykj.business.conf.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by: tf
 * description: 操作日志
 * create time: 2019/11/1 11:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperateLogAnnotation {
    String value() default "";
}

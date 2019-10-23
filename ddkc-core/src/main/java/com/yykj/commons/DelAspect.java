package com.yykj.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 删除操作记录日志
 */
@Aspect
@Component
public class DelAspect {
    private static Logger log = LoggerFactory.getLogger(DelAspect.class);
    @Pointcut("execution(* com.ruanyun.goodboys.*.StudentController.delClassByStudent(..))")
    public void delLog(){}

    @Before("delLog()")
    public void dobefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getSession();
        log.debug("jinlai");

    }
}

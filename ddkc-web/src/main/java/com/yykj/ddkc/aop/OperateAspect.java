package com.yykj.ddkc.aop;

import com.alibaba.fastjson.JSON;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.entity.OperateLog;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.service.OperateLogService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
@Aspect
@Component
public class OperateAspect extends BaseApiController {
    public Logger log= LoggerFactory.getLogger(OperateAspect.class);

    @Autowired
    OperateLogService operateLogService;
    @Autowired
    ShopService shopService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.yykj.ddkc.aop.OperateLogAnnotation)")
    public void OperatePointCut() {}

    //切面 配置通知
    @AfterReturning("OperatePointCut()")
    public void saveSysLog(JoinPoint joinPoint) {

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OperateLogAnnotation operateLogAnnotation = method.getAnnotation(OperateLogAnnotation.class);
        if (operateLogAnnotation != null) {
            //保存日志
            OperateLog operateLog = new OperateLog();
            String value = operateLogAnnotation.value();
            operateLog.setContent(value);//保存获取的操作
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Shop shop=shopService.getById(getShopIdByRequest(request));
            operateLog.setOperateTime(CalendarUtils.getDate());
            operateLog.setShopId(shop.getId());
            operateLog.setAccount(shop.getPhone());
            operateLog.setRealName(shop.getContact());
            operateLogService.insert(operateLog);
        }

    }
}
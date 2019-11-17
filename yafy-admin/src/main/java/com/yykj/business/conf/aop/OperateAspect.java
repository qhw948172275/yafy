package com.yykj.business.conf.aop;

import com.alibaba.fastjson.JSON;
import com.yykj.business.BaseController;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.dao.AccountLogMapper;
import com.yykj.system.entity.AccountLog;
import com.yykj.system.service.SysRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * create by: tf
 * description: 操作日志切面
 * create time: 2019/11/1 11:19
 */
@Aspect
@Component
public class OperateAspect extends BaseController {


    @Autowired
    AccountLogMapper accountLogMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    SysRoleService sysRoleService;


    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.yykj.business.conf.aop.OperateLogAnnotation)")
    public void OperatePointCut() {}

    //切面 配置通知
    @AfterReturning("OperatePointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        AccountLog accountLog = new AccountLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OperateLogAnnotation operateLogAnnotation = method.getAnnotation(OperateLogAnnotation.class);
        if (operateLogAnnotation != null) {
            String value = operateLogAnnotation.value();
            accountLog.setDescr(value);//保存获取的操作
        }

        //请求URl
        accountLog.setPaths(request.getRequestURL().toString());

        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        String param=params.substring(0,(params.length()>500?500:params.length()));
        accountLog.setParams(param);

        accountLog.setAccountTime(CalendarUtils.getDate());
        accountLog.setAccountId(getSysUer().getId());
        accountLog.setSchoolId(getSysUer().getSchoolId());
        accountLog.setAccount(getSysUer().getName());
        accountLog.setRealName(getSysUer().getRealName());
        accountLog.setRoleName(sysRoleService.selectRoleNamesByUserId(getSysUer().getId()));


        accountLogMapper.insert(accountLog);
    }


}
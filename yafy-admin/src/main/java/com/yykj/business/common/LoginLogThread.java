package com.yykj.business.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.ip.IPUtils;
import com.yykj.system.entity.SysLoginLog;
import com.yykj.system.entity.SysUser;
import com.yykj.system.service.LoginLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author qhw
 * @Date 2019/12/4 0004 下午 14:36
 * @Version V1.0
 **/
public class LoginLogThread extends Thread {
    private Logger log= LoggerFactory.getLogger(LoginLogThread.class);
    private LoginLogService loginLogService;
    private String ip;
    private SysUser sysUser;
    public LoginLogThread(){

    }
    public LoginLogThread(LoginLogService logService,String ip,SysUser sysUser) {
        this.loginLogService=logService;
        this.ip=ip;
        this.sysUser=sysUser;
    }

    @Override
    public void run() {
        SysLoginLog loginLog = new SysLoginLog();
        JsonNode ipInfo = IPUtils.getIPInfo(ip);
        loginLog.setUserId(sysUser.getId());
        loginLog.setLoginTime(CalendarUtils.getDate());
        loginLog.setRealmName(sysUser.getRealName());
        loginLog.setSchoolId(sysUser.getSchoolId());
        loginLog.setUserName(sysUser.getName());
        loginLog.setLoginIp(ip);
        loginLog.setLoginProvince(IPUtils.getProvince(ipInfo));
        loginLog.setLoginAddress(IPUtils.getAddress(ipInfo));
        loginLogService.insert(loginLog);
        log.debug("loginLog to String {}",loginLog);
    }
}

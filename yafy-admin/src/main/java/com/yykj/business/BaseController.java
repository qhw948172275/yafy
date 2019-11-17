package com.yykj.business;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
* @Description 公共的控制器类
* @Author 王晓
* @Date 2019/10/29 9:35
* @Param null
* @Return
* @Exception
*/
@Controller("baseController")
public class BaseController {
    protected static Logger log = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;


    /**
     * 获取用户信息
     * @return
     */
    public SysUser getSysUer(){
        Subject subject = SecurityUtils.getSubject();
       return (SysUser) subject.getPrincipal();
    }

    /**
     * create by: tf
     * description: 删除定时任务
     * create time: 2019/11/5 18:04
     */
    public void jobDelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }
}

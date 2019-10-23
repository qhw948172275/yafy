package com.yykj.ddkc.platform;

import com.yykj.ddkc.service.OperateLogService;
import com.yykj.ddkc.service.ShopLoginLogService;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "platform/log")
public class LogController extends BaseController {
    @Autowired
    ShopLoginLogService shopLoginLogService;
    @Autowired
    OperateLogService operateLogService;

    /**
     * 登录日志主页面
     * @return
     */
    @RequestMapping(value = "loginLogIndex")
    public Object loginLogIndex(){
        return "ddkc/platform/log/loginLogIndex";
    }

    /**
     * 登录日志数据
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "loginLogData")
    public Object loginLogData(HttpServletRequest request,String keyword){
        Map<String,Object> map=getMapByExample(request);
        if(StringUtils.isNotEmpty(keyword)){
            map.put("keyword",keyword);
        }
        return buildPageResultByAsync(shopLoginLogService.selectAllPageFor(map));
    }

    /**
     * 操作日志主页面
     * @return
     */
    @GetMapping(value = "operateLogIndex")
    public String operateLogIndex(){
        return "ddkc/platform/log/operateLogIndex";
    }

    /**
     * 操作日志数据
     * @param request
     * @param keyword
     * @return
     */
    @ResponseBody
    @GetMapping(value = "operateLogData")
    public Object operateLogData(HttpServletRequest request,String keyword) {
        Map<String,Object> map=getMapByExample(request);
        if(StringUtils.isNotEmpty(keyword)){
            map.put("keyword",keyword);
        }
        return buildPageResultByAsync(operateLogService.selectAllPage(map));
    }
}

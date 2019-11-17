package com.yykj.business.platform.controller.system;

import com.yykj.business.BaseController;
import com.yykj.system.service.LoginLogService;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SysLoginLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: tf
 * description: 登陆日志
 * create time: 2019/11/1 10:54
 */
@Api(tags = {"登陆日志"})
@RestController
@RequestMapping(value = "loginLog")
public class LoginLogController extends BaseController {

    @Autowired
    LoginLogService loginLogService;

    /**
     * create by: tf
     * description: 获取登陆日志数据
     * create time: 2019/11/1 10:51
     */
    @ApiOperation(value ="获取登陆日志数据",response = SysLoginLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "用户名",paramType="query"),
            @ApiImplicitParam(name="realName",value = "真实姓名",paramType="query"),
            @ApiImplicitParam(name="page",value = "页码",paramType="query",dataType = "int"),
            @ApiImplicitParam(name="limit",value = "页面大小",paramType="query",dataType = "int")
    })
    @GetMapping(value ="page")
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    protected JsonResult page(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "realName", required = false) String realName,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            Map<String, Object> map = new HashMap<>();
            if (null != name && name.length() > 0) {
                map.put("name", name);
            }
            if (null != realName && realName.length() > 0) {
                map.put("realName", realName);
            }
            map.put("page", page);
            map.put("limit", limit);
            return JsonResultUtils.buildJsonOK(loginLogService.selectAllResponsePage(map));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg("网络异常");//1
        }
    }
}

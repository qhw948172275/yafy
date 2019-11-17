package com.yykj.business.platform.controller.system;

import com.yykj.business.BaseController;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.AccountLog;
import com.yykj.system.service.AccountLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: tf
 * description: 操作日志
 * create time: 2019/11/1 14:20
 */
@Api(tags = {"操作日志"})
@RestController
@RequestMapping(value = "accountLog")
public class AccountLogController extends BaseController {

    @Autowired
    AccountLogService accountLogService;

    /**
     * create by: tf
     * description: 获取操作日志数据
     * create time: 2019/11/1 14:23
     */
    @ApiOperation(value ="获取操作日志数据",response = AccountLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name="keyword",value = "帐号或真实姓名",paramType="query"),
            @ApiImplicitParam(name="page",value = "页码",paramType="query",dataType = "int"),
            @ApiImplicitParam(name="limit",value = "页面大小",paramType="query",dataType = "int")
    })
    @GetMapping(value ="page")
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    protected JsonResult page(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            Map<String, Object> map = new HashMap<>();
            if (null != keyword && keyword.length() > 0) {
                map.put("keyword", keyword);
            }
            map.put("page", page);
            map.put("limit", limit);
            return JsonResultUtils.buildJsonOK(accountLogService.selectAllResponsePage(map));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg("网络异常");//1
        }
    }

}

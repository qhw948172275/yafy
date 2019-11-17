package com.yykj.business.platform.controller.system;



import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.business.BaseController;
import com.yykj.system.dto.ResourceDto;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.service.ResourceDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "主页面接口")
@RestController
@RequestMapping(value="system/index")
public class IndexController extends BaseController {
    @Autowired
    private ResourceDtoService resourceDtoService;


    /**
     * 获取本用户所有用的菜单
     * @return
     */
    @ApiOperation(value ="获取本用户所有用的菜单")
    @GetMapping(value="")
    public JsonResult index(){
        try {
           List<ResourceDto> resourceDtos= resourceDtoService.selectByUserId(getSysUer().getId());
            if(resourceDtos.size()==0){
                return JsonResultUtils.buildJsonOK("该用户没有权限");
            }
            return JsonResultUtils.buildJsonOK(resourceDtos);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 获取当前登录信息
     * @return
     */
    @ApiOperation(value = "获取当前登录信息")
    @GetMapping(value = "getUserInfo")
    public JsonResult getUserInfo(){
        try {
            ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
            json.putPOJO("sysUser",getSysUer());
            return JsonResultUtils.buildJsonOK(json);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}

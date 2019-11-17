package com.yykj.business.platform.controller.system;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.business.BaseController;
import com.yykj.system.dto.ResourceDto;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SysResource;
import com.yykj.system.service.ResourceDtoService;
import com.yykj.system.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 资源访问控制类
 *
 * @author kimi
 * @dateTime 2012-3-20 下午8:16:45
 */
@Api(tags = "资源管理接口")
@RestController("systemResourceController")
@RequestMapping("system/resource")
public class ResourceController extends BaseController {
    public Logger log= LoggerFactory.getLogger(ResourceController.class);
   @Autowired
    private ResourceDtoService resourceDtoService;
   @Autowired
   private SysResourceService systemResourceService;
    /**
     * 获取所有资源
     * @return
     */
    @ApiOperation(value="获取所有资源",response =ResourceDto.class )
    @GetMapping("")
    public JsonResult getTree(){
        try{
            List<ResourceDto> resourceDtos=resourceDtoService.selectAll();
            return JsonResultUtils.buildJsonOK(resourceDtos);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 编辑资源
     * @param resourceId
     * @return
     */
    @ApiOperation(value="编辑资源",response =SysResource.class )
    @GetMapping(value="edit")
   public JsonResult edit( Integer resourceId){
        try{
            ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
            SysResource systemResource=systemResourceService.getById(resourceId);
            json.putPOJO("systemResource",systemResource);
            return JsonResultUtils.buildJsonOK(json);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
   }
    /**
     * 保存资源
     * @param systemResource
     * @return
     */
    @ApiOperation(value="保存资源")
    @PostMapping(value="save")
    public JsonResult save(@RequestBody SysResource systemResource){
        try{
            resourceDtoService.save(systemResource);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return JsonResultUtils.buildJsonFail(e.getMessage());
        }

    }

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    @ApiOperation(value="删除资源")
    @GetMapping(value="delete")
    public JsonResult delete(@RequestParam(value="resourceId")Integer  resourceId){
        try{

            systemResourceService.deleteByResourceId(resourceId);

            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
        return JsonResultUtils.buildJsonFail();
    }
}

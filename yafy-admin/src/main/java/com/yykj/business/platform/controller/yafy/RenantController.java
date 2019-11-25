package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.yykj.business.BaseController;
import com.yykj.business.dto.RenantDto;
import com.yykj.business.response.RenantIndexResponse;
import com.yykj.business.service.RenantService;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 15:16
 * @Version V1.0
 **/
@Api(tags = {"租客管理","先不对----doing----"})
@RestController
@RequestMapping("renant")
public class RenantController extends BaseController {
    @Autowired
    RenantService renantService;
    /**
     * description:获取普通租客列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 15:19
     */
    @ApiOperation(value = "获取普通租客列表",response = RenantIndexResponse.class)
    @ApiImplicitParam(name = "status" ,value = "0-未交，1-已交")
    @GetMapping("index")
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit,@RequestParam Integer status){
        try {
            PageHelper.startPage(page,limit);
            RenantIndexResponse renantIndexResponse;
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description: 添加普通租客信息
     * create by: qhw
     * create time: 2019/11/20 0020 下午 15:28
     */
    @PostMapping("save")
    @ApiOperation("添加普通租客信息")
    public JsonResult save(@RequestBody RenantDto renantDto){
        try {
            renantService.save(renantDto,getSysUer());
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}

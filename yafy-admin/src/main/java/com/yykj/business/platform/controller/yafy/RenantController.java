package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.RenantDto;
import com.yykj.business.entity.Renant;
import com.yykj.business.response.RenantIndexResponse;
import com.yykj.business.service.RenantService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 15:16
 * @Version V1.0
 **/
@Api(tags = {"租客管理"})
@RestController
@RequestMapping("renant")
public class RenantController extends BaseController {
    @Autowired
    RenantService renantService;
//    /**
//     * description:获取普通租客列表
//     * create by: qhw
//     * create time: 2019/11/20 0020 下午 15:19
//     */
//    @ApiOperation(value = "获取普通租客列表",response = RenantIndexResponse.class)
//    @ApiImplicitParam(name = "status" ,value = "0-未交，1-已交")
//    @GetMapping("index")
//    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
//                            @RequestParam(value = "limit", defaultValue = "20") Integer limit,@RequestParam Integer status){
//        try {
//            PageHelper.startPage(page,limit);
//            RenantIndexResponse renantIndexResponse;
//            return JsonResultUtils.buildJsonOK();
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//    }

//    /**
//     * description: 添加普通租客信息
//     * create by: qhw
//     * create time: 2019/11/20 0020 下午 15:28
//     */
//    @PostMapping("save")
//    @ApiOperation("添加普通租客信息")
//    public JsonResult save(@RequestBody RenantDto renantDto){
//        try {
//            renantService.save(renantDto,getSysUer());
//            return JsonResultUtils.buildJsonOK();
//        }catch (Exception e){
//            e.printStackTrace();
//            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
//        }
//    }
    /**
     * description:添加普通租客信息
     * create by: qhw
     * create time: 2019/11/25 0025 下午 21:48
     */
    @ApiOperation("编辑/添加普通租客信息")
    @PostMapping("save")
    public JsonResult save(@RequestBody Renant renant){
        try {
            if(renant.getId()==null){
                renant.setCreatorId(getSysUer().getId());
                renant.setCreateTime(CalendarUtils.getDate());
                renantService.insert(renant);
            }else{
                renantService.updateById(renant);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:删除租客
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:04
     */
    @ApiOperation("删除租客")
    @GetMapping("delete")
    public JsonResult delete(@RequestParam Integer renantId){
        try {
            Renant renant=new Renant();
            renant.setId(renantId);
            renant.setStatus(SystemConstants.STATUS_NO.byteValue());
            renantService.updateById(renant);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:租客列表
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:10
     */
    @GetMapping("index")
    @ApiOperation(value = "租客列表",response =Renant.class )
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            PageHelper.startPage(page,limit);
            List<Renant> renants=renantService.selectAllListByUserId(getSysUer().getId());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(renants));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * description:租客详情
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:56
     */
    @ApiOperation(value = "租客详情",response =Renant.class)
    @GetMapping("detail")
    public JsonResult detail(@RequestParam Integer renantId){
        try {
            Renant renant=renantService.getById(renantId);
            return JsonResultUtils.buildJsonOKData("renant",renant);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}

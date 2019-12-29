package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.RentManageAreaDto;
import com.yykj.business.entity.RentManage;
import com.yykj.business.response.RentManageAreaResponse;
import com.yykj.business.response.RentManageRoomResponse;
import com.yykj.business.service.RentManageService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 16:15
 * @Version V1.0
 **/
@Api(tags = "套房租金管理")
@RestController
@RequestMapping("rentManageArea")
public class RentManageAreaController extends BaseController {
    @Autowired
    RentManageService rentManageService;

    @ApiOperation(value = "小房间租金",response = RentManageRoomResponse.class)
    @GetMapping("index")
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        PageHelper.startPage(page,limit);
        List<RentManageAreaResponse> rentManageRoomResponses=rentManageService.selectRentRentManageAreaResponse(getSysUer().getId());
        return JsonResultUtils.buildJsonOK(new PageInfo<>(rentManageRoomResponses));
    }

    @ApiOperation("保存套房缴费信息")
    @PostMapping("save")
    public JsonResult save(@RequestBody RentManageAreaDto rentManage){
        if(rentManage.getId()==null){
            rentManage.setCreateTime(CalendarUtils.getDate());
            rentManage.setCreatorId(getSysUer().getId());
        }
        rentManageService.saveArea(rentManage);
        return JsonResultUtils.buildJsonOK();
    }

    @ApiOperation(value = "套房费用信息编辑",response = RentManageAreaResponse.class)
    @GetMapping("edit")
    public JsonResult edit(@RequestParam Integer areaId,@RequestParam Integer rentManageId){
        RentManageAreaResponse rentManageAreaResponse=rentManageService.selectOneRentManageAreaResponse(areaId,rentManageId);
        return JsonResultUtils.buildJsonOKData("rentManageAreaResponse",rentManageAreaResponse);
    }

    /**
     * description:确认提交租
     * create by: qhw
     * create time: 2019/12/11 0011 下午 17:07
     */
    @ApiOperation("确认提交租")
    @GetMapping("delete")
    public JsonResult delete(@RequestParam Integer rentManageId){
        RentManage rentManage=new RentManage();
        rentManage.setId(rentManageId);
        rentManage.setStatus(SystemConstants.STATUS_NO);
        rentManageService.updateById(rentManage);
        return JsonResultUtils.buildJsonOK();
    }
}

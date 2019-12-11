package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.dto.RentManageRoomDto;
import com.yykj.business.entity.RentManage;
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
 * @Date 2019/12/11 0011 下午 14:15
 * @Version V1.0
 **/
@Api(tags = "小房间租金管理")
@RestController
@RequestMapping("rentManageRoom")
public class RentManageRoomController extends BaseController {
    @Autowired
    RentManageService rentManageService;

    @ApiOperation(value = "小房间租金",response = RentManageRoomResponse.class)
    @GetMapping("index")
    public JsonResult index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        PageHelper.startPage(page,limit);
        List<RentManageRoomResponse> rentManageRoomResponses=rentManageService.selectRentManageService(getSysUer().getId());
        return JsonResultUtils.buildJsonOK(new PageInfo<>(rentManageRoomResponses));
    }

    /**
     * description:保存租金
     * create by: qhw
     * create time: 2019/12/11 0011 下午 15:27
     */
    @ApiOperation("保存租金")
    @PostMapping("save")
   public JsonResult save(@RequestBody RentManageRoomDto rentManageRoomDto){
        if(rentManageRoomDto.getId()==null){
            rentManageRoomDto.setCreateTime(CalendarUtils.getDate());
            rentManageRoomDto.setCreatorId(getSysUer().getId());
        }
        rentManageService.save(rentManageRoomDto);
        return JsonResultUtils.buildJsonOK();
   }

   @ApiOperation("获取编辑小房间租金信息")
   @GetMapping("edit")
   public JsonResult edit(@RequestParam Integer rentManageId,@RequestParam Integer roomId ){
       RentManageRoomResponse roomResponse=rentManageService.selectRentManageRoomResponse(rentManageId,roomId);
       return JsonResultUtils.buildJsonOKData("roomResponse",roomResponse);
   }

   /**
    * description:确认提交租
    * create by: qhw
    * create time: 2019/12/11 0011 下午 16:08
    */
   @ApiOperation("确认提交租")
   @GetMapping("delete")
   public JsonResult delete(@RequestParam Integer rentManageId ){
       RentManage rentManage=new RentManage();
       rentManage.setId(rentManageId);
       rentManage.setStatus(SystemConstants.STATUS_NO);
       rentManageService.updateById(rentManage);
       return JsonResultUtils.buildJsonOK();
   }
}

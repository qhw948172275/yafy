package com.yykj.business.platform.controller.yafy;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.business.BaseController;
import com.yykj.business.entity.Room;
import com.yykj.business.service.RoomService;
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
 * @Date 2019/11/20 0020 下午 16:17
 * @Version V1.0
 **/
@Api(tags = "小房间管理")
@RestController
@RequestMapping("room")
public class RoomController extends BaseController {
    @Autowired
    RoomService roomService;

    /**
     * description:房间列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 16:47
     */
    @GetMapping("roomList")
    @ApiOperation(value = "房间列表",response =Room.class )
    public JsonResult roomList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "20") Integer limit){
        try {
            PageHelper.startPage(page,limit);
            List<Room> rooms=roomService.selectRoomListByUserId(getSysUer().getId());
            return JsonResultUtils.buildJsonOK(new PageInfo<>(rooms));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:保存小房间
     * create by: qhw
     * create time: 2019/11/20 0020 下午 16:50
     */
    @PostMapping("save")
    @ApiOperation("编辑保存小房间")
    public JsonResult save(@RequestBody Room room){
        try {
            if(room.getId()==null){
                room.setCreateTime(CalendarUtils.getDate());
                room.setCreatorId(getSysUer().getId());
                room.setIsNull((byte)0);
                room.setTenantNember(0);
                roomService.insert(room);
            }else{
                roomService.updateById(room);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:删除小房间
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:28
     */
    @GetMapping("delete")
    @ApiOperation("删除小房间")
    public JsonResult delete(@RequestParam Integer roomId){
        try {
            Room room=new Room();
            room.setId(roomId);
            room.setStatus(SystemConstants.STATUS_NO.byteValue());
            roomService.updateById(room);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
    /**
     * description:小房间详情
     * create by: qhw
     * create time: 2019/11/25 0025 下午 23:00
     */
    @ApiOperation(value = "小房间详情",response =Room.class )
    @GetMapping("detail")
    public JsonResult detail(@RequestParam Integer roomId){
        try {
            Room room=roomService.getById(roomId);
            return JsonResultUtils.buildJsonOKData("room",room);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}

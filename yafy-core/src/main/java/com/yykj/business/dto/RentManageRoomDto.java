package com.yykj.business.dto;

import com.yykj.business.entity.RentManage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 15:28
 * @Version V1.0
 **/
public class RentManageRoomDto extends RentManage {
    @ApiModelProperty("小房间ID")
    private Integer roomId;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}

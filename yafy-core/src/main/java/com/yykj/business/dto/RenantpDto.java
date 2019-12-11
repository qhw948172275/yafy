package com.yykj.business.dto;

import com.yykj.business.entity.Renant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 13:59
 * @Version V1.0
 **/
public class RenantpDto extends Renant {
    @ApiModelProperty("小房间ID")
    private Integer roomId;

    @Override
    public Integer getRoomId() {
        return roomId;
    }

    @Override
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}

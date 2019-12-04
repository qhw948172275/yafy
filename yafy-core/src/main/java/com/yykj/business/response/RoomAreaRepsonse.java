package com.yykj.business.response;

import com.yykj.business.entity.Room;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/3 0003 下午 16:41
 * @Version V1.0
 **/
public class RoomAreaRepsonse extends Room {
    @ApiModelProperty("小区名称")
    private String areaName;
    @ApiModelProperty("单元名称")
    private String unitName;
    @ApiModelProperty("门牌号")
    private String roomNumber;
    @ApiModelProperty("栋")
    private String block;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}

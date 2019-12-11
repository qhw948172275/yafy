package com.yykj.business.response;

import com.yykj.business.entity.RentManage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 14:21
 * @Version V1.0
 **/
public class RentManageRoomResponse extends RentManage {
    @ApiModelProperty("小区名称")
    private String areaName;
    @ApiModelProperty("0-提醒，1-不提醒")
    private Integer repo;
    @ApiModelProperty("单元")
    private String unit;
    @ApiModelProperty("门牌号")
    private Integer roomNumber;
    @ApiModelProperty("幢")
    private String block;
    @ApiModelProperty("小房间类型")
    private String roomType;
    @ApiModelProperty("小房间ID")
    private Integer roomId;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRepo() {
        return repo;
    }

    public void setRepo(Integer repo) {
        this.repo = repo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}

package com.yykj.business.response;

import com.yykj.business.entity.RentManage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 16:22
 * @Version V1.0
 **/
public class RentManageAreaResponse extends RentManage {
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
    @ApiModelProperty("套房ID")
    private Integer areaId;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}

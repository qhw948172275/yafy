package com.yykj.business.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName BigLandlordResponse
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 20:44
 * @Version V1.0
 **/
public class BigLandlordResponse {
    @ApiModelProperty(value = "大房东名称")
    private String bigLandlordName;
    @ApiModelProperty("大房东电话")
    private String bigLandlordPhone;
    @ApiModelProperty("小区名称")
    private String areaName;
    @ApiModelProperty("幢")
    private String block;
    @ApiModelProperty("单元")
    private String unit;
    @ApiModelProperty("门牌号")
    private Integer roomNumber;
    @ApiModelProperty("缴纳金额")
    private BigDecimal rentCost;
    @ApiModelProperty("上次缴纳时间")
    private Date lastPayTime;
    @ApiModelProperty("下次缴纳时间")
    private Date nextPayTime;
    @ApiModelProperty("小区套房Id")
    private Integer areaId;
    @ApiModelProperty("租房管理ID")
    private Integer rentManageId;
    @ApiModelProperty("交租次数或者未交租次数")
    private Integer countMonth;

    public String getBigLandlordName() {
        return bigLandlordName;
    }

    public void setBigLandlordName(String bigLandlordName) {
        this.bigLandlordName = bigLandlordName;
    }

    public String getBigLandlordPhone() {
        return bigLandlordPhone;
    }

    public void setBigLandlordPhone(String bigLandlordPhone) {
        this.bigLandlordPhone = bigLandlordPhone;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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

    public BigDecimal getRentCost() {
        return rentCost;
    }

    public void setRentCost(BigDecimal rentCost) {
        this.rentCost = rentCost;
    }

    public Date getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public Date getNextPayTime() {
        return nextPayTime;
    }

    public void setNextPayTime(Date nextPayTime) {
        this.nextPayTime = nextPayTime;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getRentManageId() {
        return rentManageId;
    }

    public void setRentManageId(Integer rentManageId) {
        this.rentManageId = rentManageId;
    }

    public Integer getCountMonth() {
        return countMonth;
    }

    public void setCountMonth(Integer countMonth) {
        this.countMonth = countMonth;
    }
}

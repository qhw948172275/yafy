package com.yykj.business.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName BigLandlordDto
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 16:51
 * @Version V1.0
 **/
public class BigLandlordDto {
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
    @ApiModelProperty("押金")
    private BigDecimal deposit;
    @ApiModelProperty("合同开始时间")
    private Date agreementStratTime;
    @ApiModelProperty("合同结束时间")
    private Date agreementEndTime;
    @ApiModelProperty("电表度数")
    private Integer degree;
    @ApiModelProperty("备注")
    private String remake;
    @ApiModelProperty("获取付款方式；0-月付，1-季付，2-半年付，3-年付")
    private Byte payType;

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

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Date getAgreementStratTime() {
        return agreementStratTime;
    }

    public void setAgreementStratTime(Date agreementStratTime) {
        this.agreementStratTime = agreementStratTime;
    }

    public Date getAgreementEndTime() {
        return agreementEndTime;
    }

    public void setAgreementEndTime(Date agreementEndTime) {
        this.agreementEndTime = agreementEndTime;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }
}

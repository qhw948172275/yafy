package com.yykj.business.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 15:29
 * @Version V1.0
 **/
public class RenantDto {
    @ApiModelProperty("租客名称")
    private String name;
    @ApiModelProperty("租客电话")
    private String phone;
    @ApiModelProperty("租客身份证")
    private String idCard;
    @ApiModelProperty("套房ID")
    private Integer areaId;
    @ApiModelProperty("租金")
    private BigDecimal rentCost;
    @ApiModelProperty("房间类型：主卧，大次卧，小次卧。大隔断，小隔断，黑房间，其他")
    private String type;
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
    @ApiModelProperty("下次缴纳时间")
    private Date naextPayTime;
    @ApiModelProperty("获取付款方式；0-月付，1-季付，2-半年付，3-年付")
    private Byte payType;
    @ApiModelProperty("钥匙存放地址")
    private String keyPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public BigDecimal getRentCost() {
        return rentCost;
    }

    public void setRentCost(BigDecimal rentCost) {
        this.rentCost = rentCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getNaextPayTime() {
        return naextPayTime;
    }

    public void setNaextPayTime(Date naextPayTime) {
        this.naextPayTime = naextPayTime;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}

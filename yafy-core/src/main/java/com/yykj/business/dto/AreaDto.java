package com.yykj.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.business.entity.Area;
import com.yykj.system.commons.CalendarUtils;
import io.swagger.annotations.ApiModelProperty;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author qhw
 * @Date 2019/12/1 0001 下午 17:05
 * @Version V1.0
 **/
public class AreaDto extends Area {
    /**
     * 押金
     */
    @ApiModelProperty("押金")
    private BigDecimal deposit;
    /**
     * 合同开始时间
     */
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    @ApiModelProperty("合同开始时间")
    private Date agreementStratTime;

    /**
     * 合同结束时间
     */
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    @ApiModelProperty("合同结束时间")
    private Date agreementEndTime;

    /**
     * 电表度数
     */
    @ApiModelProperty("电表度数")
    private Integer degree;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    @ApiModelProperty(" 付款方式；0-月付，1-季付，2-半年付，3-年付")
    private Byte payType;
    @ApiModelProperty("合同ID")
    private Integer agreementId;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }
}

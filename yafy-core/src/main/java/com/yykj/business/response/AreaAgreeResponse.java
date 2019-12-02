package com.yykj.business.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.business.entity.Area;
import com.yykj.system.commons.CalendarUtils;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author qhw
 * @Date 2019/12/2 0002 下午 16:42
 * @Version V1.0
 **/
public class AreaAgreeResponse extends Area {
    @ApiModelProperty("合同ID")
    private Integer agreementId;

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
     * 是否在合同有效期内；0-在，1-不在
     */
    @ApiModelProperty("是否在合同有效期内；0-在，1-不在")
    private Byte status;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    @ApiModelProperty("付款方式；0-月付，1-季付，2-半年付，3-年付")
    private Byte payType;

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
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

    @Override
    public Byte getStatus() {
        return status;
    }

    @Override
    public void setStatus(Byte status) {
        this.status = status;
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
}

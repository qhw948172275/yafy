package com.yykj.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_agreement")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @Column(name = "agreement_strat_time")
    private Date agreementStratTime;

    /**
     * 合同结束时间
     */
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    @ApiModelProperty("合同结束时间")
    @Column(name = "agreement_end_time")
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
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    @ApiModelProperty("付款方式；0-月付，1-季付，2-半年付，3-年付")
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取押金
     *
     * @return deposit - 押金
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * 设置押金
     *
     * @param deposit 押金
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * 获取合同开始时间
     *
     * @return agreement_strat_time - 合同开始时间
     */
    public Date getAgreementStratTime() {
        return agreementStratTime;
    }

    /**
     * 设置合同开始时间
     *
     * @param agreementStratTime 合同开始时间
     */
    public void setAgreementStratTime(Date agreementStratTime) {
        this.agreementStratTime = agreementStratTime;
    }

    /**
     * 获取合同结束时间
     *
     * @return agreement_end_time - 合同结束时间
     */
    public Date getAgreementEndTime() {
        return agreementEndTime;
    }

    /**
     * 设置合同结束时间
     *
     * @param agreementEndTime 合同结束时间
     */
    public void setAgreementEndTime(Date agreementEndTime) {
        this.agreementEndTime = agreementEndTime;
    }

    /**
     * 获取电表度数
     *
     * @return degree - 电表度数
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * 设置电表度数
     *
     * @param degree 电表度数
     */
    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    /**
     * 获取是否在合同有效期内；0-在，1-不在
     *
     * @return status - 是否在合同有效期内；0-在，1-不在
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置是否在合同有效期内；0-在，1-不在
     *
     * @param status 是否在合同有效期内；0-在，1-不在
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    /**
     * 获取付款方式；0-月付，1-季付，2-半年付，3-年付
     *
     * @return pay_type - 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置付款方式；0-月付，1-季付，2-半年付，3-年付
     *
     * @param payType 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Agreement(){

    }

    public Agreement(BigDecimal deposit,Date agreementStratTime,Date agreementEndTime,Integer degree,Integer creatorId,String remake,Byte payType){
        this.degree=degree;
        this.agreementEndTime=agreementEndTime;
        this.agreementStratTime=agreementStratTime;
        this.createTime= CalendarUtils.getDate();
        this.creatorId=creatorId;
        this.status= SystemConstants.STATUS_OK.byteValue();
        this.deposit=deposit;
        this.remark=remake;
        this.status=SystemConstants.STATUS_OK.byteValue();
        this.payType=payType;
    }
}
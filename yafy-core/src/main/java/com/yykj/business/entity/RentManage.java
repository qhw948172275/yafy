package com.yykj.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "rent_manage")
public class RentManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 上次缴纳租金时间
     */
    @ApiModelProperty("上次缴纳租金时间")
    @Column(name = "last_pay_time")
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    private Date lastPayTime;

    /**
     * 下次缴纳租金时间
     */
    @ApiModelProperty("下次缴纳租金时间")
    @Column(name = "next_pay_time")
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    private Date nextPayTime;

    /**
     * 租金费用
     */
    @ApiModelProperty("租金费用")
    @Column(name = "rent_cost")
    private BigDecimal rentCost;

    /**
     * 创建人呢ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否交租：0-未交，1-已交
     */
    @ApiModelProperty("是否交租：0-未交，1-已交")
    private Integer status;

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
     * 获取上次缴纳租金时间
     *
     * @return last_pay_time - 上次缴纳租金时间
     */
    public Date getLastPayTime() {
        return lastPayTime;
    }

    /**
     * 设置上次缴纳租金时间
     *
     * @param lastPayTime 上次缴纳租金时间
     */
    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    /**
     * 获取下次缴纳租金时间
     *
     * @return next_pay_time - 下次缴纳租金时间
     */
    public Date getNextPayTime() {
        return nextPayTime;
    }

    /**
     * 设置下次缴纳租金时间
     *
     * @param nextPayTime 下次缴纳租金时间
     */
    public void setNextPayTime(Date nextPayTime) {
        this.nextPayTime = nextPayTime;
    }

    /**
     * 获取租金费用
     *
     * @return rent_cost - 租金费用
     */
    public BigDecimal getRentCost() {
        return rentCost;
    }

    /**
     * 设置租金费用
     *
     * @param rentCost 租金费用
     */
    public void setRentCost(BigDecimal rentCost) {
        this.rentCost = rentCost;
    }

    /**
     * 获取创建人呢ID
     *
     * @return creator_id - 创建人呢ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人呢ID
     *
     * @param creatorId 创建人呢ID
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
     * 获取是否交租：0-未交，1-已交
     *
     * @return status - 是否交租：0-未交，1-已交
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否交租：0-未交，1-已交
     *
     * @param status 是否交租：0-未交，1-已交
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public RentManage(){

    }
    public RentManage(BigDecimal rentCost,Date lastPayTime,Date nextPayTime,Integer creatorId){
        this.createTime= CalendarUtils.getDate();
        this.creatorId=creatorId;
        this.lastPayTime=lastPayTime;
        this.nextPayTime=nextPayTime;
        this.rentCost=rentCost;
        this.status= SystemConstants.STATUS_OK;
    }
}
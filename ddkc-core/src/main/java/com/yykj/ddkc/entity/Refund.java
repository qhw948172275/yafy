package com.yykj.ddkc.entity;

import javax.persistence.*;

import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;

@Table(name = "t_refund")
public class Refund {
	
	public Refund() {}
	
    public Refund( String numbers, Integer orderId, Byte type, Double amount, 
			 Integer sysUserId,String remark) {
		this.numbers = numbers;
		this.orderId = orderId;
		this.type = type;
		this.amount = amount;
		this.status = SystemConstants.STATUS_OK.byteValue();
		this.createTime = CalendarUtils.getTimeStamp();
		this.sysUserId = sysUserId;
		this.remark = remark;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 退款序列号
     */
    private String numbers;

    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 类型 0:未发货退款 1:已发货退款
     */
    private Byte type;

    /**
     * 退款金额
     */
    private Double amount;

    /**
     * 退款时间
     */
    @Column(name = "refund_time")
    private Long refundTime;

    /**
     * 状态 0:待退款 1:退款成功 2:退款失败
     */
    private Byte status;

    /**
     * 失败原因
     */
    @Column(name = "fail_reason")
    private String failReason;

    @Column(name = "create_time")
    private Long createTime;

    /**
     * 操作员id
     */
    @Column(name = "sys_user_id")
    private Integer sysUserId;

    /**
     * 失败code
     */
    @Column(name = "fail_code")
    private String failCode;

    /**
     * 描述
     */
    private String remark;

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
     * 获取退款序列号
     *
     * @return numbers - 退款序列号
     */
    public String getNumbers() {
        return numbers;
    }

    /**
     * 设置退款序列号
     *
     * @param numbers 退款序列号
     */
    public void setNumbers(String numbers) {
        this.numbers = numbers == null ? null : numbers.trim();
    }

    /**
     * @return order_id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取类型 0:未发货退款 1:已发货退款
     *
     * @return type - 类型 0:未发货退款 1:已发货退款
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型 0:未发货退款 1:已发货退款
     *
     * @param type 类型 0:未发货退款 1:已发货退款
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取退款金额
     *
     * @return amount - 退款金额
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置退款金额
     *
     * @param amount 退款金额
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取退款时间
     *
     * @return refund_time - 退款时间
     */
    public Long getRefundTime() {
        return refundTime;
    }

    /**
     * 设置退款时间
     *
     * @param refundTime 退款时间
     */
    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    /**
     * 获取状态 0:待退款 1:退款成功 2:退款失败
     *
     * @return status - 状态 0:待退款 1:退款成功 2:退款失败
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态 0:待退款 1:退款成功 2:退款失败
     *
     * @param status 状态 0:待退款 1:退款成功 2:退款失败
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取失败原因
     *
     * @return fail_reason - 失败原因
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * 设置失败原因
     *
     * @param failReason 失败原因
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    /**
     * @return create_time
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取操作员id
     *
     * @return sys_user_id - 操作员id
     */
    public Integer getSysUserId() {
        return sysUserId;
    }

    /**
     * 设置操作员id
     *
     * @param sysUserId 操作员id
     */
    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * 获取失败code
     *
     * @return fail_code - 失败code
     */
    public String getFailCode() {
        return failCode;
    }

    /**
     * 设置失败code
     *
     * @param failCode 失败code
     */
    public void setFailCode(String failCode) {
        this.failCode = failCode == null ? null : failCode.trim();
    }

    /**
     * 获取描述
     *
     * @return remark - 描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置描述
     *
     * @param remark 描述
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
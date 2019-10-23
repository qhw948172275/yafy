package com.yykj.ddkc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop_account_notice_log")
public class ShopAccountNoticeLog {
    /**
     * 语音提醒记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 语音提醒订单ID
     */
    @Column(name = "notice_order_id")
    private String noticeOrderId;

    /**
     * 平台订单ID
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 提醒电话
     */
    private String phone;

    /**
     * 返回码
     */
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 返回信息
     */
    @Column(name = "result_msg")
    private String resultMsg;

    /**
     * 提醒时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取语音提醒记录ID
     *
     * @return id - 语音提醒记录ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置语音提醒记录ID
     *
     * @param id 语音提醒记录ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取语音提醒订单ID
     *
     * @return notice_order_id - 语音提醒订单ID
     */
    public String getNoticeOrderId() {
        return noticeOrderId;
    }

    /**
     * 设置语音提醒订单ID
     *
     * @param noticeOrderId 语音提醒订单ID
     */
    public void setNoticeOrderId(String noticeOrderId) {
        this.noticeOrderId = noticeOrderId == null ? null : noticeOrderId.trim();
    }

    /**
     * 获取平台订单ID
     *
     * @return order_id - 平台订单ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置平台订单ID
     *
     * @param orderId 平台订单ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取提醒电话
     *
     * @return phone - 提醒电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置提醒电话
     *
     * @param phone 提醒电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取返回码
     *
     * @return result_code - 返回码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置返回码
     *
     * @param resultCode 返回码
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    /**
     * 获取返回信息
     *
     * @return result_msg - 返回信息
     */
    public String getResultMsg() {
        return resultMsg;
    }

    /**
     * 设置返回信息
     *
     * @param resultMsg 返回信息
     */
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    /**
     * 获取提醒时间
     *
     * @return create_time - 提醒时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置提醒时间
     *
     * @param createTime 提醒时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
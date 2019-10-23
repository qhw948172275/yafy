package com.yykj.ddkc.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_withdraw")
public class WithDraw {
    /**
     * 商户提现表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商户ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 交易号
     */
    @Column(name = "trans_number")
    private String transNumber;

    /**
     * 提现金额
     */
    @Column(name = "with_draw_price")
    private BigDecimal withDrawPrice;

    /**
     * 手续费
     */
    @Column(name = "charge_price")
    private BigDecimal chargePrice;

    /**
     * 提现状态。0表示提现中；1表示提现成功；2表示提现失败。
     */
    private Integer status;

    /**
     * 失败原因
     */
    @Column(name = "fail_msg")
    private String failMsg;

    /**
     * 提现到账用户openID
     */
    private String openid;

    /**
     * 微信转账手续费
     */
    @Column(name = "wechat_charge_price")
    private BigDecimal wechatChargePrice;

    /**
     * 微信交易流水号
     */
    @Column(name = "wechat_trans_number")
    private String wechatTransNumber;

    /**
     * 提现时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 提现成功时间
     */
    @Column(name = "success_time")
    private Date successTime;

    /**
     * 获取商户提现表
     *
     * @return id - 商户提现表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商户提现表
     *
     * @param id 商户提现表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商户ID
     *
     * @return shop_id - 商户ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置商户ID
     *
     * @param shopId 商户ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取交易号
     *
     * @return trans_number - 交易号
     */
    public String getTransNumber() {
        return transNumber;
    }

    /**
     * 设置交易号
     *
     * @param transNumber 交易号
     */
    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber == null ? null : transNumber.trim();
    }

    /**
     * 获取提现金额
     *
     * @return with_draw_price - 提现金额
     */
    public BigDecimal getWithDrawPrice() {
        return withDrawPrice;
    }

    /**
     * 设置提现金额
     *
     * @param withDrawPrice 提现金额
     */
    public void setWithDrawPrice(BigDecimal withDrawPrice) {
        this.withDrawPrice = withDrawPrice;
    }

    /**
     * 获取手续费
     *
     * @return charge_price - 手续费
     */
    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    /**
     * 设置手续费
     *
     * @param chargePrice 手续费
     */
    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    /**
     * 获取提现状态。0表示提现中；1表示提现成功；2表示提现失败。
     *
     * @return status - 提现状态。0表示提现中；1表示提现成功；2表示提现失败。
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置提现状态。0表示提现中；1表示提现成功；2表示提现失败。
     *
     * @param status 提现状态。0表示提现中；1表示提现成功；2表示提现失败。
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取失败原因
     *
     * @return fail_msg - 失败原因
     */
    public String getFailMsg() {
        return failMsg;
    }

    /**
     * 设置失败原因
     *
     * @param failMsg 失败原因
     */
    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg == null ? null : failMsg.trim();
    }

    /**
     * 获取提现到账用户openID
     *
     * @return openid - 提现到账用户openID
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置提现到账用户openID
     *
     * @param openid 提现到账用户openID
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * 获取微信转账手续费
     *
     * @return wechat_charge_price - 微信转账手续费
     */
    public BigDecimal getWechatChargePrice() {
        return wechatChargePrice;
    }

    /**
     * 设置微信转账手续费
     *
     * @param wechatChargePrice 微信转账手续费
     */
    public void setWechatChargePrice(BigDecimal wechatChargePrice) {
        this.wechatChargePrice = wechatChargePrice;
    }

    /**
     * 获取微信交易流水号
     *
     * @return wechat_trans_number - 微信交易流水号
     */
    public String getWechatTransNumber() {
        return wechatTransNumber;
    }

    /**
     * 设置微信交易流水号
     *
     * @param wechatTransNumber 微信交易流水号
     */
    public void setWechatTransNumber(String wechatTransNumber) {
        this.wechatTransNumber = wechatTransNumber == null ? null : wechatTransNumber.trim();
    }

    /**
     * 获取提现时间
     *
     * @return create_time - 提现时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置提现时间
     *
     * @param createTime 提现时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取提现成功时间
     *
     * @return success_time - 提现成功时间
     */
    public Date getSuccessTime() {
        return successTime;
    }

    /**
     * 设置提现成功时间
     *
     * @param successTime 提现成功时间
     */
    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }
}
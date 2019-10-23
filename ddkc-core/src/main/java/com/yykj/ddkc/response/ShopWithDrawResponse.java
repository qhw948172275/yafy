package com.yykj.ddkc.response;

import java.math.BigDecimal;

public class ShopWithDrawResponse {
    /**
     *日期
     */
    private String dateStr;
    /**
     *时间
     */
    private String timeStr;
    /**
     *商家名称
     */
    private String shopName;
    /**
     *商家ID
     */
    private Integer shopId;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private Integer status;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

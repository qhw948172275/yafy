package com.yykj.ddkc.response;

import java.math.BigDecimal;

public class WithDrawResponse {
    /**
     * 日期
     */
    private String dateStr;
    /**
     * 申请提现商家
     */
    private Integer shopCount;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * 成功提现金额
     */
    private BigDecimal successAmount;
    /**
     * 成功提现商家数量
     */
    private Integer successShopCount;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public Integer getSuccessShopCount() {
        return successShopCount;
    }

    public void setSuccessShopCount(Integer successShopCount) {
        this.successShopCount = successShopCount;
    }
}

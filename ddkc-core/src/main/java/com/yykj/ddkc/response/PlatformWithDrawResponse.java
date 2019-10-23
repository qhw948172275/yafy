package com.yykj.ddkc.response;

import java.math.BigDecimal;

/**
 * 首页表格实体类
 */
public class PlatformWithDrawResponse {
    /**
     * 日期
     */
    private String dateStr;
    /**
     * 商家数量
     */
    private Integer shopCount;
    /**
     * 体现金额
     */
    private BigDecimal  amount;


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

}

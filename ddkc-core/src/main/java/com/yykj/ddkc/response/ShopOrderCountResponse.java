package com.yykj.ddkc.response;

import java.math.BigDecimal;

/**
 * 商家订单流水统计实体类
 */
public class ShopOrderCountResponse {
    /**
     * 日期
     */
    private String dateStr;
    /**
     * 商家名称
     */
    private String shopName;
    /**
     * 商家ID号
     */
    private Integer shopId;
    /**
     * 总订单量
     */
    private Integer orderCount;
    /**
     * 完成订单量
     */
    private Integer successCount;
    /**
     *总营业额
     */
    private BigDecimal totalPrice;
    /**
     *平台抽成
     */
    private BigDecimal platformCost;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPlatformCost() {
        return platformCost;
    }

    public void setPlatformCost(BigDecimal platformCost) {
        this.platformCost = platformCost;
    }
}

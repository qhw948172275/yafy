package com.yykj.ddkc.response;

public class ShopHistogramResponse {
    /**
     * 几号
     */
    private Integer dayDate;
    /**
     * 商家数量
     */
    private Integer shopCount;

    public Integer getDayDate() {
        return dayDate;
    }

    public void setDayDate(Integer dayDate) {
        this.dayDate = dayDate;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }
}

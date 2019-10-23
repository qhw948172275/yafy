package com.yykj.ddkc.response;

import java.math.BigDecimal;

public class OrderCountResponse {
    /**
     * 日期
     */
    private String dateStr;
    /**
     * 订单总数
     */
    private Integer orderCount;
    /**
     *成功单量
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

    public OrderCountResponse(){
        super();
    }


    public OrderCountResponse(Integer type){
        if(type==0){
            this.orderCount=0;
            this.platformCost=new BigDecimal(0);
            this.totalPrice=new BigDecimal(0);
        }
    }
}

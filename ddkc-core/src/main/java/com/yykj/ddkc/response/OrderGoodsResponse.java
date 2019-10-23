package com.yykj.ddkc.response;

import java.math.BigDecimal;

public class OrderGoodsResponse {

    private String goodsName;
    private String placeOrderTime;
    private String successPayTime;
    private BigDecimal price;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(String placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public String getSuccessPayTime() {
        return successPayTime;
    }

    public void setSuccessPayTime(String successPayTime) {
        this.successPayTime = successPayTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

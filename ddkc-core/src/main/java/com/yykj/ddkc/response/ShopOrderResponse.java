package com.yykj.ddkc.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class ShopOrderResponse {
    /**
     * 订单Id
     */
    @ApiModelProperty(value = "订单Id")
    private Integer orderId;
    /**
     *商家名
     */
    @ApiModelProperty(value = "商家名")
    private String shopName;
    /**
     *商品名多个以，连接
     */
    @ApiModelProperty(value = "商品名多个以，连接")
    private String goodsName;
    /**
     *总价格
     */
    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice=new BigDecimal(0);
    /**
     *优惠价格
     */
    @ApiModelProperty(value = "优惠价格")
    private BigDecimal discountPrice=new BigDecimal(0);
    /**
     *商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer goodsCount=0;
    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款")
    private Integer status;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}

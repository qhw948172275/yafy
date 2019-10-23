package com.yykj.ddkc.response;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class OrderLogResponse {
    /**
     * 订单日期
     */
    @ApiModelProperty(value = "订单日期")
    private String orderLogDate;
    /**
     * 订单总量
     */
    @ApiModelProperty(value = "订单总量")
    private Integer orderCount;
    /**
     * 完成订单量
     */
    @ApiModelProperty(value = "完成订单量")
    private Integer successCount;
    /**
     * 总收入
     */
    @ApiModelProperty(value = "总收入")
    private BigDecimal totalCost=new BigDecimal(0);
    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    private List<OrderGoodsResponse> orderGoodsResponses;

    public String getOrderLogDate() {
        return orderLogDate;
    }

    public void setOrderLogDate(String orderLogDate) {
        this.orderLogDate = orderLogDate;
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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<OrderGoodsResponse> getOrderGoodsResponses() {
        return orderGoodsResponses;
    }

    public void setOrderGoodsResponses(List<OrderGoodsResponse> orderGoodsResponses) {
        this.orderGoodsResponses = orderGoodsResponses;
    }
}

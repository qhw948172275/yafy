package com.yykj.ddkc.vo;

import io.swagger.annotations.ApiModelProperty;

public class ConfirmVo {
    @ApiModelProperty(value = "购物车ID集合，以“,”方式拼接成字符串")
    private String cartIds;
    @ApiModelProperty(value = "店铺ID")
    private Integer shopId;
    @ApiModelProperty(value = "收货地址ID")
    private Integer addressId;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package com.yykj.ddkc.response;

import com.yykj.ddkc.entity.Order;
import io.swagger.annotations.ApiModelProperty;

/**
 * 后台顶管管理主页实体类
 */
public class OrderIndexResponse extends Order {
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}

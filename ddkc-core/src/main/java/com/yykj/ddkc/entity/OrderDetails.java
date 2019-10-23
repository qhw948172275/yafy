package com.yykj.ddkc.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import javax.persistence.*;

import com.yykj.ddkc.response.CartResponse;
import com.yykj.system.commons.NumberUtils;

@Table(name = "t_order_details")
public class OrderDetails {
    /**
     * 订单明细ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "订单明细ID")
    private Integer id;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价")
    private BigDecimal price;

    /**
     * 销售价
     */
    @ApiModelProperty(value = "销售价")
    @Column(name = "sales_price")
    private BigDecimal salesPrice;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Long counts;

    /**
     * 商品小计总价
     */
    @ApiModelProperty(value = "商品小计总价")
    @Column(name = "total_sales_price")
    private BigDecimal totalSalesPrice;

    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID")
    @Column(name = "shop_id")
    private Integer shopId;
    /**
     * 只引用不操作数据库
     */
    @Transient
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    /**
     * 只引用不操作数据库
     */
    @Transient
    @ApiModelProperty(value = "商品头像")
    private String goodsHeadAddress;


    public OrderDetails () {}

    public OrderDetails(CartResponse cr, Order order) {
		this.orderId = order.getId();
		this.goodsId = cr.getGoodsId();
		this.price = cr.getPrice();
		this.salesPrice = cr.getSalesPrice();
		this.counts = cr.getCounts();
		this.totalSalesPrice = new BigDecimal(cr.getSubTotal());
		this.shopId = cr.getGoodsId();
	}

	/**
     * 获取订单明细ID
     *
     * @return id - 订单明细ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置订单明细ID
     *
     * @param id 订单明细ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品ID
     *
     * @return goods_id - 商品ID
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品ID
     *
     * @param goodsId 商品ID
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品原价
     *
     * @return price - 商品原价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品原价
     *
     * @param price 商品原价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取销售价
     *
     * @return sales_price - 销售价
     */
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    /**
     * 设置销售价
     *
     * @param salesPrice 销售价
     */
    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * 获取商品数量
     *
     * @return counts - 商品数量
     */
    public Long getCounts() {
        return counts;
    }

    /**
     * 设置商品数量
     *
     * @param counts 商品数量
     */
    public void setCounts(Long counts) {
        this.counts = counts;
    }

    /**
     * 获取商品小计总价
     *
     * @return total_sales_price - 商品小计总价
     */
    public BigDecimal getTotalSalesPrice() {
        return totalSalesPrice;
    }

    /**
     * 设置商品小计总价
     *
     * @param totalSalesPrice 商品小计总价
     */
    public void setTotalSalesPrice(BigDecimal totalSalesPrice) {
        this.totalSalesPrice = totalSalesPrice;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
//    /**
//     * 优惠价格
//    * @author chenbiao
//    * @date 2019年8月16日 下午12:28:45
//    * 参数说明
//    *
//    * @return
//     */
//    public Double getDiscount() {
//		//计算原总价
//		Double oldTotal = NumberUtils.doubleMultiply(getCounts().doubleValue(), getPrice().doubleValue(), 2);
//		return NumberUtils.doubleSubtract(oldTotal, getSalesPrice().doubleValue(), 2);
//	}

    public String getGoodsName() {


        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsHeadAddress() {
        return goodsHeadAddress;
    }

    public void setGoodsHeadAddress(String goodsHeadAddress) {
        this.goodsHeadAddress = goodsHeadAddress;
    }
}
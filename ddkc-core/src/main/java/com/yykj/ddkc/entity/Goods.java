package com.yykj.ddkc.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_goods")
public class Goods {
    /**
     * 商品ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID")
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 商品分类ID
     */
    @ApiModelProperty(value = "商品分类ID")
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 商品封面
     */
    @ApiModelProperty(value = "商品封面")
    private String cover;

    /**
     * 商品详情图片信息
     */
    @ApiModelProperty(value = "商品详情图片信息")
    private String details;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价")
    private BigDecimal price;

    /**
     * 销售价格
     */
    @ApiModelProperty(value = "销售价格")
    @Column(name = "sales_price")
    private BigDecimal salesPrice;

    /**
     * 数量单位。例如"盒"、"包"、"公斤"、"米"等。
     */
    @ApiModelProperty(value = "数量单位。例如\"盒\"、\"包\"、\"公斤\"、\"米\"等。")
    private String quanity;

    /**
     * 商品库存
     */
    @ApiModelProperty(value = "商品库存")
    private Long stock;

    /**
     * 销售量
     */
    @ApiModelProperty(value = "销售量")
    @Column(name = "sales_counts")
    private Long salesCounts;

    /**
     * 总销售额
     */
    @ApiModelProperty(value = "总销售额")
    @Column(name = "total_sales_price")
    private BigDecimal totalSalesPrice;

    /**
     * 状态。0表示上架；1表示下架
     */
    @ApiModelProperty(value = "状态。0表示上架；1表示下架,2表示删除")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "upate_time")
    private Date upateTime;

    /**
     *购物车的商品数量
     */
    @Transient
    private Long cartGoodsCount=0L;

    /**
     * 获取商品ID
     *
     * @return id - 商品ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品ID
     *
     * @param id 商品ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品名称
     *
     * @return goods_name - 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
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

    /**
     * 获取商品分类ID
     *
     * @return category_id - 商品分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置商品分类ID
     *
     * @param categoryId 商品分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取商品封面
     *
     * @return cover - 商品封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置商品封面
     *
     * @param cover 商品封面
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 获取商品详情图片信息
     *
     * @return details - 商品详情图片信息
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置商品详情图片信息
     *
     * @param details 商品详情图片信息
     */
    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
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
     * 获取销售价格
     *
     * @return sales_price - 销售价格
     */
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    /**
     * 设置销售价格
     *
     * @param salesPrice 销售价格
     */
    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * 获取数量单位。例如"盒"、"包"、"公斤"、"米"等。
     *
     * @return quanity - 数量单位。例如"盒"、"包"、"公斤"、"米"等。
     */
    public String getQuanity() {
        return quanity;
    }

    /**
     * 设置数量单位。例如"盒"、"包"、"公斤"、"米"等。
     *
     * @param quanity 数量单位。例如"盒"、"包"、"公斤"、"米"等。
     */
    public void setQuanity(String quanity) {
        this.quanity = quanity == null ? null : quanity.trim();
    }

    /**
     * 获取商品库存
     *
     * @return stock - 商品库存
     */
    public Long getStock() {
        return stock;
    }

    /**
     * 设置商品库存
     *
     * @param stock 商品库存
     */
    public void setStock(Long stock) {
        this.stock = stock;
    }

    /**
     * 获取销售量
     *
     * @return sales_counts - 销售量
     */
    public Long getSalesCounts() {
        return salesCounts;
    }

    /**
     * 设置销售量
     *
     * @param salesCounts 销售量
     */
    public void setSalesCounts(Long salesCounts) {
        this.salesCounts = salesCounts;
    }

    /**
     * 获取总销售额
     *
     * @return total_sales_price - 总销售额
     */
    public BigDecimal getTotalSalesPrice() {
        return totalSalesPrice;
    }

    /**
     * 设置总销售额
     *
     * @param totalSalesPrice 总销售额
     */
    public void setTotalSalesPrice(BigDecimal totalSalesPrice) {
        this.totalSalesPrice = totalSalesPrice;
    }

    /**
     * 获取状态。0表示上架；1表示下架
     *
     * @return status - 状态。0表示上架；1表示下架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示上架；1表示下架
     *
     * @param status 状态。0表示上架；1表示下架
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return upate_time - 更新时间
     */
    public Date getUpateTime() {
        return upateTime;
    }

    /**
     * 设置更新时间
     *
     * @param upateTime 更新时间
     */
    public void setUpateTime(Date upateTime) {
        this.upateTime = upateTime;
    }

    public Long getCartGoodsCount() {
        return cartGoodsCount;
    }

    public void setCartGoodsCount(Long cartGoodsCount) {
        this.cartGoodsCount = cartGoodsCount;
    }
}
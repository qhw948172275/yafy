package com.yykj.ddkc.response;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsResponse {
    private Integer id;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 商品分类ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 商品封面
     */
    private String cover;

    /**
     * 商品详情图片信息
     */
    private String details;

    /**
     * 商品原价
     */
    private BigDecimal price;

    /**
     * 销售价格
     */
    @Column(name = "sales_price")
    private BigDecimal salesPrice;

    /**
     * 数量单位。例如"盒"、"包"、"公斤"、"米"等。
     */
    private String quanity;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 销售量
     */
    @Column(name = "sales_counts")
    private Long salesCounts;

    /**
     * 总销售额
     */
    @Column(name = "total_sales_price")
    private BigDecimal totalSalesPrice;

    /**
     * 状态。0表示上架；1表示下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "upate_time")
    private Date upateTime;
    /**
     * 店铺名称
     */
    private String shopName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getQuanity() {
        return quanity;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getSalesCounts() {
        return salesCounts;
    }

    public void setSalesCounts(Long salesCounts) {
        this.salesCounts = salesCounts;
    }

    public BigDecimal getTotalSalesPrice() {
        return totalSalesPrice;
    }

    public void setTotalSalesPrice(BigDecimal totalSalesPrice) {
        this.totalSalesPrice = totalSalesPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpateTime() {
        return upateTime;
    }

    public void setUpateTime(Date upateTime) {
        this.upateTime = upateTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}

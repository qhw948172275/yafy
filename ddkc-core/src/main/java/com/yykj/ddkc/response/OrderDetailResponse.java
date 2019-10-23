package com.yykj.ddkc.response;

import com.yykj.ddkc.entity.OrderDetails;
import com.yykj.system.commons.CalendarUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDetailResponse {
    @ApiModelProperty(value = "订单Id")
    private Integer orderId;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Column(name = "order_number")
    private String orderNumber;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 订单总价
     */
    @ApiModelProperty(value = "订单总价")
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    /**
     * 优惠价格
     */
    @ApiModelProperty(value = "优惠价格")
    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID")
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款
     */
    @ApiModelProperty(value = "订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款")
    private Integer status;

    /**
     * 下单时间
     */
    @ApiModelProperty(value = "下单时间")
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 下单日期
     */
    @ApiModelProperty(value = "下单日期")
    private String createDate;
    /**
     * 下单时间引用
     */
    @ApiModelProperty(value = "下单时间引用")
    private String createTimeStr;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String remark;

    /**
     * 订单名称
     */
    @ApiModelProperty(value = "订单名称")
    @Column(name = "order_name")
    private String orderName;

    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    @Column(name = "contact_address")
    private String contactAddress;
    /**
     * 商家接单时间
     */
    @ApiModelProperty(value = "商家接单时间")
    @Column(name = "receipt_time")
    private Date receiptTime;
    /**
     * 收货时间
     */
    @ApiModelProperty(value = "收货时间")
    @Column(name = "take_over_time")
    private Date takeOverTime;
    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    /**
     * 订单商品列表
     */
    @ApiModelProperty(value = "订单商品列表")
    private List<OrderDetails> orderDetailsList;
    /**
     * 配送费
     */
    @ApiModelProperty(value = "配送费")
    private BigDecimal freight;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;
    /**
     * 送货地址
     */
    @ApiModelProperty(value = "送货地址")
    private String address;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Date getTakeOverTime() {
        return takeOverTime;
    }

    public void setTakeOverTime(Date takeOverTime) {
        this.takeOverTime = takeOverTime;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCreateDate() {
        if(this.createTime!=null){
            return CalendarUtils.dateToString(this.createTime,CalendarUtils.yyyy_MM_dd);
        }
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTimeStr() {
        if(this.createTime!=null){
            return CalendarUtils.dateToString(this.createTime,CalendarUtils.HH_mm);
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

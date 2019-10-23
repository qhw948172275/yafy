package com.yykj.ddkc.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.NumberUtils;
import com.yykj.system.commons.SystemConstants;

@Table(name = "t_order")
public class Order {

	public Order() {
	}

	public Order(String orderNumber, String orderName, Integer memberId, Double totalPrice, Double discountPrice,
			Integer shopId, String remark, Address address, Double freight, Double platformCost,
			Double totalGoodsPrice) {

		this.orderNumber = orderNumber;
		this.memberId = memberId;
		this.totalPrice = new BigDecimal(totalPrice);
		this.discountPrice = new BigDecimal(discountPrice);
		this.shopId = shopId;
		this.status = SystemConstants.STATUS_OK;
		this.createTime = CalendarUtils.getDate();
		this.remark = remark;
		this.orderName = orderName;
		this.contact = address.getContact();
		this.contactPhone = address.getPhone();
		this.contactAddress = address.getAddress()+address.getDoorNumber();
		this.remark = remark;
		this.freight = new BigDecimal(freight);
		this.platformCost = new BigDecimal(platformCost);
		this.totalGoodsPrice = new BigDecimal(totalGoodsPrice);
		this.shopBalance = new BigDecimal(NumberUtils.doubleSubtract(totalPrice, platformCost, 2));
	}

	/**
	 * 订单ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "订单ID")
	private Integer id;

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
	 * 订单取消时间
	 */
	@ApiModelProperty(value = "取消时间")
	@Column(name = "cannel_time")
	private Date cannelTime;
	/**
	 * 退款时间
	 */
	@ApiModelProperty(value = "退款时间")
	private Date refundTime;

	/**
	 * 配送费
	 */
	@ApiModelProperty(value = "配送费")
	@Column(name = "freight")
	private BigDecimal freight;
	/**
	 * 平台费用
	 */
	@ApiModelProperty(value = "平台费用")
	@Column(name = "platform_cost")
	private BigDecimal platformCost;
	/**
	 * 商品总价
	 */
	@ApiModelProperty(value = "商品总价")
	@Column(name = "total_goods_price")
	private BigDecimal totalGoodsPrice;
	/**
	 * 商家余额
	 */
	@ApiModelProperty(value = "商家余额")
	@Column(name = "shop_balance")
	private BigDecimal shopBalance;

	/**
	 * 预支付ID
	 */
	@Column(name = "prepay_id")
	@ApiModelProperty(value = "预支付ID")
	private String prepayId;

	/**
	 * 获取订单ID
	 *
	 * @return id - 订单ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置订单ID
	 *
	 * @param id 订单ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取订单号
	 *
	 * @return order_id - 订单号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * 获取会员ID
	 *
	 * @return member_id - 会员ID
	 */
	public Integer getMemberId() {
		return memberId;
	}

	/**
	 * 设置会员ID
	 *
	 * @param memberId 会员ID
	 */
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	/**
	 * 获取订单总价
	 *
	 * @return total_price - 订单总价
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置订单总价
	 *
	 * @param totalPrice 订单总价
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
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
	 * 获取订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款
	 *
	 * @return status - 订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款
	 */
	public Integer getStatus() {
		return status;
	}

	public String getStatusText() {
		if (null != status) {
			switch (status) {
			case 0:
				return "待支付";
			case 1:
				return "待确认";
			case 2:
				return "待收货";
			case 3:
				return "已完成";
			case 4:
				return "已取消";
			case 5:
				return "退款中";
			case 6:
				return "已退款";

			default:
				break;
			}
		}
		return null;
	}

	/**
	 * 设置订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款
	 *
	 * @param status 订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取下单时间
	 *
	 * @return create_time - 下单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	public String getCreateTimeText() {
		if(null != createTime) {			
			return CalendarUtils.dateToString(createTime, CalendarUtils.yyyy_MM_dd__HH_mm_ss);
		}
		return null;
	}

	/**
	 * 设置下单时间
	 *
	 * @param createTime 下单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取支付时间
	 *
	 * @return pay_time - 支付时间
	 */
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * 设置支付时间
	 *
	 * @param payTime 支付时间
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * 获取订单备注
	 *
	 * @return remark - 订单备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置订单备注
	 *
	 * @param remark 订单备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 获取订单名称
	 *
	 * @return order_name - 订单名称
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * 设置订单名称
	 *
	 * @param orderName 订单名称
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName == null ? null : orderName.trim();
	}

	/**
	 * 获取收货人
	 *
	 * @return contact - 收货人
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * 设置收货人
	 *
	 * @param contact 收货人
	 */
	public void setContact(String contact) {
		this.contact = contact == null ? null : contact.trim();
	}

	/**
	 * 获取联系电话
	 *
	 * @return contact_phone - 联系电话
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置联系电话
	 *
	 * @param contactPhone 联系电话
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone == null ? null : contactPhone.trim();
	}

	/**
	 * 获取收货地址
	 *
	 * @return contact_address - 收货地址
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	/**
	 * 设置收货地址
	 *
	 * @param contactAddress 收货地址
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress == null ? null : contactAddress.trim();
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
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

	public Date getCannelTime() {
		return cannelTime;
	}

	public void setCannelTime(Date cannelTime) {
		this.cannelTime = cannelTime;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getPlatformCost() {
		return platformCost;
	}

	public void setPlatformCost(BigDecimal platformCost) {
		this.platformCost = platformCost;
	}

	public BigDecimal getTotalGoodsPrice() {
		return totalGoodsPrice;
	}

	public void setTotalGoodsPrice(BigDecimal totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}

	public BigDecimal getShopBalance() {
		return shopBalance;
	}

	public void setShopBalance(BigDecimal shopBalance) {
		this.shopBalance = shopBalance;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

}
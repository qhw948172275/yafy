package com.yykj.ddkc.response;

import java.math.BigDecimal;

import com.yykj.ddkc.entity.Cart;
import com.yykj.system.commons.NumberUtils;
/**
 * 购物车业务实体
* @author chenbiao
* @date 2019年8月15日 下午5:30:33 
*
 */
public class CartResponse  extends Cart{
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品封面
	 */
	private String cover;
	/**
	 * 商品原价
	 */
	private BigDecimal price;
	/**
	 * 销售价
	 */
	private BigDecimal salesPrice;
	/**
	 * 商铺库存
	 */
	private Long stock;

	/**
	 * 数量单位。例如"盒"、"包"、"公斤"、"米"等。
	 */
	private String quanity;

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
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
	public Long getStock() {
		return stock;
	}
	public void setStock(Long stock) {
		this.stock = stock;
	}
	/**
	 * 小计
	 * 
	* @author chenbiao
	* @date 2019年8月16日 上午11:57:02
	* 参数说明 
	* 
	* @return
	 */
	public Double getSubTotal() {
		if(getCounts()==null){
			return null;
		}
		if(getSalesPrice()==null){
			return null;
		}
		return NumberUtils.doubleMultiply(getCounts().doubleValue(), getSalesPrice().doubleValue(), 2);
	}
	/**
	 * 优惠金额
	* @author chenbiao
	* @date 2019年8月16日 上午11:59:27
	* 参数说明 
	* 
	* @return
	 */
	public Double getDiscount() {
		if(getCounts()==null){
			return null;
		}
		if(getPrice()==null){
			return null;
		}
		//计算原总价
		Double oldTotal = NumberUtils.doubleMultiply(getCounts().doubleValue(), getPrice().doubleValue(), 2);
		return NumberUtils.doubleSubtract(oldTotal, getSubTotal(), 2);
	}

	public String getQuanity() {
		return quanity;
	}

	public void setQuanity(String quanity) {
		this.quanity = quanity;
	}
}

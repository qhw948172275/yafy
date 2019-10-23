package com.yykj.ddkc.response;

import java.util.List;

import com.yykj.ddkc.entity.Shop;
/**
 * 购物车店铺信息
* @author chenbiao
* @date 2019年8月15日 下午5:38:36 
*
 */
public class CartShopResponse extends Shop {
	/**
	 * 购物车商品信息
	 */
	private List<CartResponse> goodsResponse;

	public List<CartResponse> getGoodsResponse() {
		return goodsResponse;
	}

	public void setGoodsResponse(List<CartResponse> goodsResponse) {
		this.goodsResponse = goodsResponse;
	}

}

package com.yykj.ddkc.response;

import java.util.List;

import com.yykj.ddkc.entity.ShopCategory;

/**
 * 店铺分类业务类
 * 
* @author chenbiao
* @date 2019年8月14日 下午4:22:55 
*
 */
public class ShopCategoryResponse extends ShopCategory{
	
	private List<GoodsResponse> goods;

	public List<GoodsResponse> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsResponse> goods) {
		this.goods = goods;
	}

}

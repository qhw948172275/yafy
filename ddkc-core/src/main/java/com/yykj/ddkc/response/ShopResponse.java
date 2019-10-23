package com.yykj.ddkc.response;

import java.math.BigDecimal;

import com.yykj.ddkc.entity.Shop;

public class ShopResponse extends Shop{
	
	/**
	 * 距离当前坐标距离
	 */
	private BigDecimal distance;

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

}

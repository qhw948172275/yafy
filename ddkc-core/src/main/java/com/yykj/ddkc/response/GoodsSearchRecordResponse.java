package com.yykj.ddkc.response;

import com.yykj.ddkc.entity.GoodsSearchRecord;

/**
 * 商品搜素记录业务类
* @author chenbiao
* @date 2019年8月15日 下午5:05:24 
*
 */
public class GoodsSearchRecordResponse extends GoodsSearchRecord{
	
	/**
	 * 搜索次数
	 */
	private Integer counts;

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	} 

}

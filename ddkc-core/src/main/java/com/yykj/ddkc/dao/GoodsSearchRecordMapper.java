package com.yykj.ddkc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yykj.ddkc.entity.GoodsSearchRecord;
import com.yykj.ddkc.response.GoodsSearchRecordResponse;
import com.yykj.system.commons.service.MyMapper;

public interface GoodsSearchRecordMapper extends MyMapper<GoodsSearchRecord> {
	
	/**
	 * 获取前10个指定商铺热门搜索关键词信息
	* @author chenbiao
	* @date 2019年8月15日 下午5:08:23
	* 参数说明 
	* 
	* @param shopId
	* @return
	 */
	@Select("SELECT gsr.id, gsr.`status`, gsr.shop_id, gsr.create_time, gsr.content, COUNT( gsr.content ) AS counts  FROM t_goods_search_record gsr  WHERE gsr.`status` = 0  AND gsr.shop_id = #{shopId}  GROUP BY 	gsr.content  ORDER BY counts DESC , gsr.create_time desc LIMIT 10")
	List<GoodsSearchRecordResponse> selectTop10ByShopId(@Param("shopId")Integer shopId);
}
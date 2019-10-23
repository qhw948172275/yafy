package com.yykj.ddkc.dao;

import java.math.BigDecimal;
import java.util.List;

import com.yykj.ddkc.response.ShopHistogramResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.response.ShopResponse;
import com.yykj.system.commons.service.MyMapper;

public interface ShopMapper extends MyMapper<Shop> {
	
	/**
	 * 根据经纬度获取附近500米以内的商家信息
	* @author chenbiao
	* @date 2019年8月14日 上午10:20:18
	* 参数说明 
	* 
	* @param lat
	* @param lng
	 * @param random 
	* @return
	 */
	List<ShopResponse> selectByLatAndLng(@Param("lat")BigDecimal lat,@Param("lng")BigDecimal lng,@Param("distance") String distance);

	/**
	 * 根据关键字搜索商家信息
	* @author chenbiao
	* @date 2019年8月14日 上午10:45:55
	* 参数说明 
	* 
	* @param object
	* @return
	 */
	List<ShopResponse> selectByKeyword(@Param("lat")BigDecimal lat,@Param("lng")BigDecimal lng,@Param("keyword") String keyword,@Param("id") Integer id);

	/**
	 * 更新商家余额
	 * @param dateStr
	 */
    void updateAllBalance(@Param("dateStr")String dateStr);
	/**
	 * 统计本月新建的商家
	 * @param newDate
	 * @return
	 */
    List<ShopHistogramResponse> selectShopHistogramResponse(@Param("newDate") String newDate,@Param("createId")Integer createId);
    
    /**
     * 查询没有关联商户账号的商家
    * @author chenbiao
    * @date 2019年9月23日 下午7:31:02
    * 参数说明 
    * 
    * @return
     */
    @Select(value = "SELECT ts.* from t_shop ts where status != 2 and id not in (select DISTINCT tsa.shop_id from t_shop_account tsa)")
    List<Shop> selectByIdNotInShopAccountByIsMaster();
    
}
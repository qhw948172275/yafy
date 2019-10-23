package com.yykj.ddkc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yykj.ddkc.entity.Cart;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.CartShopResponse;
import com.yykj.system.commons.service.MyMapper;

public interface CartMapper extends MyMapper<Cart> {
	
	/**
	 * 根据会员ID获取购物车信息
	* @author chenbiao
	* @date 2019年8月15日 下午5:46:25
	* 参数说明 
	* 
	* @param memberId
	* @return
	 */
	public List<CartShopResponse> selectCartShopResponseByMemberId(@Param("memberId")Integer memberId);
	/**
	 * 根据店铺ID+会员ID获取单个店铺的购物车商品信息
	* @author chenbiao
	* @date 2019年8月15日 下午5:46:39
	* 参数说明 
	* 
	* @param shopId
	* @return
	 */
	public List<CartResponse> selectCartResponseByShopIdAndMemberId(@Param("shopId")Integer shopId,@Param("memberId")Integer memberId);
	/**
	 * 根据购物车ID获取购物车信息
	* @author chenbiao
	* @date 2019年8月16日 上午11:40:14
	* 参数说明 
	* 
	* @param ids
	* @param memberId
	* @return
	 */
	public List<CartResponse> selectCartResponseByIdIn(@Param("ids")List<Integer> ids, @Param("memberId")Integer memberId);

	/**
	 * 批量修改购物车状态
	 * @param cartIds
	 */
	void updateStatusByList(@Param("cartIds") List<Integer> cartIds,@Param("status") Integer status);
}
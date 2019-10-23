package com.yykj.ddkc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yykj.ddkc.dao.CartMapper;
import com.yykj.ddkc.entity.Cart;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.CartShopResponse;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;
/**
 * 购物车业务实现
* @author chenbiao
* @date 2019年8月15日 下午5:27:39 
*
 */
@Service
public class CartService extends AbstractBaseCrudService<Cart, Integer>{
	
	@Autowired
	CartMapper cartMapper;
	/**
	 * 根据会员ID获取购物车列表
	* @author chenbiao
	* @date 2019年8月15日 下午5:45:19
	* 参数说明 
	* 
	* @param memberId
	* @return
	 */
	public List<CartShopResponse> selectCartByMemberId(Integer memberId){
		return cartMapper.selectCartShopResponseByMemberId(memberId);
	}
	/**
	 * 根据店铺ID+会员ID获取会员购物车中指定店铺的商品列表信息
	* @author chenbiao
	* @date 2019年8月15日 下午5:48:33
	* 参数说明 
	* 
	* @param shopId
	* @param memberId
	* @return
	 */
	public List<CartResponse> selectCartResponseByShopIdAndMemberId(Integer shopId, Integer memberId){
		return cartMapper.selectCartResponseByShopIdAndMemberId(shopId, memberId);
	}
	
	public List<CartResponse> selectCartResponseByIdIn(List<Integer> ids, Integer memberId){
		return cartMapper.selectCartResponseByIdIn(ids, memberId);
	}
	/**
	 * 根据会员ID+商品ID获取购物车信息
	* @author chenbiao
	* @date 2019年8月15日 下午5:59:46
	* 参数说明 
	* 
	* @param memberId
	* @param goodsId
	* @return
	 */
	public Cart selectByMemberIdAndGoodsId(Integer memberId,Integer goodsId) {
		Example example = new Example(tClass);
		example.createCriteria()
			.andEqualTo("status", SystemConstants.STATUS_OK)
			.andEqualTo("goodsId", goodsId)
			.andEqualTo("memberId", memberId);
		List<Cart> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 批量删除购物车数据信息
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午6:52:59
	* 参数说明 
	* 
	* @param goodsIds
	* @param memberId
	* @return
	 */
	public boolean batchRemoveByGoodsIds(Integer[] goodsIds,Integer memberId) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("memberId", memberId)
			.andIn("goodsId", Arrays.asList(goodsIds));
		Cart cart = new Cart();
		cart.setStatus(SystemConstants.STATUS_NO);
		return mapper.updateByExampleSelective(cart, example) > 0;
	}

	/**
	 * 批量修改购物车状态
	 * @param cartIds
	 * @param status
	 */
	public void updateStatusByList(List<Integer> cartIds,Integer status){
		cartMapper.updateStatusByList(cartIds,status);
	}

	/**
	 * 批量添加购物车
	 * @param carts
	 */
	public void insertList(List<Cart> carts) {
		cartMapper.insertList(carts);
	}
}

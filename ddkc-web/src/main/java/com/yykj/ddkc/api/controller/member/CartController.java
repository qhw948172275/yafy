package com.yykj.ddkc.api.controller.member;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.ErrorCodeUtils;
import com.yykj.ddkc.entity.Delivery;
import com.yykj.ddkc.service.DeliveryService;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.MemberLoginRequired;
import com.yykj.ddkc.entity.Cart;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.CartShopResponse;
import com.yykj.ddkc.service.CartService;
import com.yykj.ddkc.service.GoodsService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 购物车相关接口 
* @author chenbiao
* @date 2019年8月15日 下午5:40:00 
*
 */
@Api(value = "购物车相关接口",tags = "购物车相关接口")
@RestController
@RequestMapping(value = "/api/member/cart")
public class CartController extends BaseApiController{
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	GoodsService goodsService;
	@Autowired
	DeliveryService deliveryService;
	
	
	/**
	 * 获取会员购物车列表
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午5:50:32
	* 参数说明 
	* 
	* @param request
	* @return
	 */
	@ApiOperation(value = "购物车列表")
	@MemberLoginRequired
	@GetMapping(value = "index")
	public JsonResult index(HttpServletRequest request) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			List<CartShopResponse> shopInfo = cartService.selectCartByMemberId(memberId);
			return JsonResultUtils.buildJsonOK(shopInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	/**
	 * 根据店铺ID获取购物车列表信息
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午6:18:24
	* 参数说明 
	* 
	* @param request
	* @param shopId
	* @return
	 */
	@ApiOperation(value = "根据店铺ID获取购物车列表")
	@GetMapping(value = "shop/{shopId}")
	public JsonResult listByShopId(HttpServletRequest request,@PathVariable("shopId")Integer shopId) {
		try {
			ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
			String token = getToken(request,"memberToken");
			List<CartResponse> cartResponses=new ArrayList<>();
			if(StringUtils.isNotEmpty(token)){
				Integer memberId = getMemberIdByRequest(request);
				if(memberId!=null){
					cartResponses.addAll(cartService.selectCartResponseByShopIdAndMemberId(shopId, memberId));
				}else{
					return ErrorCodeUtils.ERROR_LOGIN_TIMEOUT.getResult();//没有从redis获取到会员ID，说登录失效
				}
			}
			//获取商家配送费
			Delivery shopDelivery = deliveryService.getByType(0);
			json.putPOJO("cartResponses",cartResponses);
			json.putPOJO("shopDelivery",shopDelivery);
			return JsonResultUtils.buildJsonOK(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	/**
	 * 添加购物车
	* @author chenbiao
	* @date 2019年8月15日 下午5:51:07
	* 参数说明 
	* 
	* @param request
	* @return
	 */
	@ApiOperation(value = "添加商品进入购物车")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "goodsId",value = "商品ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int")
	})
	@MemberLoginRequired
	@PostMapping(value = "add")
	public JsonResult add(HttpServletRequest request,@RequestParam("goodsId")Integer goodsId,@RequestParam("shopId")Integer shopId) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			Shop shop = shopService.getById(shopId);
			if(null == shop || shop.getStatus().equals(SystemConstants.STATUS_NO)) {
				return JsonResultUtils.buildJsonFailMsg("店铺不存在或已禁用");
			}
			Goods goods = goodsService.getById(goodsId);
			if(null == goods || goods.getStatus().equals(SystemConstants.STATUS_NO)) {
				return JsonResultUtils.buildJsonFailMsg("商品不存在或已下架");
			}else if(shopId != goods.getShopId().intValue()) {
				return JsonResultUtils.buildJsonFailMsg("当前店铺不存在对应商品");
			}
			if(goods.getStock()==null||goods.getStock()==0){
				return JsonResultUtils.buildJsonFailMsg("商品库存不足");
			}
			Cart cart = cartService.selectByMemberIdAndGoodsId(memberId, goodsId);
			if(null != cart) {
				//增加购物车数量之后，判定会导致库存不足
				if((cart.getCounts()+1) > goods.getStock()) {					
					return JsonResultUtils.buildJsonFailMsg("商品库存不足");
				}
				cart.setCounts(cart.getCounts() + 1 );
				if(cartService.updateById(cart) > 0) { //添加购物车数量成功
					return JsonResultUtils.buildJsonOK();
				}
			}else {
				//当前购物车当中没有对应商品，需要往购物车当中加入商品信息
				cart = new Cart(memberId, shopId, goodsId, 1l);
				if(cartService.insert(cart) > 0) { //加入购物车成功
					return JsonResultUtils.buildJsonOK();
				}
			}
			return JsonResultUtils.buildJsonFailMsg("添加购物车失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	/**
	 * 从购物车当中减除商品
	* @author chenbiao
	* @date 2019年8月15日 下午6:13:15
	* 参数说明 
	* 
	* @param request
	* @param goodsId
	* @param shopId
	* @return
	 */
	@ApiOperation(value = "从购物车中减除商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "goodsId",value = "商品ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int")
	})
	@MemberLoginRequired
	@PostMapping(value = "remove")
	public JsonResult remove(HttpServletRequest request,@RequestParam("goodsId")Integer goodsId,@RequestParam("shopId")Integer shopId) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			Shop shop = shopService.getById(shopId);
			if(null == shop || shop.getStatus().equals(SystemConstants.STATUS_NO)) {
				return JsonResultUtils.buildJsonFailMsg("店铺不存在或已禁用");
			}
			Goods goods = goodsService.getById(goodsId);
			if(null == goods || goods.getStatus().equals(SystemConstants.STATUS_NO)) {
				return JsonResultUtils.buildJsonFailMsg("商品不存在或已下架");
			}else if(shopId != goods.getShopId().intValue()) {
				return JsonResultUtils.buildJsonFailMsg("当前店铺不存在对应商品");
			}
			Cart cart = cartService.selectByMemberIdAndGoodsId(memberId, goodsId);
			if(null != cart) {
				if(cart.getCounts() == 1l) {
					//购物车商品对应的数量只有1个了。减除之后，自动将这条购物车信息删除掉
					cart.setCounts(0l);
					cart.setStatus(SystemConstants.STATUS_NO);
					if(cartService.updateById(cart) > 0) { //减除成功
						return JsonResultUtils.buildJsonOK();
					}
				}else {
					cart.setCounts(cart.getCounts() - 1);
					if(cartService.updateById(cart) > 0) {
						return JsonResultUtils.buildJsonOK();
					}
				}
				return JsonResultUtils.buildJsonFailMsg("购物车商品减除失败");
			}
			return JsonResultUtils.buildJsonFailMsg("当前购物车信息不存在");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	
	/**
	 * 从购物车当中批量移除商品信息
	* @author chenbiao
	* @date 2019年8月15日 下午6:54:25
	* 参数说明 
	* 
	* @param request
	* @param goodsIds
	* @return
	 */
	@ApiOperation(value = "从购物车中批量移除商品")
	@MemberLoginRequired
	@GetMapping(value = "batchRemove")
	public JsonResult batchRemove(HttpServletRequest request,@RequestParam String goodsIds) {
		try {
			String[] goodsIdStr=goodsIds.split(",");
			Integer[] goodsId=new Integer[goodsIdStr.length];
			for( int i=0;i<goodsIdStr.length;i++){
				goodsId[i]=Integer.valueOf(goodsIdStr[i]);
			}
			Integer memberId = getMemberIdByRequest(request);
			if(cartService.batchRemoveByGoodsIds(goodsId, memberId)) {
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFailMsg("批量移除失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}

}

package com.yykj.ddkc.api.controller.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.ServiceConstants;
import com.yykj.commons.wechat.AppConfig;
import com.yykj.commons.wechat.WXPayCore;
import com.yykj.commons.wechat.WechatUtils;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.MemberLoginRequired;
import com.yykj.ddkc.config.WxMaConfiguration;
import com.yykj.ddkc.entity.Address;
import com.yykj.ddkc.entity.Cart;
import com.yykj.ddkc.entity.Delivery;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.Member;
import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.entity.OrderDetails;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.OrderDetailResponse;
import com.yykj.ddkc.response.ShopOrderResponse;
import com.yykj.ddkc.service.AddressService;
import com.yykj.ddkc.service.CartService;
import com.yykj.ddkc.service.DeliveryService;
import com.yykj.ddkc.service.GoodsService;
import com.yykj.ddkc.service.MemberService;
import com.yykj.ddkc.service.OrderService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.ddkc.vo.ConfirmVo;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.ConvertUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.NumberUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 会员订单相关
 * 
* @author chenbiao
* @date 2019年8月15日 下午6:57:02 
*
 */
@Api(value = "会员订单相关接口",tags = "会员订单相关接口")
@RestController
@RequestMapping(value = "/api/member/order")
public class OrderController extends BaseApiController{
	
	private static final String SUCCESS = "SUCCESS";
	private static final String FAIL = "FAIL";
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	AppConfig appConfig;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	ShopService shopService;
	@Autowired
	MemberService memberService;
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	WxMaConfiguration wxMaConfiguration;
	
	
	/**
	 * 去结算接口
	* @author chenbiao
	* @date 2019年8月15日 下午7:03:38
	* 参数说明 
	* 
	* @param cartIds
	* @return
	 */
	@ApiOperation(value = "去结算接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cartIds",value = "购物车ID集合，以“,”方式拼接成字符串",paramType = "query",dataType = "String"),
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int")
	})
	@MemberLoginRequired
	@GetMapping(value = "confirm")
	public JsonResult confirm(@RequestParam String cartIds,@RequestParam Integer shopId,HttpServletRequest request) {
		try {
			List<Integer> ids = ConvertUtils.convertString(cartIds);
			Integer memberId = getMemberIdByRequest(request);
			List<CartResponse> carts = cartService.selectCartResponseByIdIn(ids, memberId);
			if(null == carts || carts.size() == 0) {
				return JsonResultUtils.buildJsonFailMsg("购物车信息不存在");
			}else if(!validateShop(carts,shopId)) {//校验指定购物车信息是否是同一个商铺，如果不是同一个商铺不能结算
				return JsonResultUtils.buildJsonFailMsg("购物车信息当中存在多个商铺");
			}
			Double goodsTotalPrice = 0d;
			Double totalDiscountPrice = 0d;
			//检查商品库存是否足够
			for(CartResponse c : carts) {
				if(c.getCounts() > c.getStock()) {
					//购物车数量大于商品库存数量,则无法结单
					return JsonResultUtils.buildJsonFailMsg("商品“"+c.getGoodsName()+"”剩余库存不足");
				}
				goodsTotalPrice = NumberUtils.doubleAdd(goodsTotalPrice, c.getSubTotal(), 2);
				totalDiscountPrice = NumberUtils.doubleAdd(totalDiscountPrice, c.getDiscount(), 2);
			}
			ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
			json.putPOJO("cartInfo", carts); //购物车信息
			json.put("deliveryPrice",deliveryService.getByType(0).getVal());//平台配送费
			json.put("goodsTotalPrice", goodsTotalPrice);//结算总价
			json.put("totalPrice", NumberUtils.doubleAdd(goodsTotalPrice,Double.parseDouble(deliveryService.getByType(0).getVal())));//结算总价
			json.put("totalDiscountPrice", totalDiscountPrice);//结算总优惠价
			return JsonResultUtils.buildJsonOK(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	/**
	 * 确认并支付接口
	* @author chenbiao
	* @date 2019年8月15日 下午7:10:17
	* 参数说明
	* @param request
	* @return
	 */
	@ApiOperation(value = "确认并支付接口")
	@MemberLoginRequired
	@PostMapping(value = "confirm")
	public JsonResult confirm(@RequestBody ConfirmVo confirmVo, HttpServletRequest request) {
		try {
			List<Integer> ids = ConvertUtils.convertString(confirmVo.getCartIds());
			Integer memberId = getMemberIdByRequest(request);
			List<CartResponse> carts = cartService.selectCartResponseByIdIn(ids, memberId);
			if(null == carts || carts.size() == 0) {
				return JsonResultUtils.buildJsonFailMsg("购物车信息不存在");
			}else if(!validateShop(carts,confirmVo.getShopId())) {//校验指定购物车信息是否是同一个商铺，如果不是同一个商铺不能结算
				return JsonResultUtils.buildJsonFailMsg("购物车信息当中存在多个商铺");
			}
			Double goodsTotalPrice = 0d;
			Double totalDiscountPrice = 0d;
			StringBuffer orderName = new StringBuffer("");
			//检查商品库存是否足够
			for(CartResponse c : carts) {
				if(c.getCounts() > c.getStock()) {
					//购物车数量大于商品库存数量,则无法结单
					return JsonResultUtils.buildJsonFailMsg("商品“"+c.getGoodsName()+"”剩余库存不足");
				}
				orderName.append(c.getGoodsName());
				goodsTotalPrice = NumberUtils.doubleAdd(goodsTotalPrice, c.getSubTotal(), 2);
				totalDiscountPrice = NumberUtils.doubleAdd(totalDiscountPrice, c.getDiscount(), 2);
			}


			Address address  = addressService.getByMemberIdAndId(memberId, confirmVo.getAddressId());
			if(null == address) {
				return JsonResultUtils.buildJsonFailMsg("收货地址不存在");
			}
			//获取商家配送费
			Delivery shopDelivery = deliveryService.getByType(0);
			//获取平台抽成费
			Delivery platformDelivery = deliveryService.getByType(1);
			Order order = new Order(orderService.buildOrderNumber(), 
					orderName.toString(),
					memberId, 
					NumberUtils.doubleAdd(goodsTotalPrice, Double.parseDouble(shopDelivery.getVal()), 2),
					totalDiscountPrice, 
					confirmVo.getShopId(),
					confirmVo.getRemark(),
					address, 
					Double.parseDouble(shopDelivery.getVal()), 
					Double.parseDouble(platformDelivery.getVal()), 
					goodsTotalPrice);
			//下单
			if(null != (order = orderService.addOrder(order,carts))) {

				// 构建支付信息
				ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
				Member member= memberService.getById(memberId);
				//向微信服务器下单，过去预支付信息
				Map<String, String> prePayInfo = WechatUtils.getPrePayMapInfo(order,appConfig,member);
				if (prePayInfo != null) {
					// 支付信息
					Map<String, String> result = new HashMap<String, String>();
					result.put("appId", prePayInfo.get("appid"));
					result.put("nonceStr", prePayInfo.get("nonce_str"));
					result.put("package", "prepay_id="+prePayInfo.get("prepay_id"));
					result.put("signType", "MD5");
					result.put("timeStamp", String.valueOf(CalendarUtils.getTimeStamp()/1000));
					result.put("sign", WXPayCore.buildRequestMysign(WXPayCore.createLinkString(result),appConfig.getPayKey()));
					result.put("packages", result.get("package"));
					json.put("orderId",  order.getId());
					json.put("appId", appConfig.getAppid());
					json.put("merchantId", appConfig.getMerchantId());
					json.putPOJO("payInfo", result);
					json.put("timeExpire", ServiceConstants.TIME_EXPIRE);
					/**
					 * 如果下单成功把该购物车的商品软删除
					 */
					if(prePayInfo.containsKey("prepay_id")){
						cartService.updateStatusByList(ids,1);
						order.setPrepayId(prePayInfo.get("prepay_id"));
						orderService.updateById(order);
					}
					return JsonResultUtils.buildJsonOK(json);
				}
				return JsonResultUtils.buildJsonFailMsg("构造支付信息失败");
			}
			return JsonResultUtils.buildJsonFailMsg("下单失败");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	/**
	 * 根据订单ID获取支付信息接口
	* @author chenbiao
	* @date 2019年8月15日 下午7:11:52
	* 参数说明 
	* 
	* @param orderId
	* @param request
	* @return
	 */
	@ApiOperation(value = "根据订单ID调用支付接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId",value = "订单ID",paramType = "query",dataType = "int"),
	})
	@MemberLoginRequired
	@PostMapping(value = "pay")
	public JsonResult pay(@RequestParam Integer orderId,HttpServletRequest request) {
		
		try {
			Integer memberId = getMemberIdByRequest(request);
			Order order = orderService.getById(orderId);
			if(null == order || !order.getMemberId().equals(memberId)) {
				return JsonResultUtils.buildJsonFailMsg("订单不存在");
			}else if(order.getStatus() != 0) {
				return JsonResultUtils.buildJsonFailMsg("当前订单状态不能支付");
			}
			Shop shop = shopService.getById(order.getShopId());
			if(null == shop || shop.getStatus() == 1) {
				return JsonResultUtils.buildJsonFailMsg("改商家不存在或已锁定");
			}
			
			// 构建支付信息
			ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
			Member member= memberService.getById(memberId);
			//向微信服务器下单，过去预支付信息
			Map<String, String> prePayInfo = WechatUtils.getPrePayMapInfo(order,appConfig,member);
			if (prePayInfo != null) {
				// 支付信息
				Map<String, String> result = new HashMap<String, String>();
				result.put("appId", prePayInfo.get("appid"));
				result.put("nonceStr", prePayInfo.get("nonce_str"));
				result.put("package", "prepay_id="+prePayInfo.get("prepay_id"));
				result.put("signType", "MD5");
				result.put("timeStamp", String.valueOf(CalendarUtils.getTimeStamp()/1000));
				result.put("sign", WXPayCore.buildRequestMysign(WXPayCore.createLinkString(result),appConfig.getPayKey()));
				result.put("packages", result.get("package"));
				json.put("orderId",  order.getId());
				json.put("appId", appConfig.getAppid());
				json.put("merchantId", appConfig.getMerchantId());
				json.putPOJO("payInfo", result);
				order.setPrepayId(prePayInfo.get("prepay_id"));
				orderService.updateById(order);
				return JsonResultUtils.buildJsonOK(json);
			}
			return JsonResultUtils.buildJsonFailMsg("构造支付信息失败");
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	
	/**
	 * 根据订单ID获取订单详情
	* @author chenbiao
	* @date 2019年8月16日 下午5:28:48
	* 参数说明 
	* 
	* @param orderId
	* @param request
	* @return
	 */
	@ApiOperation(value = "根据订单ID获取订单详情接口",response = OrderDetailResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId",value = "订单ID",paramType = "query",dataType = "int"),
	})
	@MemberLoginRequired
	@GetMapping(value = "detail")
	public JsonResult detail(@RequestParam Integer orderId,HttpServletRequest request) {
		
		try {
			OrderDetailResponse orderDetailResponse=orderService.selectOrderDetailResponseByOrderId(orderId);
			if(orderDetailResponse==null){
				orderDetailResponse=new OrderDetailResponse();
			}
			return JsonResultUtils.buildJsonOK(orderDetailResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	
	/**
	 * 订单支付成功回调通知
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午7:28:55
	* 参数说明 
	* 
	* @param request
	* @param response
	* @return
	 */
	@ResponseBody
	@RequestMapping(value = "paySuccess",consumes = "text/xml")
	public String paySuccess(@RequestBody String notifyContent,HttpServletRequest request,
			HttpServletResponse response) {
		if (null != notifyContent) {
			log.debug("web pay notify content:{}", notifyContent);
			try {
				Map<String, String> returnMap = WXPayCore.buildContentMap(notifyContent);
				if (SUCCESS.equals(returnMap.get("return_code"))) {
					Order order = orderService.selectByOrderId(returnMap.get("outTradeNo"));
					if(null == order) {
						return buildReturnMsg(FAIL, "订单不存在");
					}
					// 回调验签
					if (WXPayCore.verifyByNotifyCallBack(returnMap, appConfig.getPayKey())) {
						String res = SUCCESS;
						// 验签通过
						log.info("验签通过");
						log.info(returnMap.toString());
						if (SUCCESS.equals(returnMap.get("result_code"))) {
							JsonNode json = JsonUtils.stringToJson(orderService.payNotifyByWechatPay(returnMap,wxMaConfiguration.getMaMsgService(appConfig.getAppid())));
							res = json.get("result").asText();
						}
						return buildReturnMsg(res, null);// 处理成功，返回响应
					}
					return buildReturnMsg(FAIL, "验签失败");
				}
				return buildReturnMsg(SUCCESS, null);// 处理成功，返回响应
			} catch (Exception e) {
				e.printStackTrace();
				return buildReturnMsg(FAIL, "出现错误");
			}
		}
		return buildReturnMsg(FAIL, "回调内容不能为空");
	}
	/**
	 * 根据订单ID取消订单接口
	* @author chenbiao
	* @date 2019年8月15日 下午7:12:51
	* 参数说明 
	* 
	* @param orderId
	* @param request
	* @return
	 */
	@ApiOperation(value = "根据订单ID取消订单接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId",value = "订单ID",paramType = "query",dataType = "int"),
	})
	@MemberLoginRequired
	@PostMapping(value = "cannel")
	public JsonResult cannel(@RequestParam Integer orderId,HttpServletRequest request) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			if(orderService.cannelOrder(orderId, memberId)) {
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFailMsg("订单取消失败");
		} catch (Exception e) {
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	
	/**
	 * 订单确认送达
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午3:10:11
	* 参数说明 
	* 
	* @param orderId
	* @param request
	* @return
	 */
	@ApiOperation(value = "订单确认送达接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orderId",value = "订单ID",paramType = "query",dataType = "int"),
	})
	@MemberLoginRequired
	@PostMapping(value = "finished")
	public JsonResult finished(@RequestParam Integer orderId,HttpServletRequest request) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			if(orderService.finishedOrder(orderId, memberId)) {
				//更新商家可见余额
				Order order=orderService.getById(orderId);
				Shop shop=shopService.getById(order.getShopId());
				Double soBalance= NumberUtils.doubleAdd(shop.getSoBalance().doubleValue(),order.getTotalPrice().doubleValue());
				shop.setSoBalance(new BigDecimal(soBalance));
				shopService.updateById(shop);
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFailMsg("订单确认失败");
		} catch (Exception e) {
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}

	
	/**
	 * 构造返回数据信息
	 * 
	 * @author chenbiao
	 * @date 2017年3月9日 下午2:17:07 参数说明
	 * 
	 * @param returnCode
	 * @param returnMsg
	 * @return
	 */
	private String buildReturnMsg(String returnCode, String returnMsg) {
		StringBuffer sb = new StringBuffer("<xml>");
		sb.append("<return_code>").append(returnCode).append("</return_code>");
		if (returnMsg != null) {
			sb.append("<return_msg>").append(returnMsg).append("</return_msg>");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * 校验购物车信息是否是同一个商铺
	* @author chenbiao
	* @date 2019年8月16日 下午12:09:24
	* 参数说明 
	* 
	* @param carts
	* @return
	 */
	private boolean validateShop(List<CartResponse> carts,Integer shopId) {
		for(CartResponse c : carts) {
			if(!c.getShopId().equals(shopId)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 订单列表
	 * @return
	 */
	@MemberLoginRequired
	@ApiOperation(value="订单列表",response =ShopOrderResponse.class )
	@GetMapping(value = "orderList")
	public JsonResult orderList(HttpServletRequest request,Integer status){
		try {
			List<ShopOrderResponse> shopOrderResponses=orderService.selectShopOrderResponse(null,status,getMemberIdByRequest(request));
			return JsonResultUtils.buildJsonOK(shopOrderResponses);
		}catch (Exception e){
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}


	/**
	 * 再来一单
	 * @return
	 */
	@MemberLoginRequired
	@ApiImplicitParam(name = "orderId",value = "订单ID")
	@ApiOperation(value = "再来一单")
	@GetMapping(value = "anotherOrder")
	public JsonResult anotherOrder(@RequestParam  Integer orderId,HttpServletRequest request){
		try {
			ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
			OrderDetailResponse orderDetailResponse=orderService.selectOrderDetailResponseByOrderId(orderId);
			if(orderDetailResponse==null){
				return JsonResultUtils.buildJsonFailMsg("订单不存在");
			}
			//查询本店所有上架商品
			List<Goods> goodsList = goodsService.selectByKeyword(null, orderDetailResponse.getShopId());
			Set<Integer> goodsIdSet=new HashSet<>();
			for(Goods goods:goodsList){
				goodsIdSet.add(goods.getId());
			}
			List<Cart> carts=new ArrayList<>();
			StringBuffer msg=new StringBuffer();
			List<Integer> goodIds=new ArrayList<>();
			for(OrderDetails orderDetails :orderDetailResponse.getOrderDetailsList()){
				if(!goodsIdSet.contains(orderDetails.getGoodsId())){
					goodIds.add(orderDetails.getGoodsId());
					continue;
				}
				Cart cart=new Cart(getMemberIdByRequest(request),orderDetailResponse.getShopId(),orderDetails.getGoodsId(),orderDetails.getCounts());
				carts.add(cart);
			}
			if(goodIds.size()>0){
				List<Goods> goodsList1=goodsService.selectGoodsListByIds(goodIds);
				for(Goods goods:goodsList1){
					msg.append(goods.getGoodsName());
					msg.append(",");
				}
			}
			String smsg=msg.toString();
			if(smsg.length()>0){
				smsg=smsg.substring(0,smsg.length()-1);
				smsg+="已下架";
				json.put("smsg",smsg);
			}


			//查询该会员的购物车是否本店铺的的商品
			List<CartResponse> cartList=cartService.selectCartResponseByShopIdAndMemberId(orderDetailResponse.getShopId(),getMemberIdByRequest(request));
			if(cartList.size()>0){
				List<Integer> cartIds=new ArrayList<>();//购物车IDs
				for(CartResponse cartResponse:cartList){
					cartIds.add(cartResponse.getId());
				}
				//清除该会员再本店铺的购物车
				cartService.updateStatusByList(cartIds,1);
			}
			//添加新商品到购物车
			cartService.insertList(carts);


			return JsonResultUtils.buildJsonOK(json);
		}catch (Exception e){
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
}

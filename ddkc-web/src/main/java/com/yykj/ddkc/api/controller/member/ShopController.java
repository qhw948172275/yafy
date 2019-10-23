package com.yykj.ddkc.api.controller.member;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yykj.commons.ErrorCodeUtils;
import com.yykj.ddkc.entity.ShopCategory;
import com.yykj.ddkc.response.ShopResponse;
import com.yykj.system.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.GoodsSearchRecord;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.GoodsSearchRecordResponse;
import com.yykj.ddkc.service.CartService;
import com.yykj.ddkc.service.GoodsService;
import com.yykj.ddkc.service.ShopCategoryService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 店铺相关接口
 * 
* @author chenbiao
* @date 2019年8月15日 下午5:11:38 
*
 */
@Api(value = "店铺相关接口",tags = "店铺相关接口")
@RestController(value = "shopByMember")
@RequestMapping(value = "/api/member/shop/")
public class ShopController extends BaseApiController{
	
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	ShopCategoryService shopCategoryService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	CartService cartService;
	/**
	 * 根据店铺ID获取店铺详情接口
	* @author chenbiao
	* @date 2019年8月14日 下午4:35:30
	* 参数说明 
	* 
	* @param id
	* @param lat
	* @param lng
	* @return
	 */
	@ApiOperation(value = "根据店铺ID获取店铺详情接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "店铺ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "lat",value = "会员经度",paramType = "query"),
		@ApiImplicitParam(name = "lng",value = "会员纬度",paramType = "query"),
	})
	@GetMapping(value = "detail")
	public JsonResult detail(@RequestParam("id")Integer id,@RequestParam BigDecimal lat, @RequestParam BigDecimal lng) {
		try {
			ShopResponse shopResponse=shopService.selectResponseById(lat, lng, id);
			if(shopResponse==null){
				return ErrorCodeUtils.ERROR_4115.getResult();
			}
			return JsonResultUtils.buildJsonOK(shopResponse);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	/**
	 * 根据店铺ID获取店铺分类列表
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午6:20:35
	* 参数说明 
	* 
	* @param shopId
	* @return
	 */
	@ApiOperation(value = "根据店铺ID获取店铺分类列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int")
	})
	@GetMapping(value = "categories")
	public JsonResult categories(@RequestParam("shopId")Integer shopId) {
		try {
			ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
            List<ShopCategory> shopCategoryList=shopCategoryService.selectByShopId(shopId);
			json.putPOJO("shopCategoryList",shopCategoryList);
			return JsonResultUtils.buildJsonOK(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	
	/**
	 * 根据分类获取商品列表
	 * 
	* @author chenbiao
	* @date 2019年8月14日 下午4:40:50
	* 参数说明 
	* 
	* @param categoryId
	* @param page
	* @param limit
	* @return
	 */
	@ApiOperation(value = "根据店铺分类ID获取商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "categoryId",value = "店铺分类ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "page",value = "当前页",paramType = "query",dataType = "int",defaultValue = "1"),
		@ApiImplicitParam(name = "limit",value = "展示行数",paramType = "query",dataType = "int",defaultValue = "10")
	})
	@GetMapping(value = "goods")
	public JsonResult goods(HttpServletRequest request,@RequestParam("shopId")Integer shopId,@RequestParam("categoryId")Integer categoryId,
			@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "limit",defaultValue = "10")Integer limit) {
		try {
			ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
			PageInfo<Goods> goods = goodsService.selectByPageByShopCategoryId(shopId, categoryId,page,limit);
            Integer memberId = getMemberIdByRequest(request);
            List<CartResponse> cartResponses = cartService.selectCartResponseByShopIdAndMemberId(shopId, memberId);
            if(cartResponses.size()>0){
                List<Goods> goodsList=goods.getList();
                Map<Integer,Goods> countMap=new HashMap<>();
                for(Goods goodss:goodsList){
                    countMap.put(goodss.getId(), goodss);
                }
                for(CartResponse cartResponse:cartResponses){
                    if(countMap.containsKey(cartResponse.getGoodsId())){
                        Goods goods1=countMap.get(cartResponse.getGoodsId());
                        goods1.setCartGoodsCount(cartResponse.getCounts());
                    }
                }
            }
			json.putPOJO("goods",goods);
			return JsonResultUtils.buildJsonOK(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	
	/**
	 * 根据关键字搜索指定店铺商品信息
	* @author chenbiao
	* @date 2019年8月15日 下午4:57:02
	* 参数说明 
	* 
	* @param shopId
	* @param keyword
	* @return
	 */
	@ApiOperation(value = "店铺内通过关键词搜索商品接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int"),
		@ApiImplicitParam(name = "keyword",value = "商品关键词",paramType = "query",dataType = "String")
	})
	@GetMapping(value = "goods/search")
	public JsonResult searchGoods(@RequestParam("shopId")Integer shopId,@RequestParam("keyword")String keyword,HttpServletRequest request) {
		try {
			if(StringUtils.isEmpty(keyword)){
				return JsonResultUtils.buildJsonOK();
			}
			List<Goods> goodsList = goodsService.selectByKeyword(keyword, shopId);
			//添加搜索记录
			if(StringUtils.isNotEmpty(keyword)){
				GoodsSearchRecord record = new GoodsSearchRecord(shopId, keyword, SystemConstants.STATUS_OK, CalendarUtils.getDate());
				goodsService.addGoodsSearchRecord(record);
			}
            List<CartResponse> cartResponses = cartService.selectCartResponseByShopIdAndMemberId(shopId, getMemberIdByRequest(request));
			if(cartResponses.size()>0){
			    Map<Integer,Goods> goodsCount=new HashMap<>();
			   for(Goods goods:goodsList){
                   goodsCount.put(goods.getId(),goods);
               }
			   for(CartResponse cartResponse:cartResponses){
			   	  if(goodsCount.containsKey(cartResponse.getGoodsId())){
					  Goods goods=goodsCount.get(cartResponse.getGoodsId());
					  goods.setCartGoodsCount(cartResponse.getCounts());
				  }
               }

            }
			return JsonResultUtils.buildJsonOK(goodsList);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	/**
	 * 根据指定店铺ID获取热门搜索关键词记录
	* @author chenbiao
	* @date 2019年8月15日 下午4:59:09
	* 参数说明 
	* 
	* @param shopId
	* @return
	 */
	@ApiOperation(value = "获取指定店铺热门搜索关键词记录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shopId",value = "店铺ID",paramType = "query",dataType = "int")
	})
	@GetMapping(value = "goods/search/hotKey")
	public JsonResult hotKey(@RequestParam("shopId")Integer shopId) {
		try {
			List<GoodsSearchRecordResponse> responses = goodsService.selectHotKeyByShopId(shopId);
			return JsonResultUtils.buildJsonOK(responses);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
	
	/**
	 * 商品详情信息
	 * 
	* @author chenbiao
	* @date 2019年8月15日 下午5:23:23
	* 参数说明 
	* 
	* @param goodsId
	* @return
	 */
	@ApiOperation(value = "商品详情",notes = "用户登录状态下会下行店铺购物车信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "goodsId",value = "商品ID",paramType = "query",dataType = "int")
	})
	@GetMapping(value = "goods/detail")
	public JsonResult goodsDetail(@RequestParam("goodsId")Integer goodsId,HttpServletRequest request) {
		try {
			Goods goods = goodsService.getById(goodsId);
			if(null != goods) {
				if(goods.getStatus() == SystemConstants.STATUS_NO) {
					return JsonResultUtils.buildJsonFailMsg("该商品已下架");
				}
				ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
				json.putPOJO("info", goods);
				Integer memberId = getMemberIdByRequest(request);
				if(null != memberId) {
					//说明用户已登录
					List<CartResponse> cartInfo = cartService.selectCartResponseByShopIdAndMemberId(goods.getShopId(), memberId);
					json.putPOJO("cartInfo", cartInfo);
				}
				return JsonResultUtils.buildJsonOK(json);
			}
			return JsonResultUtils.buildJsonFailMsg("商品不存在");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg("服务端异常");
		}
	}
}

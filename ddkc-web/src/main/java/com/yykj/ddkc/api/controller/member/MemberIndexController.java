package com.yykj.ddkc.api.controller.member;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.response.ShopResponse;
import com.yykj.ddkc.service.DeliveryService;
import com.yykj.ddkc.service.MemberService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 会员首页
* @author chenbiao
* @date 2019年8月14日 上午9:50:54 
*
 */
@Api(value = "会员首页",tags = "会员首页")
@RestController(value = "memberIndexController")
@RequestMapping(value = "api/member/")
public class MemberIndexController extends BaseApiController{
	
	@Autowired
	MemberService memberService;
	
	
	@Autowired 
	ShopService shopService;
	
	@Autowired
	DeliveryService deliveryService;
	
	/**
	 * 会员端首页接口
	* @author chenbiao
	* @date 2019年8月14日 上午9:51:52
	* 参数说明 
	* 
	* @param request
	* @return
	 */
	@ApiOperation(value = "根据地理位置坐标获取商家信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keyword",value = "小区名称,如果不填则默认搜索后台设定距离以内的商家",paramType = "query",required = false),
		@ApiImplicitParam(name = "lat",value = "经度",paramType = "query"),
		@ApiImplicitParam(name = "lng",value = "纬度",paramType = "query"),
	})
	@GetMapping(value = "index")
	public JsonResult index(HttpServletRequest request,@RequestParam(value = "keyword",required = false)String keyword,
			@RequestParam BigDecimal lat, @RequestParam BigDecimal lng) {
		try {
			List<ShopResponse> shopList = null;
			if(StringUtils.isNotEmpty(keyword)) {
				shopList = shopService.selectByKeyword(lat, lng, keyword);
			}else 
				shopList = shopService.selectByLatAndLng(lat, lng,deliveryService.getByType(2));
			return JsonResultUtils.buildJsonOK(shopList);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}

}

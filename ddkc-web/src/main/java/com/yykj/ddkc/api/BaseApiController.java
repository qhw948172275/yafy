package com.yykj.ddkc.api;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.RedisUtils;
import com.yykj.commons.ServiceConstants;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.RandomUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口基础控制类
 * 
* @author chenbiao
* @date 2019年8月12日 上午10:47:38 
*
 */
@Controller
public class BaseApiController {
	
	/**
	 * 日志
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public RedisUtils redisUtils;
	
	
	/**
	 * 创建会员token
	* @author chenbiao
	* @date 2018年9月20日 上午10:26:13
	* 参数说明 
	*
	* @return
	 */
	public ObjectNode createTokenInfoByMember(Integer memberId) {
		//会员token值
		String token = RandomUtils.getRandomCharAndNumr(32);
		redisUtils.setStringForKey(ServiceConstants.MEMBER_TOKEN_KEY+token, memberId);
		redisUtils.setExpireKey(ServiceConstants.MEMBER_TOKEN_KEY + token,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		//刷新token值
		String refreshToken = MD5Password.md5(token+memberId);//刷新token实现方式，通过token和用户ID进行加密
		redisUtils.setStringForKey(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken, memberId);
		redisUtils.setExpireKey(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		//刷新token和会员token的关联关系
		redisUtils.setStringForKey(ServiceConstants.MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken, token);
		redisUtils.setExpireKey(ServiceConstants.MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		
		ObjectNode obj = JsonUtils.getMapperInstance().createObjectNode();
		obj.put("token",token);
		obj.put("refreshToken",refreshToken);
		return obj;
	}
	
	/**
	 * 创建商家token
	* @author chenbiao
	* @date 2019年8月12日 上午10:45:53
	* 参数说明 
	* 
	* @param shopId
	* @return
	 */
	public JsonResult createTokenInfoByShop(Integer shopId,Integer shopAccountId) {
		//商家token值
		String token = RandomUtils.getRandomCharAndNumr(32);
		redisUtils.setStringForKey(ServiceConstants.SHOP_TOKEN_KEY+token, shopId);
		redisUtils.setExpireKey(ServiceConstants.SHOP_TOKEN_KEY + token,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		//刷新token值
		String refreshToken = MD5Password.md5(token+shopId);//刷新token实现方式，通过token和商家ID进行加密
		redisUtils.setStringForKey(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken, shopId);
		redisUtils.setExpireKey(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		//刷新token和商家token的关联关系
		redisUtils.setStringForKey(ServiceConstants.SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken, token);
		redisUtils.setExpireKey(ServiceConstants.SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		
		//缓存token与商户账号关联关系
		redisUtils.setStringForKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token, shopAccountId);
		redisUtils.setExpireKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY + token,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		
		redisUtils.setStringForKey(ServiceConstants.SHOP_REFRESH_ACCOUNT_TOKEN_KEY+refreshToken, shopAccountId);
		redisUtils.setExpireKey(ServiceConstants.SHOP_REFRESH_ACCOUNT_TOKEN_KEY+refreshToken,new Long(30) , TimeUnit.DAYS);//设置token的有效期
		
		ObjectNode obj = JsonUtils.getMapperInstance().createObjectNode();
		obj.put("token",token);
		obj.put("refreshToken",refreshToken);
		return JsonResultUtils.buildJsonOK(obj);
	}

	/**
	 * 根据token获取会员ID
	 *
	* @author chenbiao
	* @date 2019年8月12日 下午2:16:53
	* 参数说明
	* @return
	 */
	public Integer getMemberIdByRequest(HttpServletRequest request) {
		String token = getToken(request,"memberToken");
		String memberId = redisUtils.getStringForKey(ServiceConstants.MEMBER_TOKEN_KEY+token);
		if(null != memberId) {
			return Integer.parseInt(memberId);
		}
		return null;
	}
	/**
	 * 根据token获取商户ID信息
	* @author chenbiao
	* @date 2019年8月12日 下午2:18:48
	* 参数说明
	*
	* @return
	 */
	public Integer getShopIdByRequest(HttpServletRequest request) {
		String token = getToken(request,"shopToken");
		String shopId = redisUtils.getStringForKey(ServiceConstants.SHOP_TOKEN_KEY+token);
		if(null != shopId) {
			return Integer.parseInt(shopId);
		}
		return null;
	}


	public String getToken(HttpServletRequest request,String tokenName){
		return  request.getHeader(tokenName);
	}

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}

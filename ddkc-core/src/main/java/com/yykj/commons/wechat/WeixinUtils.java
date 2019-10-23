package com.yykj.commons.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.commons.HTTPUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.StringUtils;

/**
 * 微信相关帮助类
 * 
* @author chenbiao
* @date 2017年5月18日 下午1:58:06 
*
 */
public class WeixinUtils {

	/**
	 * 获取授权请求链接
	 * 
	* @author chenbiao
	* @date 2017年7月12日 下午4:51:50
	* 参数说明 
	* 
	* @param appid 应用id 
	* @param scope 权限 snsapi_userinfo获取用户信息权限；snsapi_base静默授权
	* @param callBack 授权回调地址
	* @param redirectUrl 回调处理之后重定向地址
	* @param agent 客户端环境
	* @param state 透传参数
	* @return
	* @throws UnsupportedEncodingException
	 */
	public static String getOauthUrl(String appid,String scope,String callBack,String redirectUrl,String agent, String state) throws UnsupportedEncodingException{
		if(null == scope){
			scope = "snsapi_userinfo";
		}
		StringBuffer sb = new StringBuffer();
		if (null != agent && agent.toLowerCase().contains("micromessenger")){ //微信中登录
			sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
//			appid =appid;
		}
		else { //非微信环境通过扫码方式登录
			scope = "snsapi_login";
			sb.append("https://open.weixin.qq.com/connect/qrconnect?appid=");
		}
		sb.append(appid).append("&redirect_uri=");
		if(null != redirectUrl && redirectUrl.length() > 0){
			sb.append(URLEncoder.encode(callBack + (callBack.indexOf("?") != -1 ? "&" : "?") +"redirectUrl=" + redirectUrl,"utf-8"));
		}else
			sb.append(URLEncoder.encode(callBack,"utf-8"));
		sb.append("&response_type=code&scope=").append(scope);
		if(StringUtils.isNotEmpty(state)){
			sb.append("&state="+state);
		}
		
		return sb.toString();
	}
	/**
	 * 通过授权码获取授权令牌
	* @author chenbiao
	* @date 2017年5月18日 下午1:42:42
	* 参数说明 
	* 
	* @param appid
	* @param appSecret
	* @param code
	* @return
	* <pre>
	* { 
	*	"access_token":"ACCESS_TOKEN", 
	*	"expires_in":7200, 
	*	"refresh_token":"REFRESH_TOKEN",
	*	"openid":"OPENID", 
	*	"scope":"SCOPE" 
	* }
	* </pre>
	 */
	public static JsonNode getAccessTokenInfoByCode(String appid,String appSecret,String code){
		return getAccessTokenInfoByCode(appid, appSecret, code, "authorization_code");
	}
	public static JsonNode getAccessTokenInfoByCode(String appid,String appSecret,String code,String grantType){
		try {
			StringBuffer url =  new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
			url.append(appid).append("&secret=").append(appSecret).append("&code=").append(code).append("&grant_type=").append(grantType);
			System.out.println("getAccessTokenInfoByCode的url:"+url);
			String rs = HTTPUtils.getGet(url.toString(), null);
			return JsonUtils.getValue(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据授权令牌+用户唯一标识获取用户信息
	* @author chenbiao
	* @date 2017年5月18日 下午1:50:49
	* 参数说明 
	* 
	* @param token
	* @param openId
	* @return
	* <pre>
		{ 
		"openid":"OPENID",
		"nickname":"NICKNAME",
		"sex":1,
		"province":"PROVINCE",
		"city":"CITY",
		"country":"COUNTRY",
		"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
		"privilege":[
		"PRIVILEGE1", 
		"PRIVILEGE2"
		],
		"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
	* </pre>
	 */
	public static JsonNode getUserInfoByTokenAndOpenId(String token,String openId){
		try {
			StringBuffer url =  new StringBuffer("https://api.weixin.qq.com/sns/userinfo?access_token=");
			url.append(token).append("&openid=").append(openId);
			String rs = HTTPUtils.getGet(url.toString(), null);
			return JsonUtils.getValue(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 刷新或续期accessToken
	* @author chenbiao
	* @date 2017年5月18日 下午1:53:38
	* 参数说明 
	* 
	* @param appid
	* @param refreshToken
	* @return
	* <pre>
	* { 
	*	"access_token":"ACCESS_TOKEN", 
	*	"expires_in":7200, 
	*	"refresh_token":"REFRESH_TOKEN", 
	*	"openid":"OPENID", 
	*	"scope":"SCOPE" 
	*}
	* </pre>
	 */
	public static JsonNode getRefreshTokenByToken(String appid,String refreshToken){
		try {
			StringBuffer url =  new StringBuffer("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=");
			url.append(appid).append("&grant_type=refresh_token&refresh_token=").append(refreshToken);
			String rs = HTTPUtils.getGet(url.toString(), null);
			return JsonUtils.getValue(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	// 获取Token
//	public String getToken(String appId, String secret,RedisUtils redisUtils) {
//		try {
//			String token = redisUtils.getStringForKey(SystemConstants.WEICHAT_OFFICAL_TOKEN);
//			
//			System.out.println("===缓存token"+token);
//			
//			if (token == null) {
//				// 重新获取token
//				String rs = HTTPUtils
//						.getGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId
//								+ "&secret=" + secret, null);
//				JsonNode json = JsonUtils.getValue(rs);
//				System.out.println("获取新的token"+JsonUtils.beanToJson(json));
//				if(json.has("access_token")){
//					token = json.get("access_token").asText();
//					Long expiresIn = json.get("expires_in").asLong();
//	
//					// 保存在redius中
//					redisUtils.setStringForKey(SystemConstants.WEICHAT_OFFICAL_TOKEN, token);
//					redisUtils.setExpireKey(SystemConstants.WEICHAT_OFFICAL_TOKEN, expiresIn, TimeUnit.SECONDS);
//					return token;
//				}
//			} else {
//				return token;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String getTicket(String token,RedisUtils redisUtils) {
//		try {
//			String ticket = redisUtils.getStringForKey(SystemConstants.WEICHAT_OFFICAL_TICKET);
//			
//			if (ticket == null || StringUtils.isEmpty(ticket)) {
//				// 重新获取ticket
//				String rs = HTTPUtils.getGet(
//						"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi",
//						null);
//				JsonNode json = JsonUtils.getValue(rs);
//				System.out.println("\n\n\n\n"+JsonUtils.beanToJson(json));
//				if(json.has("ticket")){
//					ticket = json.get("ticket").asText();
//					Long expiresIn = json.get("expires_in").asLong();
//					// 保存在redius中
//					redisUtils.setStringForKey(SystemConstants.WEICHAT_OFFICAL_TICKET, ticket);
//					redisUtils.setExpireKey(SystemConstants.WEICHAT_OFFICAL_TICKET, expiresIn, TimeUnit.SECONDS);
//	
//					return ticket;
//				}
//			} else {
//				return ticket;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}

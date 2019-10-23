package com.yykj.commons.wechat;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.commons.HTTPUtils;


/**
 * 小程序帮助类
 * 
* @author chenbiao
* @date 2019年8月12日 上午11:33:52 
*
 */
public class MiniProgramUtils {
	
	private final static String codeSessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={JSCODE}&grant_type=authorization_code";
	/**
	 * 获取accesstoke地址
	 */
	private final static String accessTokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={SECRET}";

	/**
	 * 登录凭证校验
	* @author chenbiao
	* @date 2019年8月12日 上午11:36:54
	* 参数说明 
	* 
	* @param appid
	* @param appSecret
	* @param code
	 */
	public static JsonNode code2Session(String appid,String appSecret,String code) {
		String reqUrl = codeSessionUrl.replace("{APPID}", appid).replace("{SECRET}", appSecret).replace("{JSCODE}", code);
		System.out.println(reqUrl);
		JsonNode node = HTTPUtils.httpGet(reqUrl);
		return node;
	}

	/**
	 * 获取access_toke
	 */
	public static JsonNode getAccesssToken(String appid,String appSecret){
		String reqUrl=accessTokenUrl.replace("{APPID}",appid).replace("{SECRET}",appSecret);
		JsonNode node = HTTPUtils.httpGet(reqUrl);
		return node;
	}
}

package com.yykj.commons;

import java.util.Map;
import java.util.TreeMap;

/**
 * 业务公用类
 * 
* @author chenbiao
* @date 2018年7月28日 下午10:38:33 
*
 */
public class ServiceConstants {
	/**
	 * 服务协议状态
	 */
	public static final Map<Integer,String> SERVICEAGREEMENT_TYPE = new TreeMap<Integer , String>();
	
	/**
	 * 小程序下单服务器IP地址
	 */
	public static final String MINIPROGRAM_SPILL_CREATE_IP = "140.246.138.117";
	/**
	 * 订单有效期，默认15分钟
	 */
	public static final Long TIME_EXPIRE = 15*60*1000l;
	/**
	 * 订单支付成功，商户确认时间，默认5分钟
	 */
	public static final Long SHOP_APPLY_TIME_EXPIRE = 5*60*1000l;

	/**
	 * 订单默认完成时间2小时
	 */
	public static final Long ORDER_FINISH_TIME_EXPIRE = 2*60*60*1000l;
	/**
	 * 商家提现接口地址
	 */
	public static final String SHOP_TRANSFERS="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	static {
		
		SERVICEAGREEMENT_TYPE.put(0, "生效");
		SERVICEAGREEMENT_TYPE.put(1, "失效");
	}
	
	
	/**
	 * 会员token相关
	 */
	public static final String MEMBER_TOKEN_KEY = "ddkc:member:token:";
	public static final String MEMBER_REFRESH_TOKEN_KEY = "ddkc:member:token:refresh:";
	public static final String MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY = "ddkc:member:token:refresh:bindToken:";
	/**
	 * 会员短信验证码key
	 */
	public static final String MEMBER_MSG_CODE_KEY = "ddkc:member:msg:code:key:";

	
	/**
	 * 商家token相关
	 */
	public static final String SHOP_TOKEN_KEY = "ddkc:shop:token:";
	public static final String SHOP_ACCOUNT_TOKEN_KEY = "ddkc:shop:account:token:";
	public static final String SHOP_REFRESH_TOKEN_KEY = "ddkc:shop:token:refresh:";
	public static final String SHOP_REFRESH_ACCOUNT_TOKEN_KEY = "ddkc:shop:account:token:refresh:";
	public static final String SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY = "ddkc:shop:token:refresh:bindToken:";
	/**
	 * 商家短信验证码key
	 */
	public static final String SHOP_MSG_CODE_KEY = "ddkc:shop:msg:code:key:";
	

	/**
	 * 防盗链Token秘钥
	 */
	public static final String SECRET_KEY = "zevesxf812z4k27olrpg8711tscyijws";

	/**
	 * 超级管理员代码
	 */
	public static final String SUPER_ADMIN_CODE="administrator";


}

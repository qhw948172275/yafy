package com.yykj.ddkc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "wechat.miniprogram")
public class WxMaProperties {

	/**
	 * 设置微信小程序的appid
	 */
	private String appid;

	/**
	 * 设置微信小程序的Secret
	 */
	private String appsecret;

	/**
	 * 设置微信小程序消息服务器配置的token
	 */
	private String token;

	/**
	 * 设置微信小程序消息服务器配置的EncodingAESKey
	 */
	private String aesKey;

	/**
	 * 消息格式，XML或者JSON
	 */
	private String msgDataFormat;

}

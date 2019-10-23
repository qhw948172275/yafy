package com.yykj.commons.wechat;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 小程序配置文件
 * 
* @author chenbiao
* @date 2019年8月12日 上午11:04:36 
*
 */
@Configuration
@ConfigurationProperties(prefix = "wechat.miniprogram")
public class AppConfig implements Serializable{
	/** 
	*/
	private static final long serialVersionUID = -4218085515410024367L;
	/**
	 * 应用ID
	 */
	private String appid;
	/**
	 * 应用密钥
	 */
	private String appsecret;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 支付回调通知地址
	 */
	private String payNotifyUrl;
	/**
	 * 支付密钥
	 */
	private String payKey;
	/**
	 * p12证书，用于退款操作
	 */
	private String p12;
	/**
	 * 支付成功推送的支付成功模版消息ID
	 */
	private String paySuccessMessageTemplateId;
	
	public String getTradeType() {
		return "JSAPI";//小程序支付，交易类型固定
	}

	public String getAppid() {
		return appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getP12() {
		return p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}

	public String getPaySuccessMessageTemplateId() {
		return paySuccessMessageTemplateId;
	}

	public void setPaySuccessMessageTemplateId(String paySuccessMessageTemplateId) {
		this.paySuccessMessageTemplateId = paySuccessMessageTemplateId;
	}

}

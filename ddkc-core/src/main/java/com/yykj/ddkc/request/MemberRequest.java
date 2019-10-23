package com.yykj.ddkc.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员请求实体封装
 * 
* @author chenbiao
* @date 2019年8月12日 上午11:57:01 
*
 */
@ApiModel(description = "会员请求实体封装")
public class MemberRequest {
	
	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickName;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String avastarUrl;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "")
	private String gender;
	/**
	 * 国家
	 */
	@ApiModelProperty(value = "国家")
	private String country;
	/**
	 * 省份
	 */
	@ApiModelProperty(value = "省份")
	private String province;
	/**
	 * 城市
	 */
	@ApiModelProperty(value = "城市")
	private String city;
	/**
	 * 语言
	 */
	@ApiModelProperty(value = "语言")
	private String language;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvastarUrl() {
		return avastarUrl;
	}
	public void setAvastarUrl(String avastarUrl) {
		this.avastarUrl = avastarUrl;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

}

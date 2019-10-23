package com.yykj.ddkc.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.system.commons.CalendarUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_shop_account")
@ApiModel(description = "员工账号实体信息")
public class ShopAccount implements Serializable{
    /** 
	*/
	private static final long serialVersionUID = 6423799292248377015L;

	/**
     * 商户账号id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "员工账号ID")
    private Integer id;

    /**
     * 商户ID
     */
    @Column(name = "shop_id")
    @ApiModelProperty(value = "所属店铺ID；可不填写")
    private Integer shopId;
    /**
     * 账号姓名
     */
    @Column(name = "name")
    @ApiModelProperty(value = "账号姓名")
    private String name;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 是否是主账号。0表示否；1表示是
     */
    @Column(name = "is_master")
    @ApiModelProperty(value = "是否主账号。0表示否；1表示是；可不填写")
    private Integer isMaster;

    /**
     * 是否接收语音通知。0表示否；1表示是
     */
    @Column(name = "is_notice")
    @ApiModelProperty(value = "是否接收语音通知。0表示否；1表示是")
    private Integer isNotice;

    /**
     * 状态。0表示正常；1表示禁用；2表示已删除
     */
    @ApiModelProperty(value = "状态。0表示正常；1表示禁用；2表示已删除；可不填写")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间；可不填写")
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    @ApiModelProperty(value = "创建人ID；可不填写")
    private Integer creatorId;
    /**
     * 用户openID
     */
    @Column(name="open_id")
    @ApiModelProperty(value = "主账号openId")
    private String openId;

    /**
     * 获取商户账号id
     *
     * @return id - 商户账号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商户账号id
     *
     * @param id 商户账号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商户ID
     *
     * @return shop_id - 商户ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置商户ID
     *
     * @param shopId 商户ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取是否是主账号。0表示否；1表示是
     *
     * @return is_master - 是否是主账号。0表示否；1表示是
     */
    public Integer getIsMaster() {
        return isMaster;
    }

    /**
     * 设置是否是主账号。0表示否；1表示是
     *
     * @param isMaster 是否是主账号。0表示否；1表示是
     */
    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * 获取是否接收语音通知。0表示否；1表示是
     *
     * @return is_notice - 是否接收语音通知。0表示否；1表示是
     */
    public Integer getIsNotice() {
        return isNotice;
    }

    /**
     * 设置是否接收语音通知。0表示否；1表示是
     *
     * @param isNotice 是否接收语音通知。0表示否；1表示是
     */
    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    /**
     * 获取状态。0表示正常；1表示禁用；2表示已删除
     *
     * @return status - 状态。0表示正常；1表示禁用；2表示已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示正常；1表示禁用；2表示已删除
     *
     * @param status 状态。0表示正常；1表示禁用；2表示已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd__HH_mm_ss,timezone = CalendarUtils.TIME_ZONE)
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
    
}
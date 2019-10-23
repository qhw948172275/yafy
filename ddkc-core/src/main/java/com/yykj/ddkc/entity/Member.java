package com.yykj.ddkc.entity;

import java.util.Date;
import javax.persistence.*;

import com.yykj.ddkc.request.MemberRequest;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;

@Table(name = "t_member")
public class Member {
    /**
     * 会员ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户唯一标识
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 会员头像
     */
    private String avastar;

    /**
     * 会员昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 会员手机号
     */
    private String phone;

    /**
     * 状态。0表示启用；1表示禁用
     */
    private Integer status;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private Date createTime;
    public Member() {
    	super();
    }
    public Member(String openId2, MemberRequest memberRequest) {
    	this.openId = openId2;
    	this.nickName = memberRequest.getNickName();
    	this.avastar = memberRequest.getAvastarUrl();
    	this.status = SystemConstants.STATUS_OK;
    	this.createTime = CalendarUtils.getDate();
	}

	/**
     * 获取会员ID
     *
     * @return id - 会员ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置会员ID
     *
     * @param id 会员ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员头像
     *
     * @return avastar - 会员头像
     */
    public String getAvastar() {
        return avastar;
    }

    /**
     * 设置会员头像
     *
     * @param avastar 会员头像
     */
    public void setAvastar(String avastar) {
        this.avastar = avastar == null ? null : avastar.trim();
    }

    /**
     * 获取会员昵称
     *
     * @return nick_name - 会员昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置会员昵称
     *
     * @param nickName 会员昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取会员手机号
     *
     * @return phone - 会员手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置会员手机号
     *
     * @param phone 会员手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取状态。0表示启用；1表示禁用
     *
     * @return status - 状态。0表示启用；1表示禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示启用；1表示禁用
     *
     * @param status 状态。0表示启用；1表示禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取注册时间
     *
     * @return create_time - 注册时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置注册时间
     *
     * @param createTime 注册时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
    
}
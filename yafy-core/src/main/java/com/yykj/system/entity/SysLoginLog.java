package com.yykj.system.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_login_log")
public class SysLoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 登录用户昵称
     */
    @ApiModelProperty(value ="登录用户昵称")
    @Column(name = "user_name")
    private String userName;

    /**
     * 真实名称
     */
    @ApiModelProperty(value ="真实名称")
    @Column(name = "realm_name")
    private String realmName;

    /**
     * 登录IP
     */
    @ApiModelProperty(value ="登录IP")
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 登录省份
     */
    @ApiModelProperty(value ="登录省份")
    @Column(name = "login_province")
    private String loginProvince;

    /**
     * 登录地址
     */
    @ApiModelProperty(value ="登录地址")
    @Column(name = "login_address")
    private String loginAddress;

    /**
     * 登录时间
     */
    @ApiModelProperty(value ="登录时间")
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 学校ID
     */
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取登录用户昵称
     *
     * @return user_name - 登录用户昵称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置登录用户昵称
     *
     * @param userName 登录用户昵称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取真实名称
     *
     * @return realm_name - 真实名称
     */
    public String getRealmName() {
        return realmName;
    }

    /**
     * 设置真实名称
     *
     * @param realmName 真实名称
     */
    public void setRealmName(String realmName) {
        this.realmName = realmName == null ? null : realmName.trim();
    }

    /**
     * 获取登录IP
     *
     * @return login_ip - 登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置登录IP
     *
     * @param loginIp 登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * 获取登录省份
     *
     * @return login_province - 登录省份
     */
    public String getLoginProvince() {
        return loginProvince;
    }

    /**
     * 设置登录省份
     *
     * @param loginProvince 登录省份
     */
    public void setLoginProvince(String loginProvince) {
        this.loginProvince = loginProvince == null ? null : loginProvince.trim();
    }

    /**
     * 获取登录地址
     *
     * @return login_address - 登录地址
     */
    public String getLoginAddress() {
        return loginAddress;
    }

    /**
     * 设置登录地址
     *
     * @param loginAddress 登录地址
     */
    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress == null ? null : loginAddress.trim();
    }

    /**
     * 获取登录时间
     *
     * @return login_time - 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置登录时间
     *
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取学校ID
     *
     * @return school_id - 学校ID
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置学校ID
     *
     * @param schoolId 学校ID
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "\nSysLoginLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", realmName='" + realmName + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginProvince='" + loginProvince + '\'' +
                ", loginAddress='" + loginAddress + '\'' +
                ", loginTime=" + loginTime +
                ", schoolId=" + schoolId +
                '}';
    }
}
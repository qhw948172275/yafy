package com.yykj.system.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_user")
public class SysUser  implements Serializable {
    /**
     * 登录用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String name;

    /**
     * 用户email号。用于保证用户唯一，且用于通过email进行帐号激活。
     */
    @ApiModelProperty(value = "email号")
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态:0-启用，1-禁用")
    private Integer status;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String creator;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    /**
     * 登录次数
     */
    @Column(name = "login_Times")
    private Integer loginTimes;

    /**
     * 上次登录时间
     */
    @Column(name = "last_Login_Time")
    private Long lastLoginTime;

    /**
     * 上次登录IP
     */
    @Column(name = "last_Login_Ip")
    private String lastLoginIp;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty(value = "用户真实姓名")
    @Column(name = "real_name")
    private String realName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    @Column(name = "id_card")
    private String idCard;

    /**
     * 紧急联系电话
     */
    @ApiModelProperty(value = "紧急联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID")
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 角色名称，多个情况下以、号隔开
     */
    @Column(name = "role_names")
    private String roleNames;

    /**
     * 加盟校ID
     */
    @ApiModelProperty(value = "加盟校ID")
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 用户类型。0表示平台用户；1表示加盟校用户
     */
    @ApiModelProperty(value="用户类型。0表示平台用户；1表示加盟校用户")
    private Integer type;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String cover;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 获取登录用户ID
     *
     * @return id - 登录用户ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置登录用户ID
     *
     * @param id 登录用户ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户昵称
     *
     * @return name - 用户昵称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户昵称
     *
     * @param name 用户昵称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取用户email号。用于保证用户唯一，且用于通过email进行帐号激活。
     *
     * @return email - 用户email号。用于保证用户唯一，且用于通过email进行帐号激活。
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置用户email号。用于保证用户唯一，且用于通过email进行帐号激活。
     *
     * @param email 用户email号。用于保证用户唯一，且用于通过email进行帐号激活。
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建者
     *
     * @return creator - 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取登录次数
     *
     * @return login_Times - 登录次数
     */
    public Integer getLoginTimes() {
        return loginTimes;
    }

    /**
     * 设置登录次数
     *
     * @param loginTimes 登录次数
     */
    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    /**
     * 获取上次登录时间
     *
     * @return last_Login_Time - 上次登录时间
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置上次登录时间
     *
     * @param lastLoginTime 上次登录时间
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取上次登录IP
     *
     * @return last_Login_Ip - 上次登录IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置上次登录IP
     *
     * @param lastLoginIp 上次登录IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * 获取用户真实姓名
     *
     * @return real_name - 用户真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置用户真实姓名
     *
     * @param realName 用户真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取身份证号码
     *
     * @return id_card - 身份证号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号码
     *
     * @param idCard 身份证号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * 获取紧急联系电话
     *
     * @return contact_phone - 紧急联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 设置紧急联系电话
     *
     * @param contactPhone 紧急联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * 获取创建者ID
     *
     * @return creator_id - 创建者ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建者ID
     *
     * @param creatorId 创建者ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取角色名称，多个情况下以、号隔开
     *
     * @return role_names - 角色名称，多个情况下以、号隔开
     */
    public String getRoleNames() {
        return roleNames;
    }

    /**
     * 设置角色名称，多个情况下以、号隔开
     *
     * @param roleNames 角色名称，多个情况下以、号隔开
     */
    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames == null ? null : roleNames.trim();
    }

    /**
     * 获取加盟校ID
     *
     * @return school_id - 加盟校ID
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置加盟校ID
     *
     * @param schoolId 加盟校ID
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取用户类型。0表示平台用户；1表示加盟校用户
     *
     * @return type - 用户类型。0表示平台用户；1表示加盟校用户
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型。0表示平台用户；1表示加盟校用户
     *
     * @param type 用户类型。0表示平台用户；1表示加盟校用户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取用户头像
     *
     * @return cover - 用户头像
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置用户头像
     *
     * @param cover 用户头像
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
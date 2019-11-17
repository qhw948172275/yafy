package com.yykj.system.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_account_log")
public class AccountLog {
    /**
     * 操作日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 区域
     */
    private String area;

    /**
     * 方法名
     */
    private String methods;

    /**
     * 请求全路径
     */
    @ApiModelProperty(value = "请求全路径")
    private String paths;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 请求描述
     */
    @ApiModelProperty(value = "请求描述")
    private String descr;

    /**
     * 省市县
     */
    private String addr;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作人ID
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 操作时间
     */
    @Column(name = "account_time")
    @ApiModelProperty(value = "操作时间")
    private Date accountTime;

    /**
     * 学校ID
     */
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 角色
     */
    @Column(name = "role_name")
    @ApiModelProperty(value = "角色")
    private String roleName;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 帐号
     */
    @ApiModelProperty(value = "帐号")
    private String account;

    /**
     * 获取操作日志ID
     *
     * @return id - 操作日志ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置操作日志ID
     *
     * @param id 操作日志ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取区域
     *
     * @return area - 区域
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区域
     *
     * @param area 区域
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 获取方法名
     *
     * @return methods - 方法名
     */
    public String getMethods() {
        return methods;
    }

    /**
     * 设置方法名
     *
     * @param methods 方法名
     */
    public void setMethods(String methods) {
        this.methods = methods == null ? null : methods.trim();
    }

    /**
     * 获取请求全路径
     *
     * @return paths - 请求全路径
     */
    public String getPaths() {
        return paths;
    }

    /**
     * 设置请求全路径
     *
     * @param paths 请求全路径
     */
    public void setPaths(String paths) {
        this.paths = paths == null ? null : paths.trim();
    }

    /**
     * 获取请求参数
     *
     * @return params - 请求参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置请求参数
     *
     * @param params 请求参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 获取请求描述
     *
     * @return descr - 请求描述
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 设置请求描述
     *
     * @param descr 请求描述
     */
    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    /**
     * 获取省市县
     *
     * @return addr - 省市县
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置省市县
     *
     * @param addr 省市县
     */
    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    /**
     * 获取IP地址
     *
     * @return ip - IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP地址
     *
     * @param ip IP地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取操作人ID
     *
     * @return account_id - 操作人ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置操作人ID
     *
     * @param accountId 操作人ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取操作时间
     *
     * @return account_time - 操作时间
     */
    public Date getAccountTime() {
        return accountTime;
    }

    /**
     * 设置操作时间
     *
     * @param accountTime 操作时间
     */
    public void setAccountTime(Date accountTime) {
        this.accountTime = accountTime;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
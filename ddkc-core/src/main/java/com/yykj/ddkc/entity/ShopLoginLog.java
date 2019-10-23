package com.yykj.ddkc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop_login_log")
public class ShopLoginLog {
    /**
     * 商户登录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商户ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 登录时间
     */
    @Column(name = "login_times")
    private Date loginTimes;

    /**
     * 登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 登录地址
     */
    private String address;
    /**
     * 省份
     */
    private String province;

    /**
     * 登录设备
     */
    private String devices;

    /**
     * 获取商户登录ID
     *
     * @return id - 商户登录ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商户登录ID
     *
     * @param id 商户登录ID
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
     * 获取登录时间
     *
     * @return login_times - 登录时间
     */
    public Date getLoginTimes() {
        return loginTimes;
    }

    /**
     * 设置登录时间
     *
     * @param loginTimes 登录时间
     */
    public void setLoginTimes(Date loginTimes) {
        this.loginTimes = loginTimes;
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
     * 获取登录地址
     *
     * @return address - 登录地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置登录地址
     *
     * @param address 登录地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取登录设备
     *
     * @return devices - 登录设备
     */
    public String getDevices() {
        return devices;
    }

    /**
     * 设置登录设备
     *
     * @param devices 登录设备
     */
    public void setDevices(String devices) {
        this.devices = devices == null ? null : devices.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
package com.yykj.ddkc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop_search_log")
public class ShopSearchLog {
    /**
     * 店铺搜索日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 搜索关键字
     */
    private String keywords;

    /**
     * 搜索会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 搜索时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态。0表示正常；1表示已删除
     */
    private Integer status;

    /**
     * 获取店铺搜索日志ID
     *
     * @return id - 店铺搜索日志ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置店铺搜索日志ID
     *
     * @param id 店铺搜索日志ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取搜索关键字
     *
     * @return keywords - 搜索关键字
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置搜索关键字
     *
     * @param keywords 搜索关键字
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * 获取搜索会员ID
     *
     * @return member_id - 搜索会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置搜索会员ID
     *
     * @param memberId 搜索会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取搜索时间
     *
     * @return create_time - 搜索时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置搜索时间
     *
     * @param createTime 搜索时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取状态。0表示正常；1表示已删除
     *
     * @return status - 状态。0表示正常；1表示已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示正常；1表示已删除
     *
     * @param status 状态。0表示正常；1表示已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
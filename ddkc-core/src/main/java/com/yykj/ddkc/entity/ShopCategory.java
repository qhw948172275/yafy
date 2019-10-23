package com.yykj.ddkc.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop_category")
public class ShopCategory {
    /**
     * 店铺类别ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID")
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 分类状态。0表示启用；1表示禁用
     */
    @ApiModelProperty(value = "分类状态。0表示启用；1表示禁用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 获取店铺类别ID
     *
     * @return id - 店铺类别ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置店铺类别ID
     *
     * @param id 店铺类别ID
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
     * 获取分类名称
     *
     * @return name - 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分类名称
     *
     * @param name 分类名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取分类状态。0表示启用；1表示禁用
     *
     * @return status - 分类状态。0表示启用；1表示禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置分类状态。0表示启用；1表示禁用
     *
     * @param status 分类状态。0表示启用；1表示禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
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
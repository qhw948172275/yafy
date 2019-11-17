package com.yykj.business.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "big_landlord")
public class BigLandlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 小区ID
     */
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态：0-启用，1-禁用
     */
    private Byte status;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取小区ID
     *
     * @return area_id - 小区ID
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置小区ID
     *
     * @param areaId 小区ID
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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
     * 获取状态：0-启用，1-禁用
     *
     * @return status - 状态：0-启用，1-禁用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态：0-启用，1-禁用
     *
     * @param status 状态：0-启用，1-禁用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}
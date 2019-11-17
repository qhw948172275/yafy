package com.yykj.business.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 房间类型
     */
    private String type;

    /**
     * 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 是否是空房，0-是，1-不是
     */
    @Column(name = "is_null")
    private Byte isNull;

    /**
     * 租客人数
     */
    @Column(name = "tenant_nember")
    private Integer tenantNember;

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
     * 钥匙存放地址
     */
    @Column(name = "key_path")
    private String keyPath;

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
     * 获取房间类型
     *
     * @return type - 房间类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置房间类型
     *
     * @param type 房间类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取付款方式；0-月付，1-季付，2-半年付，3-年付
     *
     * @return pay_type - 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置付款方式；0-月付，1-季付，2-半年付，3-年付
     *
     * @param payType 付款方式；0-月付，1-季付，2-半年付，3-年付
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取是否是空房，0-是，1-不是
     *
     * @return is_null - 是否是空房，0-是，1-不是
     */
    public Byte getIsNull() {
        return isNull;
    }

    /**
     * 设置是否是空房，0-是，1-不是
     *
     * @param isNull 是否是空房，0-是，1-不是
     */
    public void setIsNull(Byte isNull) {
        this.isNull = isNull;
    }

    /**
     * 获取租客人数
     *
     * @return tenant_nember - 租客人数
     */
    public Integer getTenantNember() {
        return tenantNember;
    }

    /**
     * 设置租客人数
     *
     * @param tenantNember 租客人数
     */
    public void setTenantNember(Integer tenantNember) {
        this.tenantNember = tenantNember;
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
     * 获取钥匙存放地址
     *
     * @return key_path - 钥匙存放地址
     */
    public String getKeyPath() {
        return keyPath;
    }

    /**
     * 设置钥匙存放地址
     *
     * @param keyPath 钥匙存放地址
     */
    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath == null ? null : keyPath.trim();
    }
}
package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_rent_manage_renant")
public class RentManageRenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 租客ID
     */
    @Column(name = "renant_id")
    private Integer renantId;

    /**
     * 租金管理ID
     */
    @Column(name = "rent_manage_id")
    private Integer rentManageId;

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
     * 获取租客ID
     *
     * @return renant_id - 租客ID
     */
    public Integer getRenantId() {
        return renantId;
    }

    /**
     * 设置租客ID
     *
     * @param renantId 租客ID
     */
    public void setRenantId(Integer renantId) {
        this.renantId = renantId;
    }

    /**
     * 获取租金管理ID
     *
     * @return rent_manage_id - 租金管理ID
     */
    public Integer getRentManageId() {
        return rentManageId;
    }

    /**
     * 设置租金管理ID
     *
     * @param rentManageId 租金管理ID
     */
    public void setRentManageId(Integer rentManageId) {
        this.rentManageId = rentManageId;
    }
}
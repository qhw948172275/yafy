package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_rent_area")
public class RentArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 套房ID
     */
    @Column(name = "area_id")
    private Integer areaId;

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
     * 获取套房ID
     *
     * @return area_id - 套房ID
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置套房ID
     *
     * @param areaId 套房ID
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public RentArea(){

    }

    public RentArea(Integer areaId,Integer rentManageId){
        this.areaId=areaId;
        this.rentManageId=rentManageId;
    }
}
package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_landlord_area")
public class LandlordArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 小区ID
     */
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 房东ID
     */
    @Column(name = "landlord_id")
    private Integer landlordId;

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
     * 获取房东ID
     *
     * @return landlord_id - 房东ID
     */
    public Integer getLandlordId() {
        return landlordId;
    }

    /**
     * 设置房东ID
     *
     * @param landlordId 房东ID
     */
    public void setLandlordId(Integer landlordId) {
        this.landlordId = landlordId;
    }

    public LandlordArea(){

    }
    public LandlordArea(Integer areaId,Integer landlordId){
        this.areaId=areaId;
        this.landlordId=landlordId;
    }
}
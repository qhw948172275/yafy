package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_area_agreement")
public class AreaAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 小区套房ID
     */
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 合同ID
     */
    @Column(name = "agreement_id")
    private Integer agreementId;

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
     * 获取小区套房ID
     *
     * @return area_id - 小区套房ID
     */
    public Integer getAreaId() {
        return areaId;
    }

    /**
     * 设置小区套房ID
     *
     * @param areaId 小区套房ID
     */
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取合同ID
     *
     * @return agreement_id - 合同ID
     */
    public Integer getAgreementId() {
        return agreementId;
    }

    /**
     * 设置合同ID
     *
     * @param agreementId 合同ID
     */
    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public AreaAgreement(){

    }

    public AreaAgreement(Integer areaId,Integer agreementId){
        this.agreementId=agreementId;
        this.areaId=areaId;
    }
}
package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_agreement_renant")
public class AgreementRenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 租客ID
     */
    @Column(name = "renant_id")
    private Integer renantId;

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
}
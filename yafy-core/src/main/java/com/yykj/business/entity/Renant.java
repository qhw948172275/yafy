package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "t_renant")
public class Renant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 租客电话
     */
    private String name;

    /**
     * 租客电话
     */
    private String phone;

    /**
     * 身份证号码
     */
    @Column(name = "id_crad")
    private Long idCrad;

    /**
     * 状态；0-启用，1-禁用
     */
    private Long status;

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
     * 获取租客电话
     *
     * @return name - 租客电话
     */
    public String getName() {
        return name;
    }

    /**
     * 设置租客电话
     *
     * @param name 租客电话
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取租客电话
     *
     * @return phone - 租客电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置租客电话
     *
     * @param phone 租客电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取身份证号码
     *
     * @return id_crad - 身份证号码
     */
    public Long getIdCrad() {
        return idCrad;
    }

    /**
     * 设置身份证号码
     *
     * @param idCrad 身份证号码
     */
    public void setIdCrad(Long idCrad) {
        this.idCrad = idCrad;
    }

    /**
     * 获取状态；0-启用，1-禁用
     *
     * @return status - 状态；0-启用，1-禁用
     */
    public Long getStatus() {
        return status;
    }

    /**
     * 设置状态；0-启用，1-禁用
     *
     * @param status 状态；0-启用，1-禁用
     */
    public void setStatus(Long status) {
        this.status = status;
    }
}
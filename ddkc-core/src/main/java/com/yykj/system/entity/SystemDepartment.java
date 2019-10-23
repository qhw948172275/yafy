package com.yykj.system.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 系统部门
 *
 * @author HF
 * @date 2018-2-4 14:17:11
 */
@Entity
@Table(name = "t_sys_department")
public class SystemDepartment implements Serializable {


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 部门名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 父亲部门id。根部门为1
     */
    @Column(name = "parentid")
    private Integer parentid;

    /**
     * 在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
     */
    @Column(name = "order")
    private Integer order;

    /**
     * 创建时间
     */
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 父parentid拼接
     */
    @Column(name = "level")
    private String level;

    /**
     * 0 删除，1 正常
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 获取id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取部门名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父亲部门id。根部门为1
     */
    public Integer getParentid() {
        return this.parentid;
    }

    /**
     * 设置父亲部门id。根部门为1
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
     */
    public Integer getOrder() {
        return this.order;
    }

    /**
     * 设置在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 获取创建时间
     */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
     * 设置创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    /**
     * 设置修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取父parentid拼接
     */
    public String getLevel() {
        return this.level;
    }

    /**
     * 设置父parentid拼接
     */
    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}


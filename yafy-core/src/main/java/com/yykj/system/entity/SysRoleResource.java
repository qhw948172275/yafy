package com.yykj.system.entity;

import javax.persistence.*;

@Table(name = "r_sys_role_resource")
public class SysRoleResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色组ID
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 资源功能ID
     */
    @Column(name = "resource_id")
    private Integer resourceId;

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
     * 获取角色组ID
     *
     * @return role_id - 角色组ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色组ID
     *
     * @param roleId 角色组ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取资源功能ID
     *
     * @return resource_id - 资源功能ID
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * 设置资源功能ID
     *
     * @param resourceId 资源功能ID
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
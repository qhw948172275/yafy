package com.yykj.system.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "t_sys_role")
public class SystemRole implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    private String description;

    private Integer status;

    private String creator;

    private Long createtime;

    private String lastUpdateCreator;

    private Long lastUpdateCreatetime;

    private Integer parentId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public String getLastUpdateCreator() {
        return lastUpdateCreator;
    }

    public void setLastUpdateCreator(String lastUpdateCreator) {
        this.lastUpdateCreator = lastUpdateCreator;
    }

    public Long getLastUpdateCreatetime() {
        return lastUpdateCreatetime;
    }

    public void setLastUpdateCreatetime(Long lastUpdateCreatetime) {
        this.lastUpdateCreatetime = lastUpdateCreatetime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
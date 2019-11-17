package com.yykj.system.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "t_sys_role")
public class SysRole {
    /**
     * 角色组的唯一标示
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色组名称
     */
    @ApiModelProperty(value = "角色组名称")
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色组描述
     */
    @ApiModelProperty(value = "角色组描述")
    private String description;

    /**
     * 角色状态,0默认为有效，1表示已被禁用不能使用。
     */
    @ApiModelProperty(value = "角色状态,0默认为有效，1表示已被禁用不能使用。")
    private Integer status;

    /**
     * 角色组创建人
     */
    @ApiModelProperty(value = "角色组创建人")
    private String creator;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Long createtime;

    /**
     * 此角色组上一次修改人
     */
    @ApiModelProperty(value = "此角色组上一次修改人")
    @Column(name = "last_update_creator")
    private String lastUpdateCreator;

    /**
     * 此角色上一次修改时间
     */
    @ApiModelProperty(value = "此角色上一次修改时间")
    @Column(name = "last_update_createtime")
    private Long lastUpdateCreatetime;

    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID")
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 加盟校ID
     */
    @ApiModelProperty(value = "加盟校ID")
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 当前角色层级目录
     */
    private String path;

    /**
     * 获取角色组的唯一标示
     *
     * @return id - 角色组的唯一标示
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色组的唯一标示
     *
     * @param id 角色组的唯一标示
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色组名称
     *
     * @return role_name - 角色组名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色组名称
     *
     * @param roleName 角色组名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取角色组描述
     *
     * @return description - 角色组描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色组描述
     *
     * @param description 角色组描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取角色状态,0默认为有效，1表示已被禁用不能使用。
     *
     * @return status - 角色状态,0默认为有效，1表示已被禁用不能使用。
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置角色状态,0默认为有效，1表示已被禁用不能使用。
     *
     * @param status 角色状态,0默认为有效，1表示已被禁用不能使用。
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取角色组创建人
     *
     * @return creator - 角色组创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置角色组创建人
     *
     * @param creator 角色组创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Long getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取此角色组上一次修改人
     *
     * @return last_update_creator - 此角色组上一次修改人
     */
    public String getLastUpdateCreator() {
        return lastUpdateCreator;
    }

    /**
     * 设置此角色组上一次修改人
     *
     * @param lastUpdateCreator 此角色组上一次修改人
     */
    public void setLastUpdateCreator(String lastUpdateCreator) {
        this.lastUpdateCreator = lastUpdateCreator == null ? null : lastUpdateCreator.trim();
    }

    /**
     * 获取此角色上一次修改时间
     *
     * @return last_update_createtime - 此角色上一次修改时间
     */
    public Long getLastUpdateCreatetime() {
        return lastUpdateCreatetime;
    }

    /**
     * 设置此角色上一次修改时间
     *
     * @param lastUpdateCreatetime 此角色上一次修改时间
     */
    public void setLastUpdateCreatetime(Long lastUpdateCreatetime) {
        this.lastUpdateCreatetime = lastUpdateCreatetime;
    }

    /**
     * @return parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取创建者ID
     *
     * @return creator_id - 创建者ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建者ID
     *
     * @param creatorId 创建者ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取加盟校ID
     *
     * @return school_id - 加盟校ID
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置加盟校ID
     *
     * @param schoolId 加盟校ID
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取当前角色层级目录
     *
     * @return path - 当前角色层级目录
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置当前角色层级目录
     *
     * @param path 当前角色层级目录
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}
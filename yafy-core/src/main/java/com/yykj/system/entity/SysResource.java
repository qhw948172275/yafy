package com.yykj.system.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "t_sys_resource")
public class SysResource {
    /**
     * 功能ID，功能在数据库的唯一标示。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    @Column(name = "resource_name")
    private String resourceName;

    /**
     * 功能请求路径
     */
    @ApiModelProperty(value = "功能请求路径")
    @Column(name = "resource_url")
    private String resourceUrl;

    /**
     * 是否是基础功能,0表示不是基础功能，1表示是基础功能。
     */
    @ApiModelProperty(value = "是否是基础功能,0表示不是基础功能，1表示是基础功能。")
    @Column(name = "is_basic")
    private Integer isBasic=1;

    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID")
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 所有父级id拼接
     */
    @ApiModelProperty(value = "所有父级id拼接")
    @Column(name = "parent_path")
    private String parentPath;

    /**
     * 当前节点的级别，例如例如根节点，第一级节点，，，，
     */
    private Integer level;

    /**
     * 功能描述
     */
    @ApiModelProperty(value = "功能描述")
    private String remark;

    /**
     * 状态0表示正常，1表示不正常
     */
    @ApiModelProperty(value = "状态0表示正常，1表示不正常")
    private Integer status;

    /**
     * 1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统
     */
    @Column(name = "resource_kind")
    private Integer resourceKind;

    /**
     * 资源类型 0 菜单 1按钮
     */
    @ApiModelProperty(value = "资源类型 0 菜单 1按钮")
    @Column(name = "resource_type")
    private Integer resourceType;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer seq;

    /**
     * 打开方式 ajax,iframe
     */
    @Column(name = "open_mode")
    private Integer openMode;

    /**
     * 打开状态
     */
    private Integer opened;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon2;

    /**
     * 获取功能ID，功能在数据库的唯一标示。
     *
     * @return id - 功能ID，功能在数据库的唯一标示。
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置功能ID，功能在数据库的唯一标示。
     *
     * @param id 功能ID，功能在数据库的唯一标示。
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取功能名称
     *
     * @return resource_name - 功能名称
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * 设置功能名称
     *
     * @param resourceName 功能名称
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * 获取功能请求路径
     *
     * @return resource_url - 功能请求路径
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * 设置功能请求路径
     *
     * @param resourceUrl 功能请求路径
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    /**
     * 获取是否是基础功能,0表示不是基础功能，1表示是基础功能。
     *
     * @return is_basic - 是否是基础功能,0表示不是基础功能，1表示是基础功能。
     */
    public Integer getIsBasic() {
        return isBasic;
    }

    /**
     * 设置是否是基础功能,0表示不是基础功能，1表示是基础功能。
     *
     * @param isBasic 是否是基础功能,0表示不是基础功能，1表示是基础功能。
     */
    public void setIsBasic(Integer isBasic) {
        this.isBasic = isBasic;
    }

    /**
     * 获取父节点ID
     *
     * @return parent_id - 父节点ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取所有父级id拼接
     *
     * @return parent_path - 所有父级id拼接
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * 设置所有父级id拼接
     *
     * @param parentPath 所有父级id拼接
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath == null ? null : parentPath.trim();
    }

    /**
     * 获取当前节点的级别，例如例如根节点，第一级节点，，，，
     *
     * @return level - 当前节点的级别，例如例如根节点，第一级节点，，，，
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置当前节点的级别，例如例如根节点，第一级节点，，，，
     *
     * @param level 当前节点的级别，例如例如根节点，第一级节点，，，，
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取功能描述
     *
     * @return remark - 功能描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置功能描述
     *
     * @param remark 功能描述
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取状态0表示正常，1表示不正常
     *
     * @return status - 状态0表示正常，1表示不正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态0表示正常，1表示不正常
     *
     * @param status 状态0表示正常，1表示不正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统
     *
     * @return resource_kind - 1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统
     */
    public Integer getResourceKind() {
        return resourceKind;
    }

    /**
     * 设置1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统
     *
     * @param resourceKind 1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统
     */
    public void setResourceKind(Integer resourceKind) {
        this.resourceKind = resourceKind;
    }

    /**
     * 获取资源类型 0 菜单 1按钮
     *
     * @return resource_type - 资源类型 0 菜单 1按钮
     */
    public Integer getResourceType() {
        return resourceType;
    }

    /**
     * 设置资源类型 0 菜单 1按钮
     *
     * @param resourceType 资源类型 0 菜单 1按钮
     */
    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * 获取排序
     *
     * @return seq - 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置排序
     *
     * @param seq 排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取打开方式 ajax,iframe
     *
     * @return open_mode - 打开方式 ajax,iframe
     */
    public Integer getOpenMode() {
        return openMode;
    }

    /**
     * 设置打开方式 ajax,iframe
     *
     * @param openMode 打开方式 ajax,iframe
     */
    public void setOpenMode(Integer openMode) {
        this.openMode = openMode;
    }

    /**
     * 获取打开状态
     *
     * @return opened - 打开状态
     */
    public Integer getOpened() {
        return opened;
    }

    /**
     * 设置打开状态
     *
     * @param opened 打开状态
     */
    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    /**
     * 获取图标
     *
     * @return icon2 - 图标
     */
    public String getIcon2() {
        return icon2;
    }

    /**
     * 设置图标
     *
     * @param icon2 图标
     */
    public void setIcon2(String icon2) {
        this.icon2 = icon2 == null ? null : icon2.trim();
    }
}
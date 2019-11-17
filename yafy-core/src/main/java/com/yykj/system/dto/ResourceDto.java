package com.yykj.system.dto;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ResourceDto implements Serializable {
    private Integer id;
    @ApiModelProperty(value = "菜单名称")
    private String resourceName;
    @ApiModelProperty(value = "菜单路径")
    private String resourceUrl;
    @ApiModelProperty(value = "是否是基础菜单：0-不是，1-是;默认1")
    private Integer isBasic;
    @ApiModelProperty(value = "父菜单")
    private Integer parentId;

    private String parentPath;

    private Integer level;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "0-启用，1-禁用")
    private Integer status;

    private Integer resourceKind; // 1商城 2平台 3直播 5erp 6系统
    @ApiModelProperty(value = "0-是菜单，1-按钮")
    private Integer resourceType;
    @ApiModelProperty(value = "排序：大的在前面")
    private Integer seq;

    private Integer openMode;

    private Integer opened;
    @ApiModelProperty(value = "图标地址")
    private String icon;
    @ApiModelProperty(value = "子菜单")
    private  List<ResourceDto> resourceDtos;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(Integer isBasic) {
        this.isBasic = isBasic;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResourceKind() {
        return resourceKind;
    }

    public void setResourceKind(Integer resourceKind) {
        this.resourceKind = resourceKind;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getOpenMode() {
        return openMode;
    }

    public void setOpenMode(Integer openMode) {
        this.openMode = openMode;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public List<ResourceDto> getResourceDtos() {
        return resourceDtos;
    }

    public void setResourceDtos(List<ResourceDto> resourceDtos) {
        this.resourceDtos = resourceDtos;
    }
}

package com.yykj.system.dto;

import com.yykj.system.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RoleResourceDto extends SysRole {
    /**
     * 资源列表IDs
     */
    @ApiModelProperty(value = "资源列表IDs")
    private List<Integer>  resourceIds;

    public List<Integer> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Integer> resourceIds) {
        this.resourceIds = resourceIds;
    }
}

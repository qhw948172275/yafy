package com.yykj.system.dto;

import com.yykj.system.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SysUserDto extends SysUser {

    @ApiModelProperty(value = "角色IDs")
    private List<Integer> roleIds;

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}


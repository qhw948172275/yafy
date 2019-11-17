package com.yykj.system.dao;

import com.yykj.system.commons.service.MyMapper;
import com.yykj.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {

    /**
     * 根据当前用户ID获取对应角色信息
     * @return
     */
    List<SysRole> selectByUserId(@Param("userId") Integer userId, @Param("status")Integer status);

    /**
     * create by: tf
     * description: 根据当前用户ID获取对应角色组
     * create time: 2019/11/1 13:40
     */
    String selectRoleNamesByUserId(@Param("userId") Integer userId);
}
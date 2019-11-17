package com.yykj.system.dao;

import com.yykj.system.commons.service.MyMapper;
import com.yykj.system.entity.SysResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysResourceMapper extends MyMapper<SysResource> {
    /**
     * 根据用户ID查询权限菜单
     * @param userId
     * @return
     */
    List<SysResource> selectByUserId(@Param("userId") Integer userId);
}
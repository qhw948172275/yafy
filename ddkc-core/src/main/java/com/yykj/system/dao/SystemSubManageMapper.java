package com.yykj.system.dao;

import java.util.List;
import java.util.Map;

import com.yykj.system.entity.SystemSubManage;

import tk.mybatis.mapper.common.Mapper;

/**
 * 子项目模块管理
 */
public interface SystemSubManageMapper extends Mapper<SystemSubManage> {

    List<Map<String ,Object>> selectAllTree();

}

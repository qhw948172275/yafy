package com.yykj.system.dao;

import com.yykj.system.entity.SystemDepartment;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface SystemDepartmentMapper extends Mapper<SystemDepartment>,InsertListMapper<SystemDepartment> {
}
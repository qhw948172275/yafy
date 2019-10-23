package com.yykj.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yykj.system.entity.SystemResource;

import tk.mybatis.mapper.common.Mapper;

public interface SystemResourceMapper extends Mapper<SystemResource>{
	/**
	 * 根据角色ID查询角色所能管理权限
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午4:55:15
	 * @param rid
	 * @return
	 */
	public List<SystemResource> getResourceListByRid(@Param("rid") Integer rid);
	/**
	 * 根据角色ID查询角色所能管理权限
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午4:55:15
	 * @param rid
	 * @return
	 */
	public List<SystemResource> getResourceListByRidIn(@Param("rids") List<Integer> list);

}
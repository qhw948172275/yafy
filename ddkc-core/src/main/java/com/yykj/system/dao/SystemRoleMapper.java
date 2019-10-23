package com.yykj.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;

import tk.mybatis.mapper.common.Mapper;

public interface SystemRoleMapper extends Mapper<SystemRole>{

	List<SystemResource> selectResourceListByRoleIdList(@Param("list") List<Integer> list);
	
	/**
	 * 根据用户ID查询用户所属角色
	 * (一个用户可能有多个角色)
	 * 
	 * @author kimi
	 * @dateTime 2012-3-23 上午11:27:00
	 * @param uid
	 * @return
	 */
	List<SystemRole> getRoleByUserId(@Param("uid") int uid);

    List<SystemRole> selectAllByKeyword(@Param("keyword") String keyword);
}
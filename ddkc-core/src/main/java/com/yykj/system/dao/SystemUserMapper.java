package com.yykj.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yykj.system.entity.SystemUser;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface SystemUserMapper extends Mapper<SystemUser>,InsertListMapper<SystemUser>{
	/**
	 * 查询单个角色下的用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-4-19 下午1:28:38
	 * @param rid
	 * @return
	 */
	List<SystemUser> getUserEntityListInRid(@Param("roleid") int rid);

	/**
	 * 查询多个角色下的用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-4-19 下午1:28:41
	 * @param rid
	 * @return
	 */
	List<SystemUser> getUsersListInRids(@Param("userName") String userName, @Param("roleList") List<Integer> rid,
			@Param("limitFrom") Integer limitFrom, @Param("limitTo") Integer limitTo);

	/**
	 * 查询多个角色下的用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-4-19 下午1:28:41
	 * @param rid
	 * @return
	 */
	int getUserCountInRids(@Param("userName") String userName, @Param("roleList") List<Integer> rid);

	/**
	 * 查询不属于当前角色的用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-4-19 下午1:28:45
	 * @param rid
	 * @return
	 */
	List<SystemUser> getUserEntityListNotInRid(@Param("roleid") int rid);

	/**
	 * 查询非超级用户列表
	 */
	List<SystemUser> getUserListByNoAdmin(@Param("name") String name);
}
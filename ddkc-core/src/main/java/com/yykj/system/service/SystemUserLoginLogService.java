package com.yykj.system.service;

import java.util.List;

import com.yykj.system.commons.PageTools;
import com.yykj.system.entity.SystemLoginLog;
import com.yykj.system.entity.SystemUser;

/**
 * 用户登录日志历史记录
 * 
 * @author kimi
 * @dateTime 2013-9-30 上午11:25:26
 */
public interface SystemUserLoginLogService {

	/**
	 * 添加用户登录日志
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:28:51
	 * @param userLoginLog 用户登录日志实例
	 * @param user 当前登录对象
	 * @param failRemark 用户登录失败原因
	 * @return
	 * @throws Exception
	 */
	boolean addUserLoginLog(SystemUser user, String failRemark) throws Exception;

	/**
	 * 查询用户登录日志总行数
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:28:19
	 * @param userLoginLog 检索条件
	 * @return
	 * @throws Exception
	 */
	long selectCountByUserLoginLog(SystemLoginLog userLoginLog,String startTime,String endTime) throws Exception;

	/**
	 * 查询用户的登录日志
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:28:09
	 * @param userLoginLog 检索条件
	 * @return
	 * @throws Exception
	 */
	List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog,String startTime,String endTime) throws Exception;

	/**
	 * 查询用户的登录日志，分页
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:27:53
	 * @param userLoginLog 检索条件
	 * @param page 分页
	 * @return
	 * @throws Exception
	 */
	List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog,String startTime,String endTime, PageTools page) throws Exception;

	/**
	 * 查询用户的登录日志，分页，排序
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:27:35
	 * @param userLoginLog 检索条件
	 * @param orderBy 排序
	 * @param page 分页
	 * @return
	 * @throws Exception
	 */
	List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog,String startTime,String endTime, String[] orderBy, PageTools page) throws Exception;

	/**
	 * 删除用户的登录日志
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:27:22
	 * @param uid 用户ID
	 * @return
	 * @throws Exception
	 */
	boolean deleUserLoginLog(int uid) throws Exception;

	/**
	 * 批量删除多个用户的登录日志
	 * 
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:27:07
	 * @param uid 用户ID集合
	 * @return
	 * @throws Exception
	 */
	boolean deleUserLoginLog(Integer[] uid) throws Exception;

}

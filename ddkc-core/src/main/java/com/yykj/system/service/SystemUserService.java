package com.yykj.system.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.yykj.system.commons.BaseService;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemUser;
import com.yykj.system.response.SystemUserExt;

/**
 * 用户管理接口
 * 
 * @author kimi
 * @dateTime 2012-3-9 下午3:28:54
 */
public interface SystemUserService extends BaseService<SystemUser, Serializable> {

	// UserExt getUserByName(String name);
	/**
	 * 指定字段的登录方式
	* @author chenbiao
	* @date 2017年2月20日 下午9:54:21
	* 参数说明 
	* 
	* @param name
	* @param fieldName
	* @return
	 */
	SystemUserExt getUserExtByName(String name, String fieldName);
	/**
	 * 多种方式登录
	* @author chenbiao
	* @date 2017年2月20日 下午9:54:04
	* 参数说明 
	* 
	* @param username
	* @return
	 */
	SystemUserExt getUserExtByLoginName(String username);

	SystemUser getUserByEmail(String email);

	SystemUser getUserByName(String userName);

	/**
	 * 创建用户，关联角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:25:56
	 * @param user
	 * @param roles
	 * @return
	 */
	boolean addUser(SystemUser user, List<Integer> roles);

	/**
	 * 批量创建用户
	 *
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:25:56
	 * @param users
	 * @return
	 */
	public Object addUser(List<SystemUser> users);

	/**
	 * 验证用户名是否已被注册
	 * 
	 * @author kimi
	 * @dateTime 2012-3-14 上午11:47:59
	 * @param username
	 * @return
	 */
	boolean validateUserNameExists(String username);

	/**
	 * 验证电子邮件是否被注册
	 * 
	 * @author kimi
	 * @dateTime 2012-3-14 上午11:47:59
	 * @param email
	 * @return
	 */
	boolean validateEmailExists(String email);
	boolean validateEmailExists(String email,int userId);
	/**
	 * 验证手机号是否存在
	* @author chenbiao
	* @date 2017年2月20日 上午11:55:51
	* 参数说明 
	* 
	* @param phone
	* @return
	 */
	boolean validatePhoneExists(String phone);
	boolean validatePhoneExists(String phone,int userId);

	/**
	 * 删除用户
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:28:23
	 * @param uid
	 * @return
	 */
	int deleUser(int uid);

	/**
	 * 修改用户，修改角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:28:21
	 * @param user
	 * @param rid
	 * @return
	 */
	int updateUser(SystemUser user, List<Integer> rid);

	/**
	 * 修改用户
	 * <p>
	 * 多用在用户登录成功之后，修改用户登录次数，以及登录IP等信息
	 * </p>
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:28:21
	 * @param user
	 * @return
	 */
	boolean updateUser(SystemUser user);

	/**
	 * 根据条件获取用户列表
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:27:48
	 * @return
	 */
	MyPageInfo<SystemUser> getUserListByName(String name, PageTools page);

	/**
	 * 查询非超级管理员的用户
	 * @param name
	 * @param pageTools
	 * @return
	 */
	MyPageInfo<SystemUser> getUserListByNoAdmin(String name, PageTools pageTools);

	/**
	 * 根据条件获取用户列表
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:27:48
	 * @return
	 */
	List<SystemUser> getUserListByName(String name, List<SystemRole> roles, PageTools page);

	/**
	 * 根据条件获取用户总数
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:27:48
	 * @return
	 */
	long getUserCountsByName(String name);

	/**
	 * 根据条件获取用户总数
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:27:48
	 * @return
	 */
	long getUserCountsByName(String name, List<SystemRole> roles);

	/**
	 * @author kimi
	 * @dateTime 2012-3-14 下午12:17:14
	 * @param user
	 * @return
	 */
	@Deprecated
	boolean updateUserStatus(SystemUser user);

	/**
	 * @author kimi
	 * @dateTime 2012-3-14 下午1:56:51
	 * @param id
	 * @return
	 */
	SystemUser getUserById(int id);

	/**
	 * @author kimi
	 * @dateTime 2012-3-21 下午5:53:51
	 * @param rid
	 * @return 根据角色获取用户列表
	 */
	List<SystemUser> getUserListInRoleId(int rid);

	/**
	 * @author kimi
	 * @dateTime 2012-3-21 下午5:54:11
	 * @param rid
	 * @return 获取不属于角色ID的用户列表
	 */
	List<SystemUser> getUserListNotInRoleId(int rid);

	/**
	 * 获取当前登录用户所能管理的子集用户
	 * 
	 * @author kimi
	 * @dateTime 2013-8-6 上午11:24:27
	 * @return
	 */
	List<SystemUser> getUserListByParentEqualsCurrentUser();
	
	/**
	 * 获取所有系统用户
	 *
	 *
	 * @author cym   
	 * @date 2018年1月30日 上午11:55:21
	 */
	List<SystemUser> getAll();

	/**
	 * 根据手机号获取系统用户
	 * @param mobiles
	 * @return
	 */
	List<SystemUser> getUserListByMobile(Collection<String> mobiles);

}

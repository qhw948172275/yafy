package com.yykj.system.service;

import java.io.Serializable;
import java.util.List;

import com.yykj.system.commons.BaseService;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemRoleResource;
import com.yykj.system.response.SystemRoleExt;

/**
 * 角色管理接口
 * 
 * @author kimi
 * @dateTime 2012-3-19 下午12:00:11
 */
public interface SystemRoleService extends BaseService<SystemRole, Serializable> {

	/**
	 * 添加角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 上午11:59:18
	 * @param role 新增角色对象
	 * @return
	 */
	boolean addRole(SystemRole role, String[] resourceId);

	/**
	 * 根据ID，获取角色对象
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午5:28:09
	 * @param id
	 * @return
	 */
	SystemRole getRoleById(int id);

	/**
	 * 修改角色对象，更改角色的权限
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 上午11:58:59
	 * @param role 修改之后的角色对象
	 * @return 修改角色
	 */
	boolean updateRole(SystemRole role, String[] resourceId);

	/**
	 * 删除角色，包括删除这个角色与资源权限的关联关系
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 上午11:58:35
	 *           删除角色
	 * @param rid 角色ID
	 * @return 删除结果
	 */
	boolean deleRoleById(int rid);

	/**
	 * 验证角色名称是否存在
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午4:21:12
	 * @param roleName
	 * @return
	 */
	boolean validateRoleName(String roleName);

	/**
	 * 修改角色状态
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午5:18:49
	 * @param rid
	 * @param status
	 * @return
	 */
	boolean changeStatusByRoleId(int rid, int status);

	/**
	 * 获取角色权限
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午7:21:21
	 * @param rre
	 * @return
	 */
	List<SystemRoleResource> getRoleResourceList(int roleId);

	/**
	 * 添加用户到角色组
	 * 
	 * @author kimi
	 * @dateTime 2012-3-22 下午5:39:25
	 * @param uArr
	 * @param rid
	 * @return
	 */
	boolean addUserByRid(String[] uArr, int rid);

	/**
	 * 将用户从角色组中移除
	 * 
	 * @author kimi
	 * @dateTime 2012-3-22 下午5:39:28
	 * @param uArr
	 * @param rid
	 * @return
	 */
	boolean removeUserByRid(String[] uArr, int rid);

	/**
	 * 根据用户ID，获取用户所属角色扩展的详细信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:57
	 * @param userId
	 * @return
	 */
	List<SystemRoleExt> getRoleExtByUserId(int userId);

	/**
	 * 根据用户ID，获取用户所属角色的详细信息
	 * 
	 * @author kimi
	 * @dateTime 2013-4-17 下午2:09:45
	 * @param userId 用户ID
	 * @return
	 */
	List<SystemRole> getRoleListByUid(int userId);
	
	/**
	 * 根据UID查询当前用户所对应的角色信息，不递归循环查询角色下的角色
	 * 
	 * @author kimi
	 * @dateTime 2013-4-22 下午3:23:44
	 * @param uid
	 * @return
	 */
	List<SystemRole> getCurrentRolesByUid(int uid);

	/**
	 * 根据当前用户所能管理管理到的角色列表
	 * 
	 * @author kimi
	 * @dateTime 2013-4-17 下午2:09:45
	 * @param userId 用户ID
	 * @return
	 */
	//List<SystemRole> getRoleListByCurrentUser();

	/**
	 * 根据当前用户所能管理管理到的角色列表
	 * 
	 * @author kimi
	 * @dateTime 2013-4-17 下午2:09:45
	 * @param userId 用户ID
	 * @return
	 */
	//List<SystemRole> getRoleListByUid(boolean isLimitCurrentRole);

	List<SystemRole>  getRoleListByUid(int userId, boolean isLimitCurrentRoles);

	List<SystemRole> getAllRole();

    /**
     * 根据角色创建着名称查询角色
     * @param userName
     * @return
     */
    List<SystemRole>  getRoleListByCreator(String userName);

	/**
	 * 查询超级管理员
	 * @return
	 */
	SystemRole getAdmin();

    /**
     * 是否是超级用户
     * @param roles
     * @return
     */
    boolean isAdmin(List<SystemRole> roles);

	/**
	 * 判断是否包含超级管理员
	 */
	boolean isContainAdminIds(List<Integer> roleIds);

    List<SystemRole> selectAllByKeyword(String keyword);
}

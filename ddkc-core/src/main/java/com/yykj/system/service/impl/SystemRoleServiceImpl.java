package com.yykj.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.yykj.system.commons.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yykj.system.commons.ListUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseService;
import com.yykj.system.dao.SystemLoginLogMapper;
import com.yykj.system.dao.SystemResourceMapper;
import com.yykj.system.dao.SystemRoleMapper;
import com.yykj.system.dao.SystemRoleResourceMapper;
import com.yykj.system.dao.SystemRoleUserMapper;
import com.yykj.system.dao.SystemUserMapper;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemRoleResource;
import com.yykj.system.entity.SystemRoleUser;
import com.yykj.system.response.SystemRoleExt;
import com.yykj.system.service.SystemRoleService;

import tk.mybatis.mapper.entity.Example;

/**
 * 角色管理接口实现
 * 
 * @author kimi
 * @dateTime 2012-3-19 下午12:01:04
 */
@Service
public class SystemRoleServiceImpl extends AbstractBaseService<SystemRole, Serializable> implements SystemRoleService {


	@Autowired
	protected SystemUserMapper userMapper;// 用户映射接口

	@Autowired
	protected SystemResourceMapper resourceMapper;// 资源映射接口

	@Autowired
	protected SystemRoleUserMapper roleUserMapper;// 用户角色关联关系映射接口
	/**
	 * 角色映射接口
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:02:38
	 */
	@Autowired
	protected SystemRoleMapper roleMapper;

	/**
	 * 角色与资源的关联关系映射接口
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午3:45:52
	 */
	@Autowired
	protected SystemRoleResourceMapper roleresourceMapper;

	@Autowired
	protected SystemLoginLogMapper userLoginLogMapper;
	
	/**
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:02:42
	 */
	final Logger logger = LoggerFactory.getLogger(SystemRoleServiceImpl.class);

	/**
	 * 角色列表
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:01:17
	 *           (non-JSDoc)
	 * @see com.quest.core.service.RoleManager#getRoleList(com.quest.account.model.ext.UserExt,
	 *      me.gall.quest.service.utils.PageTools)
	 */
	/*public List<SystemRole> getRoleList(String roleName, PageTools page) {
		Example roleExample = new Example(SystemRole.class);
		if (!UserUtils.getSubject().hasRole(SystemConstants.ADMINISTRATOR)) {// 判断是否是administrator角色
			return getRoleListByUid(UserUtils.getCurrentUserInfo().getUser().getId(), true);
		}
		return roleMapper.selectByExample(roleExample);
	}*/

	/**
	 * 添加角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:07:39
	 *           (non-JSDoc)
	 */
	public boolean addRole(SystemRole role, String[] resourceId) {
		boolean flag = false;
		if ((this.roleMapper.insertSelective(role)) > 0) {
			if (null != resourceId && resourceId.length > 0) {
				for (int j = 0; j < resourceId.length; j++) {
					SystemRoleResource rr = new SystemRoleResource();
					rr.setRoleId(role.getId());
					rr.setResourceId(Integer.parseInt(resourceId[j]));
					if (this.roleresourceMapper.insert(rr) > 0) {
						flag = true;
					} else {
						return false;
					}
				}
			}
			flag = true;
			
		}
		return flag;
	}

	/**
	 * 修改角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:07:26
	 *           (non-JSDoc)
	 */
	public boolean updateRole(SystemRole role, String[] resourceId) {
		boolean flag = false;
		if (this.roleMapper.updateByPrimaryKeySelective(role) > 0) {
			if (null != resourceId && resourceId.length > 0) {
				Example rre = new Example(SystemRoleResource.class);
				rre.createCriteria().andEqualTo("roleId", role.getId());
				this.roleresourceMapper.deleteByExample(rre);
				for (int j = 0; j < resourceId.length; j++) {
					SystemRoleResource rr = new SystemRoleResource();
					rr.setRoleId(role.getId());
					rr.setResourceId(Integer.parseInt(resourceId[j]));
					if (this.roleresourceMapper.insert(rr) > 0) {
						flag = true;
					} else {
						return false;
					}
				}
			}
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除角色
	 * 
	 * @author kimi
	 * @dateTime 2012-3-19 下午12:07:16
	 *           (non-JSDoc)
	 */
	public boolean deleRoleById(int rid) {
		if (this.roleMapper.deleteByPrimaryKey(rid) > 0) {
			Example rre = new Example(SystemRoleResource.class);
			rre.createCriteria().andEqualTo("roleId",rid);
			this.roleresourceMapper.deleteByExample(rre);// 删除角色的同时，删除掉当前角色与权限的关联关系
			Example roleUserExample = new Example(SystemRoleUser.class);
			roleUserExample.createCriteria().andEqualTo("roleId", rid);
			this.roleUserMapper.deleteByExample(roleUserExample);// 删除角色的同时，删除掉当前角色与用户之间的关联关系
			return true;
		}
		return false;
	}

	/**
	 * 验证角色信息是否存在
	 * 
	 * @author kimi
	 * @dateTime 2012-3-20 下午4:58:47
	 *           (non-JSDoc)
	 */
	public boolean validateRoleName(String roleName) {
		Example rrb = new Example(SystemRole.class);
		rrb.createCriteria().andEqualTo("roleName", roleName);
		List<SystemRole> rlist = this.roleMapper.selectByExample(rrb);
		if (null != rlist && rlist.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 修改角色的状态
	 * 
	 * @author kimi
	 * @dateTime 2012-6-5 下午3:17:42
	 *           (non-JSDoc)
	 */
	public boolean changeStatusByRoleId(int rid, int status) {
		SystemRole re = new SystemRole();
		re.setId(rid);
		re.setStatus(status);
		return this.roleMapper.updateByPrimaryKeySelective(re) > 0 ? true : false;
	}

	/**
	 * 获取角色信息
	 * 
	 * @author kimi
	 * @dateTime 2012-6-5 下午3:17:46
	 *           (non-JSDoc)
	 */
	public SystemRole getRoleById(int id) {
		return this.roleMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据角色ID，获取角色对应的权限
	 * 
	 * @author kimi
	 * @dateTime 2012-6-5 下午3:17:49
	 *           (non-JSDoc)
	 */
	public List<SystemRoleResource> getRoleResourceList(int roleId) {
		Example rreb = new Example(SystemRoleResource.class);
		rreb.createCriteria().andEqualTo("roleId", roleId);
		return this.roleresourceMapper.selectByExample(rreb);
	}

	/**
	 * 将用户添加到RID角色组当中
	 * 
	 * @author kimi
	 * @dateTime 2012-3-22 下午5:45:08
	 *           (non-JSDoc)
	 */
	public boolean addUserByRid(String[] uArr, int rid) {
		boolean flag = false;
		for (int i = 0; i < uArr.length; i++) {
			SystemRoleUser ru = new SystemRoleUser();
			ru.setRoleId(rid);
			ru.setUid(Integer.parseInt(uArr[i]));
			if (this.roleUserMapper.insert(ru) > 0) {
				flag = true;
			} else {
				return false;
			}
		}
		return flag;
	}

	/**
	 * 将用户移除角色为RID的角色组
	 * 
	 * @author kimi
	 * @dateTime 2012-3-22 下午5:44:34
	 *           (non-JSDoc)
	 */
	public boolean removeUserByRid(String[] uArr, int rid) {
		boolean flag = false;
		for (int i = 0; i < uArr.length; i++) {
			Example rue = new Example(SystemRoleUser.class);
			rue.createCriteria().andEqualTo("roleId", rid).andEqualTo("uid", Integer.parseInt(uArr[i]));
			if (this.roleUserMapper.deleteByExample(rue) > 0) {
				flag = true;
			} else {
				return false;
			}
		}
		return flag;
	}

	/**
	 * @author kimi
	 * @dateTime 2013-4-18 下午3:40:08
	 *           (non-JSDoc)
	 * @see com.quest.core.service.RoleManager#getRoleListByUid()
	 */
	/*public List<SystemRole> getRoleListByCurrentUser() {
		if (UserUtils.isAdministrator()) {
			return getRoleListByUid(UserUtils.getCurrentUserInfo().getUser().getId(), false);
		}
		return getRoleListByUid(UserUtils.getCurrentUserInfo().getUser().getId(), true);
	}*/

	/**
	 * 根据用户ID获取用户所在角色信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:11
	 */
	/*public List<SystemRole> getRoleListByUid(boolean isLimitCurrentRoles) {
		return getRoleListByUid(UserUtils.getCurrentUserInfo().getUser().getId(), isLimitCurrentRoles);
	}*/

	/**
	 * 根据用户ID获取用户所在角色信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:11
	 */
	public List<SystemRole> getRoleListByUid(int uid) {
		return getRoleListByUid(uid, true);
	}

	/**
	 * 根据用户ID获取用户所在角色信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:11
	 */
	public List<SystemRole> getCurrentRolesByUid(int uid) {
		return roleMapper.getRoleByUserId(uid);
	}

	/**
	 * 根据用户ID获取用户所在角色信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:11
	 */
	public List<SystemRole> getRoleListByUid(int userId, boolean isLimitCurrentRoles) {
		List<SystemRole> list = roleMapper.getRoleByUserId(userId);
		if (null != list && list.size() > 0) {
			List<SystemRole> temps;
			List<Integer> rids = ListUtils.getIdsByList(SystemConstants.ID, list);
			Example re;
			boolean flag = false;
			List<SystemRole> resultList = new ArrayList<SystemRole>();
			// 如果是超级管理员，则加载超级管理员角色
			if (!isLimitCurrentRoles) {
				resultList.addAll(list);
			}
			for (int i = 0; i > -1; i++) {
				re = new Example(SystemRole.class);
				re.createCriteria().andIn("parentId", rids);
				temps = this.roleMapper.selectByExample(re);
				if (null != temps && temps.size() > 0) {
					rids = ListUtils.getIdsByList(SystemConstants.ID, temps);
					// list.addAll(temps);
					if (resultList.size() > 0) {
						// 不直接添加集合到角色集合当中，是因为可能一个用户存在多个角色并且这多个角色存在继承关系，在进行迭代的时候必须剔除掉
						for (SystemRole role : temps) {
							for (int n = 0; n < resultList.size(); n++) {
								if (resultList.get(n).getId().intValue() == role.getId()) {
									flag = true;
									break;
								}
							}
							if (!flag) {
								resultList.add(role);
							}
							flag = false;
						}
					} else {
						resultList.addAll(temps);
					}
				} else {
					break;
				}
			}
			return resultList;
		}
		return null;
	}

	/**
	 * 根据用户ID获取用户所在角色信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-7 下午5:09:11
	 *           (non-JSDoc)
	 */
	public List<SystemRoleExt> getRoleExtByUserId(int userId) {
		List<SystemRole> roleList = getRoleListByUid(userId, false);
		if (null != roleList && roleList.size() > 0) {
			List<SystemRoleExt> roleExtList = new ArrayList<SystemRoleExt>();
			SystemRoleExt roleExt;
			for (SystemRole role : roleList) {
				roleExt = new SystemRoleExt();
				roleExt.setId(role.getId());
				roleExt.setRoleName(role.getRoleName());
				roleExt.setDescription(role.getDescription());
				roleExt.setPermissions(new HashSet<SystemResource>(integrationResource(resourceMapper.getResourceListByRid(role.getId()))));
				roleExtList.add(roleExt);
			}
			return roleExtList;
		}
		return null;
	}

	/**
	 * 整合请求资源
	 * 
	 * @author kimi
	 * @dateTime 2012-7-9 下午7:58:47
	 * @param res
	 * @return
	 */
	private List<SystemResource> integrationResource(List<SystemResource> res) {
		String str;
		final String replace_before = "\\$\\{id\\}";
		final String replace_later = "*";
		for (SystemResource re : res) {
			str = re.getResourceUrl().replaceAll(replace_before, replace_later);
			re.setResourceUrl(str);
		}
		return res;
	}
	
	@Override
	public List<SystemRole> getAllRole() {
		// TODO Auto-generated method stub
		return roleMapper.selectAll();
	}

	@Override
	public List<SystemRole> getRoleListByCreator(String userName) {
		Example example = new Example(SystemRole.class);
		example.createCriteria().andEqualTo("creator",userName);
		return roleMapper.selectByExample(example);
	}

	@Override
	public SystemRole getAdmin() {
		SystemRole role = new SystemRole();
		role.setRoleName(SystemConstants.ADMINISTRATOR);
		return roleMapper.selectOne(role);
	}

	@Override
	public boolean isAdmin(List<SystemRole> roles) throws RuntimeException{
		AtomicBoolean isAdmin = new AtomicBoolean(false);
		if (roles != null && !roles.isEmpty()) {
			roles.forEach(n -> {
				if (n.getRoleName().equals(SystemConstants.ADMINISTRATOR)) {
					isAdmin.set(true);
				}
			});
		}
		return isAdmin.get();
	}

	@Override
	public boolean isContainAdminIds(List<Integer> roleIds) {
		boolean is = false;
		SystemRole adminRole = null;
		try {
			adminRole = getAdmin();
		} catch (Exception e) {
			e.printStackTrace();
			is = true;
			return is;
		}
		if (adminRole == null) {
			return is;
		}
		Integer adminRoleId = adminRole.getId();
		if (roleIds != null && !roleIds.isEmpty()) {
			for (Integer roleId : roleIds) {
				if (adminRoleId.equals(roleId)) {
					is = true;
					break;
				}
			}
		}
		return is;
	}

	@Override
	public List<SystemRole> selectAllByKeyword(String keyword) {
	    if(StringUtils.isNotEmpty(keyword)){
	    	keyword="%"+keyword+"%";
		}else{
			keyword=null;
		}
		return roleMapper.selectAllByKeyword(keyword);
	}
}

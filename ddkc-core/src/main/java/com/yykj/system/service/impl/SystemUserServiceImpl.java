package com.yykj.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yykj.system.commons.ListUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseService;
import com.yykj.system.dao.SystemResourceMapper;
import com.yykj.system.dao.SystemRoleMapper;
import com.yykj.system.dao.SystemRoleUserMapper;
import com.yykj.system.dao.SystemUserMapper;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemRoleUser;
import com.yykj.system.entity.SystemUser;
import com.yykj.system.response.SystemUserExt;
import com.yykj.system.service.SystemRoleService;
import com.yykj.system.service.SystemUserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 用户管理实现
 *
 * @author kimi
 * @dateTime 2012-3-9 下午3:29:46
 */
@Service
public class SystemUserServiceImpl extends AbstractBaseService<SystemUser, Serializable> implements SystemUserService {

    static Logger logger = LoggerFactory.getLogger(SystemUserServiceImpl.class);

    @Autowired
    private SystemRoleMapper roleMapper;

    @Autowired
    protected SystemUserMapper userMapper;// 用户映射接口

    @Autowired
    protected SystemResourceMapper resourceMapper;// 资源映射接口

    @Autowired
    protected SystemRoleUserMapper roleUserMapper;// 用户角色关联关系映射接口

    @Autowired
    protected SystemRoleService systemRoleService;// 用户角色关联关系映射接口

    /**
     * 添加用户
     *
     * @author kimi
     * @dateTime 2012-3-9 下午3:29:59
     * (non-JSDoc)
     */
    public boolean addUser(SystemUser user) {
        return this.userMapper.insert(user) > 0 ? true : false;
    }

    /**
     * 删除用户
     *
     * @author kimi
     * @dateTime 2012-3-9 下午3:30:07
     * (non-JSDoc)
     */
    public int deleUser(int uid) {
        int result = -1;
        if ((result = this.userMapper.deleteByPrimaryKey(uid)) > 0) {
            Example roleUserExample = new Example(SystemRoleUser.class);
            roleUserExample.createCriteria().andEqualTo("uid", uid);
            roleUserMapper.deleteByExample(roleUserExample);
//			userLog.info("删除用户成功。用户ID为：{},操作人：{}", uid, UserUtils.getCurrentUserInfo().getUser().getRealName());
        }
        return result;
    }

    /**
     * 更新用户信息成功
     *
     * @author kimi
     * @dateTime 2013-9-29 下午2:17:51
     * (non-JSDoc)
     */
    public boolean updateUser(SystemUser user) {
        if (null != user) {
            if (this.userMapper.updateByPrimaryKeySelective(user) > 0) {
//				userLog.info("更新用户信息成功。用户ID为：{},操作人：{}", user.getId(), UserUtils.getCurrentUserInfo().getUser().getRealName());
                return true;
            }
//			userLog.info("更新用户信息失败。用户ID为：{},操作人：{}", user.getId(), UserUtils.getCurrentUserInfo().getUser().getRealName());
        }
        return false;
    }

    /**
     * 更改用户状态
     *
     * @author kimi
     * @dateTime 2012-3-14 下午12:17:50
     * (non-JSDoc)
     */
    public boolean updateUserStatus(SystemUser user) {
        if (this.userMapper.updateByPrimaryKeySelective(user) > 0) {
//			userLog.info("修改用户状态成功。用户ID为：{},操作人：{}", user.getId(), UserUtils.getCurrentUserInfo().getUser().getRealName());
            return true;
        }
//		userLog.info("修改用户状态失败。用户ID为：{},操作人：{}", user.getId(), UserUtils.getCurrentUserInfo().getUser().getRealName());
        return false;
    }

    /**
     * 获取用户列表
     *
     * @param name 用户名
     * @param page 分页
     * @return
     * @author kimi
     * @dateTime 2012-3-9 下午3:30:32
     */
    public MyPageInfo<SystemUser> getUserListByName(String name, PageTools pageTools) {
        Page<?> page = PageHelper.startPage(pageTools.getCurrentPage() + 1, pageTools.getPageSize());

        Example userEntityExample = new Example(SystemUser.class);
        Criteria cri = userEntityExample.createCriteria();
        if (null != name && !"".equals(name)) {
            cri.andCondition("name like '%" + name + "%' or phone like '%" + name + "%' or real_name like '%" + name + "%'");
        }

        userEntityExample.setOrderByClause(" id desc ");
        List<SystemUser> list = userMapper.selectByExample(userEntityExample);
        pageTools.setRecordCount(page.getTotal());

        return new MyPageInfo<>(list, pageTools);
    }

    /**
     * 查询非超级用户列表
     */
    public MyPageInfo<SystemUser> getUserListByNoAdmin(String name, PageTools pageTools) {
        Page<SystemUser> page = PageHelper.startPage(pageTools.getCurrentPage() + 1, pageTools.getPageSize());
        List<SystemUser> list = userMapper.getUserListByNoAdmin(name);
        pageTools.setRecordCount(page.getTotal());
        return new MyPageInfo<>(list, pageTools);
    }

    /**
     * 获取用户列表
     *
     * @param name  用户名
     * @param roles 角色集合
     * @param page  分页
     * @return
     * @author kimi
     * @dateTime 2012-3-9 下午3:30:32
     */
    @Deprecated
    public List<SystemUser> getUserListByName(String name, List<SystemRole> roles, PageTools page) {
		/*if (!UserUtils.getSubject().hasRole(SystemConstants.ADMINISTRATOR)) {
			if (null == roles || roles.size() == 0) {
				return null;
			}
			List<Integer> rids = ListUtils.getIdsByList(SystemConstants.ID, roles);
			if (null != name && !"".equals(name)) {
				return userMapper.getUsersListInRids(null, rids, page.getLimitFrom(), page.getLimitTo());
			} else {
				return userMapper.getUsersListInRids(null, rids, page.getLimitFrom(), page.getLimitTo());
			}
		} else {
			Example userEntityExample = new Example(SystemUser.class);
			Criteria cri = userEntityExample.createCriteria();
			if (null != name && !"".equals(name)) {
				cri.andLike("name", SystemConstants.PERCENT + name + SystemConstants.PERCENT);
			}
			userEntityExample.setOrderByClause(" id asc limit " + page.getLimitFrom() + "," + page.getLimitTo());
			return this.userMapper.selectByExample(userEntityExample);
		}*/
        return null;
    }

    /**
     * 根据ID获取用户
     *
     * @author kimi
     * @dateTime 2012-3-21 下午5:57:20
     * (non-JSDoc)
     */
    public SystemUser getUserById(int id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取属于rid的用户列表，超级管理员除外
     *
     * @author kimi
     * @dateTime 2012-3-21 下午5:56:44
     * (non-JSDoc)
     */
    public List<SystemUser> getUserListInRoleId(int rid) {
        return userMapper.getUserEntityListInRid(rid);
    }

    /**
     * 获取不属于rid的用户列表，超级管理员除外
     *
     * @author kimi
     * @dateTime 2012-3-21 下午5:55:57
     * (non-JSDoc)
     */
    public List<SystemUser> getUserListNotInRoleId(int rid) {
        return userMapper.getUserEntityListNotInRid(rid);
    }

    /**
     * 根据条件查询用户总数
     *
     * @author kimi
     * @dateTime 2012-8-24 上午11:34:45
     * (non-JSDoc)
     */
    public long getUserCountsByName(String name) {
        Example userEntityExample = new Example(SystemUser.class);
        Criteria cri = userEntityExample.createCriteria();
        if (null != name && !"".equals(name)) {
            cri.andLike("name", SystemConstants.PERCENT + name + SystemConstants.PERCENT);
        }
//		if (!UserUtils.isAdministrator()) {
//			List<Integer> uids = UserUtils.getIdsByList(SystemConstants.ID, getUserListByParentEqualsCurrentUser());
//			cri.andIdIn(uids);
//		}
        return this.userMapper.selectCountByExample(userEntityExample);
    }

    /**
     * 根据条件查询用户总数
     *
     * @param name  用户名
     * @param roles 角色集合
     * @return
     * @author kimi
     * @dateTime 2012-8-24 上午11:34:45
     */
    @Deprecated
    public long getUserCountsByName(String name, List<SystemRole> roles) {
		/*if (!UserUtils.getSubject().hasRole(SystemConstants.ADMINISTRATOR)) {
			if (null == roles || roles.size() == 0) {
				return 0;
			}
			List<Integer> rids = ListUtils.getIdsByList(SystemConstants.ID, roles);
			if (null != name && !"".equals(name)) {
				return userMapper.getUserCountInRids(name, rids);
			}
			return this.userMapper.getUserCountInRids(null, rids);
		} else {
			Example userEntityExample = new Example(SystemUser.class);
			Criteria cri = userEntityExample.createCriteria();
			if (null != name && !"".equals(name)) {
				cri.andLike("name", SystemConstants.PERCENT + name + SystemConstants.PERCENT);
			}
			return this.userMapper.selectCountByExample(userEntityExample);
		}*/
        return 0;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @author kimi
     * @dateTime 2013-3-7 下午4:04:40
     * (non-JSDoc)
     */
    public SystemUserExt getUserExtByName(String name, String fieldName) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != fieldName && !"".equals(fieldName)) {
            if ("email".equals(fieldName)) {
                userEntityExample.createCriteria().andEqualTo("email", name);
            } else {
                userEntityExample.createCriteria().andEqualTo("name", name);
            }
            List<SystemUser> userList = this.userMapper.selectByExample(userEntityExample);
//			SystemRoleResourceExample example;
            List<Integer> rids;
            if (null != userList && userList.size() > 0) {
                SystemUserExt ue = new SystemUserExt();
                ue.setUser(userList.get(0));
                // 一个用户可能会有多种角色
                ue.setRoles(roleMapper.getRoleByUserId(userList.get(0).getId()));
                if (null != ue.getRoles() && ue.getRoles().size() > 0) {
                    // 判断用户是否是超级管理员，如果是超级管理员(administrator)那么当前用户拥有所有权限。
                    if (!isAdministrator(ue.getRoles())) {
                        // 设置当前用户的权限
                        rids = ListUtils.getIdsByList(SystemConstants.ID, ue.getRoles());
                        ue.setAuthorities(SystemResourceServiceImpl.integrationResource(resourceMapper.getResourceListByRidIn(rids)));
                    } else {
                        // 如果是超级管理员角色，则直接获取当前所有资源表中的权限
                        ue.setAuthorities(SystemResourceServiceImpl.integrationResource(this.resourceMapper
                                .selectByExample(new Example(SystemResource.class))));
                    }
                }
                return ue;
            }
        }
        return null;
    }

    /**
     * 后台可以通过登录名，邮箱，手机号三种方式登录
     *
     * @param name
     * @return
     * @author chenbiao
     * @date 2017年2月20日 下午9:53:04
     * 参数说明
     */
    public SystemUserExt getUserExtByLoginName(String name) {
        Example userEntityExample = new Example(SystemUser.class);
        userEntityExample.createCriteria().andEqualTo("name", name);
//        userEntityExample.or().andEqualTo("email", name);
//        userEntityExample.or().andEqualTo("phone", name);
        List<SystemUser> userList = this.userMapper.selectByExample(userEntityExample);
//		SystemRoleResourceExample example;
        List<Integer> rids;
        if (null != userList && userList.size() > 0) {
            SystemUserExt ue = new SystemUserExt();
            ue.setUser(userList.get(0));
            // 一个用户可能会有多种角色
            ue.setRoles(roleMapper.getRoleByUserId(userList.get(0).getId()));
            if (null != ue.getRoles() && ue.getRoles().size() > 0) {
                // 判断用户是否是超级管理员，如果是超级管理员(administrator)那么当前用户拥有所有权限。
                if (!isAdministrator(ue.getRoles())) {
                    // 设置当前用户的权限
                    rids = ListUtils.getIdsByList(SystemConstants.ID, ue.getRoles());
                    ue.setAuthorities(SystemResourceServiceImpl.integrationResource(resourceMapper
                            .getResourceListByRidIn(rids)));
                } else {
                    // 如果是超级管理员角色，则直接获取当前所有资源表中的权限
                    ue.setAuthorities(SystemResourceServiceImpl.integrationResource(this.resourceMapper
                            .selectByExample(new Example(SystemResource.class))));
                }
            }
            return ue;
        }
        return null;
    }

    /**
     * 判断指定角色集当中是否有administrator角色
     *
     * @param roles
     * @return
     * @author kimi
     * @dateTime 2013-7-31 下午4:55:55
     */
    private boolean isAdministrator(List<SystemRole> roles) {
        if (null != roles && roles.size() > 0) {
            for (SystemRole role : roles) {
                if (SystemConstants.ADMINISTRATOR.equals(role.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public SystemUser getUserByEmail(String email) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != email && !"".equals(email)) {
            // userEntityExample.createCriteria().andEmailEqualTo(email);
            userEntityExample.createCriteria().andEqualTo("name", email);
            List<SystemUser> list = this.userMapper.selectByExample(userEntityExample);
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 验证用户名是否存在
     *
     * @author kimi
     * @dateTime 2012-3-14 上午11:50:34
     * (non-JSDoc)
     */
    public boolean validateUserNameExists(String username) {
        Example ue = new Example(SystemUser.class);
        ue.createCriteria().andEqualTo("name", username);
        List<SystemUser> ls = this.userMapper.selectByExample(ue);

        return ls.size() > 0;
    }

    /**
     * 验证电子邮件是否被注册过了
     * <p>
     * 为了防止登录时被暴力破解，添加验证码功能，通过电子邮件发送验证码到邮箱当中
     *
     * @return <li><code>true</code>表示此电子邮件已经被注册过了</li><li><code>false</code>表示此电子邮件未被注册过</li> (non-JSDoc)
     * @author kimi
     * @dateTime 2013-3-13 下午3:25:29
     */
    public boolean validateEmailExists(String email) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != email && !"".equals(email)) {
            userEntityExample.createCriteria().andEqualTo("email", email);
            List<SystemUser> list = this.userMapper.selectByExample(userEntityExample);
            if (null != list && list.size() > 0) {
                return true;
            }
        }
        return false;
    }
    public boolean validateEmailExists(String email,int userId) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != email && !"".equals(email)) {
            userEntityExample.createCriteria().andEqualTo("email", email).andNotEqualTo("id", userId);
            List<SystemUser> list = this.userMapper.selectByExample(userEntityExample);
            if (null != list && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验手机号是否存在
     *
     * @param phone
     * @return
     */
    public boolean validatePhoneExists(String phone) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != phone && !"".equals(phone)) {
            userEntityExample.createCriteria().andEqualTo("phone", phone);
            List<SystemUser> list = this.userMapper.selectByExample(userEntityExample);
            if (null != list && list.size() > 0) {
                return true;
            }
        }
        return false;
    }
    public boolean validatePhoneExists(String phone,int userId) {
        Example userEntityExample = new Example(SystemUser.class);
        if (null != phone && !"".equals(phone)) {
            userEntityExample.createCriteria().andEqualTo("phone", phone).andNotEqualTo("id", userId);
            List<SystemUser> list = this.userMapper.selectByExample(userEntityExample);
            if (null != list && list.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否可以新增或者更新用户 ,判断超级用户是否>1
     *
     * @param user
     * @param roles
     * @return
     */
    private boolean isCanAddOrUpdate(SystemUser user, List<Integer> roles) {
        List<SystemUser> adminUses = getAdminUses();
        SystemRole adminRole = systemRoleService.getAdmin();

        Integer adminUserSize = adminUses.size();
        // 如果至少1个超级用户
        if (adminUses != null && adminUserSize >= 1) {
            if (roles != null) {
                boolean isContailAdminRole = false;
                for (Integer roId : roles) {
                    if (roId.equals(adminRole.getId())) {
                        // 如果需要更新的角色包含超级管理员角色
                        isContailAdminRole = true;
                        break;
                    }
                }
                if (isContailAdminRole) {
                    // 判断是否是编辑的超级管理员用户
                    boolean isCurAdminUser = false;
                    for (SystemUser user1 : adminUses) {
                        if (user !=null && user.getId() != null && user.getId().equals(user1.getId())) {
                            isCurAdminUser = true;
                            break;
                        }
                    }

                    // 如果不是超级管理员
                    if (!isCurAdminUser) {
                        ++adminUserSize;
                    }
                }

            }

            if (adminUserSize > 1) {
                try {
                    throw new RuntimeException("超级管理员最多一个");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addUser(SystemUser user, List<Integer> roles) {
        // 只能存在一个超级管理员权限的用户
        if (!isCanAddOrUpdate(user, roles)) {
            return false;
        }

        if (this.userMapper.insertSelective(user) > 0) {
            this.asyncAddRoleUser(user, roles);
            return true;
        }
        return false;
    }

    public Object addUser(List<SystemUser> users) {
        if (users != null && !users.isEmpty()) {
            this.userMapper.insertList(users);
        }
        return null;
    }

    public int updateUser(SystemUser user, List<Integer> rid) {
        // 只能存在一个超级管理员权限的用户
        if (!isCanAddOrUpdate(user, rid)) {
            return -1;
        }

        if (null != user.getPassword()) {
            user.setPassword(MD5Password.md5(user.getPassword()));
        }
        if (this.userMapper.updateByPrimaryKeySelective(user) > 0) {
            this.asyncAddRoleUser(user, rid);
            return 1;
        }
        return -1;
    }

    public List<SystemUser> getAdminUses() {
        SystemRole adminRole = systemRoleService.getAdmin();

        SystemRoleUser roleUser = new SystemRoleUser();
        roleUser.setRoleId(adminRole.getId());
        List<SystemRoleUser> adminRoleUsers = roleUserMapper.select(roleUser);
        List<Integer> userIds = new ArrayList<>();
        for (SystemRoleUser adminRoleUser : adminRoleUsers) {
            userIds.add(adminRoleUser.getUid());
        }
        return getUsersByIds(userIds);
    }

    private List<SystemUser> getUsersByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        Example example = new Example(SystemUser.class);
        example.createCriteria().andIn("id", ids);
        return userMapper.selectByExample(example);
    }

    @Async
    public void asyncAddRoleUser(SystemUser user, List<Integer> rid) {
        if (null != rid && rid.size() > 0) {
            Example example = new Example(SystemRoleUser.class);
            example.createCriteria().andEqualTo("uid", user.getId());
            this.roleUserMapper.deleteByExample(example);
            SystemRoleUser roleUser;
            for (int i = 0; i < rid.size(); i++) {
                roleUser = new SystemRoleUser();
                roleUser.setRoleId(rid.get(i));
                roleUser.setUid(user.getId());
                this.roleUserMapper.insertSelective(roleUser);
            }
        }
    }


    public SystemUser getUserByName(String userName) {
        Example user = new Example(SystemUser.class);
        user.createCriteria().andEqualTo("name", userName);
        List<SystemUser> list = this.userMapper.selectByExample(user);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @return
     * @author kimi
     * @dateTime 2013-8-6 上午11:58:21
     */
    @Override
    @Deprecated
    public List<SystemUser> getUserListByParentEqualsCurrentUser() {
        return null;
		/*if (UserUtils.isAdministrator()) {
			return getUserListByName(null, null);
		} else {
			List<SystemRole> roleList = UserUtils.getCurrentRoles();
			if (null == roleList || roleList.size() == 0) {
				return null;
			}
			List<Integer> rids = ListUtils.getIdsByList(ListUtils.DEFAULT_FIELDNAME, roleList);
			if (null != rids && rids.size() > 0) {
				return userMapper.getUsersListInRids(null, rids, null, null);
			}
			return null;
		}*/
    }

    /**
     * 获取所有系统用户
     *
     * @author cym
     * @date 2018年1月30日 上午11:55:37
     */
    @Override
    public List<SystemUser> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<SystemUser> getUserListByMobile(Collection<String> mobiles) {
        Example example = new Example(SystemUser.class);
        example.createCriteria().andIn("phone", mobiles);
        return userMapper.selectByExample(example);
    }
}

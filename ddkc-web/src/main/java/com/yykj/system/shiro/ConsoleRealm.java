package com.yykj.system.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.response.SystemUserExt;
import com.yykj.system.service.SystemRoleService;
import com.yykj.system.service.SystemUserService;

/**
 * quest默认的Realm
 * <p>
 * Realm 是可以访问程序特定的安全数据如用户、角色、权限等的一个组件。Realm会将这些程序特定的安全数据转换成一种shiro可以理解的形式，<br/>
 * shiro就可以依次提供容易理解的Subject程序API而不管有多少数据源或者程序中你的数据如何组织。<br/>
 * Realm 通常和数据源如数据库、LDAP目录、文件系统或者其它类似的数据源是一对一的关系，所以，可以用数据源相应的<br/>
 * API如JDBC、File IO、 Hibernate 或者JPA以及其它的API来实现Realm接口,从而获取授权的相关数据（角色、权限等）。
 * realm本质上就是指定一个安全的DAO。因为大部分这类数据源通常都会同时存储认证数据(如密码)和授权数据(角色和权限),所以每一个shiro
 * Realm都可以同时执行认证和授权操作。
 * 
 * @author kimi
 * @dateTime 2013-3-7 下午1:48:58
 */
public class ConsoleRealm extends AuthorizingRealm {

	private static final String DEFAULT_FIELDNAME = "username";// 默认字段名称

	private static final String DEFAULT_HASHALGORITHMNAME = "MD5";// 默认加密方式

	private static final boolean DEFAULT_ISCREDENTIALSMATCHER = false;// 默认不进行加密处理

	private Logger log = LoggerFactory.getLogger(getClass());

	private String hashAlgorithmName = DEFAULT_HASHALGORITHMNAME;// 加密方式

	private boolean isCredentialsMatcher = DEFAULT_ISCREDENTIALSMATCHER;// 是否加密处理

	private String fieldName = DEFAULT_FIELDNAME;// 用户名字段名称

	private boolean isSalt = false;// 是否盐值。默认不盐值

	private String salt = "42trip";// 默认盐值

	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemRoleService systemRoleService;

	public ConsoleRealm() {
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		System.out.println("登录进来了。...进行用户认证");

		SystemUserExt user = systemUserService.getUserExtByLoginName(upToken.getUsername());

		System.out.println("user:" + user);
		if (null != user) {
			if (!user.isEnabled()) {
				throw new AuthenticationException("用户被禁用了");
			}
			System.out.println("认证中....");
			updateUserInfo(user, upToken);// 修改用户登录次数，用户最后一次登录时间，用户最后一次登录IP等
			return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		}
		throw new AuthenticationException("用户名不存在");
	}

	private void updateUserInfo(SystemUserExt user, UsernamePasswordToken upToken) {
		user.getUser().setLastLoginIp(upToken.getHost());
		user.getUser().setLoginTimes(user.getUser().getLoginTimes() + 1);
		user.getUser().setLastLoginTime(System.currentTimeMillis());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = ((SystemUserExt) principals.fromRealm(getName()).iterator().next()).getUsername();
		SystemUserExt user = systemUserService.getUserExtByName(username, "username");// systemUserService提供的根据userName得到User
		if (null != user) {

			// 授权信息对象
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			log.info("权限验证......");
			List<SystemRole> roles = user.getRoles();
			if (null != roles && roles.size() > 0) {
				for (int i = 0; i < roles.size(); i++) {
					info.addRole(roles.get(i).getRoleName());
				}
			}
			List<SystemResource> resources = user.getAuthorities();
			if (null != resources && resources.size() > 0) {
				for (int i = 0; i < resources.size(); i++) {
					// 为用户授权
					info.addStringPermission(resources.get(i).getResourceUrl());
				}
			}
			return info;
		}
		return null;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	public void setsystemUserService(SystemUserService systemUserService) {
		this.systemUserService = systemUserService;
	}

	public Boolean isCredentialsMatcher() {
		return isCredentialsMatcher;
	}

	public void setIsCredentialsMatcher(Boolean isCredentialsMatcher) {
		this.isCredentialsMatcher = isCredentialsMatcher;
	}

	public String getHashAlgorithmName() {
		return hashAlgorithmName;
	}

	public void setHashAlgorithmName(String hashAlgorithmName) {
		this.hashAlgorithmName = hashAlgorithmName;
		log.debug("****************** isCredentialsMatcher is " + isCredentialsMatcher);
		if (isCredentialsMatcher) {
			log.debug("***************** the hashAlgorithmName is :" + hashAlgorithmName);
			setCredentialsMatcher(new HashedCredentialsMatcher(hashAlgorithmName));
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public SystemUserService getSystemUserService() {
		return systemUserService;
	}

	public void setSystemUserService(SystemUserService systemUserService) {
		this.systemUserService = systemUserService;
	}

	public SystemRoleService getSystemRoleService() {
		return systemRoleService;
	}

	public void setSystemRoleService(SystemRoleService systemRoleService) {
		this.systemRoleService = systemRoleService;
	}

	public boolean getIsSalt() {
		return isSalt;
	}

	public void setIsSalt(boolean isSalt) {
		this.isSalt = isSalt;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}

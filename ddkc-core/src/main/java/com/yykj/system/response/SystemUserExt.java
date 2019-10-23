package com.yykj.system.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.entity.SystemUser;

/**
 * 用户扩展类
 * 
 * @author kimi
 * @dateTime 2012-3-9 下午3:55:15
 */
public class SystemUserExt implements Serializable {

	/**
	 * @author kimi
	 * @dateTime 2012-6-12 下午2:19:31
	 */
	private static final long serialVersionUID = -1857185926526195360L;

	/**
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:54:56
	 */
	private SystemUser user;

	/**
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:54:59
	 */
	private List<SystemRole> roles;

	private List<SystemResource> authorities;

	public List<SystemResource> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SystemResource> authorities) {
		this.authorities = authorities;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getName();
	}

	public boolean isAccountNonExpired() {
		return user.getStatus() != 0 ? false : true;
	}

	public boolean isAccountNonLocked() {
		return user.getStatus() != 0 ? false : true;
	}

	public boolean isCredentialsNonExpired() {
		return user.getStatus() != 0 ? false : true;
	}

	public boolean isEnabled() {
		return user.getStatus() != 0 ? false : true;
	}

	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser user) {
		this.user = user;
	}

	public List<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}

	/**
	 * 判断用户是否是超级用户
	 * @param user
	 * @return
	 */
	public static boolean isAdministrator(SystemUserExt user) {
		boolean isAdministrator = false;
		if (user == null || user.getRoles().isEmpty() ) {
			return isAdministrator;
		}

		List<SystemRole> roles = user.getRoles();
		for (SystemRole role : roles) {
			if ("administrator".equals(role.getRoleName())) {
				isAdministrator = true;
			}
		}
		return isAdministrator;
	}

	/**
	 * 获取用户角色集合
	 * @param user
	 * @return
	 */
	public static List<Integer> getRoleIds(SystemUserExt user) {
		List<SystemRole> roles = user.getRoles();
		List<Integer> roleIds = new ArrayList<Integer>();
		for (SystemRole role : roles) {
			roleIds.add(role.getId());
		}
		return roleIds;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return getUsername();
	}

	/**
	 * 重载hashCode,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hashCode(getUsername());
	}

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemUserExt other = (SystemUserExt) obj;
		if (null != user) {
			if (getUsername() == null) {
				if (other.getUsername() != null)
					return false;
			} else if (!getUsername().equals(other.getUsername()))
				return false;
		}
		return true;
	}
}

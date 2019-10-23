package com.yykj.system.response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.yykj.system.entity.SystemResource;

/**
 * 基础角色扩展类
 * 
 * @author kimi
 * @dateTime 2013-3-7 下午4:40:00
 */
public class SystemRoleExt  implements Serializable {

	private Integer id;

	private String roleName;

	private String description;

	private Set<SystemResource> permissions;// 角色所对应的权限

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// public Set<Resource> getPermissions() {
	// return permissions;
	// }
	public void setPermissions(Set<SystemResource> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 获取当前角色的权限信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-11 下午5:46:43
	 * @return 返回当前角色所拥有的权限信息
	 */
	public Set<String> getPermissions() {
		if (null != permissions && permissions.size() > 0) {
			Set<String> set = new HashSet<String>();
			Iterator<SystemResource> iterator = permissions.iterator();
			SystemResource resource;
			while (iterator.hasNext()) {
				resource = iterator.next();
				set.add(resource.getResourceUrl());
			}
			return set;
		}
		return null;
	}
}

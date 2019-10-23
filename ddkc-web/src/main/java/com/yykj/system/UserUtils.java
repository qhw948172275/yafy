package com.yykj.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.yykj.system.entity.SystemResource;
import com.yykj.system.entity.SystemRole;
import com.yykj.system.response.SystemUserExt;



/**
 * shiro用户帮助类
 * 
 * @author kimi
 * @dateTime 2013-3-19 下午3:21:36
 */
public class UserUtils {

	public static final String DEFAULT_FIELDNAME = "id";

	/**
	 * 获取当前登录用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:46:15
	 * @return
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:46:15
	 * @return
	 */
	public static SystemUserExt getCurrentUserInfo() {
		Subject sub = getSubject();
		if (null != sub) {
			return ((SystemUserExt) sub.getPrincipal());
		}
		return null;
	}

	/**
	 * 获取当前用户所属角色
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:28:46
	 * @return
	 */
	public static List<SystemRole> getCurrentRoles() {
		SystemUserExt user = getCurrentUserInfo();
		if (null != user) {
			return user.getRoles();
		}
		return null;
	}
	/**
	 * 获取当前用户的所有权限
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:28:46
	 * @return
	 */
	public static List<SystemResource> getCurrentAuthorties() {
		SystemUserExt user = getCurrentUserInfo();
		if(null != user){
			return user.getAuthorities();
		}
		return null;
	}
	public static final String ADMINISTRATOR = "administrator";// 超级管理员角色
	public static boolean isAdministrator() {
		Subject su = getSubject();
		return su.hasRole(ADMINISTRATOR);
	}

	/**
	 * 获取指定集合中，指定字段的ID集合
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午5:26:33
	 * @param fieldName 字段名称
	 * @param list 集合
	 * @return fieldName集合
	 */
	/*public static List<Integer> getIdsByList(String fieldName, List<? extends Object> list) {
		if (null != fieldName && !"".equals(fieldName)) {
			if (null != list && list.size() > 0) {
				List<Integer> results = new LinkedList<Integer>();
				for (int i = 0; i < list.size(); i++) {
					Reflect r = Reflect.on(list.get(i));
					results.add(Integer.parseInt(r.field(fieldName).get().toString()));
				}
				return results;
			}
		}
		return null;
	}*/

	/**
	 * 获取指定集合中，指定字段的ID集合
	 * 
	 * @author kimi
	 * @dateTime 2013-3-19 下午5:26:33
	 * @param fieldName 字段名称
	 * @param list 集合
	 * @return fieldName集合
	 */
	/*public static List<String> getFieldsByList(String fieldName, List<? extends Object> list) {
		if (null != fieldName && !"".equals(fieldName)) {
			if (null != list && list.size() > 0) {
				List<String> results = new LinkedList<String>();
				for (int i = 0; i < list.size(); i++) {
					Reflect r = Reflect.on(list.get(i));
					results.add(r.field(fieldName).get().toString());
				}
				return results;
			}
		}
		return null;
	}*/
}

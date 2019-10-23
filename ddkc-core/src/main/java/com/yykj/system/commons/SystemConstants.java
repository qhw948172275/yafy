package com.yykj.system.commons;

public class SystemConstants {

	public static final String DEFAULT_CHARSET = "utf-8";
	
	public final static String PERCENT = "%";
	
	/**
	 * 默认密码盐值
	 */
	public static final String DEFAULT_SALT = "duoji";
	
	/**
	 * session中存储的常量key，用户存储对应的用户信息
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:05
	 */
	public static final String SESSION_USER_KEY = "USER";

	/**
	 * session中存储的用户角色信息
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:11:20
	 */
	public static final String SESSION_USER_ROLE = "USER_ROLE";

	/**
	 * session中存储的权限
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:07
	 */
	public static final String SESSION_USER_AUTHOR = "AUTHOR";
	
	public static final String ID = "id";

	public static final String ADMINISTRATOR = "administrator";// 超级管理员角色

	/**
	 * 系统消息key
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:13
	 */
	public static final String MESSAGE = "message";

	/**
	 * 成功操作标识
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:15
	 */
	public static final String SUCCESS = "OK";

	/**
	 * 警告操作标识
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:17
	 */
	public static final String WARN = "warn";

	/**
	 * 错误标识
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:18
	 */
	public static final String ERROR = "error";

	/**
	 * 启用状态标识
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:26
	 */
	public static final Integer STATUS_OK = Integer.parseInt("0");

	/**
	 * 禁用状态标识
	 * 
	 * @author kimi
	 * @dateTime 2012-3-9 下午3:09:30
	 */
	public static final Integer STATUS_NO = Integer.parseInt("1");
	/**
	 * 云存储上传目录
	 */
	public static final String UPLOAD_DIR = "/photos/";
	/**
	 * 云存储资源访问目录
	 */
	public static final String VISIT_DIR = "http://image.cdyingyun.com/photos/";
	
}

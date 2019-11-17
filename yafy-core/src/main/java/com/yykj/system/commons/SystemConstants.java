package com.yykj.system.commons;

public class SystemConstants {
	/**
	 * 默认密码
	 */
	public static final String defaultPassword="123456";
	/**
	 * 默认加密版密码
	 */
	public static final String defaultPassword_encode="$2a$10$oitzdtvtBCKdvdyRQAOz1OlgbThEkAWpsl7ifj7H7vwH6T2GPTNAm";
	/**
	 * 用户缓存key
	 */
	public static final String SYS_USER_KEY="xh_sys_user_key_";
	/**
	 * 学生缓存key
	 */
	public static final String PARENT_KEY="xh_parent_key_";
	/**
	 * 老师缓存key
	 */
	public static final String TEACHER_KEY="xh_teacher_key_";

	/**
	 * 账户basicKey
	 */
	public static final String USERNAME_BASIC="xh_username_basic_";
	/**
	 * 用户刷新TOKEN缓存key
	 */
	public static final String SYS_USER_REFRESH_KEY="xh_sys_user_refresh_key_";
	/**
	 * 学生刷新TOKEN缓存key
	 */
	public static final String STUDENT_REFRESH_KEY="xh_student_refresh_key_";
	/**
	 * 老师刷新TOKEN缓存key
	 */
	public static final String TEACHER_REFRESH_KEY="xh_teacher_refresh_key_";



	/**
	 * 异常代码
	 */
	public static final String ERROR_CODE_414="414";//用户名不存在
	public static final String ERROR_CODE_415="415";//密码错误
	public static final String ERROR_CODE_416="416";//Token失效
	public static final String ERROR_CODE_500="500";//程序错误
	public static final String ERROR_CODE_417="417";//无权访问
	public static final String ERROR_CODE_418="418";//自定义
	/**
	 * 学校默认角色d代码
	 */
	public static final String SCHOOL_DEFAULT_ADMIN_CODE="SCHOOLDEFAULTADMINCODE";
	/**
	 * 平台管理员角色代码
	 */
	public static final String PLATFORM_ADMIN_CODE="PLATFORMADMIN";
	/**
	 * 学校默认角色名称
	 */
	public static final String SCHOOL_DEFAULT_ADMIN_NAME="校管理员";

	/**
	 * 学校默认教师角色名称
	 */
	public static final String SCHOOL_DEFAULT_TEACHER_NAME="教师";

	/**
	 * 学校基础管理角色ID
	 */
	public static final Integer SCHOOL_ADMIN_ID=38;

	/**
	 * 云存储上传目录
	 */
	public static final String UPLOAD_DIR = "/photos/";
	/**
	 * 云存储资源访问目录
	 */
	public static final String VISIT_DIR = "http://image.cdyingyun.com/photos/";
	/**
	 * 微信公众号oauth账号家长端
	 */
	public static final String PARENT_CLIENT="parent-client";
	/**
	 * 微信公众号oauth账号教师端
	 */
	public static final String TEACHER_CLIENT="teacher-client";

	/**
	 * 家长缓存学校ID
	 */
	public static final String PATENT_CACHET_SCHOOLID_KEY="parent_cache_schoolId_key_";
	/**
	 * 家长信息缓存
	 */
	public static final String PARENT_INFO_KET="parent_info_key_";
	/**
	 * 家长信息缓存
	 */
	public static final String TEACHER_INFO_KET="teacher_info_key_";

	/**
	 * 微信教师登录地址
	 */
	public static final String WE_CHAT_TEACHER_LOGIN="http://140.246.138.117:9316/wechat/teaLogin";
	/**
	 * 微信家长登录地址
	 */
	public static final String WE_CHAT_PARENT_LOGIN="http://140.246.138.117:9316/wechat/stuLogin";

	public static final String WE_CHAT_TEACHER_MENU_NAME="教师登录";
	public static final String WE_CHAT_PARENT_MENU_NAME="家长登录";
	/**
	 * 测试
	 */
	public static final String XLS_OUT_FOLDER_PREFIX_TEST="/usr/local/webserver/homeschoo/lserver/output/";

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
	 * 电话校验
	 */
	public static final String PHONE_CHECK="^1[3456789]\\d{9}$";

	/**
	 * 专项练习
	 */
	public static final String SPECIAL_PREFIX = "special_prefix:";

	/**
	 * 专项练习题缓存
	 */
	public static final String SPECIAL_PRACTICE_QUESTION = "special_practice_question:";

	/**
	 * 专项练习时间
	 */
	public static final String SPECIAL_TIME = "special_time:";

	/**
	 * 习题缓存
	 */
	public static final String QUESTION_DETAIL = "question_detail";
}

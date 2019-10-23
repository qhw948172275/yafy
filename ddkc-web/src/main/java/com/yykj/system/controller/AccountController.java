package com.yykj.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yykj.system.UserUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.MyPageInfo;
import com.yykj.system.commons.PageTools;
import com.yykj.system.commons.ValidateUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.entity.SystemUser;

/**
 * 系统用户
 * 
 * @author kimi
 * @dateTime 2012-3-13 下午7:59:03
 */
@Controller("systemAccountController")
@RequestMapping("/console/system/account")
public class AccountController extends BaseController {
	/**
	 * 查看个人信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	protected String userInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		SystemUser user = systemUserService.getUserById(getLoginUserId(request));
		model.put("user", user);
		return "/system/account/info";
	}

	/**
	 * 个人设置
	 * 
	 * @author chenbiao
	 * @date 2017年2月20日 下午10:12:33 参数说明
	 * 
	 * @param name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	protected ModelAndView setting(@RequestParam(value = "name", required = false) String name, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		PageTools page = super.getPage(request);
		MyPageInfo<SystemUser> pageInfo = systemUserService.getUserListByName(name, page);
		page.setRecordCount((int) pageInfo.getTotal());

		model.put("userlist", pageInfo.getList());
		model.put("page", page);
		model.put("currentUser", UserUtils.getCurrentUserInfo());
		return new ModelAndView("/system/user/index", model);
	}

	/**
	 * 编辑个人信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	protected String edit(Map<String, Object> model) throws Exception {
		SystemUser user = systemUserService.getUserById(UserUtils.getCurrentUserInfo().getUser().getId());
		model.put("user", user);
		return "/system/account/edit";
	}

	/**
	 * 编辑个人密码
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pwdedit", method = RequestMethod.GET)
	protected String pwdedit(Map<String, Object> model) throws Exception {
		SystemUser user = systemUserService.getUserById(UserUtils.getCurrentUserInfo().getUser().getId());
		model.put("user", user);
		return "/system/account/pwdedit";
	}

	/**
	 * 
	 * @param systemUser
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public JsonResult save(SystemUser systemUser) {
		String showmessage = "";
		boolean result = false;
		Map<String, Object> data = new HashMap<>();
		try {
			systemUser.setId(UserUtils.getCurrentUserInfo().getUser().getId());
			if (ValidateUtils.isEmpty(systemUser.getRealName())) {
				showmessage = "姓名不能为空";
			} else if (!ValidateUtils.isMail(systemUser.getEmail())) {
				showmessage = "邮箱格式不正确";
			} else if (!ValidateUtils.isMail(systemUser.getEmail())) {
				showmessage = "联系电话格式不正确";
			} else {
				if (this.systemUserService.validateEmailExists(systemUser.getEmail(),systemUser.getId())) {
					showmessage = "邮箱已经存在";
				} else if (this.systemUserService.validatePhoneExists(systemUser.getPhone(),systemUser.getId())) {
					showmessage = "联系电话已经存在";
				} else {
					systemUserService.updateUser(systemUser);
					result = true;
					UserUtils.getSubject().logout();
					showmessage = "保存成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			showmessage = "保存失败";
		}
		data.put("showmessage", showmessage);
		if (result) {
			return JsonResultUtils.buildJsonOK(data);
		} else {
			return JsonResultUtils.buildJsonFail(data);
		}
	}

	/**
	 * 
	 * @param oldPassword
	 * 
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "savepwd", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult resetPassword(String oldPassword, String newPassword) {
		String showmessage = "";
		boolean result = false;
		Map<String, Object> data = new HashMap<>();
		try {
			if (ValidateUtils.isEmpty(oldPassword)) {
				showmessage = "原密码不能为空";
			} else if (ValidateUtils.isEmpty(newPassword)) {
				showmessage = "新密码不能为空";
			} else if (newPassword.length() < 6) {
				showmessage = "请输入长度不小于6位的新密码";
			} else {
				SystemUser user = systemUserService.getUserById(UserUtils.getCurrentUserInfo().getUser().getId());
				if (!MD5Password.md5(oldPassword).equals(user.getPassword())) {
					showmessage = "原密码不正确";
				} else {
					SystemUser systemUser = new SystemUser();
					systemUser.setId(user.getId());
					systemUser.setPassword(MD5Password.md5(newPassword));
					systemUserService.updateUser(systemUser);
					result = true;
					UserUtils.getSubject().logout();
					showmessage = "保存成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			showmessage = "保存失败";
		}
		data.put("showmessage", showmessage);

		if (result) {
			return JsonResultUtils.buildJsonOK(data);
		} else {
			return JsonResultUtils.buildJsonFail(data);
		}
	}
}

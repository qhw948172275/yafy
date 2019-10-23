package com.yykj.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yykj.system.UserUtils;

/**
 * 登录处理类
 *
 * @author kimi
 * @dateTime 2013-3-19 下午3:48:45
 */
@Controller("systemLoginController")
@RequestMapping("/system/")
public class LoginController extends BaseController {

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 登录请求
	 *
	 * @return
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:49:26
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "redirectUrl", defaultValue = "") String redirectUrl, Model model,
			HttpServletRequest request) {
		model.addAttribute("redirectUrl", redirectUrl);
		if(null != getLoginUser(request)){
			return "redirect:/system/index";
		}
		return "system/login";
	}

	/**
	 * 登录失败
	 *
	 * @param userName
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:49:13
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password, HttpServletRequest request,
			Model model) throws Exception {
		log.debug("认证失败....");
		Object obj = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String errorMsg = "";
		if (null != obj) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(obj))
				errorMsg = "未知帐号";
			else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(obj))
				errorMsg = "密码错误";
			else if ("me.gall.quest.service.exception.IncorrectCaptchaException".equals(obj))
				errorMsg = "验证码错误";
			else if ("org.apache.shiro.authc.DisabledAccountException".equals(obj))
				errorMsg = "帐号已被禁用或锁定";
			else if ("org.apache.shiro.authc.AuthenticationException".equals(obj))
				errorMsg = "认证失败";
		} else {
			errorMsg = "认证失败....,而且程序猿也不知道为什么！";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, errorMsg);
		return "system/login";
	}

	/**
	 * 登录成功
	 *
	 * @param request
	 * @param model
	 * @return
	 * @author kimi
	 * @dateTime 2013-3-19 下午3:49:00
	 */
	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
	public String loginSuccess(HttpServletRequest request, Model model) {
		return "redirect:/system/index";
	}

	/**
	 * 退出操作
	 *
	 * @param request
	 * @param model
	 * @return
	 * @author chenbiao
	 * @date 2017年2月20日 下午9:32:42 参数说明
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			UserUtils.getSubject().logout();
			return "redirect:/system/login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/system/login";
		}
	}
}

package com.yykj.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 程序无权限访问
 * 
 * @author kimi
 * @dateTime 2013-3-8 下午4:10:57
 */
@Controller("systemUnauthorizedController")
public class UnauthorizedController {

	@RequestMapping("/403")
	protected ModelAndView unauthorized(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/403");
	}
}

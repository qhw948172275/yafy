package com.yykj.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yykj.system.entity.SystemLoginLog;


@Controller("systemUserLoginLogController")
@RequestMapping("/system")
public class UserLoginLogController extends BaseController {

	/**
	 * @author kimi
	 * @dateTime 2012-3-13 下午7:59:06
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLoginLog")
	protected ModelAndView userList(SystemLoginLog userLoginLog,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "city", required = false) String city, final HttpServletRequest request,
			final HttpServletResponse response, Map<String, Object> model) throws Exception {
		if (null != province && !"".equals(province)) {
			userLoginLog.setLoginAddress(province);
		}
		if (null != city && !"".equals(city)) {
			userLoginLog.setLoginAddress(userLoginLog.getLoginAddress() + city);
		}
//		PageTools page = super.getPage(request, loginLogManager.selectCountByUserLoginLog(userLoginLog, startTime, endTime));
//		List<UserLoginLog> userlist = this.loginLogManager.selectUserLoginLog(userLoginLog, startTime, endTime, page);
//		Map<String, Geography> map = IpUtilInter.countryMap.get(IpUtilInter.CHINA).getChildMap();
//		model.put("userlist", userlist);
//		model.put("page", page);
//		model.put("provinces", map);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("userLoginLog", userLoginLog);
		model.put("province", province);
		model.put("city", city);
		return new ModelAndView("/system/userLoginLog", model);
	}

	/**
	 * @author kimi
	 * @dateTime 2012-3-13 下午7:59:06
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLoginLog", params = "method=provinceChange", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> provinceChange(@RequestParam(value = "province", required = false) String province,
			final HttpServletRequest request, final HttpServletResponse response, Map<String, Object> model) throws Exception {
//		Map<String, Geography> map = IpUtilInter.countryMap.get(IpUtilInter.CHINA).getChildMap();
//		if (null != province && !"".equals(province) && map.containsKey(province)) {
//			model.put("citys", map.get(province).getChildMap().entrySet());
//		}
		return model;
	}
}

package com.yykj.ddkc.platform;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.service.ShopAccountService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.controller.BaseController;
/**
 * 商户账号管理
* @author chenbiao
* @date 2019年9月23日 下午6:22:37 
*
 */
@Controller
@RequestMapping(value = "platform/shop/account")
public class ShopAccountController extends BaseController{
	
	@Autowired
    ShopService shopService;
	@Autowired
	ShopAccountService shopAccountService;

	@GetMapping(value = "")
	public String index(@RequestParam Integer shopId,Model model) {
		model.addAttribute("shopId", shopId);
		return "ddkc/platform/shop/account/index";
	}

	/**
	 * 获取商家列表数据
	 * 
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "", params = "ajaxLoad")
	public Object ajaxLoad(HttpServletRequest request, @RequestParam Integer shopId) {
		Map<String, Object> map = getMapByExample(request);
		if (null != shopId ) {
			map.put("shopId", shopId);
		}
		return buildPageResultByAsync(shopAccountService.selectByShopIdPage(map));
	}

	/**
	 * 跳转商铺添加页面
	 * 
	 * @return
	 */
	@GetMapping(value = "add")
	public String add(@RequestParam Integer shopId,Model model) {
		model.addAttribute("shopId", shopId);
		return "ddkc/platform/shop/account/info";
	}

	/**
	 * 保存商铺信息
	 * 
	 * @param shop
	 * @param request
	 * @return
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public JsonResult save(ShopAccount account, HttpServletRequest request) {
		try {
			if(shopAccountService.validateExists(account.getAccount(),account.getId())) {
				return JsonResultUtils.buildJsonFailMsg("账号已经存在");
			}
			if (account.getId() == null) {
				account.setIsMaster(SystemConstants.STATUS_OK);
				account.setCreateTime(CalendarUtils.getDate());
				account.setCreatorId(getLoginUserId(request));
				account.setStatus(SystemConstants.STATUS_OK);
				if(shopAccountService.insert(account) > 0) {
					shopAccountService.settingNoticeByShopAccount(account);
					return JsonResultUtils.buildJsonOK();
				}
			} else {
				if(shopAccountService.updateById(account) > 0) {
					shopAccountService.settingNoticeByShopAccount(shopAccountService.getById(account.getId()));
					return JsonResultUtils.buildJsonOK();
				}
			}
			return JsonResultUtils.buildJsonOK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResultUtils.buildJsonFail();
	}


	/**
	 * 编辑商铺
	 * 
	 * @param shopId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "edit")
	public String edit(Integer id, Model model) {
		ShopAccount account = shopAccountService.getById(id);
		model.addAttribute("account", account);
		model.addAttribute("shopId", account.getShopId());
		return "ddkc/platform/shop/account/info";
	}

	/**
	 * 更改商铺状态
	 * 
	 * @param status
	 * @param shopId
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "changStatus")
	public JsonResult changStatus(ShopAccount account) {
		try {
			
			if(shopAccountService.updateById(account) > 0) {
				shopAccountService.settingNoticeByShopAccount(shopAccountService.getById(account.getId()));
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResultUtils.buildJsonFail();
	}

	/**
	 * 删除账号
	* @author chenbiao
	* @date 2019年9月23日 下午6:39:11
	* 参数说明 
	* 
	* @param id
	* @return
	 */
	@ResponseBody
	@DeleteMapping(value = "")
	public JsonResult delete(@RequestParam Integer id) {
		try {
			ShopAccount account = shopAccountService.getById(id);
			account.setStatus(2);
			if(shopAccountService.updateById(account) > 0) {
				shopAccountService.settingNoticeByShopAccount(account);
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResultUtils.buildJsonFail();
	}

}

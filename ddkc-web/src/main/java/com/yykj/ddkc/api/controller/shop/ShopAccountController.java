package com.yykj.ddkc.api.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.yykj.commons.ErrorCodeUtils;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.MemberLoginRequired;
import com.yykj.ddkc.entity.Address;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.service.ShopAccountService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "商户账号管理")
@RestController(value = "shopAccountApiController")
@RequestMapping(value = "api/shop/account")
public class ShopAccountController extends BaseApiController {

	@Autowired
	ShopAccountService shopAccountService;

	@Autowired
	ShopService shopService;

	/**
	 * 获取会员列表
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 下午2:34:45 参数说明
	 * 
	 * @param token
	 * @param page
	 * @param limit
	 * @return
	 */
	@ApiOperation(value = "商户账号列表", response = Address.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "当前页，默认1", paramType = "query", required = false),
			@ApiImplicitParam(name = "limit", value = "展示数据量，默认10条", paramType = "query", required = false) })
	@MemberLoginRequired
	@GetMapping(value = "")
	public JsonResult addressList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
		try {
			Integer shopId = getShopIdByRequest(request);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("pageNumber", page);
			map.put("pageSize", limit);
			PageInfo<ShopAccount> accounts = shopAccountService.selectByShopIdPage(map);
			return JsonResultUtils.buildJsonOK(accounts);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

	/**
	 * 
	 * 添加员工
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 下午2:39:26 参数说明
	 * 
	 * @param token
	 * @param address
	 * @return
	 */
	@ApiOperation(value = "添加员工账号")
	@MemberLoginRequired
	@PostMapping(value = "add")
	public JsonResult add(HttpServletRequest request, @RequestBody ShopAccount account) {
		try {
			if(null == account.getName()) {
				return JsonResultUtils.buildJsonFailMsg("账号名称不能为空");
			}else if(null == account.getAccount()) {
				return JsonResultUtils.buildJsonFailMsg("账号不能为空");
			}else if(null == account.getPassword()) {
				return JsonResultUtils.buildJsonFailMsg("账号密码不能为空");
			}else if(null == account.getIsNotice()) {
				return JsonResultUtils.buildJsonFailMsg("是否接收语音通知不能为空");
			}
			if (shopAccountService.validateExists(account.getAccount(), account.getId())) {
				return JsonResultUtils.buildJsonFailMsg("账号已经存在");
			}
			Integer shopId = getShopIdByRequest(request);
			Shop shop = shopService.getById(shopId);
			if (null != shop && shop.getStatus() == 0) {
				account.setShopId(shopId);
				account.setCreateTime(CalendarUtils.getDate());
				account.setCreatorId(shop.getCreateId());
				account.setIsMaster(SystemConstants.STATUS_OK);// 员工账号
				account.setStatus(SystemConstants.STATUS_OK);
				if (shopAccountService.insert(account) > 0) {
					shopAccountService.settingNoticeByShopAccount(account);
					return JsonResultUtils.buildJsonOK();
				}
				return JsonResultUtils.buildJsonFailMsg("添加员工失败");
			}
			return JsonResultUtils.buildJsonFailMsg("商户不存在或已禁用");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

	/**
	 * 保存员工信息
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 下午2:55:22 参数说明
	 * 
	 * @param token
	 * @param address
	 * @return
	 */
	@ApiOperation(value = "编辑保存员工信息")
	@MemberLoginRequired
	@PostMapping(value = "edit")
	public JsonResult edit(HttpServletRequest request, @RequestBody ShopAccount account) {
		try {
			if(null == account.getName()) {
				return JsonResultUtils.buildJsonFailMsg("账号名称不能为空");
			}else if(null == account.getAccount()) {
				return JsonResultUtils.buildJsonFailMsg("账号不能为空");
			}else if(null == account.getPassword()) {
				return JsonResultUtils.buildJsonFailMsg("账号密码不能为空");
			}else if(null == account.getIsNotice()) {
				return JsonResultUtils.buildJsonFailMsg("是否接收语音通知不能为空");
			}
			if(shopAccountService.validateExists(account.getAccount(),account.getId())) {
				return JsonResultUtils.buildJsonFailMsg("账号已经存在");
			}
			if (account.getId() == null) {
				return JsonResultUtils.buildJsonFailMsg("员工账号ID不能为空");
			}
			ShopAccount shopAccount2 = shopAccountService.getById(account.getId());
			if (null == shopAccount2) {
				return JsonResultUtils.buildJsonFailMsg("该员工不存在");
			}
			account.setIsMaster(null); // 账号类型是不能修改的
			account.setShopId(shopAccount2.getShopId());
			if (shopAccountService.updateById(account) > 0) {
				shopAccountService.settingNoticeByShopAccount(account);
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFailMsg("保存员工信息失败");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

	/**
	 * 根据ID获取员工信息
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 下午2:50:04 参数说明
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID获取员工信息", response = Address.class)
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "int", name = "id") })
	@MemberLoginRequired
	@GetMapping(value = "info")
	public JsonResult info(HttpServletRequest request, @RequestParam Integer id) {
		try {
			Integer shopId = getShopIdByRequest(request);
			ShopAccount account = shopAccountService.getById(id);
			if(null == account || account.getStatus() == 2) {
				return JsonResultUtils.buildJsonFailMsg("员工信息不存在或已删除");
			}else if(account.getShopId().intValue() != shopId) {
				return JsonResultUtils.buildJsonFailMsg("该员工信息不存在");
			}
			return JsonResultUtils.buildJsonOK(account);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

	/**
	 * 根据ID删除员工信息
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 下午2:59:24 参数说明
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID删除员工信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", dataType = "int", value = "收货地址ID", paramType = "int") })
	@MemberLoginRequired
	@DeleteMapping(value = "delete")
	public JsonResult delete(HttpServletRequest request, @RequestParam Integer id) {
		try {
			Integer shopId = getShopIdByRequest(request);
			ShopAccount account = shopAccountService.getById(id);
			if(null == account || account.getStatus() == 2) {
				return JsonResultUtils.buildJsonFailMsg("员工信息不存在或已删除");
			}else if(account.getShopId().intValue() != shopId) {
				return JsonResultUtils.buildJsonFailMsg("该员工信息不存在");
			}
			account.setStatus(2);//标记为已删除
			if(shopAccountService.updateById(account) > 0) {
				shopAccountService.settingNoticeByShopAccount(account);
				return JsonResultUtils.buildJsonOK();
			}
			return JsonResultUtils.buildJsonFailMsg("删除失败");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

}

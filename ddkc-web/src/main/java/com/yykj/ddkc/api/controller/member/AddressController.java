package com.yykj.ddkc.api.controller.member;

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
import com.yykj.ddkc.service.AddressService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 会员收货地址管理
 * 
* @author chenbiao
* @date 2019年8月12日 下午2:21:06 
*
 */
@Api(value = "会员收货地址管理",tags = "会员收货地址管理")
@RestController
@RequestMapping(value = "api/member/address")
public class AddressController extends BaseApiController {
	
	@Autowired
	AddressService addressService;
	
	/**
	 * 获取会员列表
	* @author chenbiao
	* @date 2019年8月12日 下午2:34:45
	* 参数说明 
	* 
	* @param token
	* @param page
	* @param limit
	* @return
	 */
	@ApiOperation(value = "获取会员收货地址",response = Address.class)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "page", value = "当前页，默认1", paramType = "query",required = false),
		@ApiImplicitParam(name = "limit", value = "展示数据量，默认10条", paramType = "query",required = false)
	})
	@MemberLoginRequired
	@GetMapping(value = "")
	public JsonResult addressList(HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "limit",defaultValue = "10")Integer limit) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			PageInfo<Address> addressPage = addressService.getAddressListByMemberId(memberId, page, limit);
			return JsonResultUtils.buildJsonOK(addressPage);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
	
	/**
	 * 
	 * 添加会员收货地址
	 * 
	* @author chenbiao
	* @date 2019年8月12日 下午2:39:26
	* 参数说明 
	* 
	* @param token
	* @param address
	* @return
	 */
	@ApiOperation(value = "添加会员收货地址")
	@MemberLoginRequired
	@PostMapping(value = "add")
	public JsonResult add(HttpServletRequest request,@RequestBody Address address) {
			try {
			Integer memberId = getMemberIdByRequest(request);
			address.setMemberId(memberId);
			address.setStatus(SystemConstants.STATUS_OK);
			address.setCreateTime(CalendarUtils.getDate());
			if(addressService.saveOrUpdate(address)) {
				return JsonResultUtils.buildJsonOK(address);
			}
			return JsonResultUtils.buildJsonFailMsg("添加收货地址失败");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
	/**
	 * 保存会员收货地址信息
	* @author chenbiao
	* @date 2019年8月12日 下午2:55:22
	* 参数说明 
	* 
	* @param token
	* @param address
	* @return
	 */
	@ApiOperation(value = "编辑保存会员收货地址")
	@MemberLoginRequired
	@PostMapping(value = "edit")
	public JsonResult edit(HttpServletRequest request,@RequestBody Address address) {
		try {
			if(address.getId() == null) {
				return JsonResultUtils.buildJsonFailMsg("收货地址ID不能为空");
			}
			Integer memberId = getMemberIdByRequest(request);
			address.setMemberId(memberId);
			address.setCreateTime(CalendarUtils.getDate());
			if(addressService.saveOrUpdate(address)) {
				return JsonResultUtils.buildJsonOK(address);
			}
			return JsonResultUtils.buildJsonFailMsg("保存收货地址失败");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
	/**
	 * 根据ID获取会员收货地址信息
	 * 
	* @author chenbiao
	* @date 2019年8月12日 下午2:50:04
	* 参数说明 
	* 
	* @param token
	* @param id
	* @return
	 */
	@ApiOperation(value = "根据ID获取会员收货地址信息",response = Address.class)
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query",dataType = "int",name = "id")
	})
	@MemberLoginRequired
	@GetMapping(value = "info")
	public JsonResult info(HttpServletRequest request,@RequestParam Integer id) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			Address address =  addressService.getById(id);
			//判定收货地址归属人是否是当前会员
			if(null != address && address.getMemberId().equals(memberId) && address.getStatus().equals(SystemConstants.STATUS_OK)) {
				return JsonResultUtils.buildJsonOK(address);
			}
			return JsonResultUtils.buildJsonFailMsg("会员地址信息不存在");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
	/**
	 * 获取会员用户默认的收货地址信息
	* @author chenbiao
	* @date 2019年8月12日 下午2:53:24
	* 参数说明 
	* 
	* @param token
	* @return
	 */
	@ApiOperation(value = "获取会员默认收货地址信息")
	@MemberLoginRequired
	@GetMapping(value = "def")
	public JsonResult def(HttpServletRequest request) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			Address address =  addressService.getDefaultAddressByMemberId(memberId);
			//判定收货地址归属人是否是当前会员
			if(null != address && address.getStatus().equals(SystemConstants.STATUS_OK)) {
				return JsonResultUtils.buildJsonOK(address);
			}
			return JsonResultUtils.buildJsonFailMsg("会员地址信息不存在或已删除");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
	/**
	 * 根据ID删除会员收货地址信息
	 * 
	* @author chenbiao
	* @date 2019年8月12日 下午2:59:24
	* 参数说明 
	* 
	* @param token
	* @param id
	* @return
	 */
	@ApiOperation(value = "根据ID删除会员收货地址信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",dataType = "int",value = "收货地址ID",paramType = "int")
	})
	@MemberLoginRequired
	@DeleteMapping(value = "delete")
	public JsonResult delete(HttpServletRequest request,@RequestParam Integer id) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			Address address =  addressService.getById(id);
			//判定收货地址归属人是否是当前会员
			if(null != address && address.getMemberId().equals(memberId) && address.getStatus().equals(SystemConstants.STATUS_OK)) {
				address.setStatus(SystemConstants.STATUS_NO);
				addressService.updateById(address);
				return JsonResultUtils.buildJsonOK(address);
			}
			return JsonResultUtils.buildJsonFailMsg("会员地址信息不存在或已删除");
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

}

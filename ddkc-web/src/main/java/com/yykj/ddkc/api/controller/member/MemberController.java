package com.yykj.ddkc.api.controller.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.commons.ErrorCodeUtils;
import com.yykj.commons.ServiceConstants;
import com.yykj.commons.wechat.AppConfig;
import com.yykj.commons.wechat.MiniProgramUtils;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.MemberLoginRequired;
import com.yykj.ddkc.entity.Member;
import com.yykj.ddkc.request.MemberRequest;
import com.yykj.ddkc.service.MemberService;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 会员信息接口
 * 
* @author chenbiao
* @date 2019年8月13日 上午9:51:47 
*
 */
@Api(value = "会员信息接口",tags = "会员信息接口")
@RestController
@RequestMapping(value = "api/member")
public class MemberController extends BaseApiController {

	@Autowired
	AppConfig appConfig;
	
	@Autowired
	MemberService memberService;

	/**
	 * 根据临时登录凭证 code 进行 登录凭证校验 <br/>
	 * 
	 * 使用 code 换取 openid 和 session_key 等信息
	 * 
	 * @author chenbiao
	 * @date 2019年8月12日 上午10:58:34 参数说明
	 * 
	 * @return
	 */
	@ApiOperation(value = "根据code获取sessionKey和openid")
	@ApiImplicitParams({ @ApiImplicitParam(name = "code", value = "用户登录凭证code，有效期5分钟", paramType = "query") })
	@GetMapping(value = "code2Session")
	public JsonResult code2Session(String code) {
		JsonNode node = MiniProgramUtils.code2Session(appConfig.getAppid(), appConfig.getAppsecret(), code);
		return JsonResultUtils.buildJsonOK(node);
	}
	
	/**
	 * 客户端获取用户信息并执行登录接口
	 * 
	* @author chenbiao
	* @date 2019年8月12日 上午11:58:45
	* 参数说明 
	* 
	* @param code
	* @param memberRequest
	* @return
	 */
	@ApiOperation(value = "获取用户信息并登录" )
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "openId", value = "用户唯一标识", paramType = "query")
	})
	@PostMapping(value = "loginByUserInfo")
	public JsonResult loginByUserInfo(@RequestParam String openId,@RequestBody MemberRequest memberRequest) {
		if(StringUtils.isNotEmpty(openId)) {
			Member member = memberService.getByOpenId(openId);
			if(null != member) {//老用户
				//判定老用户是否是正常状态
				if(member.getStatus() == SystemConstants.STATUS_OK) {
					//正常用户直接下行token信息
					return JsonResultUtils.buildJsonOK(createTokenInfoByMember(member.getId()));
				}
				return JsonResultUtils.buildJsonFailMsg("用户已被锁定无法登录");
			}else {
				//新用户，需要先添加用户信息
				member = new Member(openId,memberRequest);
				if(memberService.insert(member) > 0) {
					return JsonResultUtils.buildJsonOK(createTokenInfoByMember(member.getId()));
				}
			}
		}
		return JsonResultUtils.buildJsonFailMsg("openId不能为空");
	}
	
	/**
	 * 退出登录
	* @author chenbiao
	* @date 2019年8月12日 下午2:07:36
	* 参数说明 
	* 
	* @param token
	* @return
	 */
	@ApiOperation(value = "退出登录")
	@MemberLoginRequired
	@GetMapping(value = "logout")
	public JsonResult logOut(HttpServletRequest request) {
		try {
			Integer memberId = getMemberIdByRequest(request);
			String token = getToken(request,"memberToken");
			String refreshToken = MD5Password.md5(token+memberId); //计算刷新token值
			redisUtils.deleKey(ServiceConstants.MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken); //删除刷新token与token的关联关系
			redisUtils.deleKey(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken);//删除刷新token
			redisUtils.deleKey(ServiceConstants.MEMBER_TOKEN_KEY+token); //删除用户当前token
			return JsonResultUtils.buildJsonOK();
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_LOGOUT_FAIL.getResult();
		}
	}
	/**
	 * 刷新token
	 * 
	* @author chenbiao
	* @date 2019年8月12日 下午2:15:34
	* 参数说明 
	* 
	* @param refreshToken
	* @return
	 */
	@ApiOperation(value ="刷新token")
	@MemberLoginRequired
	@RequestMapping(value="refreshToken",method=RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(name="refreshToken",value = "刷新令牌",paramType="query",required = true)
	})
	public JsonResult refreshToken(@RequestParam("refreshToken")String refreshToken) {
		try {
			if(redisUtils.validateStringKeyExists(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken)) {
				String memberId = redisUtils.getStringForKey(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken);
				String token = redisUtils.getStringForKey(ServiceConstants.MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken);
				redisUtils.deleKey(ServiceConstants.MEMBER_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken); //删除刷新token与token的关联关系
				redisUtils.deleKey(ServiceConstants.MEMBER_REFRESH_TOKEN_KEY+refreshToken);//删除刷新token
				redisUtils.deleKey(ServiceConstants.MEMBER_TOKEN_KEY+token); //删除用户当前token
				Member member = memberService.getById(Integer.parseInt(memberId));
				if(null!= member && member.getStatus() == SystemConstants.STATUS_OK) {					
					return JsonResultUtils.buildJsonOK(createTokenInfoByMember(member.getId()));
				}
				return ErrorCodeUtils.ERROR_ACCOUNT_NOT_EXISTS_OR_DELETE.getResult();
			}
			return ErrorCodeUtils.ERROR_REFRESH_TOKEN_NOT_EXISTS.getResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}

}

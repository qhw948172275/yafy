package com.yykj.ddkc.api.controller.shop;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.commons.ErrorCodeUtils;
import com.yykj.commons.ServiceConstants;
import com.yykj.commons.ip.IPUtils;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.ShopLoginRequired;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.entity.ShopLoginLog;
import com.yykj.ddkc.service.AboutUsService;
import com.yykj.ddkc.service.ShopAccountService;
import com.yykj.ddkc.service.ShopLoginLogService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.ddkc.vo.ShopLoginVO;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "商家登录")
@RestController
@RequestMapping(value = "api/shop/personalCenter")
public class ShopLoginController extends BaseApiController {
    @Autowired
    ShopService shopService;
    
    @Autowired
    ShopAccountService shopAccountService;
    @Autowired
    AboutUsService aboutUsService;
    @Autowired
    ShopLoginLogService shopLoginLogService;
    /**
     * 商家登录
     * @return
     */
    @ApiOperation(value = "商家登录")
    @PostMapping(value = "loginAndPassword")
    public JsonResult loginAndPassword(@RequestHeader(value = "X-Real-IP")String ip,@RequestBody ShopLoginVO shopLoginVO,HttpServletRequest request){
        try {
        	ShopAccount account = shopAccountService.getByAccount(shopLoginVO.getPhone());
        	if(null == account || account.getStatus() != 0) {
        		 return JsonResultUtils.buildJsonFail("该手机号不存在");
        	}else if(!account.getPassword().equals(shopLoginVO.getPassword())) {
        		 return JsonResultUtils.buildJsonFail("密码错误");
        	}
        	Shop shop = shopService.getById(account.getShopId());
        	if(null == shop) {
        		return JsonResultUtils.buildJsonFail("该商户不存在");
        	}else if(shop.getStatus() == 1) {
        		return JsonResultUtils.buildJsonFail("此店铺线上业务暂停");
        	}

        	if(account.getIsMaster() == 1) {
        		//更新主账号openID
        		account.setOpenId(shopLoginVO.getOpenId());
        		shopAccountService.updateById(account);
        		//更新商户信息中openID属性，用于提现
        		shop.setOpenid(shopLoginVO.getOpenId());
        		shopService.updateById(shop);
        	}
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            OperatingSystem os = userAgent.getOperatingSystem();
            JsonNode jsonNode= IPUtils.getIPInfo(ip);

            ShopLoginLog shopLoginLog=new ShopLoginLog();
            shopLoginLog.setLoginIp(ip);
            shopLoginLog.setAddress(IPUtils.getAddress(jsonNode));
            shopLoginLog.setProvince(IPUtils.getProvince(jsonNode));
            shopLoginLog.setLoginTimes(CalendarUtils.getDate());
            shopLoginLog.setShopId(shop.getId());
            shopLoginLog.setDevices(os.getName());
            shopLoginLogService.insert(shopLoginLog);
            return createTokenInfoByShop(shop.getId(),account.getId());
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }

    }

    /**
     * 验证通过后添加新密码
     * @param newPassword
     * @param code
     * @param phone
     * @return
     */
    @ApiOperation(value ="验证通过后添加新密码" )
    @ApiImplicitParams({
            @ApiImplicitParam(value = "新密码",name ="newPassword" ),
            @ApiImplicitParam(value = "验证码",name="code"),
            @ApiImplicitParam(value = "手机号",name="phone")
    })
    @GetMapping(value = "addNewPassword")
    public JsonResult addNewPassword(@RequestParam String newPassword,@RequestParam String code,@RequestParam String phone,HttpServletRequest request){
            try {
                String oldCode=redisUtils.getStringForKey(ServiceConstants.SHOP_MSG_CODE_KEY+phone);
                if(!code.equals(oldCode)){
                    return JsonResultUtils.buildJsonFailMsg("验证码不正确");
                }
                String token=getToken(request,"shopToken");
                String shopAccountId = redisUtils.getStringForKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token);
                ShopAccount account = shopAccountService.getById(Integer.parseInt(shopAccountId));
                if(null != account && account.getStatus() == 0) {
                	account.setPassword(newPassword);
                	shopAccountService.updateById(account);
                	return JsonResultUtils.buildJsonOK("修改成功");
                }
                return JsonResultUtils.buildJsonFailMsg("修改失败");
            }catch (Exception e){
                e.printStackTrace();
                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
            }
    }

    /**
     * 退出登录
     * @return
     */
    @ShopLoginRequired
    @ApiOperation(value = "退出登录")
    @GetMapping(value = "loginOut")
    public JsonResult loginOut(HttpServletRequest request){
        try {
            Integer shopId=getShopIdByRequest(request);
            String token=getToken(request,"shopToken");
            redisUtils.deleKey(ServiceConstants.SHOP_TOKEN_KEY+token);
            redisUtils.deleKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token);
            String refreshToken = MD5Password.md5(token+shopId);//刷新token实现方式，通过token和商家ID进行加密
            redisUtils.deleKey(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken);
            redisUtils.deleKey(ServiceConstants.SHOP_REFRESH_ACCOUNT_TOKEN_KEY+refreshToken);
            redisUtils.deleKey(ServiceConstants.SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 刷新token
     * @param refreshToken
     * @return
     */
    @ShopLoginRequired
    @GetMapping(value="refreshToken")
    @ApiImplicitParam(value = "刷新token",name = "refreshToken")
    @ApiOperation(value = "刷新token")
    public JsonResult refreshToken(@RequestParam("refreshToken")String refreshToken) {
        try {
            if(redisUtils.validateStringKeyExists(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken)) {
                String shopId = redisUtils.getStringForKey(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken);
                String shopAccountId = redisUtils.getStringForKey(ServiceConstants.SHOP_REFRESH_ACCOUNT_TOKEN_KEY+refreshToken);
                String token = redisUtils.getStringForKey(ServiceConstants.SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken);
                redisUtils.deleKey(ServiceConstants.SHOP_REFRESH_TOKEN_BIND_TOKEN_KEY+refreshToken); //删除刷新token与token的关联关系
                redisUtils.deleKey(ServiceConstants.SHOP_REFRESH_TOKEN_KEY+refreshToken);//删除刷新token
                redisUtils.deleKey(ServiceConstants.SHOP_TOKEN_KEY+token); //删除用户当前token
                redisUtils.deleKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token); //删除用户当前token
                Shop shop = shopService.getById(Integer.parseInt(shopId));
                ShopAccount account = shopAccountService.getById(Integer.parseInt(shopAccountId));
                if(null!= shop && shop.getStatus() == SystemConstants.STATUS_OK && account.getStatus() == SystemConstants.STATUS_OK) {
                    return JsonResultUtils.buildJsonOK(createTokenInfoByShop(shop.getId(),account.getId()));
                }
                return ErrorCodeUtils.ERROR_ACCOUNT_NOT_EXISTS_OR_DELETE.getResult();
            }
            return ErrorCodeUtils.ERROR_REFRESH_TOKEN_NOT_EXISTS.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCodeUtils.ERROR_500.getResult();
        }
    }
    /**
     * 
     * 获取登录商户账号信息
     * 
    * @author chenbiao
    * @date 2019年9月25日 下午10:51:06
    * 参数说明 
    * 
    * @param request
    * @return
     */
    @ShopLoginRequired
    @GetMapping(value="getShopAccountInfo")
    @ApiOperation(value = "获取登录商户账号信息")
    public JsonResult getShopAccountInfo(HttpServletRequest request) {
        try {
        	
        	String token=getToken(request,"shopToken");
        	String shopAccountId = redisUtils.getStringForKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token);
            if(StringUtils.isNotEmpty(shopAccountId)) {
            	ShopAccount account = shopAccountService.getById(Integer.parseInt(shopAccountId));
            	if(null != account && account.getStatus() == 0) {
            		//当前账号状态正常；
            		return JsonResultUtils.buildJsonOK(account);
            	}
                return ErrorCodeUtils.ERROR_ACCOUNT_NOT_EXISTS_OR_DELETE.getResult();
            }
            return ErrorCodeUtils.ERROR_TOKEN_NOT_EXISTS.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCodeUtils.ERROR_500.getResult();
        }
    }

}

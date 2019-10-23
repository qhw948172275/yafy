package com.yykj.ddkc.api.controller.commons;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yykj.commons.ErrorCodeUtils;
import com.yykj.commons.ServiceConstants;

import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.RandomUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**   
*  
* @ClassName: UserSendMessageController.java
* @Description: 用户统一发送短信
* @author: chenbiao
* @date: 2018年9月8日 下午5:57:46 
*/

@Api(value = "用户统一发送短信接口",description = "用户统一发送短信接口" )
@RestController
@RequestMapping(value="user/sms")
public class UserSendMessageController extends BaseApiController {

    @Autowired
	ShopService shopService;


	/**
	 * 用户发送验证码统一接口
		 * @return: 
		 * @author chenbiao
		 * @date 2018年9月6日
		 * @param
	 */
	@ApiOperation(value ="用户发送验证码统一接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="type",value = "1-商家，2-会员",paramType="query",dataType ="int"),
			@ApiImplicitParam(name="phone",value = "手机号",paramType="query")
	})
	@RequestMapping(value="captcha",method=RequestMethod.POST)
	public JsonResult captcha(Integer type, String phone) {

		try {
			String code = RandomUtils.getRandomNumr(6);// 生成随机验证码
			switch(type) {
				case 1 ://教师端短信验证码登录
					if(shopService.selectByPhone(phone).size()>0) {
						SendSmsResponse response = AliyunSMSUtils.sendByBindPhone(phone, code);//发送验证码
						//TODO 后期替换成客户的
						System.out.println("send sms response :"+ JsonUtils.beanToJson(response).toString());
						redisUtils.setStringForKey(ServiceConstants.SHOP_MSG_CODE_KEY+phone, code);
						redisUtils.setExpireKey(ServiceConstants.SHOP_MSG_CODE_KEY+phone, 2l, TimeUnit.MINUTES);
						return JsonResultUtils.buildJsonOK();
					}
					return ErrorCodeUtils.ERROR_PHONE_NOT_EXISTS.getResult();

				default:
					return ErrorCodeUtils.ERROR_400.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorCodeUtils.ERROR_500.getResult();
		}
	}
}

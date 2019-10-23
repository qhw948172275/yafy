package com.yykj.ddkc.api.controller.commons;

import javax.servlet.http.HttpServletRequest;

import com.yykj.ddkc.entity.Delivery;
import com.yykj.ddkc.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.upyun.UpYunUtils;
import com.yykj.ddkc.entity.AboutUs;
import com.yykj.ddkc.response.OrderProcessResponse;
import com.yykj.ddkc.service.AboutUsService;
import com.yykj.ddkc.service.OrderService;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(value = "公共接口",tags = "公共接口")
@RestController
@RequestMapping(value = "api")
public class CommonController {
	
	@Autowired
	AboutUsService aboutUsService;
	@Autowired
	OrderService orderService;
	@Autowired
	DeliveryService deliveryService;
	/**
	 * 公共协议接口
	* @author chenbiao
	* @date 2019年8月12日 下午3:28:01
	* 参数说明 
	* 
	* @param type
	* @return
	 */
	@ApiOperation(value = "根据类型获取信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "type",paramType = "int",value = "信息类型。0表示关于我们，1表示平台服务协议，2表示商家入驻协议")
	})
	@GetMapping(value = "aboutUs")
	public JsonResult aboutUs(@RequestParam Integer type) {
		AboutUs aboutUs = aboutUsService.selectByType(type);
		if(null != aboutUs) {			
			return JsonResultUtils.buildJsonOK(aboutUs);
		}
		return JsonResultUtils.buildJsonFailMsg("没有找到对应信息");
	}


	/**
	 * 订单流程
	 * @param orderId
	 * @return
	 */
	@ApiOperation(value = "订单流程",response = OrderProcessResponse.class)
	@GetMapping(value = "orderProcess")
	@ApiImplicitParam(value = "订单ID",name="orderId")
	public JsonResult orderProcess(@RequestParam Integer orderId, HttpServletRequest request){
		try {
			String shopToken = request.getHeader("shopToken");
			String memberToken = request.getHeader("memberToken");
			if(shopToken==null&&memberToken==null){
				return JsonResultUtils.buildJsonFailMsg("您还未登录");
			}
			OrderProcessResponse orderProcessResponse=orderService.selectOrderProcessResponseByOrderId(orderId);

			return JsonResultUtils.buildJsonOK(orderProcessResponse);
		}catch (Exception e){
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
	
	
	/**
	 * 公共图片文件上传
	 *
	 * @return
	 * @author chenbiao
	 * @date 2017年3月14日 下午9:21:59 参数说明
	 */
	@ResponseBody
	@RequestMapping(value = "upload/img", method = RequestMethod.POST)
	protected ObjectNode uploadImage(@RequestParam(value = "file") MultipartFile file, Model model) {
		ObjectNode json = JsonUtils.getMapperInstance().createObjectNode();
		if (null != file && file.getSize() > 0) {
			String fileName = UpYunUtils.uploadFtpFile(file, SystemConstants.UPLOAD_DIR);
			json.put("code", 0);
			json.put("msg", "");
			ObjectNode objectNode = JsonUtils.getMapperInstance().createObjectNode();
			objectNode.put("url", SystemConstants.VISIT_DIR + fileName);
			json.putPOJO("data", objectNode);
		}
		return json;
	}

	/**
	 * 公共图片文件上传
	 *
	 * @return
	 * @author chenbiao
	 * @date 2017年3月14日 下午9:21:59 参数说明
	 */
	@ResponseBody
	@RequestMapping(value = "upload/imgs", method = RequestMethod.POST)
	protected JsonResult uploadImages(@RequestParam(value = "file") CommonsMultipartFile[] files, Model model) {
		if (files.length > 0) {
			ArrayNode arrayNode = JsonUtils.getMapperInstance().createArrayNode();
			for (CommonsMultipartFile file : files) {
				if (null != file && file.getSize() > 0) {
					String fileName = UpYunUtils.uploadFile(file, SystemConstants.UPLOAD_DIR);
					arrayNode.add(SystemConstants.VISIT_DIR + fileName);
				}
			}
			return JsonResultUtils.buildJsonOK(arrayNode);
		}
		return JsonResultUtils.buildJsonFail();
	}

	/**
	 * 获取平台费用
	 * @return
	 */
	@ApiOperation(value ="获取平台费用" )
	@GetMapping(value = "platformCost")
	public JsonResult platformCost(){
		try {
			ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
			Delivery shopDelivery = deliveryService.getByType(1);
			json.putPOJO("shopDelivery",shopDelivery);
			return JsonResultUtils.buildJsonOK(json);
		}catch (Exception e){
			e.printStackTrace();
			return JsonResultUtils.buildJsonFailMsg(e.getMessage());
		}
	}
}

package com.yykj.ddkc.platform;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yykj.ddkc.entity.Delivery;
import com.yykj.ddkc.service.DeliveryService;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.controller.BaseController;

/**
 * 配送设置
 * 
* @author chenbiao
* @date 2019年8月16日 上午10:18:38 
*
 */
@Controller
@RequestMapping(value = "/platform/delivery")
public class DeliveryController extends BaseController {
	
	
	@Autowired
	DeliveryService deliveryService;
	
	/**
	 * 配送设置首页
	 *  
	* @author chenbiao
	* @date 2019年8月16日 上午10:19:17
	* 参数说明 
	* 
	* @param request
	* @return
	 */
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		//商家配送费
		Delivery delivery0 = deliveryService.getByType(0);
		model.addAttribute("info0", delivery0);
		//平台抽成
		Delivery delivery1= deliveryService.getByType(1);
		model.addAttribute("info1", delivery1);
		//配送范围
		Delivery delivery2 = deliveryService.getByType(2);
		model.addAttribute("info2", delivery2);
		return "ddkc/platform/delivery/index";
	}
	/**
	 * 保存配送设置
	* @author chenbiao
	* @date 2019年8月16日 上午10:20:08
	* 参数说明 
	* 
	* @param delivery
	* @param request
	* @return
	 */
	@ResponseBody
	@PostMapping(value = "")
	public JsonResult save(Delivery delivery,HttpServletRequest request) {
		if(delivery.getType() == null || delivery.getType() < 0 || delivery.getType() > 2) {
			return JsonResultUtils.buildJsonFailMsg("type参数错误");
		}
		if(null != delivery.getId()) {
			//保存操作
			if(deliveryService.updateById(delivery) > 0) {
				return JsonResultUtils.buildJsonOK();
			}
		}else {
			if(deliveryService.insert(delivery) > 0) {
				return JsonResultUtils.buildJsonOK();
			}
		}
		return JsonResultUtils.buildJsonFailMsg("操作失败");
	}

}

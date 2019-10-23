package com.yykj.commons;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.upyun.UpYunUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 公用接口
 * 
 * @author chenbiao
 * @date 2018年6月1日 下午5:50:57
 *
 */
@Api(value = "图片上传",description = "图片上传" )
@Controller(value="appConsoleCommonController")
@RequestMapping(value = "app/commons")
public class CommonController {



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
			objectNode.put("src", SystemConstants.VISIT_DIR + fileName);
			json.putPOJO("data", objectNode);
		}
		return json;
	}
}

package com.yykj.system.commons.result;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.system.commons.JsonUtils;

/**
 * 返回标准json格式,工具方法
 *
 * @author cym
 * @date 2017年2月22日 下午5:32:57
 */
public class JsonResultUtils {
	public static final String OK = "ok";
	public static final String FAIL = "fail";
	
	
	
	public static final String ERROR_CODE_404 = "404";
	public static final String ERROR_CODE_400 = "400";
	public static final String ERROR_CODE_500 = "500";
	
	public static final String SUCCESS_CODE = "200";

	/**
	 * 返回失败
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:14
	 */
	public static JsonResult buildJsonFail() {
		return buildJson(FAIL, null, null, null);
	}

	/**
	 * 返回失败,带object
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFail(Object object) {
		return buildJson(FAIL, object, null, null);
	}

	/**
	 * 返回失败,带object,errorCode
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFail(Object object, String errorCode) {
		return buildJson(FAIL, object, errorCode, null);
	}

	/**
	 * 返回失败,带object,errorCode,errorMsg
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFail(Object object, String errorCode, String errorMsg) {
		return buildJson(FAIL, object, errorCode, errorMsg);
	}

	/**
	 * 返回失败,带errorCode
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFailCode(String errorCode) {
		return buildJson(FAIL, null, errorCode, null);
	}
	
	/**
	 * 返回失败,带errorCode
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFailMsg(String errorMsg) {
		return buildJson(FAIL, null, null, errorMsg);
	}
	
	
	/**
	 * 返回失败,带errorCode,errorMsg
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:22
	 */
	public static JsonResult buildJsonFail(String errorCode, String errorMsg) {
		return buildJson(FAIL, null, errorCode, errorMsg);
	}
	
	
	/**
	 * 返回成功
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:34
	 */
	public static JsonResult buildJsonOK() {
		return buildJson(OK, null, SUCCESS_CODE, null);
	}
	/**
	 * description:封装带有数据的正确返回
	 * create by: qhw
	 * create time: 2019/11/25 0025 下午 22:42
	 */
	public static JsonResult buildJsonOKData(String dataName,Object o){
		ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
		json.putPOJO(dataName,o);
		return buildJsonOK(json);
	}

	/**
	 * 返回成功带Object
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:41
	 */
	public static JsonResult buildJsonOK(Object object) {
		return buildJson(OK, object, SUCCESS_CODE, null);
	}

	/**
	 * 返回自定义结果
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:51
	 */
	public static JsonResult buildJson(String result) {
		return buildJson(result, null, SUCCESS_CODE, null);
	}

	/**
	 * 返回自定义结果,带Object
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:33:57
	 */
	public static JsonResult buildJson(String result, Object object) {
		return buildJson(result, object, SUCCESS_CODE, null);
	}

	/**
	 * 放回自定义结果,带Object,errorCode
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:34:12
	 */
	public static JsonResult buildJson(String result, Object object, String errorCode) {
		return buildJson(result, object, errorCode, null);
	}

	/**
	 * 放回自定义结果,带Object,errorCode,errorMsg
	 *
	 * @author cym
	 * @date 2017年2月22日 下午5:34:12
	 */
	public static JsonResult buildJson(String result, Object object, String errorCode, String errorMsg) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setResult(result);
		jsonResult.setObject(object);
		jsonResult.setErrorCode(errorCode);
		jsonResult.setErrorMsg(errorMsg);

		return jsonResult;
	}
}

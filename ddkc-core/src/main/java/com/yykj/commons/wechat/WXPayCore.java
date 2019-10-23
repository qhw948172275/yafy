package com.yykj.commons.wechat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.yykj.system.commons.MD5Password;

public class WXPayCore {

	// public static final String MERCHANT_ID = "10027833";
	// public static final String APP_ID = "wx370180e8b65ed773";
	// public static final String APP_SECRET =
	// "42trip4219542195421954219542trip";
	// public static final String TRADE_TYPE_JSAPI = "JSAPI";
	// public static final String TRADE_TYPE_NATIVE = "NATIVE";
	// public static final String TRADE_TYPE_APP = "APP";

	/**
	 * 构造需要提交的请求数据
	 * 
	 * @param requestBodyMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildRequestContent(Map<String, String> requestBodyMap, 
			String paykey,
			String tradeType) throws UnsupportedEncodingException {
		requestBodyMap.put("sign", buildRequestMysign(createLinkString(requestBodyMap), paykey));
		return mapToXml(requestBodyMap);
	}

	/**
	 * map转换xml
	 * 
	 * @author chenbiao
	 * @date 2017年3月9日 下午4:10:41 参数说明
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToXml(Map<String, String> map) {
		StringBuffer stringBuffer = new StringBuffer("<xml>");
		for (String key : map.keySet()) {
			stringBuffer.append("<").append(key).append(">").append(map.get(key)).append("</").append(key).append(">");
		}
		stringBuffer.append("</xml>");
		return stringBuffer.toString();
	}

	/**
	 * 计算微信支付签名
	 * 
	 * @param prestr
	 * @return
	 */
	public static String buildRequestMysign(String prestr, String partnerKey) {
		if (null != prestr) {
				prestr = prestr + "&key=" + partnerKey;
			return sign(prestr, "UTF-8").toUpperCase();
		}
		return null;
	}

	/**
	 * 验签
	 * 
	 * @param map
	 * @return
	 */
	public static boolean verifyByNotifyCallBack(Map<String, String> map,String partnerKey) {
		if (null != map) {
			String sign = map.get("sign");
			
			String newSign = buildRequestMysign(createLinkString(map), partnerKey);
			
			System.out.println(sign + " : " + newSign);
			if (sign.equalsIgnoreCase(newSign)) {
				return true;
			}
			return false;
		}
		return false;
	}
//	/**
//	 * 扫码支付验签
//	 * 
//	* @author chenbiao
//	* @date 2017年7月18日 下午9:35:01
//	* 参数说明 
//	* 
//	* @param map
//	* @param systemConfig
//	* @return
//	 */
//	public static boolean verifyByNotifyCallBackByScan(Map<String, String> map, SystemConfig systemConfig) {
//		if (null != map) {
//			String sign = map.get("sign");
//			
//			String newSign = buildRequestMysign(createLinkString(map), systemConfig,ServiceConstants.ORDER_TRADE_TYPE_WECHAT_NATIVE);
//			System.out.println(sign + " : " + newSign);
//			if (sign.equalsIgnoreCase(newSign)) {
//				return true;
//			}
//			return false;
//		}
//		return false;
//	}

	public static Map<String, String> buildContentMap(String notifyContent) throws DocumentException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Document doc_notify_data = DocumentHelper.parseText(notifyContent.toString());
		map.put("return_code", doc_notify_data.selectSingleNode("//xml/return_code").getText());// 通信标识，非交易标识
		if ("SUCCESS".equals(map.get("return_code"))) {
			map.put("appid", doc_notify_data.selectSingleNode("//xml/appid").getText());// 公众号ID
			map.put("mch_id", doc_notify_data.selectSingleNode("//xml/mch_id").getText());// 商户号
			map.put("nonce_str", doc_notify_data.selectSingleNode("//xml/nonce_str").getText());// 随机字符串
			map.put("sign", doc_notify_data.selectSingleNode("//xml/sign").getText());// 签名
			map.put("result_code", doc_notify_data.selectSingleNode("//xml/result_code").getText());// 业务结果
			if (null != doc_notify_data.selectSingleNode("//xml/device_info")) {
				map.put("device_info", doc_notify_data.selectSingleNode("//xml/device_info").getText());// 设备号
			}
			if (null != doc_notify_data.selectSingleNode("//xml/err_code")) {
				map.put("device_info", doc_notify_data.selectSingleNode("//xml/err_code").getText());// 错误代码
			}
			if (null != doc_notify_data.selectSingleNode("//xml/err_code_des")) {
				map.put("device_info", doc_notify_data.selectSingleNode("//xml/err_code_des").getText());// 错误代码描述
			}
			if ("SUCCESS".equals(map.get("result_code"))) {
				map.put("openid", doc_notify_data.selectSingleNode("//xml/openid").getText());// 用户标识
				map.put("is_subscribe", doc_notify_data.selectSingleNode("//xml/is_subscribe").getText());// 是否关注公众号
				map.put("trade_type", doc_notify_data.selectSingleNode("//xml/trade_type").getText());// 交易类型
				map.put("bank_type", doc_notify_data.selectSingleNode("//xml/bank_type").getText());// 付款银行
				map.put("total_fee", doc_notify_data.selectSingleNode("//xml/total_fee").getText());// 总金额
				if (null != doc_notify_data.selectSingleNode("//xml/cash_fee")) {
					map.put("cash_fee", doc_notify_data.selectSingleNode("//xml/cash_fee").getText());// 现金券金额
				}
				if (null != doc_notify_data.selectSingleNode("//xml/coupon_fee")) {
					map.put("coupon_fee", doc_notify_data.selectSingleNode("//xml/coupon_fee").getText());// 现金券金额
				}
				if (null != doc_notify_data.selectSingleNode("//xml/fee_type")) {
					map.put("fee_type", doc_notify_data.selectSingleNode("//xml/fee_type").getText());// 货币种类
				}
				map.put("transaction_id", doc_notify_data.selectSingleNode("//xml/transaction_id").getText());// 微信支付订单号
				map.put("out_trade_no", doc_notify_data.selectSingleNode("//xml/out_trade_no").getText());// 商户订单号
				if (null != doc_notify_data.selectSingleNode("//xml/attach")) {
					map.put("attach", doc_notify_data.selectSingleNode("//xml/attach").getText());// 商家数据包
				}
				map.put("time_end", doc_notify_data.selectSingleNode("//xml/time_end").getText());// 支付完成时间
			}
		} else {
			map.put("return_msg", doc_notify_data.selectSingleNode("//xml/return_msg").getText());// 返回信息
		}
		return map;
	}
	/**
	 * 
	* 扫码支付功能回调数据
	* @author chenbiao
	* @date 2017年7月18日 下午7:06:55
	* 参数说明 
	* 
	* @param xmlStr
	* @return
	* @throws DocumentException
	 */
	public static Map<String,String> xmlToMapByScan(String xmlStr) throws DocumentException{
		Map<String,String> result = new HashMap<String,String>();
		Document data = DocumentHelper.parseText(xmlStr.toString());
		result.put("appid", data.selectSingleNode("//xml/appid").getText());
		result.put("openid", data.selectSingleNode("//xml/openid").getText());
		result.put("mch_id", data.selectSingleNode("//xml/mch_id").getText());
		result.put("is_subscribe", data.selectSingleNode("//xml/is_subscribe").getText());
		result.put("nonce_str", data.selectSingleNode("//xml/nonce_str").getText());
		result.put("product_id", data.selectSingleNode("//xml/product_id").getText());
		result.put("sign", data.selectSingleNode("//xml/sign").getText());
		return result;
	}
	
	public static void main(String[] args) throws DocumentException {
		
		
		String str = "<xml><appid><![CDATA[wxcb9e5592529ab891]]></appid><openid><![CDATA[oxADd1Se-YF5tk0nItMfKMdgjLYY]]></openid><mch_id><![CDATA[1464109202]]></mch_id><is_subscribe><![CDATA[Y]]></is_subscribe><nonce_str><![CDATA[gStYC0GPOqUqYPoY]]></nonce_str><product_id><![CDATA[201707182051018871]]></product_id><sign><![CDATA[FE3850DD9A6C59DBD73EB5D0758F4672]]></sign></xml>";
		boolean flag = verifyByNotifyCallBack(xmlToMapByScan(str), null);
		System.out.println(flag);
	}

	public static String sign(String text, String input_charset) {
		return MD5Password.MD5Encode(text, input_charset);
	}

//	@Deprecated
//	private static byte[] getContentBytes(String content, String charset) {
//		if (charset == null || "".equals(charset)) {
//			return content.getBytes();
//		}
//		try {
//			return content.getBytes(charset);
//		} catch (UnsupportedEncodingException e) {
//			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
//		}
//	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if ("sign".equals(key)) {
				continue;
			}
			if (null != value && !"".equals(value)) {
				// 拼接时，不包括最后一个&字符
				if(prestr != ""){
					prestr = prestr + "&" + key + "=" + value;
				}else{
					prestr = prestr + key + "=" + value;
				}
			}
		}
		System.out.println("-----" + prestr);
		return prestr;
	}
}

package com.yykj.commons.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yykj.commons.ServiceConstants;
import com.yykj.ddkc.entity.Member;
import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.entity.WechatNotify;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.NumberUtils;
import com.yykj.system.commons.SystemConstants;

/**
 * 微信支付相关帮助类
 * 
 * @author chenbiao
 * @date 2018年6月5日 下午5:25:02
 *
 */
public class WechatUtils {

	static Logger logger = LoggerFactory.getLogger(WechatUtils.class);

	public static final String RETURN_CODE_SUCCESS = "success";
	
	/**
	 * 微信支付统一下单接口，返回预支付订单信息
	 * 
	 * @param order
	 * @param string2
	 * @param wechatPayAppid
	 * @param wxConfig
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> getPrePayMapInfo(Order order, AppConfig payInfo, Member member) throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("appid", payInfo.getAppid());
		map.put("mch_id", payInfo.getMerchantId());
		map.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
		map.put("body", order.getOrderNumber());
		map.put("attach", "");
		map.put("out_trade_no", order.getOrderNumber());// 订单号
		map.put("total_fee",
				String.valueOf(new Double(NumberUtils.doubleMultiply(order.getTotalPrice().doubleValue(), 100)).intValue()));// 设置总金额
		map.put("spbill_create_ip", ServiceConstants.MINIPROGRAM_SPILL_CREATE_IP);//用户下单ip
		map.put("time_start", CalendarUtils.dateToString(order.getCreateTime(), CalendarUtils.yyyyMMddHHmmss));// 下单时间
		map.put("time_expire", CalendarUtils.timeStampToString(
								order.getCreateTime().getTime() + ServiceConstants.TIME_EXPIRE,
								CalendarUtils.yyyyMMddHHmmss));// 订单失效时间
		map.put("goods_tag", "");
		map.put("trade_type", payInfo.getTradeType());
		map.put("notify_url", payInfo.getPayNotifyUrl());
		map.put("openid", member.getOpenId());
		String body = WXPayCore.buildRequestContent(map, payInfo.getPayKey(),payInfo.getTradeType());
		try {
			
			//采用绕过验证的方式处理https请求  
	        SSLContext sslcontext = createIgnoreVerifySSL();  

	        //设置协议http和https对应的处理socket链接工厂的对象  
	        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	            .register("http", PlainConnectionSocketFactory.INSTANCE)  
	            .register("https", new SSLConnectionSocketFactory(sslcontext))  
	            .build();  
	        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
	        HttpClients.custom().setConnectionManager(connManager); 
			
			HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
			post.addHeader("Content-type", "text/xml");
			post.addHeader("Charset", "utf-8");
			
			HttpEntity entity = new ByteArrayEntity(body.getBytes(SystemConstants.DEFAULT_CHARSET));
			post.setEntity(entity);
			
			CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build(); 
			CloseableHttpResponse response = client.execute(post); 
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(jsonStr);
			Map<String,String> doXMLtoMap = xml2Map(jsonStr);
			return doXMLtoMap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	* 绕过验证 
	*   
	* @return 
	* @throws NoSuchAlgorithmException  
	* @throws KeyManagementException  
	*/  
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	        SSLContext sc = SSLContext.getInstance("SSLv3");  

	        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
	        X509TrustManager trustManager = new X509TrustManager() {  
	            @Override  
	            public void checkClientTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  

	            @Override  
	            public void checkServerTrusted(  
	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                    String paramString) throws CertificateException {  
	            }  

	            @Override  
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	                return null;  
	            }  
	        };  

	        sc.init(null, new TrustManager[] { trustManager }, null);  
	        return sc;  
	    }


	/**
	 * 微信退款
	 * 
	 * 
	 * 
	 * <xml>
		   <return_code><![CDATA[SUCCESS]]></return_code>
		   <return_msg><![CDATA[OK]]></return_msg>
		   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
		   <mch_id><![CDATA[10000100]]></mch_id>
		   <nonce_str><![CDATA[NfsMFbUFpdbEhPXP]]></nonce_str>
		   <sign><![CDATA[B7274EB9F8925EB93100DD2085FA56C0]]></sign>
		   <result_code><![CDATA[SUCCESS]]></result_code>
		   <transaction_id><![CDATA[1008450740201411110005820873]]></transaction_id>
		   <out_trade_no><![CDATA[1415757673]]></out_trade_no>
		   <out_refund_no><![CDATA[1415701182]]></out_refund_no>
		   <refund_id><![CDATA[2008450740201411110000174436]]></refund_id>
		   <refund_channel><![CDATA[]]></refund_channel>
		   <refund_fee>1</refund_fee> 
		</xml>
	 * 
	 * @param order
	 * @param wxNotify
	 * @param refund
	 * @param wxConfig
	 * @return
	 * @throws Exception
	 */
	public static Map<?, ?> refundByWXPay(String refundNo,Order order, WechatNotify wechatNotify,AppConfig appConfig) throws Exception {
		// 退款请求地址
		String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

//		String appid = null;
//		String merchantId = null;
//		String secret = null;
//		String paySecret = null;
//		String certp12 = null;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appConfig.getAppid());
		packageParams.put("mch_id", appConfig.getMerchantId());
		packageParams.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));

		packageParams.put("out_trade_no", wechatNotify.getOutTradeNo());
		packageParams.put("transaction_id", wechatNotify.getTransactionId());
		packageParams.put("out_refund_no", refundNo);
		packageParams.put("total_fee", wechatNotify.getTotalFee().toString());
		packageParams.put("refund_fee",
				String.valueOf(new Double(NumberUtils.doubleMultiply(order.getTotalPrice().doubleValue(), 100)).intValue()));
		//packageParams.put("op_user_id", member);

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appConfig.getAppid(), appConfig.getAppsecret(), appConfig.getPayKey());
		// 签名
		String sign = reqHandler.createSign(packageParams);
		packageParams.put("sign", sign);
		// 组装提交参数
		String arrayToXml = arrayToXml(packageParams);
		Map<?, ?> call = call(url, arrayToXml, appConfig.getP12(), appConfig.getMerchantId());
		logger.info(JsonUtils.beanToJson(call));
		return call;
	}

	/**
	 * 构造需要提交的请求数据
	 * 
	 * @param requestBodyMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildRequestContent(Map<String, String> requestBodyMap,AppConfig payInfo)
			throws UnsupportedEncodingException {
		requestBodyMap.put("sign", buildRequestMysign(createLinkString(requestBodyMap), payInfo.getPayKey()));
		StringBuffer stringBuffer = new StringBuffer("<xml>");
		for (String key : requestBodyMap.keySet()) {
			stringBuffer.append("<").append(key).append(">").append(requestBodyMap.get(key)).append("</").append(key)
					.append(">");
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
			return sign(prestr, SystemConstants.DEFAULT_CHARSET).toUpperCase();
		}
		return null;
	}

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
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
					prestr = prestr + key + "=" + value;
				} else {
					prestr = prestr + key + "=" + value + "&";
				}
			}
		}
		System.out.println("-----" + prestr);
		return prestr;
	}

	public static String sign(String text, String input_charset) {
		return MD5Password.MD5Encode(text, input_charset);
	}

	/**
	 * 把HashMap转换成xml
	 * 
	 * @param arr
	 * @return #time:下午5:32:36
	 *
	 */
	public static String arrayToXml(SortedMap<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (isNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	private static Map<String, String> xml2Map(String strxml) {

		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(strxml);// 将xml转为dom对象
			Element root = doc.getRootElement();// 获取根节点
			map = recGetXmlElementValue(root, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> recGetXmlElementValue(Element ele, Map<String, String> map) {
		List<Element> eleList = ele.elements();
		if (eleList.size() == 0) {
			map.put(ele.getName(), ele.getTextTrim());
			return map;
		} else {
			for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
				Element innerEle = iter.next();
				recGetXmlElementValue(innerEle, map);
			}
			return map;
		}
	}

	/**
	 * 带上证书进行请求，将返回数据转换成map方便下一步操作
	 * 
	 * @author chenbiao
	 * @date 2018年6月5日 下午8:25:39 参数说明
	 * 
	 * @param url
	 * @param arrayToXml
	 * @param certp12
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> call(String url, String arrayToXml, String certp12, String merchantId) throws Exception {
		Map<String, String> doXMLtoMap = new HashMap<String, String>();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		
		InputStream instream = WechatUtils.class.getResourceAsStream("/"+certp12);
		try {
			keyStore.load(instream, merchantId.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, merchantId.toCharArray()).build();
		// Allow TLSv1 protocol only
		// SSLConnectionSocketFactory sslsf = new
		// SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" },
		// null,
		// SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
				NoopHostnameVerifier.INSTANCE);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(arrayToXml, "UTF-8"));
			System.out.println(httpPost.getURI());
			System.out.println("executing request" + httpPost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpPost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			doXMLtoMap = xml2Map(jsonStr);
			System.out.println(jsonStr);
			response.close();

		} finally {

			httpclient.close();
		}
		return doXMLtoMap;
	}
}

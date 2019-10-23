package com.yykj.commons;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.system.commons.JsonUtils;


/**
 * 订单语音通知播报帮助类
* @author chenbiao
* @date 2019年9月23日 下午3:39:29 
*
 */
public class NoticeUtils {
	
	public static final String appcode = "7120813b929142eb875d7153f834ceb2";
	
	/**
	 * 向商家通知语音确认
	* @author chenbiao
	* @date 2019年9月23日 下午5:39:08
	* 参数说明 
	* 
	* @param phpone
	* @return
	 */
	public static JsonNode sendVoicenotifySMS(String phone) {
		String host = "http://mbyytz.market.alicloudapi.com";
	    String path = "/mobai_voicenotifysms";
	    String method = "POST";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    //querys.put("param", "param");
	    querys.put("phone", phone);
//	    querys.put("phone", "15184431499");
	    querys.put("templateId", "TP19092315");
	    Map<String, String> bodys = new HashMap<String, String>();


	    try {
	    	HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    	return JsonUtils.stringToJson(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	public static void main(String[] args) {
		String host = "http://mbyytz.market.alicloudapi.com";
	    String path = "/mobai_voicenotifysms";
	    String method = "POST";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    //querys.put("param", "param");
	    querys.put("phone", "18782931093");
//	    querys.put("phone", "15184431499");
	    querys.put("templateId", "TP19092315");
	    Map<String, String> bodys = new HashMap<String, String>();


	    try {
	    	HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

}

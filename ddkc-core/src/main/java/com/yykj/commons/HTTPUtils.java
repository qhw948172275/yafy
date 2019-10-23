package com.yykj.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.yykj.system.commons.JsonUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Map;

public class HTTPUtils {

	private static Logger log = LoggerFactory.getLogger(HTTPUtils.class);



	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final int CONNECT_TIME_OUT = 5000; //链接超时时间3秒

	private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).build();

	private static SSLContext wx_ssl_context = null; //微信支付ssl证书

	private static final String MCH_ID  = "1000000000";//证书密码默认是商户号


	public static String sendGetHttpRequest(String getUrl, Map<String, Object> parameters) {
		String lines = "";
		BufferedReader in = null;
		try {
			String parameStr = "";
			if (parameters != null && !parameters.isEmpty()) {
				for (String parameName : parameters.keySet())
					parameStr += parameName + "=" + parameters.get(parameName) + "&";
				getUrl = getUrl + "?" + parameStr;
			}
			URL url = new URL(getUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();// 打开连接
			openConnection.setRequestProperty("Accept", "*/*");
			openConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			openConnection.setRequestMethod("GET");
			in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
			String value;
			while ((value = in.readLine()) != null)
				lines += value;
			in.close();
			// 断开连接
			openConnection.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines.equals("") ? null : lines;
	}

	public static String getGet(String getUrl, Map<String, String> parameters) {
		String lines = "";
		BufferedReader in = null;
		try {
			String parameStr = "";
			if (parameters != null && !parameters.isEmpty()) {
				for (String parameName : parameters.keySet())
					parameStr += parameName + "=" + parameters.get(parameName) + "&";
				getUrl = getUrl + "?" + parameStr;
			}
			URL url = new URL(getUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();// 打开连接
			openConnection.setRequestProperty("Accept", "*/*");
			openConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			openConnection.setRequestMethod("GET");
			in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
			String value;
			while ((value = in.readLine()) != null)
				lines += value;
			in.close();
			// 断开连接
			openConnection.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines.equals("") ? null : lines;
	}

	public static String sendGetHttpRequest(String getUrl) {
		String lines = "";
		BufferedReader in = null;
		try {
			URL url = new URL(getUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();// 打开连接
			openConnection.setRequestMethod("GET");// 默认请求方式
			openConnection.setRequestProperty("Accept", "*/*");
			openConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
			String value;
			while ((value = in.readLine()) != null)
				lines += value;
			in.close();
			// 断开连接
			openConnection.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines.equals("") ? null : lines;
	}

	public static <T> String sendPostHttpRequest(String getUrl, Map<String, T> parameters) {
		PrintWriter out = null;
		BufferedReader in = null;
		String lines = "";
		try {
			String parameStr = "";
			if (parameters != null && !parameters.isEmpty()) {
				for (String parameName : parameters.keySet())
					parameStr += parameName + "=" + parameters.get(parameName) + "&";
				parameStr = parameStr.substring(0, parameStr.length() - 1);
			}
			URL url = new URL(getUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();// 打开连接
			openConnection.setRequestProperty("Accept", "*/*");
			openConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			openConnection.setRequestMethod("POST");
			System.out.println(parameStr);
			// 发送POST请求必须设置如下两行
			// 打开读写
			openConnection.setDoOutput(true);
			openConnection.setDoInput(true);
			// 设置连接类型
			openConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// openConnection.connect();//开始连接
			// openConnection.getInputStream()隐含openConnection.connect()
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(openConnection.getOutputStream());
			if (!parameStr.equals(""))
				// 发送请求参数
				out.print(parameStr);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null)
				lines += line;
			// 断开连接
			openConnection.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return lines.equals("") ? null : lines;
	}

	public static String sendPostHttpRequest(String getUrl) {
		return sendPostHttpRequest(getUrl, "");
	}

	public static String sendPostHttpRequest(String getUrl, String parameStr) {
		PrintWriter out = null;
		BufferedReader in = null;
		String lines = "";
		try {
			URL url = new URL(getUrl);
			HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();// 打开连接
			openConnection.setRequestProperty("Accept", "*/*");
			openConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); // 模拟ie浏览器
			openConnection.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			// 打开读写
			openConnection.setDoOutput(true);
			openConnection.setDoInput(true);
			// 设置连接类型
			openConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// openConnection.connect();//开始连接
			// openConnection.getInputStream()隐含openConnection.connect()
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(openConnection.getOutputStream());
			// 发送请求参数
			if (parameStr != null && !parameStr.equals(""))
				out.print(parameStr);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null)
				lines += line;
			// 断开连接
			openConnection.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return lines.equals("") ? null : lines;
	}

	/**
	 * 发送post请求
	 * @param strURL 请求路径
	 * @param params 请求参数
	 * @return
	 */
	public static byte[] doPost(String strURL, String params){
		System.out.println(strURL);
		System.out.println(params);
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("contentType", "utf-8");
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
			//	String result = new String(data, "utf-8"); // utf-8编码
//				System.out.println(result);
				return data;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; // 自定义错误信息
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param responseMsg
	 *            要回送的消息
	 *            状态码 正常：200 异常：400
	 */
	public static void doPost(HttpServletRequest request, HttpServletResponse response, String responseMsg) {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");// HTTP 1.1
			response.setHeader("Pragma", "no-cache");// HTTP 1.0
			response.setDateHeader("Expires", 0);

			PrintWriter writer = response.getWriter();
			writer.write(responseMsg);
			writer.flush();
			writer.close();

		} catch (Exception ex) {
			log.error("Error Message------>" + ex.getMessage());
		}
	}

	public static JsonNode httpGet(String url) {
		try {
			return JsonUtils.stringToJson(sendPostHttpRequest(url));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Post请求+证书
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String doRefund(String url,String data,String sslPath,String machId) throws Exception {
		/**
		 * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		 */
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		/**
		 *此处要改
		 *wxconfig.SSLCERT_PATH : 指向你的证书的绝对路径，带着证书去访问
		 */
		InputStream is = HTTPUtils.class.getResourceAsStream("/"+sslPath);
//		ClassPathResource cpr = new ClassPathResource(sslPath);
//		FileInputStream instream = new FileInputStream(cpr.getFile());//P12文件目录
		try {
			/**
			 * 此处要改
			 *
			 * 下载证书时的密码、默认密码是你的MCHID mch_id
			 * */
			keyStore.load(is, machId.toCharArray());//这里写密码
		} finally {
			is.close();
		}

		// Trust own CA and all self-signed certs
		/**
		 * 此处要改
		 * 下载证书时的密码、默认密码是你的MCHID mch_id
		 * */
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, machId.toCharArray())//这里也是写密码的
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1" },
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
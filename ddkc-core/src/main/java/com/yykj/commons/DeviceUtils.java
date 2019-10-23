package com.yykj.commons;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DeviceUtils {
	

    /**电脑上的IE或Firefox浏览器等的User-Agent关键词*/
    private static String[] pcHeaders=new String[]{
    "Windows 98",
    "Windows ME",
    "Windows 2000",
    "Windows XP",
    "Windows NT",
    "Ubuntu"
    };
    /**手机浏览器的User-Agent里的关键词*/
    private static String[] mobileUserAgents=new String[]{
    "Nokia",//诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0 (Nokia5800 XpressMusic)UC AppleWebkit(like Gecko) Safari/530
    "SAMSUNG",//三星手机 SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1
    "MIDP-2",//j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaE75-1 /110.48.125 Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like Gecko) Safari/413
    "CLDC1.1",//M600/MIDP2.0/CLDC1.1/Screen-240X320
    "SymbianOS",//塞班系统的，
    "MAUI",//MTK山寨机默认ua
    "UNTRUSTED/1.0",//疑似山寨机的ua，基本可以确定还是手机
    "Windows CE",//Windows CE，Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; IEMobile 7.11)
    "iPhone",//iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117
    "iPad",//iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10
    "Android",//Android是否也转wap？Mozilla/5.0 (Linux; U; Android 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7) AppleWebKit/530.17 (KHTML like Gecko) Version/4.0 Mobile Safari/530.17
    "BlackBerry",//BlackBerry8310/2.7.0.106-4.5.0.182
    "UCWEB",//ucweb是否只给wap页面？ Nokia5800 XpressMusic/UCWEB7.5.0.66/50/999
    "ucweb",//小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;) Opera ucweb-squid
    "BREW",//很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId: 40105; Lang: zhcn) ucweb-squid
    "J2ME",//很奇怪的ua，只有J2ME四个字母
    "YULONG",//宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0
    "YuLong",//还是宇龙
    "COOLPAD",//宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0
    "TIANYU",//天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320
    "TY-",//天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0
    "K-Touch",//还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
    "Haier",//海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007 Browser/WAP2.0
    "DOPOD",//多普达手机
    "Lenovo",// 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22 Profile/MIDP2.0 Configuration/CLDC1.1
    "LENOVO",// 联想手机，比如：LENOVO-P780/176A
    "HUAQIN",//华勤手机
    "AIGO-",//爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0
    "CTC/1.0",//含义不明
    "CTC/2.0",//含义不明
    "CMCC",//移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0
    "DAXIAN",//大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0
    "MOT-",//摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1 Software/R533_G_11.10.54R
    "SonyEricsson",// 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0 (compatible; MSIE 6.0; Symbian OS; 405) Opera 8.65 [zh-CN]
    "GIONEE",//金立手机
    "HTC",//HTC手机
    "ZTE",//中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile
    "HUAWEI",//华为手机，
    "webOS",//palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN) AppleWebKit/532.2 (KHTML like Gecko) Version/1.0 Safari/532.2 Pre/1.0
    "GoBrowser",//3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290 Safari
    "IEMobile",//Windows CE手机自带浏览器，
    "WAP2.0"//支持wap 2.0的
    };
	/**
    * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
    * @param request http请求
    * @return 如果命中手机特征规则，则返回对应的特征字符串
    */
    public static boolean isMobileDevice(HttpServletRequest request){
        boolean b = false;
        boolean pcFlag = false;
        boolean mobileFlag = false;
        String userAgent = request.getHeader("User-Agent");
        for (int i = 0;!mobileFlag && userAgent!=null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
            if(userAgent.contains(mobileUserAgents[i])){
                mobileFlag = true;
                break;
            }
        }
        for (int i = 0; userAgent!=null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
            if(userAgent.contains(pcHeaders[i])){
                pcFlag = true;
                break;
            }
        }
        if(mobileFlag==true && pcFlag==false){
            b=true;
        }
        return b;//false pc  true shouji
    
    }
    
    
    public static String getAgent(HttpServletRequest  request){
    	return request.getHeader("User-Agent");
    }
    public static String getDevice(HttpServletRequest  request){
    	 String userAgent = request.getHeader("User-Agent");
         for (int i = 0;userAgent!=null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
             if(userAgent.contains(mobileUserAgents[i])){
               return mobileUserAgents[i];
             }
         }
         for (int i = 0; userAgent!=null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
             if(userAgent.contains(pcHeaders[i])){
                return pcHeaders[i];
             }
         }
         return null;
    }
    /**
     * 
    * 判定是否是苹果设备
    * 
    * @author chenbiao
    * @date 2017年3月24日 下午3:30:25
    * 参数说明 
    * 
    * @param request
    * @return
     */
    public static boolean isIphoneDevice(HttpServletRequest request){
        boolean mobileFlag = false;
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        for (int i = 0;!mobileFlag && userAgent!=null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
            if(userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("imac")){
                mobileFlag = true;
                break;
            }
        }
        return mobileFlag;
    }
    
    /**
	 * get the real IP
	 * @param request
	 * @return
	 */
	public static String getRealIP(HttpServletRequest request) {
		if(request == null) {
			request = getHttpRequest();
		}
		if(request == null) return null;
		String realIP = getHttpHeader(request, "x-real-ip");
		if(StringUtils.isBlank(realIP)) {
			realIP = request.getRemoteAddr();
		}
		return realIP;
	}
	
	/**
	 * get cookie value with cookie name.
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static String getCookie(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equalsIgnoreCase(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * get httpservletRequest by spring
	 * @return
	 */
	public static HttpServletRequest getHttpRequest() {
		ServletRequestAttributes servletAttr = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(servletAttr == null) return null;
		return  servletAttr.getRequest();
	}

	public static String getHttpHeader(HttpServletRequest request, String headerName) {
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String _header = headerNames.nextElement();
			if(headerName.equalsIgnoreCase(_header)) {
				return request.getHeader(_header);
			}
		}
		return null;
	}
	/**
	 * 判定userAgent是否是微信浏览器
	* @author chenbiao
	* @date 2017年7月18日 上午10:41:23
	* 参数说明 
	* 
	* @param userAgent
	* @return
	 */
	public static boolean isMicroMessage(String userAgent) {
		if(null != userAgent && userAgent.contains("MicroMessenger")){
			return true;
		}
		return false;
	}
}

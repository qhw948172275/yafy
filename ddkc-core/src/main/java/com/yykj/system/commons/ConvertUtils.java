package com.yykj.system.commons;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/** 
 * 类型转换帮助类
 * 
 * @author BILL CHEN
 * @date 2016年6月29日 下午6:59:39 
 *  
 */
public class ConvertUtils{

	/**
	 * 将客户端传递过来的计费点参数转换成list
	 * 
	 * @author kimi
	 * @dateTime 2012-7-24 下午1:00:23
	 * @param str 计费点参数,字符串。如："1,2,3"
	 * @return 返回计费点集合
	 */
	public static List<Integer> convertString(String str) {
		if (null != str && !"".equals(str)) {
			String[] arr = str.split(",");
			List<Integer> ls = new ArrayList<Integer>(arr.length);
			for (int i = 0; i < arr.length; i++) {
				ls.add(Integer.valueOf(arr[i]));
			}
			return ls;
		}
		return null;
	}

	/**
	 * 将客户端传递过来的计费点参数转换成list
	 * 
	 * @author kimi
	 * @dateTime 2012-7-24 下午1:00:23
	 * @param str 计费点参数,字符串。如："1,2,3"
	 * @param regex 字符串分割方式
	 * @return 返回计费点集合
	 * @return
	 */
	public static List<Integer> convertString(String str, String regex) {
		if (null != str && !"".equals(str)) {
			String[] arr = str.split(regex);
			List<Integer> ls = new ArrayList<Integer>(arr.length);
			for (int i = 0; i < arr.length; i++) {
				ls.add(Integer.valueOf(arr[i]));
			}
			return ls;
		}
		return null;
	}

	/**
	 * 将客户端传递过来的计费点参数转换成list
	 * 
	 * @author kimi
	 * @dateTime 2012-7-24 下午1:00:23
	 * @param str 计费点参数,字符串。如："1,2,3"
	 * @return 返回计费点集合
	 */
	public static List<String> convertStringToList(String str) {
		if (null != str && !"".equals(str)) {
			String[] arr = str.split(",");
			List<String> ls = new ArrayList<String>(arr.length);
			for (int i = 0; i < arr.length; i++) {
				ls.add(arr[i]);
			}
			return ls;
		}
		return null;
	}


	/**
	 * @author kimi
	 * @dateTime 2013-9-30 下午2:30:45
	 * @param array
	 * @return
	 */
	public static String convertArrayToString(Object array, String regex) {
		if (null == array) {
			return null;
		}
		Object[] obj = (Object[]) array;
		if (null != obj && obj.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < obj.length; i++) {
				if ((i + 1) != obj.length) {
					sb.append(obj[i]).append(regex);
				} else {
					sb.append(obj[i]);
				}
			}
			return sb.toString();
		}
		return null;
	}

	/** 
	 * list转换成String串
	 * @author BILL CHEN
	 * @date 2016年6月29日 下午7:11:24
	 * 参数说明 
	 * 
	 * @param list
	 * @param regex
	 * @return  
	 */
	public static String listToString(List list, String regex) {
		if(null != list){
			StringBuffer s=new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				s.append(list.get(i)).append(regex);
			}
			return	s.substring(0, s.lastIndexOf(regex));
		}
		return null;
	}
	/**
	 * set集合元素转换成字符串
	 * @author chenbiao
	 * @date 2017年1月18日 下午10:21:03
	 * 参数说明 
	 * 
	 * @param set
	 * @param regex
	 * @return
	 */
	public static String setToString(Set set, String regex) {
		if(null != set && set.size() > 0){
			StringBuffer s=new StringBuffer();
			Iterator itera = set.iterator();
			while (itera.hasNext()) {
				s.append(itera.next()).append(regex);
			}
			return	s.substring(0, s.lastIndexOf(regex));
		}
		return null;
	}

	/** 
	 * 对指定字符串通过指定分隔符循环进行拼接
	 * @author BILL CHEN
	 * @date 2016年6月29日 下午7:16:33
	 * 参数说明 
	 * 
	 * @param str
	 * @param count
	 * @param regex
	 * @return  
	 */
	public static String appendStrByCount(String str, Integer count, String regex) {
		if(count > 0 && null != str && null != regex){
			StringBuffer s=new StringBuffer();
			for (int i = 0; i < count; i++) {
				s.append(str).append(regex);
			}
			return s.substring(0, s.lastIndexOf(regex));
		}
		return null;
	}
	/**
	 * 获取集合对象中某一类属性值
	 * 
	 * @author chenbiao
	 * @date 2016年10月9日 上午11:39:01
	 * 参数说明 
	 * 
	 * @param list
	 * @param fieldName
	 * @return
	 */
	public static List getFields(List list, String fieldName) {
		if(CollectionUtils.isEmpty(list))
			return null;
		try {
			List res = new LinkedList();
			Class claz;Field[] fds;String fdName;Method[] ms;String methodName;boolean flag;
			for(Object obj : list){
				claz = obj.getClass();
				fds = claz.getDeclaredFields();// 获取他的字段数组
				flag = false;
				for(Field f : fds){
					fdName = f.getName();
					ms = claz.getMethods();
					methodName = buildGetMethodName(fieldName);
					for(Method m : ms){
						if(m.getName().equals(methodName)){
							res.add(m.invoke(obj, null));
							flag = true;
							break;
						}
					}
					if(flag){
						break;
					}
				}
			}
			return res;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Set getFieldsBySet(List list, String fieldName) {
		if(CollectionUtils.isEmpty(list))
			return null;
		try {
			Set res = new HashSet();
			Class claz;Field[] fds;String fdName;Method[] ms;String methodName;boolean flag;
			for(Object obj : list){
				claz = obj.getClass();
				fds = claz.getDeclaredFields();// 获取他的字段数组
				flag = false;
				for(Field f : fds){
					fdName = f.getName();
					ms = claz.getMethods();
					methodName = buildGetMethodName(fieldName);
					for(Method m : ms){
						if(m.getName().equals(methodName)){
							res.add(m.invoke(obj, null));
							flag = true;
							break;
						}
					}
					if(flag){
						break;
					}
				}
			}
			return res;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String buildGetMethodName(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return "get" + sb.toString();
		} else {
			return null;
		}
	}
	/**
	 * unicode转换成中文
	 *
	 * @author gonglb
	 * @date 2017年4月7日 上午10:36:52
	 */
	public static String decodeUnicode(String theString) {
		char aChar;      
		int len = theString.length();      
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {      
			aChar = theString.charAt(x++);      
			if (aChar == '\\') {      
				aChar = theString.charAt(x++);      
				if (aChar == 'u') {      
					// Read the xxxx      
					int value = 0;      
					for (int i = 0; i < 4; i++) {      
						aChar = theString.charAt(x++);      
						switch (aChar) {      
						case '0':      
						case '1':      
						case '2':      
						case '3':      
						case '4':      
						case '5':      
						case '6':      
						case '7':      
						case '8':      
						case '9':      
							value = (value << 4) + aChar - '0';      
							break;      
						case 'a':      
						case 'b':      
						case 'c':      
						case 'd':      
						case 'e':      
						case 'f':      
							value = (value << 4) + 10 + aChar - 'a';      
							break;      
						case 'A':      
						case 'B':      
						case 'C':      
						case 'D':      
						case 'E':      
						case 'F':      
							value = (value << 4) + 10 + aChar - 'A';      
							break;      
						default:      
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");      
						}      

					}      
					outBuffer.append((char) value);      
				} else {      
					if (aChar == 't')      
						aChar = '\t';      
					else if (aChar == 'r')      
						aChar = '\r';      

					else if (aChar == 'n')      

						aChar = '\n';      

					else if (aChar == 'f')      

						aChar = '\f';      

					outBuffer.append(aChar);      
				}      
			} else     
				outBuffer.append(aChar);      
		}      
		return outBuffer.toString();      
	}     

}

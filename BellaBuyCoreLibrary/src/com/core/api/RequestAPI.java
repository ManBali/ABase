package com.core.api;


/**
 * @author sufun.wu
 * @createdate 2015年1月31日 下午12:25:09
 * @Description: 请求API
 */
public class RequestAPI {
	public static  String BASE_URL="http://www.baidu.com";

	public static final String URL = BASE_URL+"index.php/";
	
	public static String getMethod(String methodName){
		return BASE_URL+URL + methodName;
	}	
	public static String getWebMethod(String methodName){
		return BASE_URL +"webview/goodsdesc.php?id="+ methodName;
	}	
	/**
	 * @author sufun.wu
	 * @createdate 2014年8月25日 下午5:05:52
	 * @Description: 获取图片相对路径
	 * @param imgPath
	 * @return
	 */
	public static String getImgMethod(String imgPath){
		return BASE_URL + "img/" +imgPath;
	}
	public static final String REGISTER="apiuser/register";
	public static final String LOGIN="apiuser/login";
}

package com.core.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-30 下午2:59:24
 * @Description: 校验工具类
 */
public class ValidUtil {
	
	/**
	 * @author miaoxin.ye
	 * @createdate 2013-12-30 下午3:00:30
	 * @Description: 校验用户名
	 * @param username
	 * @return
	 */
	public static String validUsername(String username){
		String message="";
		if(TextUtils.isEmpty(username)){
			message="请输入用户名！";
		}else if(CoreTextUtil.getTextLength(username)>30){
			message="用户名不能超过30个字符!";
		}
		return message;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2013-12-30 下午3:00:21
	 * @Description: 校验密码
	 * @param pwd
	 * @return
	 */
	public static String validPassword(String pwd){
		String message="";
		if(TextUtils.isEmpty(pwd)){
			message="请输入密码！";
		}else if(pwd.length()<6){
			message="请输入6-16位密码！";
		}
		return message;
	}
	
	public static boolean validEmail(String email){
		email=email.replace(" ","");//将输入与输出中带有空格的去掉
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
	}
	
}

package com.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import android.content.Context;
import com.core.util.file.StreamUtil;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-15 下午3:52:57
 * @Description: 文本工具类
 */
public class CoreTextUtil {

	/**
	 * @Description: 获取文本内容的长度,中文算一个字符,英文算半个字符,包括标点符号
	 * @param str
	 * @return
	 */
	public static int textLength(String str){
		int number=getTextLength(str);
		int length=number/2;
		if(number % 2 != 0){
			length+=1;
		}
		str=null;
		return length;
	}
	
	/**
	 * @Description: 获取文本内容的长度,中文算两个字符,英文算一个字符
	 * @param str
	 * @return
	 */
	public static int getTextLength(String str){
		int length=0;
		try {
			str=new String(str.getBytes("GBK"), "ISO8859_1");
			length=str.length();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	/**
	 * @Description: 从Assets文件中加载字符串
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getStringFromAsset(Context context,String fileName) {
		try {
			InputStream is = context.getAssets().open(fileName);
			return StreamUtil.InputStreamToString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

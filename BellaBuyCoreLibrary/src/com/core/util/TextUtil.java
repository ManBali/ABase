package com.core.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 * @author caibing.zhang
 *
 */
public class TextUtil {

	/**
	 * 得到文件二进制流
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileContent(String fileName) throws Exception {
		File file = new File(fileName);
		if( file.exists() ){ 
			InputStream is = new FileInputStream(file);
			return getFileByte(is);
		} 
		return null; 
	} 
	
	/**
	 * 输入流转byte数组
	 * @param is
	 * @return
	 */
	public static byte[] getFileByte(InputStream is){
		try {
			byte[] buffer = new byte[1024]; 
			int len = -1; 
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((len = is.read(buffer)) != -1) { 
			         outStream.write(buffer, 0, len); 
			} 
			byte[] data = outStream.toByteArray(); 
			outStream.close(); 
			is.close(); 
			return data;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 字符串不能空
	 * @param value  要验证的字符串
	 * @return
	 */
	public static boolean stringIsNotNull(String value){
		return  value!=null && value.trim().length()>0;
	}
	
	/**
	 * 字符串为空
	 * @param value  要验证的字符串
	 * @return
	 */
	public static boolean stringIsNull(String value){
		return value ==null || value.trim().length()<=0;
	}
	
	/**
	 * 
	 * @author caibing.zhang
	 * @createdate 2012-6-26 下午10:38:11
	 * @Description: 字符串转InputStream
	 * @param xml
	 * @return
	 */
	public static InputStream stringToInputStream(String xml){
		 ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
		 return stream;
	}
	
	/**
	 * 获取文本内容的长度，中文算一个字符，英文算半个字符，包括标点符号
	 * @param str
	 * @return
	 */
	public static int getTextLengthes(String str){
		int number=getTextLength(str);
		int length=number/2;
		if(number % 2 != 0){
			length+=1;
		}
		str=null;
		return length;
	}
	
	/**
	 * 获取文本内容的长度(中文算两个字符，英文算一个字符)
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
	
	public static String F="%.2f";
	/**
	 * 加法
	 * @param a
	 * @param b
	 * @return
	 */
	public static String addition(String a,String b){
		double value= Double.parseDouble(a)+ Double.parseDouble(b);
		return format(value);
	}
	
	/**
	 * @author caibing.zhang
	 * @createdate 2013-4-10 下午6:41:45
	 * @Description: 格式化，保留二位小数
	 * @param value
	 * @return
	 */
	public static String format(double value){
		return String.format(F, value);
	}
	
	/**
	 * 验证金额
	 * @param str
	 * @return
	 */
    public static boolean isSum(String str) {
    	String s="^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,7})?$";
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile(s); // 判断小数点后一位的数字的正则表达式
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false){ 
           return false; 
        } else { 
           return true; 
        } 
    }
    
    /**
     * dip转像素
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(dipValue * scale + 0.5f); 
    } 

    /**
     * 像素转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int)(pxValue / scale + 0.5f); 
    } 
    
    /**
	 * 将输入流解析为String
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamToString(InputStream is)throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				line=null;
			}
		} finally {
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		line=sb.toString();
		sb=null;
		return line;
	}

	/**
	 * 检测文字是是英文的地址
	 * true:　是英文字符
	 * false:　非英文字符
	 * @param data
	 * @return
	 */
	public static boolean isContainerRussian(String data)
	{
		String regEx = "^[a-zA-Z 0-9, . // -_ /(/)/[/] /+ `]+$";
		String s = data;
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(s);
		boolean rs = mat.find();
		return rs;
	}

}

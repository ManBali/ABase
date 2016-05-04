package com.core.util.file;

import com.core.util.constants.CoreConstant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午11:09:20
 * @Description: SharedPreference工具类
 */
public class SharedPreferenceUtil {
	
	/**
	 * @Description: 保存int值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context context,String key,int value){
		if(context==null)
		{
			return;
		}
		SharedPreferences.Editor editor = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * @Description: 获取int值
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context,String key){
		if(context==null)
		{
			return 0;
		}
		SharedPreferences shared = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
		int value=shared.getInt(key, 0);
		return value;
	}
	
	/**
	 * @Description: 保存long值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(Context context,String key,long value){
		if(context==null)
		{
			return;
		}
		SharedPreferences.Editor editor = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	/**
	 * @Description: 获取long值
	 * @param context
	 * @param key
	 * @return
	 */
	public static long getLong(Context context,String key){
		if(context==null)
		{
			return 0L;
		}
		SharedPreferences shared = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
		long value=shared.getLong(key, 0L);
		return value;
	}
	
	/**
	 * @Description: 保存boolean值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context,String key,boolean value){
		if(context==null)
		{
			return;
		}
		SharedPreferences.Editor editor = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * @Description: 获取boolean值
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context,String key){
		if(context==null)
		{
			return false;
		}
		SharedPreferences shared = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
		boolean value=shared.getBoolean(key, false);
		return value;
	}
	
	/**
	 * @Description: 保存String值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context,String key,String value){
		if(context==null)
		{
			return;
		}
		SharedPreferences.Editor editor = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * @Description: 获取String值
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context,String key){
		if(context==null)
		{
			return "";
		}
		SharedPreferences shared = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String value=shared.getString(key, "");
		return value;
	}
	
	/**
	 * @Description: 清空保存的值
	 * @param context
	 * @param key
	 */
	public static void removeString(Context context,String key){
		if(context==null)
		{
			return;
		}
		SharedPreferences shared = context.getSharedPreferences(CoreConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.remove(key);
		editor.commit();
	}
	
}

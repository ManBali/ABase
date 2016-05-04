package com.core.util.log;

import com.core.util.constants.CoreConstant;

import android.util.Log;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-15 下午2:34:34
 * @Description: 日志
 */
public class CoreLog{
	
	private static final String KEY="--Main--";
	
	public static void i(Object message){
		if(CoreConstant.IS_TEST_FLAG){
			Log.i(KEY, message.toString());
		}
	}
	
	public static void e(Object message){
		if(CoreConstant.IS_TEST_FLAG){
			Log.e(KEY, message.toString());
		}
	}
	
	public static void d(Object message){
		if(CoreConstant.IS_TEST_FLAG){
			Log.d(KEY, message.toString());
		}
	}
	
	public static void w(Object message){
		if(CoreConstant.IS_TEST_FLAG){
			Log.w(KEY, message.toString());
		}
	}
	
	public static void w(Object message,Throwable tr){
		if(CoreConstant.IS_TEST_FLAG){
			Log.w(KEY, message.toString(),tr);
		}
	}
	
}

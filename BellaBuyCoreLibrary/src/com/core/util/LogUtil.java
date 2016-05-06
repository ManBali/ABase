package com.core.util;

import android.util.Log;

import com.bellabuy.utils.constant.Constants;
import com.core.util.log.CoreLog;

/**
 * @author caibing.zhang
 * @createdate 2012-9-17 下午4:01:04
 * @Description: 日志
 */
public class LogUtil extends CoreLog {
	public static boolean isDebug=Constants.IS_TEST_FLAG;
	public static void D(String data)
	{
		if(Constants.IS_TEST_FLAG)
		{
			Log.i("LogUtil  ", "--->" + data);
		}
	}
//	private static final String KEY="--Main--";
//	
//	public static void i(Object message){
//		if(Constants.IS_TEST_FLAG){
//			Log.i(KEY, message.toString());
//		}
//	}
//	
//	public static void e(Object message){
//		if(Constants.IS_TEST_FLAG){
//			Log.e(KEY, message.toString());
//		}
//	}
//	
//	public static void d(Object message){
//		if(Constants.IS_TEST_FLAG){
//			Log.d(KEY, message.toString());
//		}
//	}
//	
//	public static void w(Object message){
//		if(Constants.IS_TEST_FLAG){
//			Log.w(KEY, message.toString());
//		}
//	}
//	
//	public static void w(Object message,Throwable tr){
//		if(Constants.IS_TEST_FLAG){
//			Log.w(KEY, message.toString(),tr);
//		}
//	}
}

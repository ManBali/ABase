package com.core.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-15 下午2:30:34
 * @Description: Toast工具类
 */
public class ToastUtil {

	/**
	 * @Description: 显示Toast消息
	 * @param message
	 */
	public static void showToastMessage(Context context,String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * @Description: 显示Toast消息
	 * @param strId
	 */
	public static void showToastMessage(Context context,int strId){
		Toast.makeText(context, context.getString(strId), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * @Description: 显示Toast消息,当在非UI线程中需要显示消息时调用此方法
	 * @param activity
	 * @param message
	 */
	public static void showToastMsgOnUiThread(final Activity activity,final String message){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}

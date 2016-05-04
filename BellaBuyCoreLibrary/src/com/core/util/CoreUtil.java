package com.core.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import com.core.util.http.CoreHttpClient;
import com.core.util.log.CoreLog;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-15 下午1:18:57
 * @Description: 核心工具类
 */
public class CoreUtil {
	
	public static ArrayList<Activity> ALL_ACTIVITY=new ArrayList<Activity>();
	
	/**
	 * @Description: 添加Activity到列表中
	 * @param activity
	 */
	public static void addAppActivity(Activity activity){
		if(!ALL_ACTIVITY.contains(activity)){
			ALL_ACTIVITY.add(activity);
		}
	}
	
	/**
	 * @Description: 从列表移除Activity
	 * @param activity
	 */
	public static void removeAppActivity(Activity activity){
		if(ALL_ACTIVITY.contains(activity)){
			ALL_ACTIVITY.remove(activity);
		}
	}
	
	/**
	 * @Description: 退出应用程序
	 */
	public static void exitApp(){
		CoreLog.w("--- 销毁 Activity size--->>:"+ALL_ACTIVITY.size());
		for (Activity ac : ALL_ACTIVITY) {
			if(!ac.isFinishing()){
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();
		HttpClient httpClient = CoreHttpClient.getInstance();
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * @Description: 判断List集合是否为空
	 * @param list
	 * @return
	 */
	public static boolean listIsNull(List<? extends Object> list){
		if(null ==list || list.size()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: dip转像素,不同分辨率适配
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue){   
        final float scale = context.getResources().getDisplayMetrics().density;   
        return (int)(dipValue * scale + 0.5f);   
    }
	
	/**
	 * @Description: 获取屏幕宽高
	 * @param activity
	 * @return
	 */
	public static int[] getDisplay(Activity activity){
		int[] display = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		display[0] = dm.widthPixels;
		display[1] = dm.heightPixels;
		return display;
	}
	
	/**
	 * @Description: 获取视图在屏幕的坐标
	 * @param view
	 * @return
	 */
	public static int[] getViewXYOnScreen(View view){
		int[] location = new int[2];  
		view.getLocationOnScreen(location);
		return location;
	}
	
	public static void clearList(List<?> list){
		if(list!=null){
			list.clear();
			list=null;
		}
	}
	
}

package com.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.bellabuy.utils.constant.Constants;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AndroidUtil {
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
	 * @Description: 清除记录的activity
	 */
	public static void removeAllAppActivity(){
		LogUtil.w("--- 销毁 Activity size--->>:"+ALL_ACTIVITY.size());
		for (Activity ac : ALL_ACTIVITY) {
			if(!ac.isFinishing()){
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();
	}
	
	/**
	 * @Description: 退出应用程序
	 */
	public static void exitApp(Context context){
		LogUtil.w("--- 销毁 Activity size--->>:"+ALL_ACTIVITY.size());
		for (Activity ac : ALL_ACTIVITY) {
			if(!ac.isFinishing()){
				ac.finish();
			}
		}
		ALL_ACTIVITY.clear();
		//对于好多应用，会在程序中杀死 进程，这样会导致我们统计不到此时Activity结束的信息，
		//对于这种情况需要调用 'MobclickAgent.onKillProcess( Context )'
		//方法，保存一些页面调用的数据。正常的应用是不需要调用此方法的。
		
		android.os.Process.killProcess(android.os.Process.myPid());
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
	
	public static int dp2px(Context context, double dp) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		display.getMetrics(displaymetrics);

		return (int) (dp * displaymetrics.density + 0.5f);
	}

	public static int px2dp(Context context, int px) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		display.getMetrics(displaymetrics);

		return (int) (px / displaymetrics.density + 0.5f);
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
	/**
	 * 2015年4月23日 09:59:53
	 * 用于生成Key加密码
	 * @param mContext
	 * @return
	 */
	public static String getHasKey(Context mContext)
	{
		//Get Has Key 
		String hash="";
        try 
        {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(Constants.PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hash=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                LogUtil.D("    KeyHash="+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } 
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return hash;
	}
	/**
	 * 取得旋转变化的动画
	 * @return
	 */
	public static Animation getRoateAnimation()
	{
		Random random=new Random();
		int count=random.nextInt(5);
		
		int start=random.nextInt(5);
		final RotateAnimation animation =new RotateAnimation(start,count,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animation.setFillAfter(true);
		animation.setDuration(600);//设置动画持续时间 
		/** 常用方法 */ 
		//animation.setRepeatCount(int repeatCount);//设置重复次数 
		//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态 
		//animation.setStartOffset(long startOffset);//执行前的等待时间
		animation.setInterpolator(new AccelerateInterpolator());
		return animation;
	}
	
	/**
	 * 取得缩放的图
	 * @return
	 */
	public static Animation getLikeScaleAnimation()
	{
		ScaleAnimation scale=new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scale.setDuration(500);
		scale.setFillAfter(false);
		return scale;
	}
	/**
	 * 取得淡入的效果图
	 * @return
	 */
	public static Animation getFadeInScaleAnimation(AnimationListener animationListerner)
	{
	
		AlphaAnimation ani=new AlphaAnimation(0.0f, 1.0f);
		ani.setDuration(2000);
		ani.setFillAfter(false);
		ani.setAnimationListener(animationListerner);
		return ani;
	}
	/**
	 * 取得淡出的效果图
	 * @return
	 */
	public static Animation getFadeOutScaleAnimation(AnimationListener animationListerner)
	{
		AlphaAnimation ani=new AlphaAnimation(1.0f, 0.0f);
		ani.setDuration(500);
		ani.setFillAfter(false);
		ani.setAnimationListener(animationListerner);
		return ani;
	}
	/**
	 * @author sufun
	 * @createtime 2015年11月16日 12:24:20
	 * @param context
	 * @param metaKey
	 * 从Manifest.xml配置文件中获取数据  	<meta-data android:name="api_key" android:value="xxx" />
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String metaValue = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				metaValue = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {
		}
		return metaValue;// xxx
	}

	/**
	 * 将View转换成Bitmap
	 * @param v
	 * @return
     */
	public static Bitmap getViewToBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * 取得当前应用的版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context)
	{
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
	}
}

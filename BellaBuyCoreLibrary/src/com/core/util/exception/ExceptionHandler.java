package com.core.util.exception ;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

import com.core.CoreApplication;
import com.core.util.CoreUtil;
import com.core.util.TimeUtil;
import com.core.util.ToastUtil;
import com.core.util.constants.CoreConstant;
import com.core.util.file.FileUtil;
import com.core.util.log.CoreLog;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 上午10:57:54
 * @Description: 异常处理
 */
public class ExceptionHandler implements UncaughtExceptionHandler {
	
	private Context mContext;
	private static ExceptionHandler INSTANCE;
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	//用来存储设备信息和异常信息
	private Map<String , String> logInfo = new HashMap<String , String>() ;
	
	private ExceptionHandler() {}

	public static ExceptionHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ExceptionHandler();
		}
		return INSTANCE;
	}
	
	/**
	 * 设置ExceptionHandler为程序的默认处理器
	 */
	public void init(Context paramContext) {
		mContext = paramContext ;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler() ;
		Thread.setDefaultUncaughtExceptionHandler(this) ;
	}
	
	public boolean isServiceRun(){
		ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
	     List<RunningServiceInfo> list = am.getRunningServices(30);
	     for(RunningServiceInfo info : list){
	         if(info.service.getClassName().equals("com.club.client.UnReadService")){
	             return true;
	         }
	    }
	    return false;
	}
	
	/**
	 * 处理 UncaughtException
	 */
	public void uncaughtException(Thread thread , Throwable ex) {
		if( ! handleException(ex) && mDefaultHandler != null) {
			// 如果自定义的没有处理则让系统默认的异常处理器来处理
			//mDefaultHandler.uncaughtException(thread , ex) ;
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace() ;
			}
			CoreUtil.exitApp();
			android.os.Process.killProcess(android.os.Process.myPid()) ;
		}else {
			try {
				Thread.sleep(3000);
			}catch(InterruptedException e) {
				e.printStackTrace() ;
			}
			CoreUtil.exitApp();
			android.os.Process.killProcess(android.os.Process.myPid()) ;
		}
	}
	
	/**
	 * 自定义异常处理
	 * @param ex
	 * @return true处理了该异常信息;否则返回false
	 */
	public boolean handleException(Throwable ex) {
		if(ex == null){
			return false ;
		}
		new Thread() {
			public void run() {
				Looper.prepare() ;
				ToastUtil.showToastMessage(mContext, "应用出现异常");
				Looper.loop() ;
			}
		}.start() ;
		// 获取设备参数信息
		getDeviceInfo(mContext) ;
		// 保存日志文件
		saveLogToFile(ex) ;
		return true ;
	}
	
	/**
	 * @Description: 收集程序崩溃的设备信息
	 * @param context
	 */
	public void getDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager() ;
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES) ;
			if(pi != null) {
				String versionName = pi.versionName == null ? "null": pi.versionName ;
				String versionCode = pi.versionCode + "" ;
				logInfo.put("versionName" , versionName) ;
				logInfo.put("versionCode" , versionCode) ;
			}
		}catch(NameNotFoundException e) {
			e.printStackTrace() ;
		}
		// 使用反射来收集设备信息.在Build类中包含各种设备信息,例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		Field[] mFields = Build.class.getDeclaredFields() ;
		for(Field field : mFields) {
			try {
				// 通过设置Accessible属性为true,才能对私有变量进行访问
				field.setAccessible(true) ;
				logInfo.put(field.getName() , field.get("").toString()) ;
				if (CoreConstant.IS_TEST_FLAG) {
					CoreLog.d(field.getName() + " : " + field.get(null));
				}
			}catch(IllegalArgumentException e) {
				e.printStackTrace() ;
			}catch(IllegalAccessException e) {
				e.printStackTrace() ;
			}
		}
	}
	
	/**
	 * @Description: 保存错误信息到文件中
	 * @param paramThrowable
	 * @return
	 */
	private String saveLogToFile(Throwable ex) {
		StringBuffer mStringBuffer = new StringBuffer() ;
		for(Map.Entry<String , String> entry : logInfo.entrySet()) {
			String key = entry.getKey() ;
			String value = entry.getValue() ;
			mStringBuffer.append(key + "=" + value + "\r\n") ;
		}
		Writer mWriter = new StringWriter() ;
		PrintWriter mPrintWriter = new PrintWriter(mWriter) ;
		ex.printStackTrace(mPrintWriter) ;
		ex.printStackTrace();
		Throwable mThrowable = ex.getCause() ;
		while(mThrowable != null) {
			mThrowable.printStackTrace(mPrintWriter) ;
			mPrintWriter.append("\r\n") ;
			mThrowable = mThrowable.getCause() ;
		}
		mPrintWriter.close() ;
		String mResult = mWriter.toString() ;
		mStringBuffer.append(mResult) ;
		String time = TimeUtil.parseDateToString(TimeUtil.sdf3, new Date());
		String fileName = "error_" + time + ".log" ;
		if(FileUtil.isExistSD()) {
			try {
				CoreLog.e(CoreApplication.LOG_DIR + fileName);
				FileOutputStream mFileOutputStream = new FileOutputStream(CoreApplication.LOG_DIR + fileName) ;
				mFileOutputStream.write(mStringBuffer.toString().getBytes()) ;
				mFileOutputStream.close() ;
				return fileName ;
			} catch(FileNotFoundException e) {
				e.printStackTrace() ;
			}
			catch(IOException e) {
				e.printStackTrace() ;
			}
		}
		return null ;
	}
	
}

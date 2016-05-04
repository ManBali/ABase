package com.core.util.download;

import com.core.R;
import com.core.util.constants.CoreConstant;
import com.core.util.constants.CoreIntentConstant;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午3:52:17
 * @Description: 版本更新
 */
public class VersionService extends Service {
	
	private NotificationManager notificationMrg;
	private int old_process = 0;
	private boolean isFirstStart=false;
	protected int appLauncher;
	protected String appName;
	protected int downloadLayoutResID;

	public void onCreate() {
		super.onCreate();
		isFirstStart=true;
		notificationMrg = (NotificationManager) this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mHandler.handleMessage(new Message());
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 1为出现，2为隐藏
			if(CoreConstant.LOADING_PROCESS>99){
				notificationMrg.cancel(0);
				stopSelf();
				return;
			}
			if(CoreConstant.LOADING_PROCESS>old_process){
				displayNotificationMessage(CoreConstant.LOADING_PROCESS);
			}
			new Thread() {
				public void run() {
					isFirstStart=false;
					Message msg = mHandler.obtainMessage();
					mHandler.sendMessage(msg);
				}
			}.start();
			old_process =CoreConstant.LOADING_PROCESS;
		}
	};

	/**
	 * @Description: 显示通知显示
	 * @param downloadCount 
	 */
	protected void displayNotificationMessage(int downloadCount) {
		Intent notificationIntent1 = new Intent(this, getClass());
		notificationIntent1.putExtra(CoreIntentConstant.NOTIFICATION_SHOW_DIALOG,true);
		notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0,notificationIntent1, 0);
		// 创建Notifcation
		Notification notification = new Notification(appLauncher,appName+"：正在下载中,请稍候...", System.currentTimeMillis());
		// 设定Notification出现时的声音，一般不建议自定义
		if(isFirstStart || CoreConstant.LOADING_PROCESS>97){
			notification.defaults |= Notification.DEFAULT_SOUND;// 设定是否振动
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// 创建RemoteViews用在Notification中
		RemoteViews contentView = new RemoteViews(getPackageName(),downloadLayoutResID);
//		contentView.setTextViewText(R.id.n_title,"升级提示");
		contentView.setTextViewText(R.id.tv_id, "下载进度："+downloadCount+"% ");
		contentView.setProgressBar(R.id.pb_id, 100, downloadCount, false);
		notification.contentView = contentView;
		notification.contentIntent = contentIntent1;
		notificationMrg.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}


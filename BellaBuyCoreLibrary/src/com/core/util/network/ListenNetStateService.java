package com.core.util.network;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import com.core.util.LogUtil;
import com.core.util.NetWorkUtil;

//监听手机网络状态（包括GPRS，WIFI， UMTS等)
//跳转service：ListenNetStateService_.intent(getApplication()).start();
//停止service：ListenNetStateService_.intent(getApplication()).stop();
public class ListenNetStateService extends Service {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                LogUtil.e("网络状态已经改变");
                ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
                NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
                NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();  
                if(wifiInfo != null && wifiInfo.isConnected()){
                	NetWorkUtil.NETWORK=true;
                	//LogUtil.e("当前网络名称：" + activeInfo.getTypeName());
                }else if(mobileInfo != null && mobileInfo.isConnected()){
                	NetWorkUtil.NETWORK=true;
                	//LogUtil.e("当前网络名称：" + activeInfo.getTypeName());
                }else{
                	NetWorkUtil.NETWORK=false;
                	//LogUtil.e("没有可用网络");
                }

            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}

package com.core.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.core.R;
import com.core.util.CoreUtil;

public abstract class AbstractCoreActivity extends FragmentActivity {
	
	protected Dialog dialog;
	protected View templateView;
	public LinearLayout mainBody;            //主体显示
//	private CoreDialog myCustomdialog;
	protected boolean isShowNoValue = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CoreUtil.addAppActivity(this);
	}

	@Override
	public void setContentView(View view) {
		if(mainBody!=null){
			mainBody.removeAllViews();
			mainBody.addView(view);
		}
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		if(mainBody!=null){
			mainBody.removeAllViews();
			mainBody.addView(view,params);
		}
	}

	/**
	 * @Description: 显示加载对话框
	 * @param task 
	 * @param message 显示的信息
	 * @param cancelable 是否支持取消操作
	 * @param isLoadingPrompt 是否使用运营推荐消息
	 */	
	public abstract Dialog showWaitDialog(String message,boolean cancelable);
	
	/**
	 * @author sufun.wu
	 * @createdate 2012-6-6 下午1:57:01
	 * @Description: 显示异步交互后的数据,隐藏加载框
	 */
	public void dissmissWaitingDialog() {
		try
		{
			if (null != mainBody){
				mainBody.setVisibility(View.VISIBLE);
			}
			if(dialog!=null){
				dialog.dismiss();
			}
		}
		catch (Exception ex)
		{

		}
	}
	
	/**
	 * @Description: 显示Body信息
	 * @param info
	 */
	protected void showBodyInfo(String info){
		if(!TextUtils.isEmpty(info)){
			TextView textView=(TextView) templateView.findViewById(R.id.tv_id);
			textView.setText(info);
		}
		if(mainBody!=null){
			mainBody.removeAllViews();
			mainBody.setGravity(Gravity.CENTER);
			mainBody.addView(templateView);
		}
	}
	
	/**
	 * @author sufun.wu
	 * @createdate 2015年1月18日 上午10:44:00
	 * @Description:  一个按钮对话框
	 * @param alertType 对话框类型
	 * @param title  标题, 为空时，设置null，显示为：温馨提示
	 * @param message 消息内容 
	 * @param btnText 按钮内容,  为空时，设置null，显示为：确定
	 * @param listener 点击按钮的回调事件
	 * @param isCancelable 是否允许点击返回键关闭对话框，false：点击返回不关闭对话框，默认为true
	 */
	public void showDialogToConfirm(int alertType,String title,String message,
			String confirmBtnText,SweetAlertDialog.OnSweetClickListener listener,boolean isCancelable){
		if(title==null){
			title=getString(R.string.prompt);
		}
		if(confirmBtnText==null){
			confirmBtnText=getString(R.string.btn_sure_text);
		}		
		try {
		new SweetAlertDialog(this, alertType)
	        .setTitleText(title)
	        .setContentText(message)
	        .setCancelables(isCancelable)
	        .setConfirmText(confirmBtnText)
	        .setConfirmClickListener(listener)
	        .show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * @author sufun.wu
	 * @createdate 2015年1月18日 上午10:44:00
	 * @Description:  一个按钮对话框
	 * @param alertType 对话框类型
	 * @param resStrTitle 标题，为空时，设置为0
	 * @param resStrMessage 消息内容 
	 * @param resStrBtnText 按钮内容,  为空时，设置0，显示为：确定
	 * @param listener 点击按钮的回调事件
	 * @param isCancelable 是否允许点击返回键关闭对话框，false：点击返回不关闭对话框，默认为true
	 */
	public void showDialogToConfirm(int alertType,int resStrTitle,int resStrMessage,
			int resStrConfirmBtnText,SweetAlertDialog.OnSweetClickListener listener,boolean isCancelable){
		String title=null;
		if(resStrTitle!=0){
			title=getString(resStrTitle);
		}
		String confirmBtnText=null;
		if(resStrConfirmBtnText!=0){
			confirmBtnText=getString(resStrConfirmBtnText);
		}
		showDialogToConfirm(alertType, title, 
				getString(resStrMessage), confirmBtnText, listener, isCancelable);
	}
	
	/**
	 * @author sufun.wu
	 * @createdate 2015年1月18日 上午11:29:45
	 * @Description: 双按钮对话框
	 * @param alertType 对话框类型
	 * @param title  标题, 为空时，设置null，显示为：温馨提示
	 * @param message 消息内容 
	 * @param positiveButtonText 右侧按钮（确定）显示文字
	 * @param positiveButtonListener  
	 * @param negativeButtonText 左侧按钮（取消）显示文字
	 * @param negativeButtonListener
	 * @param isCancelable 是否允许点击返回键关闭对话框，false：点击返回不关闭对话框，默认为true
	 */
	public void showDialogToBase(int alertType,String title,String message,
			String positiveButtonText,SweetAlertDialog.OnSweetClickListener positiveButtonListener,
			String negativeButtonText,SweetAlertDialog.OnSweetClickListener negativeButtonListener,boolean isCancelable){
		if(title==null){
			title=getString(R.string.prompt);
		}
		new SweetAlertDialog(this, alertType)
	        .setTitleText(title)
	        .setContentText(message)
	        .setCancelables(isCancelable)
	        .setConfirmText(positiveButtonText)
	        .setConfirmClickListener(positiveButtonListener)
	        .setCancelText(negativeButtonText)
	        .setCancelClickListener(negativeButtonListener)
	        .show();
	}
	
	/**
	 * @author sufun.wu
	 * @createdate 2015年1月18日 上午11:29:45
	 * @Description: 双按钮对话框
	 * @param alertType 对话框类型
	 * @param resStrTitle  标题，为空时，设置为0
	 * @param resStrMessage 消息内容 
	 * @param positiveButtonText 右侧按钮（确定）显示文字
	 * @param positiveButtonListener  
	 * @param negativeButtonText 左侧按钮（取消）显示文字
	 * @param negativeButtonListener
	 * @param isCancelable 是否允许点击返回键关闭对话框，false：点击返回不关闭对话框，默认为true
	 */
	public void showDialogToBase(int alertType,int resStrTitle,int resStrMessage,
			int resStrpositiveButtonText,SweetAlertDialog.OnSweetClickListener positiveButtonListener,
			int resStrnegativeButtonText,SweetAlertDialog.OnSweetClickListener negativeButtonListener,boolean isCancelable){
		String title=null;
		if(resStrTitle!=0){
			title=getString(resStrTitle);
		}
		new SweetAlertDialog(this, alertType)
	        .setTitleText(title)
	        .setContentText(getString(resStrMessage))
	        .setCancelables(isCancelable)
	        .setConfirmText(getString(resStrpositiveButtonText))
	        .setConfirmClickListener(positiveButtonListener)
	        .setCancelText(getString(resStrnegativeButtonText))
	        .setCancelClickListener(negativeButtonListener)
	        .show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		mainBody=null;
		CoreUtil.removeAppActivity(this);
		super.onDestroy();
	}
}

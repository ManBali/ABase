package com.core.activity;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bellabuy.activity.center.GuideUserActivity_;
import com.bellabuy.activity.home.MainActivity;
import com.bellabuy.api.BellaBuyHttpClient;
import com.bellabuy.api.ConstantsCode;
import com.bellabuy.api.RequestAPI;
import com.bellabuy.customactivityoncrash.activity.CustomErrorActivity;
import com.bellabuy.model.BaseBean;
import com.bellabuy.model.im.IMUserDataList;
import com.bellabuy.model.im.IMUserInfo;
import com.bellabuy.model.user.UserInfo;
import com.bellabuy.model.version.VersionListBean;
import com.bellabuy.nnbuy.R;
import com.bellabuy.utils.AndroidUtil;
import com.bellabuy.utils.FileLocalCache;
import com.bellabuy.utils.LogUtil;
import com.bellabuy.utils.constant.Constants;
import com.bellabuy.widgets.TitleView;
import com.bellabuy.widgets.svgview.PathView;
import com.core.customactivityoncrash.CustomActivityOnCrash;
import com.core.util.NetWorkUtil;
import com.core.util.file.FileUtil;
import com.core.util.file.SharedPreferenceUtil;
import com.core.util.image.ImageUtil;
import com.core.util.image.XUtilsImageLoader;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tendcloud.tenddata.TCAgent;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @createdate 2013-12-17 下午2:44:50
 * @Description: Activity基类
 */
public abstract class AbstractActivity extends AbstractCoreActivity {

    protected boolean isTemplate = true; // 是否使用模板
    protected boolean isShowShoppingCart = false; // 是否显示购物车
    private View waitingView;
    public TitleView titleView;
    public PopupWindow mPopupWindow;
    // private XUtilsImageLoader xUtilsImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自定义Crash
        CustomActivityOnCrash.setErrorActivityClass(CustomErrorActivity.class);
        AndroidUtil.addAppActivity(this);
        if (savedInstanceState != null) {
            LogUtil.e("------------恢复------------->>");
        }
        if (isTemplate) {
            super.setContentView(R.layout.template);
            mainBody = (LinearLayout) findViewById(R.id.view_mainBody);
            titleView = (TitleView) findViewById(R.id.title_iv_id);
            titleView.setTitleLeftButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View button) {
                    finish();
                }
            });
            titleView.mRightBtn.setVisibility(View.GONE);
        }

        int[] screenSize = AndroidUtil.getDisplay(this);
        MyApplication.mScreenWidth = screenSize[0];
        MyApplication.mScreenHeight = screenSize[1];


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e("------------onSaveInstanceState------------->>");
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.template) {
            super.setContentView(layoutResID);
        } else {
            if (mainBody != null) {
                mainBody.removeAllViews();
                // mainBody.addView(this.getLayoutInflater().inflate(layoutResID,null));
                mainBody.addView(
                        this.getLayoutInflater().inflate(layoutResID, null),
                        new LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.MATCH_PARENT));
            } else {
                super.setContentView(layoutResID);
            }
        }
    }

    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    /**
     * @return
     * @author caibing.zhang
     * @createdate 2015年1月26日 下午11:09:46
     * @Description: 初始化图片加载工具，方便在内存回收
     */
    public XUtilsImageLoader initXUtilsImageLoader() {
        // if(xUtilsImageLoader==null){
        // xUtilsImageLoader=new XUtilsImageLoader(this);
        // }
        // return xUtilsImageLoader;

        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowShoppingCart) {
            setShoppingCartNum();
        }
        TCAgent.onResume(this);
        if (MyApplication.analytics != null) {
            MyApplication.analytics.getSessionClient().resumeSession();
        }
        if (MyApplication.SHOPPING_CART_DETAIL) {
            if (this instanceof MainActivity) {
                MainActivity.getMainInstance().showShoppingCart();
                MyApplication.SHOPPING_CART_DETAIL = false;
            } else {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BellaBuyHttpClient.cancelAllRequests();
    }

    @Override
    protected void onDestroy() {
        AndroidUtil.removeAppActivity(this);
        // if(xUtilsImageLoader!=null){
        // xUtilsImageLoader.clearMemory();
        // }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        TCAgent.onPause(this);
        if (MyApplication.analytics != null) {
            MyApplication.analytics.getSessionClient().pauseSession();
            MyApplication.analytics.getEventClient().submitEvents();
        }
    }

    /**
     * @author caibing.zhang
     * @createdate 2015年1月25日 下午12:47:06
     * @Description: 设置购物车数量
     */
    public void setShoppingCartNum() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.shopping_car_id);
        if (frameLayout != null) {
            if (MyApplication.SHOPPING_CART_NUM > 0) {
                frameLayout.setVisibility(View.VISIBLE);
                TextView numTv = (TextView) findViewById(R.id.shopping_car_text_id);
                if (MyApplication.SHOPPING_CART_NUM > 99) {
                    numTv.setText(Constants.SHOPPING_CART_N);
                } else {
                    numTv.setText(String.valueOf(MyApplication.SHOPPING_CART_NUM));
                }
                Animation shake = AnimationUtils.loadAnimation(this,
                        R.anim.shake);// 加载动画资源文件
                findViewById(R.id.shopping_car_id).startAnimation(shake); // 给组件播放动画效果
            } else {
                MyApplication.SHOPPING_CART_NUM = 0;
                frameLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @author caibing.zhang
     * @createdate 2015年1月25日 下午12:47:47
     * @Description: 隐藏购物车
     */
    public void hidShoppingCart() {
        // findViewById(R.id.shopping_car_id).setVisibility(View.GONE);
    }

    /**
     * @param message
     * @author caibing.zhang
     * @createdate 2012-9-24 上午9:10:36
     * @Description: 显示Toast消息
     */
    public void showToastCroutonMessage(Style style, String message) {
        // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        // Crouton.makeText(this, message,style).show();
        Crouton.makeText(this, message, style, R.id.fl_panent_id).show();
    }

    public void showToastCroutonMessage(Style style, int resString) {
        showToastCroutonMessage(style, getString(resString));
    }

    /**
     * @param message
     * @author caibing.zhang
     * @createdate 2012-9-24 上午9:10:36
     * @Description: 显示Toast消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        // Crouton.makeText(this, message,style).show();
    }

    public void showToastMessage(int resString) {
        showToastMessage(getString(resString));
    }

    /**
     * @author caibing.zhang
     * @createdate 2015年1月31日 上午10:47:10
     * @Description: 加载失败显示的页面【显示默认的图片】
     */
    public void showBodyInfoToLoadError(String info) {
        templateView = getLayoutInflater()
                .inflate(R.layout.template_fail, null);
        super.showBodyInfo(info);
    }

    /**
     * @param resStr 文字资源
     * @author caibing.zhang
     * @createdate 2015年1月31日 上午10:47:10
     * @Description: 加载失败显示的页面【显示默认的图片】
     */
    public void showBodyInfoToLoadError(int resStr) {
        showBodyInfoToLoadError(getString(resStr));
    }

    /**
     *
     */
    /**
     * @param resImg 图片资源
     * @param resStr 文字资源
     * @author caibing.zhang
     * @createdate 2015年1月31日 上午10:46:36
     * @Description: 加载失败显示的页面【设置图片】
     */
    public void showBodyInfoToLoadError(int resImg, int resStr) {
        templateView = getLayoutInflater()
                .inflate(R.layout.template_fail, null);
        ImageView image = (ImageView) templateView.findViewById(R.id.icon_id);
        image.setBackgroundResource(resImg);
        super.showBodyInfo(getString(resStr));
    }

    /**
     * sufun
     *
     * @param message
     * @param cancelable
     * @return
     */
    public Dialog createWaitingDatlog(String message, boolean cancelable) {
        Dialog mdialog = new Dialog(this, R.style.dialog_transparent);
        View waitingView = getLayoutInflater().inflate(
                R.layout.circular_progress, null);
        // waitingView.setBackgroundResource(R.drawable.img_loading_super_bg);
        waitingView.invalidate();
        mdialog.setContentView(waitingView);
        mdialog.setCancelable(cancelable);
        mdialog.show();
        return mdialog;
    }

    /**
     * 显示加载对话框
     */
    @Override
    public Dialog showWaitDialog(String message, boolean cancelable) {
        if (null == dialog) {
            dialog = new Dialog(this, R.style.dialog_transparent);
            waitingView = getLayoutInflater().inflate(
                    R.layout.circular_progress, null);
            // waitingView.setBackgroundResource(R.drawable.img_loading_super_bg);
            waitingView.invalidate();
            dialog.setContentView(waitingView);
        }
        // TextView textView=(TextView) waitingView.findViewById(R.id.tv_id);
        // if(TextUtils.isEmpty(message)){
        // message=getString(R.string.load);
        // }
        // textView.setText(message);
        try {
            dialog.setCancelable(cancelable);
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dialog;
    }

    /**
     * 2015年12月25日 18:23:58
     *
     * @description 展示ok的对话框
     */
    public void showOkDialog(DialogInterface.OnDismissListener listener, String content) {
        SweetAlertDialog dialog = new SweetAlertDialog(AbstractActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(getString(R.string.dialog_payssion_pay_success_title))
                .setContentText(content)
                .setCancelText(getString(R.string.dialog_payssion_pay_confirm))
                .setOnDismissListener(listener);
        dialog.showCancelButton(false);
        dialog.show();
    }

    public abstract class AsyncTask {
        // 是否需要进行网络判断,true判断(默认),false不需要判断
        private boolean isNetWork = true;
        // 是否覆盖mainBody显示showWaitDialog, true覆盖显示showWaitDialog,false覆盖，不显示
        private boolean isCover;
        // private String message = null;
        private String url;
        private Class<? extends BaseBean> clazz;

        public AsyncTask() {
            this.isCover = true;
        }

        //请求时所需要使用到的参数
        private RequestParams mParams = new RequestParams();

        /**
         * @param isCover 是否覆盖mainBody显示showWaitDialog,
         *                true覆盖显示showWaitDialog,false不覆盖显示showWaitDialog，
         */
        public AsyncTask(boolean isCover) {
            this.isCover = isCover;
        }

        /**
         * @param isCover   是否覆盖mainBody显示showWaitDialog,
         *                  true覆盖显示showWaitDialog,false不覆盖显示showWaitDialog
         * @param isNetWork 是否需要进行网络判断,true判断(默认),false不需要判断
         */
        public AsyncTask(boolean isCover, boolean isNetWork) {
            this.isCover = isCover;
            this.isNetWork = isNetWork;
        }

        // public ItktAsyncTask1(boolean isNetWork,String message){
        // this.isNetWork=isNetWork;
        // this.message=message;
        // }

        /**
         * @author caibing.zhang
         * @createdate 2015年1月17日 下午3:10:26
         * @Description: 网络加载成功
         */
        public abstract void loadSuccess(BaseBean bean);

        /**
         * @author caibing.zhang
         * @createdate 2015年1月17日 下午3:10:49
         * @Description: 网络加载失败：异常处理
         */
        public abstract void exception();

        /**
         * @param url
         * @param params 参数
         * @param clazz  json解析的对象
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         */
        public void post(String url, RequestParams params,
                         final Class<? extends BaseBean> clazz) {
            post(true, url, params, clazz);
        }

        /**
         * @param isLoading 是否显示加载对话框
         * @param url
         * @param params    参数
         * @param clazz     json解析的对象
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         */
        public void post(boolean isLoading, String url, RequestParams params,
                         Class<? extends BaseBean> clazz) {
            if (params != null && !params.has("pageSize")) {
                params.put("pageSize", Constants.PAGE_SIZE);
            }
            // 没有网络或不需要网络判断
            if (isNetWork && !NetWorkUtil.NETWORK) {
                showToastCroutonMessage(Style.ALERT, R.string.not_network);
                exception();
                return;
            }

            this.url = url;
            this.clazz = clazz;
            if (params != null) {
                this.mParams = params;
            }

            if (isCover && mainBody != null) {
                mainBody.setVisibility(View.GONE);
            }

            if (isLoading) {
                showLoadDialog(true);
            }
            BellaBuyHttpClient.post(url, params, JsonHttpResponseHandler);
        }

        /**
         * @param isLoading 是否显示加载对话框
         * @param url
         * @param params    参数
         * @param clazz     json解析的对象
         * @author caibing.zhang
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         */
        public void get(boolean isLoading, String url, RequestParams params,
                        Class<? extends BaseBean> clazz) {
            // 没有网络或不需要网络判断
            if (isNetWork && !NetWorkUtil.NETWORK) {
                showToastCroutonMessage(Style.ALERT, R.string.not_network);
                exception();
                return;
            }

            this.url = url;
            this.clazz = clazz;
            if (params != null) {
                this.mParams = params;
            }

            if (isCover && mainBody != null) {
                mainBody.setVisibility(View.GONE);
            }
            if (isLoading) {
                showLoadDialog(true);
            }
            BellaBuyHttpClient.get(url, params, JsonHttpResponseHandler);
        }

        /**
         * @param statusCode
         * @return
         * @author caibing.zhang
         * @createdate 2015年1月16日 下午10:06:21
         * @Description: 判断返回的判断码
         */
        private boolean isStatusCode(int statusCode) {
            dissmissWaitingDialog();

            if (statusCode == 200) {
                return true;
            } else {
                int message;
                switch (statusCode) {
                    case 404:
                        message = R.string.status_code_404;
                        break;
                    case 500:
                        message = R.string.status_code_500;
                        break;
                    default:
                        message = R.string.failure;
                        break;
                }
                showToastCroutonMessage(Style.ALERT, message);
                return false;
            }
        }

        /**
         * @param throwable
         * @author caibing.zhang
         * @createdate 2015年1月16日 下午11:14:15
         * @Description: 处理异常
         */
        private void handleThrowable(AsyncTask asyncTask, Throwable throwable) {
            dissmissWaitingDialog();
            try {
                String exceptionInfo = analysisException(throwable);
                LogUtil.e(exceptionInfo);
                if (exceptionInfo.indexOf("UnknownHostException") != -1) {
                    showToastCroutonMessage(Style.ALERT, R.string.time_out);
                } else if (exceptionInfo.indexOf("NoDataException") != -1) {
                    showToastCroutonMessage(Style.CONFIRM, R.string.no_data);
                } else if (exceptionInfo.indexOf("SocketException") != -1) {
                    showToastCroutonMessage(Style.ALERT, R.string.time_out);
                } else if (exceptionInfo.indexOf("SocketTimeoutException") != -1) {
                    showToastCroutonMessage(Style.ALERT, R.string.time_out);
                } else if (exceptionInfo.indexOf("ConnectTimeoutException") != -1) {
                    showToastCroutonMessage(Style.ALERT, R.string.time_out);
                } else if (exceptionInfo.indexOf("HttpResponseException") != -1) {
                    showToastCroutonMessage(Style.ALERT, R.string.http_response);
                } else {
                    showToastCroutonMessage(Style.ALERT, R.string.error);
                }
            } catch (IOException e) {
                e.printStackTrace();
                showToastCroutonMessage(Style.ALERT, R.string.error);
            }

            asyncTask.exception();
        }

        /**
         * 将异常信息转化成字符串
         *
         * @param t
         * @return
         * @throws IOException
         */
        private String analysisException(Throwable t) throws IOException {
            if (t == null)
                return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                t.printStackTrace(new PrintStream(baos));
            } finally {
                baos.close();
            }
            return baos.toString();
        }

        JsonHttpResponseHandler JsonHttpResponseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                LogUtil.D("--statusCode-->:" + statusCode + ", JSONArray response-->:" + response.toString());
                if (isStatusCode(statusCode)) {
                    // loadSuccess(response.toString());
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                try {
                    super.onSuccess(statusCode, headers, response);

                    String mRequestApi = "" + url + "?";
                    if (mParams != null) {
                        if (Constants.IS_TEST_FLAG) {
                            String[] array = mParams.toString().split("&");
                            for (String string : array) {
                                mRequestApi = mRequestApi + string + "&";
                            }
                        }
                    }
                    LogUtil.D("--statusCode-->:" + statusCode + ", JSONArray response-->:" + response.toString());
                    LogUtil.D("\n\n\n MobuyResponse: url=" + mRequestApi + "  \n    " + +statusCode + ", JSONObject response-->:" + response + "\n\n\n");
                    if (isStatusCode(statusCode)) {
                        String responseInfo = response.toString();
                        FileLocalCache.saveFile(url, responseInfo);
                        BaseBean bean = JSON.parseObject(responseInfo, clazz);
                        if (ConstantsCode.successCode(bean.getIsSuccess())) {
                            loadSuccess(bean);
                        } else if (bean.getIsSuccess() == 2)// 在没有登陆的状态下，访问了需要授权的接口，导致提示参数异常
                        {
                            // 不执行任何的操作
                        } else {
                            // 用户被注销
                            if (bean.getIsSuccess() == 12) {  //&&Constants.IS_TEST_FLAG==false
                                MyApplication.getInstance().setLOGIN_STATE(false);
                                //退出IM,当用户被挤下线的情况
                                try {
                                    EMChatManager.getInstance().logout();
                                } catch (Exception ex) {

                                }
                                //MyApplication.LOGIN_STATE=false;
                                // 直接引导到用户登陆界面
                                GuideUserActivity_.intent(AbstractActivity.this)
                                        .isRelogin(true).start();
                            } else if (bean.getIsSuccess() == 50)  // 公共标识码  用于经常改变提示语 错误码为50时,提示语从后台获取展示 add:sea 2015-12-02
                            {
                                SweetAlertDialog dialog = new SweetAlertDialog(AbstractActivity.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.setTitleText(getString(R.string.dialog_payssion_pay_success_title))
                                        .setContentText(bean.msg + "")
                                        .setTitleText(getString(R.string.prompt))
                                        .setCancelText(getString(R.string.dialog_payssion_pay_confirm))
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {

                                            }
                                        });
                                //dialog.showCancelButton(false);
                                dialog.show();
                            } else {
                                int resStringId = ConstantsCode.errorCode(bean.getIsSuccess());
                                SweetAlertDialog dialog = new SweetAlertDialog(AbstractActivity.this, SweetAlertDialog.WARNING_TYPE);
                                dialog.setTitleText(getString(R.string.dialog_payssion_pay_success_title))
                                        .setContentText(getString(resStringId))
                                        .setTitleText(getString(R.string.prompt))
                                        .setCancelText(getString(R.string.dialog_payssion_pay_confirm))
                                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {

                                            }
                                        });
                                //dialog.showCancelButton(false);
                                dialog.show();
                            }
                        }
                    }
                } catch (Exception ex) {
                    LogUtil.D("AbstractActivity  JsonDecode Error------------------------------------------>" + ex.toString());
                    String mRequestApi = "" + url + "?";
                    if (mParams != null) {
                        String[] array = mParams.toString().split("&");
                        for (String string : array) {
                            mRequestApi = mRequestApi + string + "&";
                        }
                    }
                    RequestParams errorParam = new RequestParams();
                    errorParam.put("url", mRequestApi);
                    errorParam.put("errorinfo", response);
                    errorParam.put("client_info", ex.toString());
                    new AsyncTask(false) {
                        @Override
                        public void exception() {

                        }

                        @Override
                        public void loadSuccess(BaseBean bean) {

                        }
                    }.post(false, RequestAPI.API_REPORT_ERROR_DATA, errorParam, BaseBean.class);
                    handleThrowable(AsyncTask.this, ex);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  String responseString) {
                // super.onSuccess(statusCode, headers, responseString);
                LogUtil.e("onSuccess(int statusCode, Header[] headers,String responseString)--statusCode-->:"
                        + statusCode
                        + ", String responseString-->:"
                        + responseString);
                if (isStatusCode(statusCode)) {
                    // loadSuccess(responseString);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                // super.onFailure(statusCode, headers, responseString,
                // throwable);
                LogUtil.e("--statusCode-->:" + statusCode
                        + ", responseString-->:" + responseString);
                handleThrowable(AsyncTask.this, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {
                // super.onFailure(statusCode, headers, throwable,
                // errorResponse);
                // showToastCroutonMessage(Style.ALERT, R.string.error);
                LogUtil.e("--statusCode-->:" + statusCode
                        + ", JSONArray errorResponse-->");
                handleThrowable(AsyncTask.this, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                // super.onFailure(statusCode, headers, throwable,
                // errorResponse);
                // showToastCroutonMessage(Style.ALERT, R.string.error);
                LogUtil.e("--statusCode-->:" + statusCode
                        + ", JSONObject errorResponse-->");
                handleThrowable(AsyncTask.this, throwable);
            }
        };
    }

    /**
     * @param cancelable 是否支持取消
     * @Description: 显示加载对话框前初始化
     */
    public void showLoadDialog(boolean cancelable) {
        // if(TextUtils.isEmpty(message)){
        // message=getString(R.string.please_wait);
        // }
        dialog = showWaitDialog(null, cancelable);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                AbstractActivity.this.finish();
            }
        });
    }

    /**
     * 初始化PopupWindow
     *
     * @param resLayoutId
     * @return
     */
    public View initPopuptWindowView(int resLayoutId) {
        View popupView = getLayoutInflater().inflate(resLayoutId, null);
        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
                (Bitmap) null));
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.getContentView().setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU
                        && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        popupView.setTag(mPopupWindow);
        mPopupWindow.setAnimationStyle(R.style.popup_bottom_anim);
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f; // 0.0-1.0
        getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        return popupView;
    }

    /**
     * 设置标题
     */
    public void setTitleName(String titleName) {
        titleView.setTitleName(titleName);
    }

    /**
     * 设置标题
     */
    protected void setTitleName(int stringID) {
        titleView.setTitleName(stringID);
    }

    /**
     * 设置左按钮事件
     */
    protected void setTitleLeftButtonListener(View.OnClickListener listener) {
        titleView.setTitleLeftButtonListener(listener);
    }

    /**
     * 设置左按钮背景和事件
     */
    protected void setTitleLeftImageButton(int imgID,
                                           View.OnClickListener listener) {
        titleView.setTitleLeftImageButton(imgID, listener);
    }

    protected void setTitleRightButtonText(String text) {
        titleView.setTitleRightButtonText(text);
    }

    /**
     * 设置右按钮事件
     */
    protected void setTitleRightButtonListener(View.OnClickListener listener) {
        titleView.setTitleRightButtonListener(listener);
    }

    /**
     * 重置右按钮，（还原默认值）
     */
    public void setTitleRightResetButton() {
        titleView.setTitleRightResetButton();
    }

    /**
     * 设置右按钮文本和事件
     *
     * @param text
     * @param listener
     */
    protected void setTitleRightButton(String text,
                                       View.OnClickListener listener) {
        titleView.setTitleRightButton(text, listener);
    }

    /**
     * 设置右按钮背景和事件
     */
    protected void setTitleRightImageButton(int imageResId,
                                            View.OnClickListener listener) {
        titleView.setTitleRightImageButton(imageResId, listener);
    }

    /**
     * 隐藏右侧按钮
     */
    protected void hiddenTitleRightButton() {
        titleView.hiddenTitleRightButton();
    }

    /**
     * 隐藏左侧按钮
     */
    protected void hiddenTitleLeftButton() {
        titleView.hiddenTitleLeftButton();
    }

    /**
     * 隐藏右侧按钮
     */
    protected void showTitleRightButton() {
        titleView.showTitleRightButton();
    }

    // /**
    // * 设置页面背景
    // */
    // protected void setPageBackground() {
    // mainBody.setBackgroundResource(R.drawable.page_bg);
    // }
    //
    // /**
    // * 设置页面背景颜色
    // */
    // protected void setPageBackgroundCorlor() {
    // mainBody.setBackgroundColor(getResources().getColor(R.color.gray_bg));
    // }

    /**
     * @return
     * @author caibing.zhang
     * @createdate 2014年8月26日 下午2:39:28
     * @Description: 获取会员信息对象
     */
    public UserInfo getMember() {
        return ((MyApplication) getApplication()).getMember();
    }

    /**
     * @param userInfo
     * @author caibing.zhang
     * @createdate 2015年1月27日 下午4:11:00
     * @Description: 获取用户的信息，保存会员相关的的信息
     */
    public void saveMember(UserInfo userInfo) {
        if (userInfo != null) {

            MyApplication.getMyApplication().userInfo = userInfo;
            SharedPreferenceUtil.saveString(getApplicationContext(),
                    Constants.USER_NAME, userInfo.getCustomerName());
            SharedPreferenceUtil.saveString(getApplicationContext(),
                    Constants.NICK_NAME, userInfo.getNickname());
            SharedPreferenceUtil.saveString(getApplicationContext(),
                    Constants.USER_IMAGE, userInfo.getHeadUrl());

            SharedPreferenceUtil.saveString(getApplicationContext(),
                    Constants.USER_EMAIL, userInfo.getEmail());
            FileLocalCache.setSerializableData(Constants.CACHE_DIR_SYSTEM,
                    userInfo, Constants.MEMBER);

        }
    }

    /**
     * @author caibing.zhang
     * @createdate 2015年1月27日 下午9:09:17
     * @Description: 注销用户
     */
    public void cancellationMember() {
        SharedPreferenceUtil.saveString(getApplicationContext(),
                Constants.TOKEN_ID, "");
        SharedPreferenceUtil.saveString(getApplicationContext(),
                Constants.USER_NAME, "");
        SharedPreferenceUtil.saveString(getApplicationContext(),
                Constants.USER_IMAGE, "");
        MyApplication.PUSH_COUNT_NUM = 0;
        MyApplication.SHOPPING_CART_NUM = 0;
        MyApplication.TOKEN_ID = null;
        ((MyApplication) getApplication()).setMember(null);
        FileLocalCache.delSerializableData(Constants.CACHE_DIR_SYSTEM,
                Constants.MEMBER);
    }

    public int screenHeight = 0;
    public int screenWidth = 0;

    public void getScreenHeightAndWidth() {
        WindowManager manager = (WindowManager) this
                .getSystemService(this.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * @author caibing.zhang
     * @createdate 2015年2月7日 下午9:06:58
     * @Description: 版本更新
     */
    public void updateVersion() {
        new AsyncTask(false) {
            @Override
            public void loadSuccess(BaseBean bean) {
                VersionListBean v = (VersionListBean) bean;
                SweetAlertDialog dialog;

                if (v.getVK_APP_ID() != 0) {
                    MyApplication.getMyApplication().initVKConfigureWithAppId(v.getVK_APP_ID());
                }

                if (v.getVersion() > getVersionCode()) {
                    // 无需要升级
                    final String downloadUrl = v.getUrl();
                    if (TextUtils.isEmpty(downloadUrl)) {
                        if (v.getForce() != 0) {//   如果等于0则表示的是强制升级，非零不一定要强制升级
                            dialog = new SweetAlertDialog(
                                    AbstractActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.update_version_title))
                                    .setContentText(v.getDescription())
                                    .setCancelText(getString(R.string.upgrade_immediately))
                                    .setCancelClickListener(new OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Uri uri = Uri.parse("market://details?id=" + AbstractActivity.this.getPackageName());
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                            dialog.setConfirmText(getString(R.string.later_upgrade));
                            //dialog.setCancelClickListener(null);
                            dialog.setCancelables(true);
                            dialog.show();
                            return;
                        } else {
                            dialog = new SweetAlertDialog(
                                    AbstractActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.update_version_title))
                                    .setContentText(v.getDescription())
                                    .setCancelText(getString(R.string.upgrade_immediately))
                                    .setCancelClickListener(new OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Uri uri = Uri.parse("market://details?id=" + AbstractActivity.this.getPackageName());
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                            dialog.setCancelText(getString(R.string.upgrade_immediately));
                            dialog.setCancelables(true);
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    System.exit(0);
                                }
                            });
                            dialog.show();
                        }
                        return;
                    } else {
                        dialog = new SweetAlertDialog(
                                AbstractActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getString(R.string.update_version_title))
                                .setContentText(v.getDescription())
                                .setCancelText(getString(R.string.upgrade_immediately))
                                .setCancelClickListener(new OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(downloadUrl);
                                        intent.setData(content_url);
                                        startActivity(intent);
                                    }
                                });
                        // 0=强制升级
                        if (v.getForce() == 0) {
                            dialog.setCancelText(getString(R.string.upgrade_immediately));
                            dialog.setCancelables(true);
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    System.exit(0);
                                }
                            });
                        } else {
                            dialog.setCancelables(true);
                        }
                        dialog.show();
                    }
                }
            }

            @Override
            public void exception() {
            }
        }.post(false, RequestAPI.APIVERSION_VERSION, null, VersionListBean.class);
    }

    /*------------------------------------------------相机的相关方法（begin）---------------------------------------------------------------------*/
    /**
     * 拍照头像路径
     */
    private String photoPath;
    /**
     * 上传头像路径
     */
    public String uploadPhotoPath;
    private Bitmap bitmap;

    private ITakePhotoUrl photoUrl;

    public void setPhotoUrl(ITakePhotoUrl photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 打开相机 拍照
     */
    public void takePhoto() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String fileDir = FileUtil.getSDCardPath() + "DCIM/Camera/";
        FileUtil.checkDir(fileDir);
        Uri uri = Uri.fromFile(new File(fileDir, DateFormat.format(
                "yyyy-MM-dd-hh-mm-ss", new Date()) + ".jpg"));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        photoPath = uri.toString().replace("file://", "");
        startActivityForResult(cameraIntent,
                Constants.UPLOAD_PICTURE_TAKE_HEAD);
    }

    /**
     * 打开相册
     */
    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)//解决4.4以后的版本兼容性问题
        {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }

        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, Constants.UPLOAD_PICTURE_HEAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.UPLOAD_PICTURE_TAKE_HEAD:// 头像拍照返回
                try {
                    bitmap = ImageUtil.getThumbnailFromFile(photoPath, 100, 100);
                    uploadPhotoPath = ImageUtil.compressionImage(photoPath);
//                    LogUtil.D("------->uploadPhotoPath="+uploadPhotoPath);
                    if (photoUrl != null && bitmap != null) {
                        photoUrl.getUploadPhotoPath(uploadPhotoPath);
                    }
                } catch (Exception e) {
                    LogUtil.D("---------->exception=" + e.toString());
                    // TODO: handle exception
                }
                break;
            case Constants.UPLOAD_PICTURE_HEAD:// 头像从手机相册选择
                if (resultCode == 0 || null == data) {
                    return;
                }
                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                        try {
                            photoPath = cursor.getString(column_index);
//                            bitmap = ImageUtil.getThumbnailFromFile(photoPath, 100, 100);
                            uploadPhotoPath = ImageUtil.compressionImage(photoPath);
                            if (photoUrl != null) {
                                photoUrl.getUploadPhotoPath(photoPath);
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 获取上传图片的回调函数
     */
    public interface ITakePhotoUrl {
        void getUploadPhotoPath(String uploadPhotoPath);
    }

	/*------------------------------------------------相机的相关方法（end）---------------------------------------------------------------------*/


    /**
     * @return
     * @author caibing.zhang
     * @createdate 2015年3月21日 上午11:55:49
     * @Description: 获取版本号
     */
    private int getVersionCode() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    this.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 打开软键盘
     */
    public void openKeyboard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
    }

    /**
     * 拷贝到剪贴板
     *
     * @param content 需要拷贝的内容
     * @description 2015年12月31日 12:14:58
     * @author sufun
     */
    public void doCopyToPaste(String content) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(content);
    }

    int mTryGetInfoTimes = 1;

    /**
     * 再次尝试着注册IM信息
     */
    void doTryGetIMINFO() {
        mTryGetInfoTimes++;
        if (mTryGetInfoTimes >= 3) {
            return;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception ex) {

                    }
                    doIMChatLogin();
                }
            }).start();
        }

    }

    /**
     * 用户去环信那边尝试登陆的次数
     */
    int mTryLogDoLoginTimes = 1;

    /**
     * 尝试着去环信平台再次登陆
     */
    void doTryeLogin(final String userName, final String password) {
        mTryLogDoLoginTimes++;
        if (mTryLogDoLoginTimes >= 3) {
            return;
        } else {
            EMChatManager.getInstance().login(userName, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    LogUtil.D("     HXLogin Loging Success");
                }

                @Override
                public void onProgress(int progress, String status) {
                    LogUtil.D("    HXLogin   Progress  ");
                }

                @Override
                public void onError(int code, String error) {
                    LogUtil.D("    HXLogin Success-------------->");
                    doTryeLogin(userName, password);
                }
            });
        }
    }

    /**
     * @author sufun
     * @createtime 2016年1月11日 17:12:54
     * @description 用于登陆第三方的IM功能
     */
    public void doIMChatLogin() {
        RequestParams params = new RequestParams();
        params.put("tokenid", MyApplication.TOKEN_ID);
        new AsyncTask(false) {
            @Override
            public void loadSuccess(BaseBean bean) {
                /*
                UserInfoList userInfoList=(UserInfoList)bean;
                UserInfo userInfo=userInfoList.getData();
                saveMember(userInfo);

                MyApplication.SHOPPING_CART_NUM=userInfo.getQtyCount();
                LeftColumn leftColumn=userInfo.getLeftColumn();
                if(leftColumn!=null){
                    MyApplication.PUSH_COUNT_NUM=leftColumn.getPushCount();
                }*/
                if (bean.getIsSuccess() == Constants.IM_REGISTER_FAIL) {
                    doTryGetIMINFO();
                    return;
                }
                IMUserDataList mDatas = (IMUserDataList) bean;
                if (mDatas.data != null && mDatas.data.size() > 0) {
                    //开始执行登陆的操作
                    IMUserInfo item = mDatas.data.get(0);
                    //登录
                    //将环信的聊天帐号存在本地
                    SharedPreferenceUtil.saveString(AbstractActivity.this, Constants.KEY_IM_ACCOUNT, item.getUsername());
                    doTryeLogin(item.getUsername(), item.getPassword());
                }
            }

            @Override
            public void exception() {
            }
        }.post(false, RequestAPI.API_IM_GET_USER_INFO, null, IMUserDataList.class);

    }

    /**
     * @author sufun
     * @descritption 开始动画效果
     * @createtime 2016年4月12日 18:05:43
     */
    public void startSVGAnimation(PathView pathView)
    {
        pathView.setFillAfter(true);  //渲染完后，进行相关的填充
        pathView.useNaturalColors();    //进行自然色的填充
        pathView.getPathAnimator().   //使用画的效果进行描边
                //pathView.getSequentialPathAnimator().   //使用分块的方式进行描边
                delay(0).    //每一笔填充的时间
                duration(1000).   //总共的时间
                interpolator(new AccelerateDecelerateInterpolator()).
                start();
    }

}

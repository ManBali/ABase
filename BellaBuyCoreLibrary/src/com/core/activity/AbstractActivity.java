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
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.core.CoreApplication;
import com.core.R;
import com.core.api.BellaBuyHttpClient;
import com.core.api.ConstantsCode;
import com.core.model.BaseBean;
import com.core.util.AndroidUtil;
import com.core.util.FileLocalCache;
import com.core.util.LogUtil;
import com.core.util.NetWorkUtil;
import com.core.util.constants.CoreConstant;
import com.core.util.file.FileUtil;
import com.core.util.image.ImageUtil;
import com.core.widget.TitleView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
        CoreApplication.mScreenWidth = screenSize[0];
        CoreApplication.mScreenHeight = screenSize[1];
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BellaBuyHttpClient.cancelAllRequests();
    }

    @Override
    protected void onDestroy() {
        AndroidUtil.removeAppActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * @param message
     * @author sufun.wu
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
     * @author sufun.wu
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
     * @author sufun.wu
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
     * @author sufun.wu
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
     * @author sufun.wu
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
         * @author sufun.wu
         * @createdate 2015年1月17日 下午3:10:26
         * @Description: 网络加载成功
         */
        public abstract void loadSuccess(BaseBean bean);

        /**
         * @author sufun.wu
         * @createdate 2015年1月17日 下午3:10:49
         * @Description: 网络加载失败：异常处理
         */
        public abstract void exception();

        /**
         * @param url
         * @param params 参数
         * @param clazz  json解析的对象
         * @author sufun.wu
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
         * @author sufun.wu
         * @createdate 2015年1月20日 下午9:57:39
         * @Description: post
         */
        public void post(boolean isLoading, String url, RequestParams params,
                         Class<? extends BaseBean> clazz) {
            if (params != null && !params.has("pageSize")) {
                params.put("pageSize", CoreConstant.PAGE_SIZE);
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
         * @author sufun.wu
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
         * @author sufun.wu
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
         * @author sufun.wu
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
                    showToastCroutonMessage(Style.CONFIRM, R.string.error_code_no_data);
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
                        if (CoreConstant.IS_TEST_FLAG) {
                            String[] array = mParams.toString().split("&");
                            for (String string : array) {
                                mRequestApi = mRequestApi + string + "&";
                            }
                        }
                    }
                    LogUtil.D("--statusCode-->:" + statusCode + ", JSONArray response-->:" + response.toString());
                    //LogUtil.D("\n\n\n MobuyResponse: url=" + mRequestApi + "  \n    " + +statusCode + ", JSONObject response-->:" + response + "\n\n\n");
                    if (isStatusCode(statusCode)) {
                        String responseInfo = response.toString();
                        FileLocalCache.saveFile(url, responseInfo);
                        BaseBean bean = JSON.parseObject(responseInfo, clazz);
                        if (ConstantsCode.successCode(bean.getIsSuccess())) {  //状态码正常的情况下，返回上层执行
                            loadSuccess(bean);
                        } else// 非正常的情况下，直接进行拦截，不让上层进行数据处理
                        {

                        }
                    }
                } catch (Exception ex) {
                    LogUtil.D("AbstractActivity  JsonDecode Error------------------------------------------>" + ex.toString());
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
     * @param userInfo
     * @author sufun.wu
     * @createdate 2015年1月27日 下午4:11:00
     * @Description: 获取用户的信息，保存会员相关的的信息
     */
    public void saveMember(Object userInfo) {

    }

    /**
     * @author sufun.wu
     * @createdate 2015年1月27日 下午9:09:17
     * @Description: 注销用户
     */
    public void cancellationMember() {

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
                CoreConstant.UPLOAD_PICTURE_TAKE_HEAD);
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
        startActivityForResult(intent, CoreConstant.UPLOAD_PICTURE_HEAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CoreConstant.UPLOAD_PICTURE_TAKE_HEAD:// 头像拍照返回
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
            case CoreConstant.UPLOAD_PICTURE_HEAD:// 头像从手机相册选择
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
     * @author sufun.wu
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

}

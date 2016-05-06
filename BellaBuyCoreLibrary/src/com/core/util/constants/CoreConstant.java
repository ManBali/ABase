package com.core.util.constants;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午6:54:35
 * @Description: 常量
 */
public class CoreConstant {
	public static  boolean IS_TEST_FLAG= false;        //日志打印,试运行环境、测试环境为true,生产环境为false
	public static boolean IS_UMENG = false;                //友盟统计控制开关,true允许使用,false则相反,配置后下次启动生效
	public static final String PREFERENCE_NAME="club"; //SharedPreference文件名
	
	//缓存文件目录0为system目录,1为SD卡目录
	public static final int CACHE_DIR_SYSTEM = 0;                 
	public static final int CACHE_DIR_SD = 1;
	
	//版本更新
	public static boolean IS_CLIENTUP_DATE;               //客户端有升级，需要进行提示
	public static int NOW_VERSION;                        //客户端当前版本号
	public static int LOADING_PROCESS = 0;       		  //下载进度
	public static String DOWNLOAD_CLIENT_PATH;  		  //应用存储路径
	
	//36位UUID
    public static final String UUID = "uuid";      
    //用户的登陆状态
    public static final String LOGIN_STATE="LOGIN_STATE";
    //目标窗口的状态
    public static final String MAIN_AMBITION_STATE="MAIN_AMBITION_STATE";
    //应该打开的网络界面
    public static final String WAP_SHOW_URL="Web_Show_URl";

	public static final String CGM_PROJECT_NUMBER=""; //google的推送CGM服务id

	public static final String PAGE_SIZE="10";

	public static final int UPLOAD_PICTURE_TAKE_HEAD = 51;//头像拍照
	public static final int UPLOAD_PICTURE_HEAD = 50;//头像从手机选择照片

	public static final String PACKAGE_NAME="com.bellabuy.nnbuy";
}

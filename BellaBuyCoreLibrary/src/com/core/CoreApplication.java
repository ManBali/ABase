package com.core;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.core.util.constants.CoreConstant;
import com.core.util.file.FileUtil;
import com.core.util.file.SharedPreferenceUtil;
import com.core.util.log.CoreLog;

import java.io.Serializable;


/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午11:02:12
 * @Description: 核心Application
 */
public class CoreApplication extends MultiDexApplication implements Serializable {

	public static final long serialVersionUID = 4656071326644680147L;
	public static Context context;
	public static boolean IS_EXIST_SDCARD;
	public static String CACHE_DIR_SD; // SD卡缓存目录
	public static String CACHE_DIR_SYSTEM; // 系统目录
	public static String IMAGE_DIR; // 图片目录
	public static String FILE_DIR; // 文件目录
	public static String LOG_DIR; // 日志目录
	public static String IMAGE_UPLOAD_TEMP; // 上传图片临时目录
	public static String LOG; // 日志保存的SD卡的目录
	public static String AllLOG;

	private boolean LOGIN_STATE = false;// 登陆状态默认为离线状态 false true:登陆状态

	public boolean IS_IM_ACTIVITY_OPEN=false;// IM聊天窗口是否是打开的,用来控制后台的通知

	/**
	 * 得到当前用户的登陆状态
	 *
	 * @return
	 */
	public boolean isLOGIN_STATE() {

		LOGIN_STATE = SharedPreferenceUtil.getBoolean(this,
				CoreConstant.LOGIN_STATE);
		return LOGIN_STATE;
		// return LOGIN_STATE;
	}

	/**
	 * 设置当前的用户登陆状态
	 *
	 * @param lOGIN_STATE
	 */
	public  void setLOGIN_STATE(boolean lOGIN_STATE) {
		SharedPreferenceUtil.saveBoolean(this, CoreConstant.LOGIN_STATE,
				lOGIN_STATE);
		LOGIN_STATE = lOGIN_STATE;
	}

	// 关于图片缓存的处理方案以及涉及到的一些常量
	private static CoreApplication coreApp;

	/** 取得单例的核心Applicatio 实例 **/
	public static CoreApplication getInstance() {
		return coreApp;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		init();
		coreApp = this;
	}


	/**
	 * @Description: 初始化
	 */
	private void init() {

		if (FileUtil.isExistSD()) {
			// SD存在
			CACHE_DIR_SD = FileUtil.getSDCacheDir(context);
			IS_EXIST_SDCARD = true;
		} else {
			// 不存在则使用系统目录
			CACHE_DIR_SD = context.getCacheDir().getPath();
		}
		CACHE_DIR_SD += "/";
		CoreLog.e("----SD卡目录---->>>:" + CACHE_DIR_SD);
		LOG = CACHE_DIR_SD + "cache.log";
		AllLOG = CACHE_DIR_SD + "allcache.log";
		IMAGE_DIR = CACHE_DIR_SD + "image/";
		FILE_DIR = CACHE_DIR_SD + "file/";
		LOG_DIR = CACHE_DIR_SD + "log/";
		IMAGE_UPLOAD_TEMP = CACHE_DIR_SD + "imageUploadTemp/";
		CACHE_DIR_SYSTEM = context.getCacheDir().getPath() + "/file/";
		FileUtil.checkDir(CACHE_DIR_SD);
		FileUtil.checkDir(IMAGE_DIR);
		FileUtil.checkDir(FILE_DIR);
		FileUtil.checkDir(LOG_DIR);
		// FileUtil.checkDir(IMAGE_UPLOAD_TEMP);
		FileUtil.checkDir(CACHE_DIR_SYSTEM);
		// TerminalUtils.getInstance(getApplicationContext()).initTerminalID();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		//VolleyTool.getInstance(this).release();
		super.onTerminate();
	}

//	File cacheDir;
//	/**
//	 * 图片下载器
//	 */
//	public ImageLoader imageLoader = null;
//
//	/**
//	 * 图片下载缓存处理器
//	 */
//	public DisplayImageOptions options;
//	/**
//	 * 带有圆角的图片显示器
//	 */
//	public DisplayImageOptions option_corner;
//
//	/**
//	 * 2015年4月21日 16:56:57
//	 * 
//	 * @author sufun 用于初始化图片的展示引擎
//	 */
//	@SuppressWarnings("deprecation")
//	public void initImageLoader() {
//		
//		
//		cacheDir = StorageUtils.getOwnCacheDirectory(this, IMAGE_DIR);// 图片存储的地址
//	
//		
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				this)
//				.memoryCacheExtraOptions(480, 800)
//				// max width, max height
//				.threadPoolSize(3)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.memoryCache(new WeakMemoryCache())//new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)
//				// You can pass your own memory cache implementation
//				.discCache(new UnlimitedDiscCache(cacheDir))
//				// You can pass your own disc cache implementation
//				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
//
//		// Initialize ImageLoader with configuration
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(config);
//		
//		
//		options = new DisplayImageOptions.Builder()
//		.showStubImage(R.drawable.wallpapermgr_thumb_default)//wallpapermgr_thumb_default
//		.showImageForEmptyUri(R.drawable.icon_nodata)
//		.bitmapConfig(Bitmap.Config.RGB_565)
//		.showImageOnFail(R.drawable.icon_nodata)
//		.cacheInMemory(false)//如果经常出现这个问题，则需要使用这个  
//		.displayer(new FadeInBitmapDisplayer(200))// 设置成淡入的效果
//		// .displayer(new RoundedBitmapDisplayer(8))//设置图像的圆角效果
//		.imageScaleType(ImageScaleType.EXACTLY)
//		.cacheOnDisc(true).build();
//	}
//
//	/**
//	 * 2015年4月22日 14:04:51
//	 * @author sufun
//	 * @describe 初始化带有圆角的图片加载器
//	 */
//	private void initCornerOption() {
//		option_corner = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.wallpapermgr_thumb_default)
//				.showImageForEmptyUri(R.drawable.icon_nodata)
//				.showImageOnFail(R.drawable.icon_nodata).cacheInMemory(true)
//				//.displayer(new FadeInBitmapDisplayer(200))// 设置成淡入的效果
//				.displayer(new RoundedBitmapDisplayer(8))//设置图像的圆角效果
//				.cacheOnDisc(true).build();
//
//	}

}

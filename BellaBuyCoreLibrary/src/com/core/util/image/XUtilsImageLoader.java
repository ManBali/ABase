package com.core.util.image;

//import com.lidroid.xutils.BitmapUtils;
//import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
//import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class XUtilsImageLoader {
	
//	private BitmapUtils bitmapUtils;  
////    private Context mContext;  
//  
//    public XUtilsImageLoader(Context context) {  
////        this.mContext = context;  
////        bitmapUtils = new BitmapUtils(mContext);  
//        bitmapUtils = BitmapHelp.getBitmapUtils(context);
//    }  
//    
//    public void clearMemory(){
////    	bitmapUtils.cancel();  //取消任务
////    	bitmapUtils.clearMemoryCache();  //清空内存缓存
////    	bitmapUtils=null;
////    	bitmapUtils.closeCache();  //清空内存、磁盘缓存
//    }
//    
//    /**
//	 * @Description: 异步加载图片
//	 * @param context
//	 * @param imageView  
//	 * @param imageUrl 图片RUL
//	 */
//	public void loadAsynchronousImage(Context context,ImageView imageView,String imageUrl){
//		CoreLog.i("--imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.wallpapermgr_thumb_default);
//			return;
//		}
//		bitmapUtils.configDefaultLoadingImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//设置图片压缩类型  
//		bitmapUtils.display(imageView, imageUrl, new CustomBitmapLoadCallBack(false));
//	}
//	
//	/**
//	 * @Description: 异步加载图片
//	 * @param context
//	 * @param imageView  
//	 * @param imageUrl 图片RUL
//	 */
//	public void loadAsynchronousImageToCallBack(Context context,
//			ImageView imageView,String imageUrl,BitmapLoadCallBack<ImageView> callBack){
//		CoreLog.i("--imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.wallpapermgr_thumb_default);
//			return;
//		}
//		bitmapUtils.configDefaultLoadingImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//设置图片压缩类型  
//		bitmapUtils.display(imageView, imageUrl,callBack);
//	}
//	
//	/**
//	 * @author caibing.zhang
//	 * @createdate 2014-1-6 下午5:03:29
//	 * @Description: 加载网络图片(圆角图片_适用于用户头像)
//	 * @param context
//	 * @param imageView
//	 * @param imageUrl
//	 */
//	public void loadAsynchronousImageRounded(Context context,ImageView imageView,String imageUrl){
//		CoreLog.i("--head imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.head_default);
//			return;
//		}
////		bitmapUtils.configDefaultLoadingImage(R.drawable.head_default);
////		bitmapUtils.configDefaultLoadFailedImage(R.drawable.head_default);
//		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//设置图片压缩类型  
//		bitmapUtils.display(imageView, imageUrl, new CustomBitmapLoadCallBack(true));
//	}
//	
//	public class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
//		private boolean isHeadImage;  //是否是用户头像
//		public CustomBitmapLoadCallBack(boolean isHeadImage) {
//			this.isHeadImage=isHeadImage;
//		}
//
//		@Override
//		public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
//		}
//
//		@Override
//		public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
//			// super.onLoadCompleted(container, uri, bitmap, config, from);
//			fadeInDisplay(isHeadImage,container, bitmap);
//		}
//	}
//	
//	private final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
//	private void fadeInDisplay(boolean isHeadImage,ImageView imageView, Bitmap bitmap) {
//		if(isHeadImage){
////			bitmap = ImageUtil.extractBitmap(bitmap, 100, 100);
//			bitmap = ImageUtil.toRoundBitmap(bitmap);
//			bitmap=ImageUtil.getfilletBitmap(bitmap);
//		}
//        final TransitionDrawable transitionDrawable =
//                new TransitionDrawable(new Drawable[]{
//                        TRANSPARENT_DRAWABLE,
//                        new BitmapDrawable(imageView.getResources(), bitmap)
//                });
//        imageView.setImageDrawable(transitionDrawable);
//        transitionDrawable.startTransition(500);
//    }
}

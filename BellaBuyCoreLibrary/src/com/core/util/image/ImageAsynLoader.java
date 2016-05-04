package com.core.util.image;

//import com.lidroid.xutils.BitmapUtils;
//import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
//import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午7:18:28
 * @Description: 图片异步加载工具类
 */
public class ImageAsynLoader {
	
//	public static BitmapUtils bitmapUtils;
//	public static BitmapUtils getInstance(Context context){
//		if (bitmapUtils == null) {
//			bitmapUtils = BitmapHelp.getBitmapUtils(context);
////            bitmapUtils = new BitmapUtils(context);
//        }
////		bitmapUtils.configDefaultLoadFailedImage(R.drawable.img_fail);
////		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
//		
////		bitmapUtils.configDefaultLoadingImage(R.drawable.wallpapermgr_thumb_default);
////		bitmapUtils.configDefaultLoadFailedImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_8888);
//	    return bitmapUtils;
//	}
//	
//	/**
//	 * @Description: 异步加载图片
//	 * @param context
//	 * @param imageView  
//	 * @param imageUrl 图片RUL
//	 */
//	public static void loadAsynchronousImage(Context context,ImageView imageView,String imageUrl){
//		CoreLog.i("--imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.wallpapermgr_thumb_default);
//			return;
//		}
//		getInstance(context);
//		bitmapUtils.configDefaultLoadingImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.wallpapermgr_thumb_default);
////		bitmapUtils.display(imageView, imageUrl);
//		bitmapUtils.display(imageView, imageUrl, new CustomBitmapLoadCallBack(false));
//	}
//	
//	/**
//	 * @Description: 异步加载图片
//	 * @param context
//	 * @param imageView  
//	 * @param imageUrl 图片RUL
//	 */
//	public static void loadAsynchronousImageToCallBack(Context context,
//			ImageView imageView,String imageUrl,BitmapLoadCallBack<ImageView> callBack){
//		CoreLog.i("--imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.wallpapermgr_thumb_default);
//			return;
//		}
//		getInstance(context);
//		bitmapUtils.configDefaultLoadingImage(R.drawable.wallpapermgr_thumb_default);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.wallpapermgr_thumb_default);
////		bitmapUtils.display(imageView, imageUrl);
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
//	public static void loadAsynchronousImageRounded(Context context,ImageView imageView,String imageUrl){
//		CoreLog.i("--head imageUrl-->:"+imageUrl);
//		if(TextUtils.isEmpty(imageUrl)){
//			imageView.setBackgroundResource(R.drawable.head_default);
//			return;
//		}
//		getInstance(context);
//		bitmapUtils.configDefaultLoadingImage(R.drawable.head_default);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.head_default);
//		bitmapUtils.display(imageView, imageUrl, new CustomBitmapLoadCallBack(true));
//	}
//	
//	public static class CustomBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
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
//	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
//	private static void fadeInDisplay(boolean isHeadImage,ImageView imageView, Bitmap bitmap) {
//		if(isHeadImage){
//			bitmap = ImageUtil.extractBitmap(bitmap, 100, 100);
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

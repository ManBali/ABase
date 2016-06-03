package com.core.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.bumptech.glide.Glide;
import com.core.R;
import com.core.widget.photoview.PhotoView;
import com.core.widget.progress.CircularProgress;

import java.util.List;

public class ImageBrowserAdapter extends PagerAdapter {

	private Context context;
	private List<String> mPhotos;
//	private int position;
//	private Bitmap bitmap;
//	private Map<Integer,Bitmap> map = new HashMap<Integer,Bitmap>();
	//private XUtilsImageLoader xUtilsImageLoader;

//	public Map<Integer, Bitmap> getMap() {
//		return map;
//	}

	public ImageBrowserAdapter(Context context, List<String> photos) {
		this.context=context;
		this.mPhotos = photos;
		//xUtilsImageLoader=new XUtilsImageLoader(context);
	}

	@Override
	public int getCount() {
		return mPhotos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public View instantiateItem(final ViewGroup container, final int position) {
		View imageLayout = LayoutInflater.from(container.getContext()).inflate(R.layout.image_browser_item, container, false);
		final PhotoView photoView = (PhotoView) imageLayout.findViewById(R.id.img_id);

		final CircularProgress spinner = (CircularProgress) imageLayout.findViewById(R.id.pb_id);

		Glide.with(context).
				load(mPhotos.get(position))
				.placeholder(com.core.R.drawable.wallpapermgr_thumb_default)
				.error(com.core.R.drawable.icon_nodata)
				.into(photoView);
		//去除那个滚动的圈圈
		spinner.setVisibility(View.GONE);
		container.addView(imageLayout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		return imageLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}

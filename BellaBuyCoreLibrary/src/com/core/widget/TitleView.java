package com.core.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellabuy.nnbuy.R;
import com.bellabuy.utils.AndroidUtil;

public class TitleView extends FrameLayout {

	public Button mLeftBtn;
	public Button mRightBtn,mRightBtn2;
	public TextView mTitle;
	
	public ImageView mShopBagImageView;
	public SFBubbleButtonView mBubbleButton;
	public EditText titleSearch;

	public Button mPhoto;
	
//	public ImageView mLikeImageView;
//	public SFBubbleButtonView mLikeBubbleButton;
	
	public View.OnClickListener mBubbleButtonClickListerner;//购物车和收藏点击事件
	private View.OnClickListener mOnLeftButtonClickListener;
	private View.OnClickListener mOnRightButtonClickListener;

	public IBackListener iBackListener;
	private IHomeListener iHomeListener;
	private Resources res;

	public TitleView(Context context) {
		this(context, null);
		res=context.getResources();
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		res=context.getResources();
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.template_title, this, true);
		mLeftBtn = (Button)view.findViewById(R.id.title_left_btn);
		mLeftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(iBackListener!=null){
					iBackListener.backListener();
				}else if(mOnLeftButtonClickListener != null){
					mOnLeftButtonClickListener.onClick(v);
				}
			}
		});
		mRightBtn = (Button)view.findViewById(R.id.title_right_btn);
		mRightBtn2 = (Button)view.findViewById(R.id.title_right_btn_2);
		mRightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mOnRightButtonClickListener != null){
					mOnRightButtonClickListener.onClick(v);
				}else if(iHomeListener!=null){
					iHomeListener.goHomeListener();
				}
			}
		});
		mTitle = (TextView)view.findViewById(R.id.title_text);
		mPhoto = (Button) view.findViewById(R.id.order_passport_photo);
		mShopBagImageView=(ImageView)view.findViewById(R.id.img_shopping_card);
		mBubbleButton=(SFBubbleButtonView)view.findViewById(R.id.bubble_shopping_cart);
		titleSearch = (EditText) view.findViewById(R.id.title_search);

//		mLikeImageView=(ImageView)view.findViewById(R.id.img_like);
//		mLikeBubbleButton=(SFBubbleButtonView)view.findViewById(R.id.bubble_like);

		mBubbleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mBubbleButtonClickListerner!=null)
				{
					mBubbleButtonClickListerner.onClick(v);
				}
			}
		});

		mShopBagImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mBubbleButtonClickListerner!=null)
				{
					mBubbleButtonClickListerner.onClick(v);
				}
			}
		});
//
//		mLikeImageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(mBubbleButtonClickListerner!=null)
//				{
//					mBubbleButtonClickListerner.onClick(v);
//				}
//			}
//		});

//		mLikeBubbleButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(mBubbleButtonClickListerner!=null)
//				{
//					mBubbleButtonClickListerner.onClick(v);
//				}
//			}
//		});
	}

	public void setTitleName(String text) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(text);
//		mTitle.setBackgroundResource(0);
	}

	public void setTitleName(int stringID) {
		setTitleName(res.getString(stringID));
	}

	public void setTitleLeftImageButton(int imgID, View.OnClickListener listener) {
		mLeftBtn.setBackgroundResource(imgID);
		mLeftBtn.setVisibility(View.VISIBLE);
		mOnLeftButtonClickListener = listener;
	}

	public void setTitleLeftButtonListener(View.OnClickListener listener){
		mOnLeftButtonClickListener = listener;
	}

	/**
	 * 设置右按钮文本
	 */
	public void setTitleRightButtonText(String text) {
		mRightBtn.setText(text);
		mRightBtn.setVisibility(View.VISIBLE);
		mRightBtn.setMinWidth(AndroidUtil.dip2px(getContext(), 65));
	}

	/**
	 * 重置右按钮，（还原默认值）
	 */
	public void setTitleRightResetButton() {
		mRightBtn.setVisibility(View.VISIBLE);
		mRightBtn2.setVisibility(View.GONE);
	}

	/**
	 * 设置右按钮文本和事件
	 */
	public void setTitleRightButton(String text,View.OnClickListener listener) {
		mRightBtn.setVisibility(View.GONE);
		mRightBtn2.setVisibility(View.VISIBLE);
		mRightBtn2.setText(text);
		mRightBtn2.setOnClickListener(listener);
//		mRightBtn.setTextColor(getResources().getColor(R.color.app_bg));
//		mRightBtn.setVisibility(View.VISIBLE);
//		mRightBtn.setBackgroundResource(resid);
//		mRightBtn.setMinWidth(AndroidUtil.dip2px(getContext(), 100));
//		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) mRightBtn.getLayoutParams();
//		params.width=params.width+50;
//		mRightBtn.setLayoutParams(params);
//		mOnRightButtonClickListener = listener;
	}

	/**
	 * 设置右按钮背景和事件
	 */
	public void setTitleRightImageButton(int imageResId,View.OnClickListener listener) {
		mRightBtn.setBackgroundResource(imageResId);
		mRightBtn.setVisibility(View.VISIBLE);
		mOnRightButtonClickListener = listener;
	}

	public void setTitleRightButtonListener(View.OnClickListener listener){
		mOnRightButtonClickListener = listener;
	}
	
	public void removeTitleLeftButton() {
		mLeftBtn.setVisibility(View.INVISIBLE);
		mOnLeftButtonClickListener = null;
	}
	
	public void hiddenTitleLeftButton() {
		mLeftBtn.setVisibility(View.INVISIBLE);
	}
	
	public void showTitleLeftButton() {
		mLeftBtn.setVisibility(View.VISIBLE);
	}
	
	public void removeTitleRightButton() {
		mRightBtn.setVisibility(View.INVISIBLE);
		mOnRightButtonClickListener = null;
	}
	
	public void hiddenTitleRightButton() {
		mRightBtn.setVisibility(View.INVISIBLE);
		mRightBtn2.setVisibility(View.GONE);
	}
	
	public void showTitleRightButton() {
		mRightBtn.setVisibility(View.VISIBLE);
	}
	
	public interface OnLeftButtonClickListener {
		public void onClick(View button);
	}

	public interface OnRightButtonClickListener {
		public void onClick(View button);
	}
	
	public interface IBackListener{
		public void backListener();
	}
	
	public interface IHomeListener{
		public void goHomeListener();
	}
}

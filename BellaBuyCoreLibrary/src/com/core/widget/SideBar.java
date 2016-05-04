package com.core.widget;

import java.util.List;

import com.core.util.CoreUtil;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午10:59:45
 * @Description: 按字母索引视图
 */
public class SideBar extends View {
	
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	private List<String>  barName;
	private int choose = -1;
	private Paint paint = new Paint();
	private boolean showBkg = false;

	public List<String> getBarName() {
		return barName;
	}

	public void setBarName(List<String> barName) {
		this.barName = barName;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#40000000"));
		}
		int height = getHeight();
		int width = getWidth();
		int singleHeight;
		if(!CoreUtil.listIsNull(barName)){
			singleHeight= height / (barName.size()-1);
			for (int i = 1; i < barName.size(); i++) {
				paint.setColor(Color.WHITE);
				paint.setTypeface(Typeface.DEFAULT_BOLD);
				paint.setAntiAlias(true);
				paint.setTextSize(20);
				if (i == choose) {
					paint.setColor(Color.parseColor("#3399ff"));
					paint.setFakeBoldText(true);
				}
				float xPos = width / 2 - paint.measureText(barName.get(i)) / 2;
				float yPos = singleHeight * (i-1) + singleHeight;
				canvas.drawText(barName.get(i), xPos, yPos, paint);
				paint.reset();
			}
		}else{
			singleHeight=10;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * barName.size());
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < barName.size()) {
					listener.onTouchingLetterChanged(barName.get(c));
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >= 0 && c < barName.size()-1) {
					listener.onTouchingLetterChanged(barName.get(c+1));
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}

package com.core.widget.listview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午10:57:02
 * @Description: 带圆角按下效果的ListView,并且解决与ScrollView冲突不能展开问题
 */
public class CoreListView extends NoScrollListView {

	public CoreListView(Context context) {
		super(context);
	}
	
	public CoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void myOnInterceptTouchEvent(MotionEvent ev,int cornerRoundResID,
			int cornerRoundTopResID,int cornerRoundBottomResID,int itemBgResID) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION) {
				break;
			} else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						setSelector(cornerRoundResID);
					} else {
						setSelector(cornerRoundTopResID);
					}
				} else if (itemnum == (getAdapter().getCount() - 1)) {
					setSelector(cornerRoundBottomResID);
				} else {
					setSelector(itemBgResID);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
	}
	
}

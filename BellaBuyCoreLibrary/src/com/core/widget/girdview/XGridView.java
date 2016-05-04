package com.core.widget.girdview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午10:51:35
 * @Description: 扩展GridView,解决与ScrollView冲突不能展开问题
 */
public class XGridView extends GridView {

	public XGridView(Context context) {
		super(context);
	}
	
	public XGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}

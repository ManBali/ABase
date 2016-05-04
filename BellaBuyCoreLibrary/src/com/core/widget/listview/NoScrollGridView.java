package com.core.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author caibing.zhang
 * @createdate 2015年3月18日 下午3:29:41
 * @Description: 扩展GridView,解决与ScrollView冲突不能展开问题
 */
public class NoScrollGridView extends GridView{  
	  
    public NoScrollGridView(Context context, AttributeSet attrs){  
         super(context, attrs);  
    }  
 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
         super.onMeasure(widthMeasureSpec, mExpandSpec);  
    }  
}  


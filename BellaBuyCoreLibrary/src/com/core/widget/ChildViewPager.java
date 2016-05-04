package com.core.widget;

import com.core.util.log.CoreLog;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author miaoxin.ye
 * @Description: 子ViewPager,解决双ViewPager冲突问题
 * @date 2013-12-20 上午11:01:20
 */
public class ChildViewPager extends ViewPager {
	
	//触摸时按下的点    
	PointF downP = new PointF(); 
	//触摸时当前的点     
	PointF curP = new PointF(); 
	PointF upP = new PointF();
	OnSingleTouchListener onSingleTouchListener;
	
	private int position = 0;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public ChildViewPager(Context context, AttributeSet attrs) {         
		super(context, attrs);         
	} 
	
	public ChildViewPager(Context context) {         
		super(context);         
	}
	
	@Override    
	public boolean onInterceptTouchEvent(MotionEvent arg0) { 
		//当拦截触摸事件到达此位置的时候，返回true，说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent         
		return true;
	}
	
	 @Override    
	 public boolean onTouchEvent(MotionEvent arg0){
		//每次进行onTouch事件都记录当前的按下的坐标 
		 curP.x = arg0.getX();         
		 curP.y = arg0.getY(); 
		 if(arg0.getAction() == MotionEvent.ACTION_DOWN){ 
			 //记录按下时候的坐标 ,切记不可用 downP = curP ,这样在改变curP的时候,downP也会改变
			 downP.x = arg0.getX();
			 downP.y = arg0.getY(); 
			//此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
			getParent().requestDisallowInterceptTouchEvent(true); 
		 }
		 if(arg0.getAction() == MotionEvent.ACTION_MOVE){ 
			 upP.x = arg0.getX();
			 upP.y = arg0.getY();
			 if(downP.x != 0 && downP.y != 0){  
				if(downP.x - upP.x > 30){
					CoreLog.i("向左滑动");
	            }  
				if(upP.x - downP.x > 30){ 
					if(position==0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}else{
						getParent().requestDisallowInterceptTouchEvent(true);
					}
					CoreLog.i("向右滑动");
			    }
		     } 
		 }
		 if(arg0.getAction() == MotionEvent.ACTION_UP){ 
			//在up时判断是否按下和松手的坐标为一个点 ,如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick 
			 if(Math.abs(downP.x-curP.x)<20 && Math.abs(downP.y-curP.y)<20){ 
				 onSingleTouch(getCurrentItem()); 
				 return true;
			 }
		 }
		 return super.onTouchEvent(arg0);
	 }
	 
	 public void onSingleTouch(int position) {         
		 if (onSingleTouchListener!= null) {               
			 onSingleTouchListener.onSingleTouch(position);         
		 }     
	 }
	 
	 public interface OnSingleTouchListener {        
		 public void onSingleTouch(int position);     
	 } 
	 
	 public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {         
		 this.onSingleTouchListener = onSingleTouchListener;     
	 }
	 
	 
	 
}

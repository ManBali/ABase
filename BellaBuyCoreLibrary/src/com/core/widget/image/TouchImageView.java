package com.core.widget.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-14 上午10:31:17
 * @Description: 多点触控图像
 */
public class TouchImageView extends ImageView {

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private static final float MAX_SCALE_RATE = 2F;
	private static final float SCALE_SLOP = 0.01f;
	private static final float MOVE_SLOP = 16;
	private int mode = NONE;
	private float startDistance;
	private Matrix currentMatrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	protected Matrix baseMatrix = new Matrix();
	private float[] mMatrixValues = new float[9];
	private PointF startPoint = new PointF();
	private PointF midPoint = new PointF();
	public Bitmap displayBitmap;
	private boolean enableMultiTouch = true;
	public boolean needMoveEvent = false;
	private int bitmapWidth;
	private int bitmapHeight;
	private OnSingleTapListener singleTapListener = null;

	public TouchImageView(Context context) {
		super(context);
		init();
	}

	public TouchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TouchImageView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		init();
	}

	private void init() {
		if (Build.VERSION.SDK_INT >= 7) {
			enableMultiTouch = true;
		} else {
			enableMultiTouch = false;
		}
		setScaleType(ScaleType.MATRIX);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		final Drawable drawable = getDrawable();
		if (drawable != null) {
			bitmapWidth = drawable.getIntrinsicWidth();
			bitmapHeight = drawable.getIntrinsicHeight();
			setFitScreenMatrix(bitmapWidth, bitmapHeight, baseMatrix);
			setImageMatrix(baseMatrix);
			currentMatrix.set(baseMatrix);
		}
	}
	
	private void setFitScreenMatrix(int bitmapW, int bitmapH, Matrix matrix) {
		if (bitmapH > 0 && bitmapW > 0) {
			matrix.reset();
			final float viewWidth = getWidth();
			final float viewHeight = getHeight();
			float viewRate = viewWidth / viewHeight;
			float bitmapRate = bitmapW / bitmapH;
			if (viewRate > bitmapRate) {
				float heightScale = viewHeight / bitmapH;
				matrix.postScale(heightScale, heightScale);
				matrix.postTranslate((viewWidth - bitmapW * heightScale) / 2, 0);
			} else {
				float widthScale = viewWidth / bitmapW;
				matrix.postScale(widthScale, widthScale);
				matrix.postTranslate(0, (viewHeight - bitmapH * widthScale) / 2);
			}
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if (!enableMultiTouch) {
			return false;
		}
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		final int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			currentMatrix.set(getImageMatrix());
			savedMatrix.set(currentMatrix);
			if (roughCompareScale(getCurrentScale(), getScale(baseMatrix))) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			startPoint.set(event.getX(), event.getY());
			return true;
		case MotionEvent.ACTION_POINTER_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			startDistance = spacing(event);
			savedMatrix.set(currentMatrix);
			midPoint(midPoint, event);
			mode = ZOOM;
			return true;
		case MotionEvent.ACTION_MOVE:
			currentMatrix.set(savedMatrix);
			if (mode == NONE) {
				float offsetX = Math.abs(event.getX() - startPoint.x);
				float offsetY = Math.abs(event.getY() - startPoint.y);
				if (offsetX > MOVE_SLOP || offsetY > MOVE_SLOP) {
					mode = DRAG;
				}
				return false;
			}
			switch (mode) {
			case DRAG:
				if (roughCompareScale(getScale(currentMatrix), getScale(baseMatrix))) {
					return false;
				}
				float moveX = event.getX() - startPoint.x;
				float moveY = event.getY() - startPoint.y;
				float currentTransX = getTransX(currentMatrix);
				float currentTransY = getTransY(currentMatrix);
				boolean hasMovedX = false;
				if (canCurrentCoverX()) {
					if (moveX > 0) {
						final float currentMaxTransX = getCurrentMaxTransX();
						if (currentTransX != currentMaxTransX) {
							if (currentTransX + moveX < currentMaxTransX) {
								currentMatrix.postTranslate(moveX, 0);
								hasMovedX = true;
							} else {
								currentMatrix.postTranslate(currentMaxTransX - currentTransX, 0);
							}
						}
					} else if (moveX < 0) {
						final float currentMinTransX = getCurrentMinTransX();
						if (currentMinTransX != currentTransX) {
							if (currentTransX + moveX > currentMinTransX) {
								currentMatrix.postTranslate(moveX, 0);
								hasMovedX = true;
							} else {
								currentMatrix.postTranslate(currentMinTransX - currentTransX, 0);
							}
						}
					}
				}

				if (canCurrentCoverY()) {
					if (moveY > 0) {
						final float currentMaxTransY = getCurrentMaxTransY();

						if (currentMaxTransY != currentTransY) {
							if (currentTransY + moveY < currentMaxTransY) {
								currentMatrix.postTranslate(0, moveY);
							} else {
								currentMatrix.postTranslate(0, currentMaxTransY - currentTransY);
							}
						}
					} else if (moveY < 0) {
						final float currentMinTransY = getCurrentMinTransY();
						if (currentMinTransY != currentTransY) {
							if (currentTransY + moveY > currentMinTransY) {
								currentMatrix.postTranslate(0, moveY);
							} else {
								currentMatrix.postTranslate(0, currentMinTransY - currentTransY);
							}
						}
					}
				}
				setImageMatrix(currentMatrix);
				getParent().requestDisallowInterceptTouchEvent(hasMovedX);
				return true;
			case ZOOM:
				float newDistance = spacing(event);
				currentMatrix.set(savedMatrix);
				float scale = newDistance / startDistance;
				currentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
				this.setImageMatrix(currentMatrix);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			mode = NONE;
			return true;
		case MotionEvent.ACTION_POINTER_UP:
			float currentScale = getScale(currentMatrix);
			float baseScale = getScale(baseMatrix);
			if (currentScale <= baseScale) {
				AnimZoomTo(baseScale, midPoint.x, midPoint.y, getTransX(baseMatrix), getTransY(baseMatrix), 300);
			} else {
				//
			}
			mode = NONE;
			return true;
		case MotionEvent.ACTION_CANCEL:
			mode = NONE;
			break;
		}
		return true;
	}

	private float spacing(MotionEvent event) {
		double x = event.getX(0) - event.getX(1);
		double y = event.getY(0) - event.getY(1);
		return (Float.parseFloat(Math.sqrt(x * x + y * y)+""));
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void zoomTo(float scale, float centerX, float centerY) {
		float oldScale = getCurrentScale();
		float deltaScale = scale / oldScale;
		currentMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(currentMatrix);
	}

	private void zoomTo(float targetScale, float centerX, float centerY, float targetTransX, float targetTransY) {
		final float currentScale = getCurrentScale();
		final float deltaScale = targetScale / currentScale;
		final float currentTransX = getTransX(currentMatrix);
		final float deltaTransX = targetTransX - currentTransX;
		final float currentTransY = getTransY(currentMatrix);
		final float deltaTransY = targetTransY - currentTransY;
		currentMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		currentMatrix.postTranslate(deltaTransX - (getTransX(currentMatrix) - currentTransX), deltaTransY - (getTransY(currentMatrix) - currentTransY));
		setImageMatrix(currentMatrix);
	}

	private void AnimZoomTo(final float destScale, final float centerX, final float centerY, final float destX, final float destY, final float duration) {
		final float perScale = (destScale - getCurrentScale()) / duration;
		final float oldScale = getCurrentScale();
		final float oldTransX = getTransX(currentMatrix);
		final float perTransX = (destX - oldTransX) / duration;
		final float oldTransY = getTransY(currentMatrix);
		final float perTransY = (destY - oldTransY) / duration;
		final long startTime = System.currentTimeMillis();
		mHandler.post(new Runnable() {
			public void run() {
				final long now = System.currentTimeMillis();
				final float currentMs = Math.min(duration, now - startTime);
				float targetScale = oldScale + (perScale * currentMs);
				float targetTransX = oldTransX + (perTransX * currentMs);
				float targetTransY = oldTransY + (perTransY * currentMs);
				zoomTo(targetScale, centerX, centerY, targetTransX, targetTransY);
				if (currentMs < duration) {
					mHandler.post(this);
				}
			}
		});
	}

	protected Handler mHandler = new Handler();

	protected void zoomTo(final float scale, final float centerX, final float centerY, final float durationMs) {
		final float perScale = (scale - getCurrentScale()) / durationMs;
		final float oldScale = getCurrentScale();
		final long startTime = System.currentTimeMillis();
		mHandler.post(new Runnable() {
			public void run() {
				long now = System.currentTimeMillis();
				float currentMs = Math.min(durationMs, now - startTime);
				float targetScale = oldScale + (perScale * currentMs);
				zoomTo(targetScale, centerX, centerY);
				if (currentMs < durationMs) {
					mHandler.post(this);
				}
			}
		});
	}

	private boolean roughCompareScale(float src, float dest) {
		if (Math.abs(src - dest) < SCALE_SLOP) {
			return true;
		} else {
			return false;
		}
	}

	public void doubleZoomIn() {
		final float currentScale = getCurrentScale();
		final float baseScale = getScale(baseMatrix);
		if (roughCompareScale(baseScale, currentScale)) {
			float targetScale = Math.min(currentScale * 2, MAX_SCALE_RATE);
			float deltaScale = targetScale / currentScale;
			final Matrix tmpMatrix = new Matrix(currentMatrix);
			tmpMatrix.postScale(deltaScale, deltaScale, getWidth() / 2, getHeight() / 2);
			float destX = (getWidth() - getScaleWidth(tmpMatrix)) / 2;
			float destY = (getHeight() - getScaleHeight(tmpMatrix)) / 2;
			AnimZoomTo(targetScale, getWidth() / 2, getHeight() / 2, destX, destY, 300);
		}
	}
	
	private GestureDetector gestureDetector = new GestureDetector(new SimpleOnGestureListener() {
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (singleTapListener != null) {
				singleTapListener.onSingleTap(TouchImageView.this);
			}
			return true;
		};
		public boolean onDoubleTap(MotionEvent event) {
			final float currentScale = getCurrentScale();
			final float baseScale = getScale(baseMatrix);
			if (roughCompareScale(baseScale, currentScale)) {
				float targetScale = Math.min(currentScale * 2, MAX_SCALE_RATE);
				float deltaScale = targetScale / currentScale;
				final Matrix tmpMatrix = new Matrix(currentMatrix);
				tmpMatrix.postScale(deltaScale, deltaScale, event.getX(), event.getY());
				float destX = (getWidth() - getScaleWidth(tmpMatrix)) / 2;
				float destY = (getHeight() - getScaleHeight(tmpMatrix)) / 2;
				AnimZoomTo(targetScale, event.getX(), event.getY(), destX, destY, 300);
				return true;
			}
			if (currentScale > getScale(baseMatrix)) {
				AnimZoomTo(getScale(baseMatrix), event.getX(), event.getY(), getTransX(baseMatrix), getTransY(baseMatrix), 300);
				return true;
			}
			return true;
		};
	});
	
	public void setOnSingleTapListener(OnSingleTapListener listener) {
		this.singleTapListener = listener;
	}

	public static interface OnSingleTapListener {
		void onSingleTap(TouchImageView view);
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		displayBitmap = bm;
	}

	public Bitmap getBitmap() {
		return displayBitmap;
	}
	
	public Matrix getBaseMatrix() {
		return baseMatrix;
	}

	protected float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(mMatrixValues);
		return mMatrixValues[whichValue];
	}

	private float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	private float getTransX(Matrix matrix) {
		return getValue(matrix, Matrix.MTRANS_X);
	}

	private float getTransY(Matrix matrix) {
		return getValue(matrix, Matrix.MTRANS_Y);
	}

	protected float getCurrentScale() {
		return getScale(currentMatrix);
	}
	
	private float getCurrentScaleWidth() {
		return getScale(currentMatrix) * bitmapWidth;
	}

	private float getScaleWidth(Matrix matrix) {
		return getScale(matrix) * bitmapWidth;
	}

	private float getScaleHeight(Matrix matrix) {
		return getScale(matrix) * bitmapHeight;
	}

	private float getCurrentScaleHeight() {
		return getScale(currentMatrix) * bitmapHeight;
	}

	private float getCurrentMinTransX() {
		return getWidth() - getCurrentScaleWidth();
	}

	private float getCurrentMaxTransX() {
		return 0;
	}

	private float getCurrentMinTransY() {
		return getHeight() - getCurrentScaleHeight();
	}

	private float getCurrentMaxTransY() {
		return 0;
	}

	private boolean canCurrentCoverX() {
		if (getCurrentScaleWidth() >= getWidth()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean canCurrentCoverY() {
		if (getCurrentScaleHeight() >= getHeight()) {
			return true;
		} else {
			return false;
		}
	}

}

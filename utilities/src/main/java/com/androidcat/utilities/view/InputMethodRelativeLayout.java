package com.androidcat.utilities.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class InputMethodRelativeLayout.
 */
public class InputMethodRelativeLayout extends RelativeLayout {

	/** The width. */
	private int width;
	
	/** The on size changed listenner. */
	protected OnSizeChangedListenner onSizeChangedListenner;
	
	/** The size changed. */
	private boolean sizeChanged = false; // 变化的标志
	
	/** The height. */
	private int height;
	
	/** The screen width. */
	private int screenWidth; // 屏幕宽度
	
	/** The screen height. */
	private int screenHeight; // 屏幕高度

	/**
	 * Instantiates a new input method relative layout.
	 *
	 * @param paramContext the param context
	 * @param paramAttributeSet the param attribute set
	 */
	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		Display localDisplay = ((Activity) paramContext).getWindowManager()
				.getDefaultDisplay();
		 DisplayMetrics metrics = new DisplayMetrics();  
	     localDisplay.getMetrics(metrics);  
		//this.screenWidth = localDisplay.getWidth();
		//this.screenHeight = localDisplay.getHeight();
	     this.screenWidth = metrics.widthPixels;  
	     this.screenHeight = metrics.heightPixels;  
	}

	/**
	 * Instantiates a new input method relative layout.
	 *
	 * @param paramContext the param context
	 * @param paramAttributeSet the param attribute set
	 * @param paramInt the param int
	 */
	public InputMethodRelativeLayout(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.width = widthMeasureSpec;
		this.height = heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 监听不为空、宽度不变、当前高度与历史高度不为0
		if ((this.onSizeChangedListenner != null) && (w == oldw) && (oldw != 0)
				&& (oldh != 0)) {
			if ((h >= oldh)
					|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4)) {
				if ((h <= oldh)
						|| (Math.abs(h - oldh) <= 1 * this.screenHeight / 4))
					return;
				this.sizeChanged = false;
			} else {
				this.sizeChanged = true;
			}
			this.onSizeChangedListenner.onSizeChange(this.sizeChanged, oldh, h);
			measure(this.width - w + getWidth(), this.height - h + getHeight());
		}
	}

	/**
	 * 设置视图偏移的监听事件.
	 *
	 * @param paramonSizeChangedListenner the new on size changed listenner
	 */
	public void setOnSizeChangedListenner(
			OnSizeChangedListenner paramonSizeChangedListenner) {
		this.onSizeChangedListenner = paramonSizeChangedListenner;
	}

	/**
	 * 视图偏移的内部接口.
	 *
	 * @author junjun
	 */
	public interface OnSizeChangedListenner {
		
		/**
		 * On size change.
		 *
		 * @param paramBoolean the param boolean
		 * @param w the w
		 * @param h the h
		 */
		void onSizeChange(boolean paramBoolean, int w, int h);
	}

}

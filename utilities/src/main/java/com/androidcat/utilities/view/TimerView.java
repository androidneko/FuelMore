package com.androidcat.utilities.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.androidcat.utilities.R;
import com.androidcat.utilities.ResourceManager;

import java.util.Timer;
import java.util.TimerTask;

// TODO: Auto-generated Javadoc

/**
 * *********************************************************** 
 * 功能：自定义控件，获取验证码时倒计时读秒<br>
 * 作者：张辉<br>
 * 时间：2015-7-6<br>
 * ***********************************************************.
 */

public class TimerView extends Button {
	
	/** The default seconds to count. */
	private int defaultSecondsToCount = 60; // default
	
	/** The configured seconds. */
	private int configuredSeconds;
	
	/** The m timer. */
	private Timer mTimer;
	
	/** The m handler. */
	private Handler mHandler = new Handler();
	
	/** The m counter. */
	private int mCounter = 0;

	private int enableTextColor;
	private int disableTextColor;

	private int enableBgDrawable;
	private int disableBgDrawable;

	/**
	 * Instantiates a new timer view.
	 *
	 * @param context the context
	 */
	public TimerView(final Context context) {
		super(context);
	}

	/**
	 * Instantiates a new timer view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public TimerView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		initParams(context, attrs, 0);
	}

	/**
	 * Instantiates a new timer view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public TimerView(final Context context, final AttributeSet attrs,
					 final int defStyle) {
		super(context, attrs, defStyle);
		initParams(context, attrs, defStyle);
	}

	/**
	 * Inits the params.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	private void initParams(final Context context, final AttributeSet attrs,
			final int defStyle) {
		TypedArray a = null;
		try {
			a = context.getTheme().obtainStyledAttributes(attrs,
					ResourceManager.getIdsByName(context, "styleable", "TimerViewAttrs"), 0, 0);
			configuredSeconds = a.getInt(R.styleable.TimerViewAttrs_custom_defaultSeconds,
					defaultSecondsToCount);
			enableTextColor = a.getResourceId(R.styleable.TimerViewAttrs_enable_text_color,
					R.color.white);
			disableTextColor = a.getResourceId(R.styleable.TimerViewAttrs_disable_text_color,
					R.color.gray);
			enableBgDrawable = a.getResourceId(R.styleable.TimerViewAttrs_enable_bg_drawable,
					R.drawable.timer_view_normal_bg);
			disableBgDrawable = a.getResourceId(R.styleable.TimerViewAttrs_disable_bg_drawable,
					R.drawable.timer_view_disabled_bg);
		} catch (Exception e) {
			configuredSeconds = defaultSecondsToCount;
		} finally {
			if (a != null) {
				a.recycle();
			}
			if (configuredSeconds <= 0) {
				configuredSeconds = defaultSecondsToCount;
			}
		}
	}

	/**
	 * Start counting down.
	 */
	public void startCountingDown() {
		this.startCountingDown(configuredSeconds);
	}

	/**
	 * Start counting down.
	 *
	 * @param seconds the seconds
	 */
	public void startCountingDown(final int seconds) {
		assert seconds >= 0;
		if (this.mTimer != null) {
			try {
				this.mTimer.cancel();
				this.mTimer.purge();
			} catch (Exception e) {
				Log.e("Exception",
						"the method of startCountingDown caused Exception", e);
			}
		}
		this.mTimer = new Timer();
		this.mCounter = seconds;
		this.setEnabled(false);
		TimerView.this.setTextColor(getResources().getColor(disableTextColor));
		TimerView.this.setBackgroundResource(disableBgDrawable);
		this.mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mCounter--;
						if (mCounter == 0) {
							TimerView.this.mTimer.cancel();
							TimerView.this.mTimer.purge();
							TimerView.this.mTimer = null;
							TimerView.this.setEnabled(true);
							TimerView.this.setText("获取验证码");
							TimerView.this.setTextColor(getResources().getColor(enableTextColor));
							TimerView.this.setBackgroundResource(enableBgDrawable);
						} else {
							TimerView.this.setText(String.valueOf(mCounter) + "秒");
						}
					}
				});
			}
		}, 0, 1000);
	}

	/**
	 * Cancel.
	 */
	public void cancel() {
		if (this.mTimer != null) {
			this.mTimer.cancel();
			this.mTimer.purge();
			this.mTimer = null;
		}
		this.setEnabled(true);
		this.setText("");
	}
}

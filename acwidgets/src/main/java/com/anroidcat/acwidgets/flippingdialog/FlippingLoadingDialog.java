package com.anroidcat.acwidgets.flippingdialog;

import android.content.Context;
import android.widget.TextView;

import com.anroidcat.acwidgets.R;

public class FlippingLoadingDialog extends BaseDialog {

	private FlippingImageView mFivIcon;
	private TextView mHtvText;
	private String mText;
	private Context mContext;

	public FlippingLoadingDialog(Context context, String text) {
		super(context);
		mText = text;
		mContext = context;
		init();
	}

	private void init() {
		setContentView(R.layout.flipping_loading_diloag);
		mFivIcon = (FlippingImageView) findViewById(R.id.loadingdialog_fiv_icon);
		mHtvText = (TextView) findViewById(R.id.loadingdialog_htv_text);
		mFivIcon.startAnimation();
		mHtvText.setText(mText);
	}

	public void setText(String text) {
		mText = text;
		mHtvText.setText(mText);
	}
	
	public void setText(int textId) {
		mText = mContext.getString(textId);
		mHtvText.setText(mText);
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}
}

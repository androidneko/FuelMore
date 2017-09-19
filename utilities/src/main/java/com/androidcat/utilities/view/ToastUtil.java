package com.androidcat.utilities.view;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.androidcat.utilities.R;
import com.androidcat.utilities.Utils;


public final class ToastUtil {
    private Context mContext;
    private Toast mToast;
    private TextView mTextView;

    public ToastUtil(Context context) {
        mContext = context;
        View toastRoot = LayoutInflater.from(mContext).inflate(R.layout.layout_toast, null);
        mTextView = ((TextView) toastRoot.findViewById(R.id.toastTv));
        mToast = new Toast(mContext);
        mToast.setView(toastRoot);
        mToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, Utils.dp2px(mContext,70));
    }

    public void showToast(int stringId) {
        mTextView.setText(stringId);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToast(String msg) {
        mTextView.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showBottomToast(String msg) {
        mTextView.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showCenterToast(String msg) {
        mTextView.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showLongToast(int stringId) {
        mTextView.setText(stringId);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showToastLong(String msg) {
        mTextView.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}

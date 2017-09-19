package com.androidcat.fuelmore.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.androidcat.fuelmore.FuelMoreApplication;
import com.androidcat.acnet.entity.User;
import com.androidcat.utilities.log.Logger;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.anroidcat.acwidgets.flippingdialog.FlippingLoadingDialog;

import java.lang.ref.WeakReference;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-18 17:06:38
 * add function description here...
 */
public class BaseActivity extends FragmentActivity {

    private final static String TAG = "BaseActivity";
    protected View loadingView;
    protected boolean isVisible = false;
    protected FlippingLoadingDialog mLoadingDialog;
    private Toast mToast;

    protected User user;
    protected ActivityHandler baseHandler = new ActivityHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog = new FlippingLoadingDialog(this, "正在加载...");
        mLoadingDialog.setCancelable(false);
        user = (User) SharePreferencesUtil.getObject(User.class);
        FuelMoreApplication.activities.add(this);
        checkAppPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = (User) SharePreferencesUtil.getObject(User.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.file(this.getClass().getSimpleName() + "--onDestroy");
        baseHandler.removeCallbacksAndMessages(null);
        FuelMoreApplication.activities.remove(this);
    }

    protected void checkAppPermission() {
    }

    protected void exitActivities(){
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("isExit",true);
        startActivity(intent);
    }

    protected void handleEventMsg(Message msg) {
        switch (msg.what) {
        }
        childHandleEventMsg(msg);
    }


    protected void childHandleEventMsg(Message msg) {
        //do nothing ...
        //only for when child need to handle those messages processed by super class
    }

    protected void showLoadingview(){
        if (loadingView != null){
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    protected void hideLoadingview(){
        if (loadingView != null){
            loadingView.setVisibility(View.GONE);
        }
        dismissLoadingDialog();
    }

    protected void showLoadingDialog(String text) {
        mLoadingDialog.setText(text);
        showDialog(mLoadingDialog);
    }

    protected void showLoadingDialog() {
        mLoadingDialog.setText("正在加载...");
        showDialog(mLoadingDialog);
    }

    protected void showLoadingDialog(int text) {
        mLoadingDialog.setText(text);
        showDialog(mLoadingDialog);
    }

    protected void dismissLoadingDialog() {
        dismissDialog(mLoadingDialog);
    }

    private void showDialog(Dialog dialog) {
        if (dialog == null || dialog.isShowing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !dialog.isShowing()) {
                dialog.show();
            }
        } else {
            if (!isFinishing() && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    private void dismissDialog(Dialog dialog) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            if (!isFinishing() && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public boolean checkPhoneNumber(String phone) {
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return phone.matches(telRegex);
        }
    }

    public void canDismissDialog(Dialog dialog){
        if(dialog==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && dialog.isShowing()) {
                dialog.dismiss();
            }
        } else {
            if (!isFinishing() && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    public void canShowDialog(Dialog dialog){
        if(dialog==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!isDestroyed() && !dialog.isShowing()) {
                dialog.show();
            }
        } else {
            if (!isFinishing() && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    public static class ActivityHandler extends Handler {
        private final WeakReference<BaseActivity> mInstance;

        public ActivityHandler(BaseActivity instance) {
            mInstance = new WeakReference<BaseActivity>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = mInstance.get();
            if (null == activity) {
                return;
            }
            activity.handleEventMsg(msg);
        }
    }
}

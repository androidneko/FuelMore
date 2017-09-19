package com.androidcat.fuelmore.ui.fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.androidcat.utilities.Utils;

import java.lang.ref.WeakReference;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-18 17:12:54
 * add function description here...
 */
public class BaseFragment extends Fragment {
    private final static String TAG = "BaseFragment";

    public FragmentHandler baseHandler;
    protected View m_viewRoot = null;
    private Dialog progressDialog;
    private Toast mToast;

    protected boolean hasFocus = false;
    protected boolean isVisible = false;

    public void iOnResume() {
        isVisible = true;
        //Logger.file(this.getClass().getSimpleName() + "--iOnResume");
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public void handleEventMsg(Message msg){
        //add process of common processing
        switch (msg.what) {
        }
        childHandleEventMsg(msg);
    }

    protected void childHandleEventMsg(Message msg) {
        //do nothing ...
        //only for when child need to handle those messages processed by super class
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Logger.file(this.getClass().getSimpleName() + "--onCreate");
        baseHandler = new FragmentHandler(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            isVisible = false;
        }else {
            isVisible = true;
        }
        //Logger.file(this.getClass().getSimpleName() + "--onHiddenChanged hidden:"+hidden);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        //Logger.file(this.getClass().getSimpleName() + "--onDestroyView");
        m_viewRoot = null;
    }

    public void showToast(String text) {
        if (getActivity() == null){
            return;
        }
        if(Utils.isNull(text)){
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    protected void showProgressDialog(String msg) {
        if (progressDialog == null) {
            //progressDialog = DialogUtils.showAnimationDialog(getContext(), msg);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected Dialog showProgressDialog(String msg,boolean isInstance){
        if(getContext()==null){
            return null;
        }
        /*Dialog progressDialog = DialogUtils.showAnimationDialog(getContext(), msg);
        if(isInstance){
            showProgressDialog(msg);
        }else {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
            return progressDialog;
        }*/
        return null;
    }

    protected void dismissProgressDialog(Dialog progressDialog,boolean isInstance) {
        if(getActivity()==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getActivity().isDestroyed()) {
                return;
            }
        } else {
            if (getActivity().isFinishing()) {
                return;
            }
        }
        if(isInstance){
            dismissProgressDialog();
        }else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }


    protected void dismissProgressDialog() {
        if(getActivity()==null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getActivity().isDestroyed()) {
                return;
            }
        } else {
            if (getActivity().isFinishing()) {
                return;
            }
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static class FragmentHandler extends Handler {
        private final WeakReference<BaseFragment> mInstance;

        public FragmentHandler(BaseFragment instance) {
            mInstance = new WeakReference<BaseFragment>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment fragment = mInstance.get();
            if (null == fragment) {
                return;
            }
            fragment.handleEventMsg(msg);
        }
    }
}

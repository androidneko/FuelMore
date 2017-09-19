package com.androidcat.utilities.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/17.
 */
public class OnDoubleClickListener implements View.OnTouchListener{
    private int count=0;
    private long firClick=0;
    private long secClick=0;
    private final int interval=1500;
    private DoubleClickCallback mDoubleClickCallback;

    public interface DoubleClickCallback{
        void onDoubleClick();
    }

    public OnDoubleClickListener(DoubleClickCallback callback){
        this.mDoubleClickCallback=callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(MotionEvent.ACTION_DOWN==event.getAction()){
            count++;
            if(1==count){
                firClick=System.currentTimeMillis();
            }else if(2==count){
                secClick=System.currentTimeMillis();
                if(secClick-firClick<interval){
                    if(mDoubleClickCallback!=null){
                        mDoubleClickCallback.onDoubleClick();
                    }else {

                    }
                    count=0;
                    firClick=0;
                }else {
                    firClick=secClick;
                    count=1;
                }
                secClick=0;
            }
        }
        return true;
    }
}

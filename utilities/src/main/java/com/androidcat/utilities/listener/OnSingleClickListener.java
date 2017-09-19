package com.androidcat.utilities.listener;

import android.view.View;

/**
 * Project: bletravel_remote
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2016-7-19 13:59:20
 * add function description here...
 */
public abstract class OnSingleClickListener implements View.OnClickListener{

    private long interval=500;
    private long lastClickTime=0;

    public OnSingleClickListener(long interval){
        this.interval=interval;
    }

    public OnSingleClickListener(){
        this.interval=500;
    }

    @Override
    public void onClick(View view) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 0) {
            //时间被设置成为历史某一时刻，需要重置上一次点击时间
            lastClickTime = time;
            onSingleClick(view);
            return;
        }
        long interval = time - lastClickTime;
        if (interval > this.interval) {
            //Logger.i("两次点击时间间隔：" + interval + "--->响应onClick");
            onSingleClick(view);
            lastClickTime = time;
        } else {
            //Logger.w("两次点击时间间隔：" + interval + "--->不响应onClick");
            lastClickTime = time;
            return;
        }
    }

    public abstract void onSingleClick(View view);
}

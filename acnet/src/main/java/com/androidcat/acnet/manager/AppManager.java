package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.androidcat.acnet.consts.InterfaceCodeConst;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.request.NewsListRequest;
import com.androidcat.acnet.entity.response.StringContentResponse;
import com.androidcat.acnet.okhttp.callback.EntityResponseHandler;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-21 17:08:36
 * add function description here...
 */
public class AppManager extends BaseManager {

    public AppManager(Context context, Handler handler){
        super(context, handler);
    }

    public void getNewsList(String startid,RawResponseHandler rawResponseHandler){
        NewsListRequest request = new NewsListRequest();
        request.setStartid(startid);
        post(InterfaceCodeConst.TYPE_GET_NEWS_LIST, getPostJson(request), rawResponseHandler);
    }

    public void getNewsList(String startid){
        NewsListRequest request = new NewsListRequest();
        request.setStartid(startid);
        post(InterfaceCodeConst.TYPE_GET_NEWS_LIST, getPostJson(request), new EntityResponseHandler<StringContentResponse>() {
            @Override
            public void onStart(int code) {
                handler.sendEmptyMessage(OptMsgConst.MSG_GET_NEWS_LIST_START);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message msg = new Message();
                msg.obj = error_msg;
                msg.what = OptMsgConst.MSG_GET_NEWS_LIST_FAIL;
                handler.sendMessage(msg);
            }

            @Override
            public void onSuccess(int statusCode, StringContentResponse response) {
                Message msg = new Message();
                msg.obj = response;
                msg.what = OptMsgConst.MSG_GET_NEWS_LIST_SUCCESS;
                handler.sendMessage(msg);
            }
        });
    }
}

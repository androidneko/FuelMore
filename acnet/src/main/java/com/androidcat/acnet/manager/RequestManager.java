package com.androidcat.acnet.manager;

import com.androidcat.acnet.okhttp.MyOkHttp;
import com.androidcat.acnet.okhttp.callback.GsonResponseHandler;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-18 16:11:18
 * add function description here...
 */
public class RequestManager {

    public static void post(Object tag,int code,String json,GsonResponseHandler gsonResponseHandler){
        MyOkHttp.getInstance().post().tag(tag).jsonParams(json).url(getUrl(code)).enqueue(gsonResponseHandler);
    }

    private static String getUrl(int code){
        return "";
    }
}

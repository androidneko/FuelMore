package com.androidcat.acnet.manager;

import android.content.Context;
import android.os.Handler;

import com.androidcat.acnet.consts.InterfaceUrl;
import com.androidcat.acnet.consts.OptMsgConst;
import com.androidcat.acnet.entity.response.BaseResponse;
import com.androidcat.acnet.okhttp.MyOkHttp;
import com.androidcat.acnet.okhttp.callback.RawResponseHandler;
import com.androidcat.utilities.GsonUtil;
import com.androidcat.utilities.LogUtil;
import com.google.gson.Gson;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-18 16:50:23
 * add function description here...
 */
public class BaseManager {
    public static final String BASE_URL = "http://120.25.249.105:8080/api/v1/create";
    protected Context context;
    protected Handler handler;
    protected MyOkHttp myOkHttp;

    public BaseManager(){}

    public BaseManager(Context context){
        this.context = context;
        this.myOkHttp = MyOkHttp.getInstance();
    }

    public BaseManager(Context context,Handler handler){
        this(context);
        this.handler = handler;
    }

    protected String getUrl(int code){
        return InterfaceUrl.getUrl(code);
    }

    protected void post(int code,String json,final RawResponseHandler responseHandler){
        responseHandler.onStart(code);
        LogUtil.e("BaseManager", "request url:" + getUrl(code) + "\nrequest json:" + json);
        myOkHttp.post().url(getUrl(code)).jsonParams(json).tag(context).enqueue(new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (!GsonUtil.isJson(response)){
                    responseHandler.onFailure(statusCode,"服务器数据格式有误");
                    return;
                }
                BaseResponse baseResponse = new Gson().fromJson(response,BaseResponse.class);
                if (BaseResponse.LOGIN_ERR.equals(baseResponse.getCode())){
                    if (handler != null){
                        handler.sendEmptyMessage(OptMsgConst.TOKEN_ERROR);
                    }
                    return;
                }
                //add other common processing here
                if (BaseResponse.SUCCESS == baseResponse.getCode()){
                    responseHandler.onSuccess(statusCode,response);
                }else {
                    responseHandler.onFailure(statusCode,baseResponse.getMessage());
                }
            }

            @Override
            public void onStart(int code) {
                responseHandler.onStart(code);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                responseHandler.onFailure(statusCode,error_msg);
            }
        });
    }

    protected String getPostJson(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}

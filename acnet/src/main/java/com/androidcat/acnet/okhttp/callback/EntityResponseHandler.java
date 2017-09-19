package com.androidcat.acnet.okhttp.callback;

import com.androidcat.acnet.okhttp.MyOkHttp;
import com.androidcat.acnet.okhttp.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Gson类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class EntityResponseHandler<T> extends RawResponseHandler {

    private Type mType;

    public EntityResponseHandler() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

    @Override
    public final void onSuccess(final int statusCode, final String response) {
        try {
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    onSuccess(statusCode, (T) gson.fromJson(response, getType()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail parse gson, body=" + response);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(statusCode, "fail parse gson, body=" + response);
                }
            });

        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}

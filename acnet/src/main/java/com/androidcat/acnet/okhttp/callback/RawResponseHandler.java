package com.androidcat.acnet.okhttp.callback;

import com.androidcat.acnet.okhttp.MyOkHttp;
import com.androidcat.acnet.okhttp.util.LogUtils;
import com.androidcat.utilities.LogUtil;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * raw 字符串结果回调
 * Created by tsy on 16/8/18.
 */
public abstract class RawResponseHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail read response body");
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;
        LogUtil.e("RawResponseHandler","response:"+finalResponseBodyStr);
        MyOkHttp.mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(), finalResponseBodyStr);
            }
        });

    }

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}

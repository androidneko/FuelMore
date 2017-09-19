package com.androidcat.acnet.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.androidcat.acnet.okhttp.builder.DeleteBuilder;
import com.androidcat.acnet.okhttp.builder.DownloadBuilder;
import com.androidcat.acnet.okhttp.builder.GetBuilder;
import com.androidcat.acnet.okhttp.builder.PatchBuilder;
import com.androidcat.acnet.okhttp.builder.PostBuilder;
import com.androidcat.acnet.okhttp.builder.PutBuilder;
import com.androidcat.acnet.okhttp.builder.UploadBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * MyOkhttp
 * Created by tsy on 16/9/14.
 */
public class MyOkHttp {
    private static final int TIME_OUT = 25;

    private static OkHttpClient mOkHttpClient;
    private static MyOkHttp myOkHttp;
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * construct
     */
    private MyOkHttp()
    {
        this(null);
    }

    /**
     * construct
     * @param okHttpClient custom okhttpclient
     */
    private MyOkHttp(OkHttpClient okHttpClient)
    {
        if(mOkHttpClient == null) {
            synchronized (MyOkHttp.class) {
                if (mOkHttpClient == null) {
                    if (okHttpClient == null) {
                        mOkHttpClient = new OkHttpClient().newBuilder()
                                .connectionPool(new ConnectionPool(10,5l, TimeUnit.SECONDS))
                                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                                .build();
                    } else {
                        mOkHttpClient = okHttpClient;
                    }
                }
            }
        }
    }

    public static MyOkHttp getInstance(){
        return  getInstance(null);
    }

    public static MyOkHttp getInstance(OkHttpClient okHttpClient){
        if (myOkHttp == null){
            myOkHttp = new MyOkHttp(okHttpClient);
        }
        return  myOkHttp;
    }

    public GetBuilder get() {
        return new GetBuilder(this);
    }

    public PostBuilder post() {
        return new PostBuilder(this);
    }

    public PutBuilder put(){
        return new PutBuilder(this);
    }

    public PatchBuilder patch(){
        return new PatchBuilder(this);
    }

    public DeleteBuilder delete(){
        return new DeleteBuilder(this);
    }

    public UploadBuilder upload() {
        return new UploadBuilder(this);
    }

    public DownloadBuilder download() {
        return new DownloadBuilder(this);
    }

    /**
     * do cacel by tag
     * @param tag tag
     */
    public void cancel(Object tag) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

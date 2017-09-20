package com.androidcat.fuelmore.merchant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.androidcat.utilities.log.AndroidLogTool;
import com.androidcat.utilities.log.LogLevel;
import com.androidcat.utilities.log.Logger;
import com.androidcat.utilities.persistence.SharePreferencesUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-7-19 14:01:51
 * add function description here...
 */
public class FuelMoreApplication extends CrashReportingApplication{

    public static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载
        initImageLoader(FuelMoreApplication.this);
        //初始化缓存Context
        initSharePreference();
        //初始化日志工具
        initLogger();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public String getReportUrl() {
        return null;
    }

    @Override
    public Bundle getCrashResources() {
        return null;
    }

    private static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .discCacheFileCount(100)// 缓存文件的最大个数
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();

        // Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
    }

    private void initSharePreference(){
        SharePreferencesUtil.init(this);
    }

    private void initLogger() {
        Logger.init(Logger.DEFAULT_TAG, this)        // default PRETTYLOGGER or use just init()
                .methodCount(4)                 // default 2
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(0)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
    }

    public static void exitActivities(){
        for (Activity activity : activities){
            activity.finish();
        }
    }
}

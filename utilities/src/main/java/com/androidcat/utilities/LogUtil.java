package com.androidcat.utilities;

import android.util.Log;

import com.androidcat.utilities.log.Logger;
import com.androidcat.utilities.persistence.CacheFileManager;

import java.util.Date;

/**
 * ***********************************************************
 * 功能：日志打印的工具类，GLOBAL为true表示全局开启日志打印，false表示全局关闭日志打印<br>
 *     每个级别的日志由该级别的控制变量控制开关
 * 作者：薛龙<br>
 * 时间：2016-1-16<br>
 * ***********************************************************
 */

public final class LogUtil {

    private static final String TAG = "utilities_";

    private static final boolean GLOBAL = true;
    private static final boolean DEBUG = true;
    private static final boolean INFO = true;
    private static final boolean VERBOSE = true;
    private static final boolean ERROR = true;
    private static final boolean WARN = true;

    public static void e(String tag, String msg) {
        if (GLOBAL) {
            if (ERROR) {
                Log.e(TAG+tag,msg);
            }
        }
    }

    public static void e(String tag, String msg, Exception e) {
        if (!GLOBAL) {
            return;
        }
        if (ERROR) {
            Logger.e(e,msg);
        }
    }

    public static void v(String tag, String msg) {
        if (!GLOBAL) {
            return;
        }
        if (VERBOSE) {
            Logger.v(msg);
        }
    }

    public static void i(String tag, String msg) {
        if (!GLOBAL) {
            return;
        }
        if (INFO) {
            Logger.i(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (!GLOBAL) {
            return;
        }
        if (DEBUG) {
            Logger.d(msg);
        }
    }

    public static void w(String tag, String msg) {
        if (!GLOBAL) {
            return;
        }
        if (WARN) {
            Logger.w(msg);
        }
    }

    public static void logException(Class c, Throwable e) {
        try {
            StringBuilder exceptionInfo = new StringBuilder();
            if (e == null) {
                exceptionInfo.append(new Date().toGMTString() + "\n"
                        + "Exception:"
                        + "e is null,probably null pointer exception" + "\n");
            } else {
                e.printStackTrace();
                exceptionInfo.append(new Date().toGMTString() + "\n");
                exceptionInfo.append(e.getClass().getCanonicalName() + ":"
                        + e.getMessage() + "\n");
                StackTraceElement[] stes = e.getStackTrace();
                for (StackTraceElement ste : stes) {
                    exceptionInfo.append("at " + ste.getClassName() + "$"
                            + ste.getMethodName() + "$" + ste.getFileName()
                            + ":" + ste.getLineNumber() + "\n");
                }
            }

            e(c.getCanonicalName(), exceptionInfo.toString());
            CacheFileManager.getInstance().log(exceptionInfo.toString());
        } catch (Exception ex) {
            e(c.getCanonicalName(), ex.getMessage());
        }
    }
}

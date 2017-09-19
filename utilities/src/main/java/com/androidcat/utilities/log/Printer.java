package com.androidcat.utilities.log;

import android.content.Context;

public interface Printer {

  Printer t(String tag, int methodCount);

  Settings init(String tag);

  Settings init(String tag,Context context);

  Settings getSettings();

  void d(String message, Object... args);

  void d(String tag,String message, Object... args);

  void e(String message, Object... args);

  void e(String tag,String message, Object... args);

  void e(Throwable throwable, String message, Object... args);

  void e(String tag,Throwable throwable,String message, Object... args);

  void w(String message, Object... args);

  void w(String tag,String message, Object... args);

  void i(String message, Object... args);

  void i(String tag,String message, Object... args);

  void v(String message, Object... args);

  void v(String tag,String message, Object... args);

  void wtf(String message, Object... args);

  void json(String json);

  void jsonToFile(String json);

  void json(String tag,String json);

  void xml(String tag,String xml);

  void xml(String xml);

  void file(String message);

  void file(String message, boolean async);

  void file(String tag,String message);

  void clear();
}

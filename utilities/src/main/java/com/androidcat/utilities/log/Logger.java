package com.androidcat.utilities.log;

import android.content.Context;

/**
 * Logger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class Logger {
  public static final String DEFAULT_TAG = "SLM_Logger";

  private static Printer printer;

  //no instance
  private Logger() {
  }

  /**
   * It is used to get the settings object in order to change settings
   *
   * @return the settings object
   */
  public static Settings init() {
    return init(DEFAULT_TAG);
  }

  public static Settings init(Context context) {
    return init(DEFAULT_TAG, context);
  }

  /**
   * It is used to change the tag
   *
   * @param tag is the given string which will be used in Logger as TAG
   */
  public static Settings init(String tag) {
    printer = new LoggerPrinter();
    return printer.init(tag);
  }

  public static Settings init(String tag,Context context) {
    printer = new LoggerPrinter();
    LogFileTool.init(context);
    return printer.init(tag, context);
  }

  public static void clear() {
    printer.clear();
    printer = null;
  }

  public static Printer t(String tag) {
    return printer.t(tag, printer.getSettings().getMethodCount());
  }

  public static Printer t(int methodCount) {
    return printer.t(null, methodCount);
  }

  public static Printer t(String tag, int methodCount) {
    return printer.t(tag, methodCount);
  }

  public static void d(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.d(message, args);
  }

  public static void e(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.e(message, args);
  }

  public static void e(Throwable throwable, String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.e(throwable, message, args);
  }

  public static void i(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.i(message, args);
  }

  public static void v(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.v(message, args);
  }

  public static void w(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.w(message, args);
  }

  public static void wtf(String message, Object... args) {
    if (!Settings.DEBUG) return;
    printer.wtf(message, args);
  }

  public static void file(String message) {
    if (!Settings.DEBUG) return;
    printer.file(message);
  }

  public static void file(String message,boolean logAsync) {
    if (!Settings.DEBUG) return;
    printer.file(message,logAsync);
  }

  public static void file(String tag,String message) {
    if (!Settings.DEBUG) return;
    printer.file(tag,message);
  }

  /*public static void file(String message, boolean async) {
    if (!Settings.DEBUG) return;
    printer.file(message, async);
  }*/

  /**
   * Formats the json content and print it
   *
   * @param json the json content
   */
  public static void json(String json) {
    if (!Settings.DEBUG) return;
    printer.json(json);
  }

  /**
   * Formats the json content and print it
   *
   * @param json the json content
   */
  public static void jsonToFile(String json) {
    if (!Settings.DEBUG) return;
    printer.jsonToFile(json);
  }

  /**
   * Formats the json content and print it
   *
   * @param xml the xml content
   */
  public static void xml(String xml) {
    if (!Settings.DEBUG) return;
    printer.xml(xml);
  }

}

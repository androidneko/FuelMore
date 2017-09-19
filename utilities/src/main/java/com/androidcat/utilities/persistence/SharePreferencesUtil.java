package com.androidcat.utilities.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.androidcat.utilities.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by coolbear on 2015/3/16.
 */
public class SharePreferencesUtil {
    /**
     * The Constant FILE_NAME.
     */
    private static final String FILE_NAME = "prefs";

    private static final String INIT_KEY = "6aafd4ec5c848dd9b2e9fc2316afbdfe";
    /**
     * The prefs.
     */
    private static SecurePreferences prefs;
    private static Context context;

    private SharePreferencesUtil() {
    }

    public static void init(Context context) {
        SharePreferencesUtil.context = context;
    }

    /**
     * Gets the preference value.
     *
     * @param key the key
     * @return the preference value
     */
    public static String getValue(String key) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getString(key, "");
    }

    public static String getValue(String key, String def) {

        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getString(key, def);
    }

    /**
     * Sets the preference value.
     *
     * @param key   the key
     * @param value the value
     */
    public static void setValue(String key, String value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setValue(String key, boolean value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanValue(String key, boolean def) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getBoolean(key, def);
    }

    public static void setValue(String key, int value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntValue(String key, int def) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getInt(key, def);
    }

    public static void setValue(String key, float value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getFloatValue(String key, float def) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getFloat(key, def);
    }

    public static void setValue(String key, double value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, (float) value);
        editor.commit();
    }

    public static double getDoubleValue(String key, float def) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getFloat(key, def);
    }

    public static void setValue(String key, long value) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLongValue(String key, long def) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        return prefs.getLong(key, def);
    }

    public static void removeValue(String key) {

        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        prefs.edit().remove(key).commit();
    }

    public static boolean setObject(Object obj) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .create();

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(obj.getClass().getCanonicalName(), gson.toJson(obj));
        return editor.commit();
    }

    public static Object getObject(Class<?> clazz) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        Object objResult = null;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .create();
        String objJson = prefs.getString(clazz.getCanonicalName(), "");
        if (Utils.isNull(objJson)) {
            try {
                objResult = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            objResult = gson.fromJson(objJson, clazz);
        }
        return objResult;
    }

    public static void removeObject(Class<?> clazz) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(clazz.getCanonicalName());
        editor.commit();
    }

    public static void removeObject(Object obj) {
        if (prefs == null) {
            prefs = new SecurePreferences(context, INIT_KEY, FILE_NAME);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(obj.getClass().getCanonicalName());
        editor.commit();
    }
}

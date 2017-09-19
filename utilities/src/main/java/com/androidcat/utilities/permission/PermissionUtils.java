package com.androidcat.utilities.permission;

import android.Manifest;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: bletravel_new
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-18 14:44:53
 * add function description here...
 */
public class PermissionUtils {

    public static List<String> allPermisssions = new ArrayList<>();
    public static List<String> telephonyPermisssions = new ArrayList<>();
    public static List<String> appPermisssions = new ArrayList<>();
    static {
        allPermisssions.add(Manifest.permission.READ_CONTACTS);
        allPermisssions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        allPermisssions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        allPermisssions.add(Manifest.permission.READ_PHONE_STATE);
        allPermisssions.add(Manifest.permission.CALL_PHONE);
        allPermisssions.add(Manifest.permission.READ_CALL_LOG);
        allPermisssions.add(Manifest.permission.RECEIVE_SMS);
        allPermisssions.add(Manifest.permission.READ_SMS);
        allPermisssions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        allPermisssions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        allPermisssions.add(Manifest.permission.CAMERA);

        telephonyPermisssions.add(Manifest.permission.READ_CONTACTS);
        telephonyPermisssions.add(Manifest.permission.READ_PHONE_STATE);
        telephonyPermisssions.add(Manifest.permission.CALL_PHONE);
        telephonyPermisssions.add(Manifest.permission.READ_CALL_LOG);
        telephonyPermisssions.add(Manifest.permission.RECEIVE_SMS);
        telephonyPermisssions.add(Manifest.permission.READ_SMS);

        appPermisssions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        appPermisssions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        appPermisssions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        appPermisssions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        appPermisssions.add(Manifest.permission.CAMERA);
    }

    public static boolean hasSMSPermission(Context context){
        List<String> list = new ArrayList<>();
        list.add("android.permission.RECEIVE_SMS");
        list.add("android.permission.READ_SMS");
        return !AndPermission.hasAlwaysDeniedPermission(context, list);
    }

    public static boolean hasDeniedPermission(List<String> deniedPers){
        for (String denied : deniedPers){
            for (String per : allPermisssions){
                if (denied.equals(per)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasDeniedLocPermission(List<String> deniedPers){
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        for (String denied : deniedPers){
            for (String per : list){
                if (denied.equals(per)){
                    return true;
                }
            }
        }
        return false;
    }
}

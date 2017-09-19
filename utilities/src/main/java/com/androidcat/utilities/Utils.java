package com.androidcat.utilities;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *****************************************************************************<br>
 * 工程名称：天喻应用商店 <br>
 * 模块功能：工具类.包含通用工具方法.<br>
 * 作 者：刘桥<br>
 * 单 位：武汉天喻信息 研发中心 <br>
 * 开发日期：2013-3-21 下午5:43:37 <br>
 * *****************************************************************************<br>
 */
public class Utils {
    private static double EARTH_RADIUS = 6378.137;

    /**
     * 把dip单位转成px单位
     *
     * @param context context对象
     * @param dip     dip数值
     * @return dip对应的px值
     */
    public static int formatDipToPx(Context context, int dip) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int px = (int) Math.ceil(dip * dm.density);
        return px;
    }

    /**
     * **********************************************************<br>
     * 方法功能：安装apk<br>
     * 参数说明：<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-3-21 下午5:44:16<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static void installApk(Context context, String installPath) {
        Intent apkIntent = new Intent(Intent.ACTION_VIEW);
        final Uri puri = Uri.fromFile(new File(installPath));
        apkIntent.setDataAndType(puri,
                "application/vnd.android.package-archive");
        context.startActivity(apkIntent);

    }

    /**
     * **********************************************************<br>
     * 方法功能：检测sd卡是否可用<br>
     * 参数说明：<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-3-21 下午6:21:59<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static boolean checkSDCard() {
        return android.os.Environment.MEDIA_MOUNTED
                .equals(android.os.Environment.getExternalStorageState());
    }

    /**
     * **********************************************************<br>
     * 方法功能：将byte数组转换成16进制组成的字符串<br>
     * 参数说明：bytes 待转换的byte数组<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-3-21 下午6:23:44<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer buff = new StringBuffer();
        int len = bytes.length;
        for (int j = 0; j < len; j++) {
            if ((bytes[j] & 0xff) < 16) {
                buff.append('0');
            }
            buff.append(Integer.toHexString(bytes[j] & 0xff));
        }
        return buff.toString();
    }

    public static boolean isNetworkAvailable(Context mContext) {
        boolean isAvailable = false;
        final ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            final NetworkInfo[] netinfo = cm.getAllNetworkInfo();
            if (null != netinfo) {
                for (int i = 0; i < netinfo.length; i++) {
                    if (netinfo[i].isConnected()) {
                        isAvailable = true;
                    }
                }
            }
        }
        return isAvailable;
    }

    /**
     * 保留一位小数点
     *
     * @param dou
     * @return
     */
    public static double changeDouble(Double dou) {
        NumberFormat nf = new DecimalFormat("0.0 ");
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }

    /**
     * **********************************************************<br>
     * 方法功能：四舍五入value,精确到小数点后2位<br>
     * 参数说明：<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-3-19 上午9:51:14<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * *********************************************************<br>
     */
    public static String roundHalfUpTo2bit(float value) {
        String pattern = "#.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    /**
     * **********************************************************<br>
     * 方法功能：根据size大小转化为以B或K或M为单位<br>
     * 参数说明：size：文件大小，以B为单位<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-4-10 上午10:12:15<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static String convertToBKM(int size) {
        if (size < 1000) {
            return size + "B";
        } else if (1000 <= size && size < 1000000) {// 以K为单位,显示整数(精确到这个粒度足够了)
            return Math.round((float) size / 1000) + "K";

        } else {// 以M为单位,保留小数点后两位数字
            float value = (float) size / 1000000;
            String newValue = Utils.roundHalfUpTo2bit(value);
            return newValue + "M";
        }
    }

    /**
     * **********************************************************<br>
     * 方法功能：根据应用包名获取版本号.不存在则返回null<br>
     * 参数说明：<br>
     * 作 者：刘桥<br>
     * 开发日期：2013-3-22 下午12:41:31<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static int getVersionByPackageName(String packageName, Context ctx) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ctx.getPackageManager()
                    .getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return packageInfo.versionCode;
    }

    public static String getVersionName(Context ctx) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
        return packageInfo.versionName;
    }

    public static int getVersion(Context ctx) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
        return packageInfo.versionCode;
    }

    /**
     * **********************************************************<br>
     * 方法功能：获得设备唯一标示<br>
     * 参数说明：<br>
     * 作 者：薛龙<br>
     * 开发日期：2013-3-22 下午12:41:31<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * **********************************************************<br>
     */
    public static String getDeviceId(Context ctx) {
        String deviceId = android.os.Build.SERIAL;
        if (Utils.isNull(deviceId)){
            deviceId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (Utils.isNull(deviceId)){
            TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        }
        if (Utils.isNull(deviceId)){
            deviceId = UUID.randomUUID().toString();
        }
        return deviceId;
    }

    public static String getDateStr(String datestr) {
        if (datestr == null || "".equals(datestr)) {
            return "";
        }
        return datestr.substring(datestr.indexOf("-") + 1, datestr.indexOf(" "));
    }

    /**
     * Determine whether an application is installed.
     *
     * @param packageName the package name of the application.
     * @return true if the application is installed, false otherwise.
     */
    public static boolean isApplicationInstalled(final Context context, final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        if (null == context) {
            throw new IllegalArgumentException("context may not be null.");
        }
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (final NameNotFoundException e) {
            // Application not installed.
        }
        return false;
    }

    public static void share(Context context,String title,String text){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (Utils.isNull(title)){
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        }else {
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
        }
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    public static void openApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    public static void openUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    public static void call(Context context, String num) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        context.startActivity(intent);
    }

    public static void sendSms(Context context, String num) {
        Uri uri = Uri.parse("smsto://" + num);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        context.startActivity(intent);
    }

    public static boolean isNull(String str) {
        return null == str || "".equals(str) ||
                str.length() == 0 ||
                str.trim().length() == 0
                || "null".equals(str);
    }

    public static boolean isNumOrLetter(String inputName) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher m = p.matcher(inputName);
        return m.matches();
    }

    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void vibrate(final Context context, long milliseconds) {
        if (context == null){
            return;
        }
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public static void vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    public static String md5(String sourceStr) {
        byte[] source = sourceStr.getBytes();
        String s = null;
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'
        };
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char[] str = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return s;
    }

    //判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        if (isNull(mobiles)){
            return  false;
        }
//        Pattern p = Pattern.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[01,5-9]))\\d{8}$");
        Pattern p=Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();

    }

    //判断是否全是数字
    public static boolean isNumeric(String str) {
        if (!isNull(str)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);

            return isNum.matches();
        }
        return false;
    }

    //判断是否是身份证号
    public static boolean isIDNo(String str) {
        boolean flag = false;
        if (!isNull(str) && str.length() == 18) {
            String str17 = str.substring(0, 17);
            if (isNumeric(str17)) {
                String lastStr = str.substring(17);
                if ("X".equals(lastStr) || isNumeric(lastStr)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void clearActivityStack(List<Activity> activities) {
        for (Activity act : activities) {
            act.finish();
        }
    }

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] b = baos.toByteArray();
        String bitmapStr = Base64.encodeToString(b, Base64.DEFAULT);
        bm.recycle();
        return bitmapStr;

    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //if inJustDecodeBounds is true,decodeFile will null
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 320, 480);
        //不能写死，写死会导致分辨率低的图片被压缩失真
        //options.inSampleSize = 4;
        options.inTempStorage = new byte[100 * 1024];
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        // Decode bitmap with inSampleSize set
        try {
            InputStream is = new FileInputStream(filePath);
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeFile(filePath, options);
    }

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    public static double GetDistance(double y1, double x1, double y2, double x2)
    {
        double radLat1 = rad(y1);
        double radLat2 = rad(y2);
        double a = radLat1 - radLat2;
        double b = rad(x1) - rad(x2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        double jieguo=s*1000;
        double jieguo1=Math.round(jieguo);
        DecimalFormat dfa =new DecimalFormat("#####0");
        return Double.parseDouble(dfa.format(jieguo1));
    }

    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static boolean checkZipCode(String zipString)
    {
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static void playHeartbeatAnimation(final ImageView imageView){
        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,    0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));
        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtil.d("end","end");
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));
                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);
                imageView.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animationSet);
    }

    public static boolean isInstallWX(Context context) {
        return Utils.isApplicationInstalled(context, "com.tencent.mm");
    }

    public static boolean isInstallQQ(Context context) {
        return Utils.isApplicationInstalled(context, "com.tencent.mobileqq");
    }
}

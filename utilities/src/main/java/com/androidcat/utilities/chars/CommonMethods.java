package com.androidcat.utilities.chars;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.androidcat.utilities.LogUtil;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

// TODO: Auto-generated Javadoc

/**
 * 通用工具类,封装了很多常用方法.
 */
public class CommonMethods {

    /**
     * The Constant TAG.
     */
    private static final String TAG = "CommonMethods";

    /**
     * byte字节数组转换Short类型（未严格测试）.
     *
     * @param outBuf the out buf
     * @return the short
     */
    public static short bytesToShort(byte[] outBuf) {

        if (outBuf.length < 2) {
            return (short) (outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]);
        } else {
            return (short) (((outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]) << 8) + (outBuf[1] < 0 ? outBuf[1] + 256
                    : outBuf[1]));
        }

    }

    /**
     * 填充XX数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0xXX到数据块的右边，直到数据块的长度是8的倍数。.
     *
     * @param data   待填充XX的数据
     * @param inData the in data
     * @return the string
     */
    public static String padding(String data, String inData) {
        int padlen = 8 - (data.length() / 2) % 8;
        if (padlen != 8) {
            String padstr = "";
            for (int i = 0; i < padlen; i++)
                padstr += inData;
            data += padstr;
            return data;
        } else {
            return data;
        }
    }

    /**
     * 填充80数据，首先在数据块的右边追加一个
     * '80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。.
     *
     * @param data 待填充80的数据
     * @return the string
     */
    public static String padding80(String data) {
        int padlen = 8 - (data.length() / 2) % 8;
        String padstr = "";
        for (int i = 0; i < padlen - 1; i++)
            padstr += "00";
        data = data + "80" + padstr;
        return data;
    }

    /**
     * 获取当前时间相隔N天的日期,格式yyyymmdd.
     *
     * @param distance 和今天的间隔天数
     * @return 获取的日期, 格式yyyymmdd
     */
    public static String getDateString(int distance) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, distance);
        //
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    /**
     * 生成16位的动态链接库鉴权十六进制随机数字符串.
     *
     * @return String
     */
    public static String yieldHexRand() {
        StringBuffer strBufHexRand = new StringBuffer();
        Random rand = new Random(System.currentTimeMillis());
        int index;
        // 随机数字符
        char charArrayHexNum[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < 16; i++) {
            index = Math.abs(rand.nextInt()) % 16;
            if (i == 0) {
                while (charArrayHexNum[index] == '0') {
                    index = Math.abs(rand.nextInt()) % 16;
                }
            }
            strBufHexRand.append(charArrayHexNum[index]);
        }
        return strBufHexRand.toString();
    }

    /**
     * 分析类名.
     *
     * @param strName String
     * @return String
     */
    public static String analyseClassName(String strName) {
        String strTemp = strName.substring(strName.lastIndexOf(".") + 1,
                strName.length());
        return strTemp.substring(strTemp.indexOf(" ") + 1, strTemp.length());
    }

    /**
     * Convert int2 string.
     *
     * @param n   the n
     * @param len the len
     * @return the string
     */
    static public String convertInt2String(int n, int len) {
        String str = String.valueOf(n);
        int strLen = str.length();

        String zeros = "";
        for (int loop = len - strLen; loop > 0; loop--) {
            zeros += "0";
        }

        if (n >= 0) {
            return zeros + str;
        } else {
            return "-" + zeros + str.substring(1);
        }
    }

    /**
     * Convert string2 int.
     *
     * @param str          the str
     * @param defaultValue the default value
     * @return the int
     */
    static public int convertString2Int(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * yyyyMMddhhmmss.
     *
     * @return the date time string2
     */
    public static String getDateTimeString2() {
        Calendar cal = Calendar.getInstance();

        return new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss")
                .format(cal.getTime());

    }

    /**
     * Bytes to hex string.
     *
     * @param bytes the bytes
     * @return the string
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

    /**
     * usage: str2bytes("0710BE8716FB"); it will return a byte array, just like
     * : b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
     *
     * @param src the src
     * @return the byte[]
     */
    public static byte[] str2bytes(String src) {
        if (src == null || src.length() == 0 || src.length() % 2 != 0) {
            return null;
        }
        int nSrcLen = src.length();
        byte byteArrayResult[] = new byte[nSrcLen / 2];
        StringBuffer strBufTemp = new StringBuffer(src);
        String strTemp;
        int i = 0;
        while (i < strBufTemp.length() - 1) {
            strTemp = src.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }

    /**
     * Strcpy.
     *
     * @param d      the d
     * @param s      the s
     * @param from   the from
     * @param maxlen the maxlen
     * @return the int
     */
    public static int strcpy(byte d[], byte s[], int from, int maxlen) {
        int i;
        for (i = 0; i < maxlen; i++) {
            d[i + from] = s[i];
        }

        d[i + from] = 0;
        return i;
    }

    /**
     * Memcpy.
     *
     * @param d      the d
     * @param s      the s
     * @param from   the from
     * @param maxlen the maxlen
     * @return the int
     */
    public static int memcpy(byte d[], byte s[], int from, int maxlen) {
        int i;
        for (i = 0; i < maxlen; i++) {
            d[i + from] = s[i];
        }
        return i;
    }

    /**
     * Bytes copy.
     *
     * @param dest    the dest
     * @param source  the source
     * @param offset1 the offset1
     * @param offset2 the offset2
     * @param len     the len
     */
    public static void BytesCopy(byte[] dest, byte[] source, int offset1,
                                 int offset2, int len) {
        for (int i = 0; i < len; i++) {
            dest[offset1 + i] = source[offset2 + i];
        }
    }

    /**
     * usage: input: n = 1000000000 ( n = 0x3B9ACA00) output: byte[0]:3b
     * byte[1]:9a byte[2]:ca byte[3]:00 notice: the scope of input integer is [
     * -2^32, 2^32-1] ; **In CMPP2.0,the typeof msg id is ULONG,so,need
     * ulong2Bytes***
     *
     * @param n the n
     * @return the byte[]
     */
    public static byte[] int2Bytes(int n) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(n);
        return bb.array();
    }

    /**
     * Long2 bytes.
     *
     * @param l the l
     * @return the byte[]
     */
    public static byte[] long2Bytes(long l) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(l);
        return bb.array();
    }

    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）.
     *
     * @param val int 待转换整数
     * @param len int 指定长度
     * @return String
     */
    public static String Int2HexStr(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * Long2 hex str.
     *
     * @param val the val
     * @param len the len
     * @return the string
     */
    public static String Long2HexStr(long val, int len) {
        String result = Long.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * Gets the res string.
     *
     * @param context  the context
     * @param stringId the string id
     * @return the res string
     */
    public static String getResString(Context context, int stringId) {
        return context.getResources().getString(stringId);
    }

    /**
     * 字符串转换为字节数组
     * <p/>
     * stringToBytes("0710BE8716FB"); return: b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
     *
     * @param string the string
     * @return the byte[]
     */
    public static byte[] stringToBytes(String string) {
        if (string == null || string.length() == 0 || string.length() % 2 != 0) {
            return null;
        }
        int stringLen = string.length();
        byte byteArrayResult[] = new byte[stringLen / 2];
        StringBuffer sb = new StringBuffer(string);
        String strTemp;
        int i = 0;
        while (i < sb.length() - 1) {
            strTemp = string.substring(i, i + 2);
            byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
            i += 2;
        }
        return byteArrayResult;
    }

    /**
     * 字节数组转为16进制.
     *
     * @param bytes 字节数组
     * @return the string
     */
    public static String bytesToHex(byte[] bytes) {
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

    /**
     * ********************************************************************<br>
     * 方法功能：将用户圈存金额先转换为分，在把分转为16进制，再前补0组装为4字节圈存金额 如1元 为:"00000064" 参数说明：<br>
     * 作 者：杨明<br>
     * 开发日期：2013-9-18 上午11:53:56<br>
     * 修改日期：<br>
     * 修改人：<br>
     * 修改说明：<br>
     * ********************************************************************<br>.
     *
     * @param val the val
     * @param len the len
     * @return the string
     */
    /**
     * 将长整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val int 待转换长整数
     * @param len int 指定长度
     * @return String
     */
    public static String longToHex(long val, int len) {
        String result = Long.toHexString(val).toUpperCase();
        int rLen = result.length();
        if (rLen > len) {
            return result.substring(rLen - len, rLen);
        }
        if (rLen == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - rLen; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * 返回int的指定长度的string representation.
     *
     * @param n      the n
     * @param length the length
     * @return the string
     */
    public static String valueOfInt(int n, int length) {
        String strValue = String.valueOf(n);
        int j = length - strValue.length();
        for (int i = 0; i < j; i++) {
            strValue = "0" + strValue;
        }
        return strValue;
    }

    /**
     * 判断是否为密码.
     *
     * @param str the str
     * @return true, if is pwd
     */
    public static boolean isPwd(String str) {
        return str.matches("^[a-zA-Z0-9]{6,12}$");
    }

    /**
     * 判断是否为证件号.
     *
     * @param str the str
     * @return true, if is card num
     */
    public static boolean isCardNum(String str) {
        return str.matches("[a-zA-Z0-9]{1,18}");
    }

    /**
     * Judge update.
     *
     * @param context     the context
     * @param version     the version
     * @param packageName the package name
     * @return true, if successful
     * @throws NameNotFoundException the name not found exception
     */
    public static boolean judgeUpdate(Context context, String version,
                                      String packageName) throws NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageInfo(packageName, 0);
        double versionServer = Double.parseDouble(version);
        double versionLocal = Double.parseDouble(info.versionName);
        return versionLocal < versionServer;
    }

    /**
     * Gets the version name.
     *
     * @param context the context
     * @return the version name
     */
    // 获取Android的版本号VersionName
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pmInfo = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            String versionName = pmInfo.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    /**
     * Creates the sign.
     *
     * @param map the map
     * @param key the key
     * @return the string
     */
    // 报文中的参数按照字典顺序排列然后进行MD5生成签名sign
    public static String createSign(HashMap<String, String> map, String key) {
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = map.keySet();
        ArrayList<String> list = new ArrayList<String>(keyset);
        // 对key键值按字典升序排序
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            String value = map.get(list.get(i));
            if (null != value && !"".equals(value)) {
                sb.append(list.get(i)).append("=").append(map.get(list.get(i)))
                        .append("&");
            }
        }
        sb.append("key=").append(key);
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update((sb.toString()).getBytes());
            byte[] m = md5.digest();
            String sign = CommonMethods.bytesToHexString(m).toUpperCase();
            LogUtil.v(TAG, "md5之前的数据：" + sb.toString());
            LogUtil.v(TAG, "md5之后的数据：" + sign);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Hash map2 json.
     *
     * @param map the map
     * @return the string
     */
    public static String hashMap2Json(HashMap<String, String> map) {
        String s = "{";
        for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it
                .hasNext(); ) {
            Entry<String, String> e = it.next();
            s += "\"" + e.getKey() + "\":\"" + e.getValue() + "\",";
        }
        s = s.substring(0, s.lastIndexOf(",")) + "}";
        return s;
    }

    /**
     * Gets the random string.
     *
     * @param length the length
     * @return the random string
     */
    // 获取length位随机数
    public static String getRandomString(int length) {
        String base = "0123456789abcdef";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        String fdate = new SimpleDateFormat("yyMMdd").format(cal.getTime());
        return fdate;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public static String getTime() {
        Date date = new Date();
        String ftime = new SimpleDateFormat("HHmmss").format(date);
        return ftime;
    }

    /**
     * To hex string.
     *
     * @param val the val
     * @return the string
     */
    public static String toHexString(int val) {
        StringBuffer sb = new StringBuffer();
        if ((val & 0xff) < 0x10) {
            sb.append("0");
        }
        sb.append(Integer.toHexString(val & 0xff).toUpperCase());
        return sb.toString();
    }

    public static double getDouble2(double d) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);//BigDecimal.ROUND_HALF_UP 是4舍5入，BigDecimal.ROUND_DOWN是舍去，BigD
        return bd.doubleValue();
    }

    public static Bitmap getGrayBitmap(Context context,int resoureId){
        Bitmap mBitmap= BitmapFactory.decodeResource(context.getResources(), resoureId);
        Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas =new Canvas(mGrayBitmap);
        Paint mPaint =new Paint();
        //创建颜色变换矩阵  
        ColorMatrix mColorMatrix = new ColorMatrix();
        //设置灰度影响范围  
        mColorMatrix.setSaturation(0);
        //创建颜色过滤矩阵  
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
        //设置画笔的颜色过滤矩阵  
        mPaint.setColorFilter(mColorFilter);
       //使用处理后的画笔绘制图像  
        mCanvas.drawBitmap(mBitmap, 0, 0,mPaint);
        return mGrayBitmap;
    }

}

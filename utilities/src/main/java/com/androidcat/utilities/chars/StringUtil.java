package com.androidcat.utilities.chars;

// TODO: Auto-generated Javadoc

/**
 * The Class StringUtil.
 */
public class StringUtil {

    /**
     * Byte to hex.
     *
     * @param buffer the buffer
     * @param offset the offset
     * @param len    the len
     * @return the string
     */
    public static String byteToHex(byte[] buffer, int offset, int len) {
        StringBuffer hexString = new StringBuffer();
        String hex;
        int iValue;

        for (int i = offset; i < offset + len; i++) {
            iValue = buffer[i];
            if (iValue < 0)
                iValue += 256;

            hex = Integer.toString(iValue, 16);
            if (hex.length() == 1)
                hexString.append("0" + hex);
            else
                hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

    /**
     * Byte to hex.
     *
     * @param buffer the buffer
     * @return the string
     */
    public static String byteToHex(byte[] buffer) {
        StringBuffer hexString = new StringBuffer();
        String hex;
        int iValue;

        for (int i = 0; i < buffer.length; i++) {
            iValue = buffer[i];
            if (iValue < 0)
                iValue += 256;

            hex = Integer.toString(iValue, 16);
            if (hex.length() == 1)
                hexString.append("0" + hex);
            else
                hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

    /**
     * Ascii to hex.
     *
     * @param hexBuffer the hex buffer
     * @param iOffset   the i offset
     * @param iLen      the i len
     * @return the string
     */
    public static String asciiToHex(byte[] hexBuffer, int iOffset, int iLen) {
        StringBuffer hexString = new StringBuffer();
        String hex;
        int byteValue;

        for (int i = iOffset; i < iOffset + iLen; i++) {
            byteValue = hexBuffer[i];
            if (byteValue < 0) {
                byteValue += 256;
            }

            hex = Integer.toString(byteValue, 16);
            if (hex.length() == 1)
                hexString.append("0" + hex);
            else
                hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

    /**
     * Int to hex.
     *
     * @param value the value
     * @return the string
     */
    public static String intToHex(int value) {
        String hex;

        hex = Integer.toString(value, 16);

        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }

        return hex.toUpperCase();
    }

    /**
     * Int to bytes.
     *
     * @param value    the value
     * @param byteSize the byte size
     * @return the byte[]
     */
    public static byte[] intToBytes(int value, int byteSize) {
        String hex = StringUtil.Int2HexStr(value, byteSize * 2);
        return StringUtil.hexToBytes(hex);
    }

    /**
     * Hex to ascii.
     *
     * @param hex 将16进制的ascii 转成中文
     * @return the string
     */
    public static String hexToAscii(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return new String(buffer);
    }

    /**
     * Hex to bytes.
     *
     * @param hex 每两个字节进行处理
     * @return the byte[]
     */
    public static byte[] hexToBytes(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return buffer;
    }

    /**
     * 将一个byte数组转换为字符串.
     *
     * @param arr 要转换的byte数组
     * @return 转换好的字符串，如果数组的length=0，则返回""。
     */
    public static String bytetoString(byte[] arr) {
        String str = "";
        String tempStr = "";
        for (int i = 1; i < arr.length; i++) {
            tempStr = (Integer.toHexString(arr[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            } else {
                str = str + tempStr;
            }
        }
        return str;
    }

    /**
     * Byte to hex str.
     *
     * @param data byte 0x 01
     * @return 01
     */
    public static String byteToHexStr(byte data) {

        String result = Integer.toString((data & 0xff) + 0x100, 16)
                .substring(1);
        return result;
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
}

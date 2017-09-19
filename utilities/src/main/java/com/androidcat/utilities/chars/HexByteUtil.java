package com.androidcat.utilities.chars;

// TODO: Auto-generated Javadoc

/**
 * The Class HexByteUtil.
 */
public class HexByteUtil {

    /**
     * Hex string2 bytes.
     *
     * @param src the src
     * @return the byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        int srcLen = src.length() / 2;
        byte ret[] = new byte[srcLen];
        for (int i = 0; i < srcLen; i++)
            ret[i] = addTwoByte(fastUniteBytes(src.charAt(i * 2)),
                    fastUniteBytes(src.charAt(i * 2 + 1)));

        return ret;
    }

    /**
     * Byte2 hex.
     *
     * @param b the b
     * @return the string
     */
    public static String byte2Hex(byte b) {
        String hex = Integer.toHexString(b & 255);
        if (hex.length() == 1)
            hex = (new StringBuilder(String.valueOf('0'))).append(hex)
                    .toString();
        return hex.toUpperCase();
    }

    /**
     * Int to str2.
     *
     * @param len    the len
     * @param hexlen the hexlen
     * @return the string
     */
    public static String intToStr2(int len, int hexlen) {
        String value = "";
        String hex = Integer.toHexString(len);
        int length = hex.length();
        int offset = hexlen - length;
        String temp[] = new String[offset + 1];
        for (int i = 0; i < offset; i++)
            temp[i] = "0";

        temp[offset] = hex;
        String as[];
        int k = (as = temp).length;
        for (int j = 0; j < k; j++) {
            String str = as[j];
            value = (new StringBuilder(String.valueOf(value))).append(str)
                    .toString();
        }

        return value.toUpperCase();
    }

    /**
     * Adds the two byte.
     *
     * @param b1 the b1
     * @param b2 the b2
     * @return the byte
     */
    public static byte addTwoByte(byte b1, byte b2) {
        int v = (b1 << 4 & 240) + (b2 & 15);
        return (byte) v;
    }

    /**
     * Fast unite bytes.
     *
     * @param c the c
     * @return the byte
     */
    public static byte fastUniteBytes(char c) {
        byte outpu1;
        switch (c) {
            case 48: // '0'
                outpu1 = 0;
                break;

            case 49: // '1'
                outpu1 = 1;
                break;

            case 50: // '2'
                outpu1 = 2;
                break;

            case 51: // '3'
                outpu1 = 3;
                break;

            case 52: // '4'
                outpu1 = 4;
                break;

            case 53: // '5'
                outpu1 = 5;
                break;

            case 54: // '6'
                outpu1 = 6;
                break;

            case 55: // '7'
                outpu1 = 7;
                break;

            case 56: // '8'
                outpu1 = 8;
                break;

            case 57: // '9'
                outpu1 = 9;
                break;

            case 65: // 'A'
                outpu1 = 10;
                break;

            case 66: // 'B'
                outpu1 = 11;
                break;

            case 67: // 'C'
                outpu1 = 12;
                break;

            case 68: // 'D'
                outpu1 = 13;
                break;

            case 69: // 'E'
                outpu1 = 14;
                break;

            case 70: // 'F'
                outpu1 = 15;
                break;

            case 97: // 'a'
                outpu1 = 10;
                break;

            case 98: // 'b'
                outpu1 = 11;
                break;

            case 99: // 'c'
                outpu1 = 12;
                break;

            case 100: // 'd'
                outpu1 = 13;
                break;

            case 101: // 'e'
                outpu1 = 14;
                break;

            case 102: // 'f'
                outpu1 = 15;
                break;

            case 58: // ':'
            case 59: // ';'
            case 60: // '<'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                outpu1 = 0;
                break;
        }
        return outpu1;
    }

    public static byte[] hexStringToByteArray(String str) {
        if(str == null) {
            return null;
        } else if(str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];

            for(int i = 0; i < len; ++i) {
                buffer[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }

            return buffer;
        }
    }

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        } else {
            int len = data.length;
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < len; ++i) {
                if((data[i] & 255) < 16) {
                    sb.append("0" + Integer.toHexString(data[i] & 255));
                } else {
                    sb.append(Integer.toHexString(data[i] & 255));
                }
            }

            return sb.toString().toUpperCase();
        }
    }
}

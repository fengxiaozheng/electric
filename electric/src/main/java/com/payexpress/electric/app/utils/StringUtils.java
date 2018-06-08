package com.payexpress.electric.app.utils;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */

public class StringUtils {

    private StringUtils(){}

    //将0x12 0x13 变为string返回 1213
    public static String Hxtostring(byte data[]) {
        StringBuffer sbf2 = new StringBuffer();
        if (data != null && data.length > 0) {
            int k = 0;
            String da;
            for (k = 0; k < data.length; k++) {
                da = Integer.toHexString(data[k] & 0xff);
                sbf2.append(da.length() == 1 ? "0" + da : da);
            }
        }
        return sbf2.toString();
    }

    public static String byteArrayToStr(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] res = new char[src.length * 2]; // 每个byte对应两个字符
        final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >> 4 & 0X0F]; // 先存byte的高4位
            res[j++] = hexDigits[src[i] & 0X0F]; // 再存byte的低4位
        }

        return new String(res);
    }

    public static byte[] hexToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }

        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        String hexDigits = "0123456789ABCDEF";
        for (int i = 0; i < length; i++) {
            int pos = i * 2; // 两个字符对应一个byte
            int h = hexDigits.indexOf(hexChars[pos]) << 4; // 注1
            int l = hexDigits.indexOf(hexChars[pos + 1]); // 注2
            if(h == -1 || l == -1) { // 非16进制字符
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }
}

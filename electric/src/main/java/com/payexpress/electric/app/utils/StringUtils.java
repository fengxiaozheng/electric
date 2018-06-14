package com.payexpress.electric.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */

public class StringUtils {

    private StringUtils() {
    }

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
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
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
            if (h == -1 || l == -1) { // 非16进制字符
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }

    public static String getUuid(Context context) {
        String uuid = null;
        String PREFS_FILE = "device_id.xml";
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
        String PREFS_DEVICE_ID = "device_id";
        String id = prefs.getString(PREFS_DEVICE_ID, (String) null);
        if (id != null) {
            uuid = id;
            System.out.println("数据：DeviceUuidFactory的存储在pres文件中的id" + id);
        } else {
            String serialNumber = Build.SERIAL;
            if (serialNumber != null) {
                uuid = serialNumber;
                System.out.println("数据：DeviceUuidFactory的设备序列号 :" + serialNumber);
            }

            prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
        }

        return uuid != null ? uuid.toUpperCase() : null;
    }

    public static String MD5(String s) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(s.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(Context context) {
        String PREFS_FILE = "access_token.xml";
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
        return prefs.getString("access_token", (String) null);
    }

    public static void setToken(Context context, String token) {
        String PREFS_FILE = "access_token.xml";
        SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
        prefs.edit().putString("access_token", token);
    }

}

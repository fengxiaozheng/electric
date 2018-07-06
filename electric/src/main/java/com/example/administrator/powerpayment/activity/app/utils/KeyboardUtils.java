package com.example.administrator.powerpayment.activity.app.utils;

/**
 * Created by fengxiaozheng
 * on 2018/5/11.
 */

public class KeyboardUtils {

    private KeyboardUtils() {
    }

    public static String[] withPoint() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "清除"};
    }

    public static String[] withoutPoint() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "清除"};
    }

    public static String[] withIDCard() {
        return new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "X", "清除"};
    }
}

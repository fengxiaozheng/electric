package com.payexpress.electric.app.utils;

import android.widget.EditText;

import java.lang.reflect.Method;

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

    public static void disableShowInput(EditText editText) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
    }
}

package com.example.administrator.powerpayment.activity.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fengxiaozheng
 * on 2018/7/5.
 */

public class ToastUtil {

    private ToastUtil() {}

    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            layout.setBackgroundColor(R.drawable.user_info);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            v.setTextSize(38);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void show(Context context, String msg, int during) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, during);
//            LinearLayout layout = (LinearLayout) toast.getView();
//            layout.setBackgroundColor(R.drawable.user_info);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            v.setTextSize(38);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}

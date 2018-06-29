package com.payexpress.electric.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payexpress.electric.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean flag;//为true时方可进行网络请求，确保拿到token值
    private TextView mTime;
    private SimpleDateFormat dateFormat, timeFormat;
    private Calendar calendar;
    private Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getWindow().getDecorView().findViewById(android.R.id.content) != null) {
            ViewGroup viewGroup = getWindow().getDecorView().findViewById(android.R.id.content);
            if (viewGroup.findViewById(R.id.heat_time) != null) {
                TextView mDate = viewGroup.findViewById(R.id.heat_time);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 \n HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                mDate.setText(simpleDateFormat.format(date));
            } else {
                System.out.println("        里边空了");
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    System.out.println("          " + viewGroup.getChildAt(i).getId());
                }

            }

        } else {
            System.out.println("           控件空");
        }
    }

    protected void initDate() {
        mTime = findViewById(R.id.heat_time);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        new TimeThread().start();
    }

    private String getdayOfWeek() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String result;
        switch (day) {
            case 1:
                result = "星期日";
                break;
            case 2:
                result = "星期一";
                break;
            case 3:
                result = "星期二";
                break;
            case 4:
                result = "星期三";
                break;
            case 5:
                result = "星期四";
                break;
            case 6:
                result = "星期五";
                break;
            case 7:
                result = "星期六";
                break;
            default:
                result = "未知";
                break;
        }
        return result;
    }

    private class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);

        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    date = new Date(System.currentTimeMillis());
                    mTime.setText(String.format("%s\n%s\n%s", dateFormat.format(date), getdayOfWeek(), timeFormat.format(date)));
                    break;
                default:
                    break;
            }
            return false;
        }
    });

}

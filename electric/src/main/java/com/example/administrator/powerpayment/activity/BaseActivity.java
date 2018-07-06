package com.example.administrator.powerpayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.mvp.model.entity.TitleBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunfusheng.marqueeview.MarqueeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public class BaseActivity extends AppCompatActivity {
    private TextView mTime;
    private SimpleDateFormat dateFormat, timeFormat;
    private Calendar calendar;
    private Date date;
    private boolean isFirst;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        Intent intent = new Intent();
//        intent.setClass(this, MyJobService.class);
//        startService(intent);
    }

    protected void initText(Intent intent) {
        MarqueeView marqueeView = findViewById(R.id.l_msg_tv);
        Gson gson = new Gson();
        try {
            String s = intent.getExtras().getString("TitleBean");
            Log.e("main", "收到的数据：             " + s);
            List<TitleBean> list = gson.fromJson(s, new TypeToken<List<TitleBean>>() {
            }.getType());
            List<String> info = new ArrayList<>();
            if (list.size() > 0) {
                for (TitleBean ss : list) {
                    info.add(ss.getTitles());
                }
            } else {
                info.add("欢迎使用");
            }
            marqueeView.startWithList(info);
         } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void initDate() {
        mTime = findViewById(R.id.head_time);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        isFirst = true;
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
                    if (!isFirst) {
                        Thread.sleep(1000);
                    }
                    isFirst = false;
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

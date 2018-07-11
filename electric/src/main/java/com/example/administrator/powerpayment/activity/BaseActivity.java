package com.example.administrator.powerpayment.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.mvp.model.entity.TitleBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunfusheng.marqueeview.MarqueeView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
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
    private MyHandler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        if (!isServiceRunning(this, "com.using.services.DMCenterService")) {
//            try {
//                Intent toService = new Intent(this, DMCenterService.class);
////            toService = new Intent(this, DeviceManagementService.class);
//                startService(toService);
//                Log.e("mian", "开始DMCenterService");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

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
        handler = new MyHandler(this);
        new TimeThread(this).start();
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

    private static class TimeThread extends Thread {
        private WeakReference<Activity> reference;

        public TimeThread(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void run() {
            super.run();
            if (reference.get() != null) {
                if (reference.get() instanceof BaseActivity) {
                    ((BaseActivity) reference.get()).doRun();
                }
            }

        }
    }

    private void doRun() {
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

    private static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (reference.get() != null) {
                if (reference.get() instanceof BaseActivity) {
                    ((BaseActivity) reference.get()).doHandler(msg.what);
                }
            }
        }
    }

//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    date = new Date(System.currentTimeMillis());
//                    mTime.setText(String.format("%s\n%s\n%s", dateFormat.format(date), getdayOfWeek(), timeFormat.format(date)));
//                    break;
//                default:
//                    break;
//            }
//            return false;
//        }
//    });

    private void doHandler(int what) {
        switch (what) {
            case 1:
                date = new Date(System.currentTimeMillis());
                mTime.setText(String.format("%s\n%s\n%s", dateFormat.format(date), getdayOfWeek(), timeFormat.format(date)));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        System.out.println("       数据：调用了leak");
        fixInputMethodManagerLeak(this);
        super.onDestroy();
    }


    private void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }

}

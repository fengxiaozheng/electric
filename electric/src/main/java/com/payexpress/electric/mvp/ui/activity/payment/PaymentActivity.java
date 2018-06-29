package com.payexpress.electric.mvp.ui.activity.payment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Toast;

import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.mvp.model.api.Api;
import com.payexpress.electric.mvp.ui.activity.BaseActivity;
import com.payexpress.electric.mvp.ui.widget.LoadingDailog;

public class PaymentActivity extends BaseActivity {

    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;
    private int up = 0;
    private int down = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.e_frame, PaymentMainFragment.newInstance(), "PaymentMainFragment");
        mFragmentTransaction.addToBackStack("PaymentMainFragment");
        mFragmentTransaction.commit();
        builder = new LoadingDailog.Builder(this);
        loadingView = builder.setMessage("Loading...").create();
        initDate();
    }

    public void showDialog() {
        loadingView.show();
    }

    public void dismissDialog() {
        loadingView.dismiss();
    }

    public boolean isDialogShow(){
        return loadingView.isShowing();
    }

    public void setDialogMessage(String msg) {
        builder.setMessage(msg);
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("          "+event.getX()+","+event.getY());
                System.out.println("           上"+up);
                if (up >= 10) {
                    if (event.getX() >1820 && event.getX() > 990) {
                        down++;
                    } else {
                        down = 0;
                        up = 0;
                    }
                    System.out.println("           下"+down);
                    if (down == 10) {
                        show();
                        System.out.println("           成功");
                    } else {
                        System.out.println("           失败");
                    }
                }else {
                    if (event.getX() < 120 && event.getX() < 120) {
                        up++;
                    } else {
                        up = 0;
                    }
                }


                break;
            default:
                break;
        }
        return true;
    }

    private void show() {
        if (TextUtils.isEmpty(StringUtils.getConfig(this, Api.termPsd))) {
            Toast.makeText(this, "请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("机器信息");
        builder.setMessage("商户号："+StringUtils.getConfig(this, Api.mchtNo)
                +"\n"+"终端号："+StringUtils.getConfig(this, Api.termNo)
                +"\n"+"密码："+StringUtils.getConfig(this, Api.termPsd));
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }
}

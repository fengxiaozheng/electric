package com.example.administrator.powerpayment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.mvp.model.api.Api;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentMainFragment;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.LoadingDailog;

public class LifeChooseActivity extends BaseActivity {

    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;
    private int up = 0;
    private int down = 0;
    private int right = 0;

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
        loadingView = builder.setMessage("请稍等...").create();
    //    initText(getIntent());
        initDate();
    }

    public void showDialog() {
        builder.setUpdateInfo("请稍等...");
        loadingView.show();
    }

    public void dismissDialog() {
        loadingView.dismiss();
    }

    public boolean isDialogShow(){
        return loadingView.isShowing();
    }

    public void showDialog(String msg) {
        builder.setUpdateInfo(msg);
        loadingView.show();
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("          "+event.getX()+","+event.getY());
                System.out.println("           上"+up);
                if (up >= 10 || right >= 10) {
                    if (event.getX() >1820 && event.getY() > 990) {
                        down++;
                    } else {
                        down = 0;
                        up = 0;
                        right = 0;
                    }
                    System.out.println("           下"+down);
                    if (down == 10) {
                        if (right >= 10) {
                            toHome();
                        }else {
                            show();
                        }
                        System.out.println("           成功");
                    } else {
                        System.out.println("           失败");
                    }
                }else {
                    if (event.getX() < 120 && event.getY() < 120) {
                        up++;
                    }else if (event.getX() > 1820 && event.getY() < 120) {
                        right++;
                    }else {
                        up = 0;
                        right = 0;
                    }
                }


                break;
            default:
                break;
        }
        return true;
    }

    private void toHome() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
        System.exit(0);
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

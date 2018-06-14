package com.payexpress.electric.mvp.ui.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.BaseActivity;
import com.payexpress.electric.mvp.ui.activity.MainActivity;
import com.payexpress.electric.mvp.ui.widget.LoadingDailog;

public class PaymentActivity extends BaseActivity implements View.OnClickListener {

    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;
    private LinearLayout backHome;
    private LinearLayout back;

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
        backHome = findViewById(R.id.backHome);
        back = findViewById(R.id.back);
        backHome.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    public void backHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void back() {
        System.out.println("stack count=" + mFragmentManager.getBackStackEntryCount());
        if (mFragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            mFragmentManager.popBackStack();

        //    getCurrent.onStart();
        }

    }

    public void cComplete() {
        mFragmentManager.popBackStack("CommonPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void cFail() {
        mFragmentManager.popBackStackImmediate("CommonPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void sComplete() {
        mFragmentManager.popBackStack("SmartPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void sFail() {
        mFragmentManager.popBackStackImmediate("SmartPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void start(Fragment current, Fragment next, String tag) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.v_fragment_enter,R.anim.v_fragment_pop_exit,
                R.anim.v_fragment_pop_enter,  R.anim.v_fragment_exit);
        mFragmentTransaction.hide(current);
        mFragmentTransaction.add(R.id.e_frame, next, tag);
        mFragmentTransaction.addToBackStack(tag);
        mFragmentTransaction.commit();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backHome) {
            backHome();
        } else if (v.getId() == R.id.back) {
            back();
        }
    }
}

package com.payexpress.electric.mvp.ui.activity.gov;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.BaseActivity;
import com.payexpress.electric.mvp.ui.activity.MainActivity;
import com.payexpress.electric.mvp.ui.widget.LoadingDailog;

public class GovActivity extends BaseActivity implements View.OnClickListener {

    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;
    private LinearLayout backHome;
    private LinearLayout back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.e_frame, GovMainFragment.newInstance(), "GovMainFragment");
        mFragmentTransaction.addToBackStack("GovMainFragment");
        mFragmentTransaction.commit();
        builder = new LoadingDailog.Builder(this);
        loadingView = builder.setMessage("Loading...").create();
        backHome = findViewById(R.id.backHome);
        back = findViewById(R.id.back);
        backHome.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    public boolean isFlagTrue() {
        return flag;
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

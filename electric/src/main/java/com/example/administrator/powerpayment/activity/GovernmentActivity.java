package com.example.administrator.powerpayment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov.GovMainFragment;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.LoadingDailog;

public class GovernmentActivity extends BaseActivity {

    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.e_frame, GovMainFragment.newInstance(), "GovMainFragment");
        mFragmentTransaction.addToBackStack("GovMainFragment");
        mFragmentTransaction.commit();
        builder = new LoadingDailog.Builder(this);
        loadingView = builder.setMessage("请稍等...").create();
        initText(getIntent());
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



}

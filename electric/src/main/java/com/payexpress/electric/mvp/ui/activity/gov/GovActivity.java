package com.payexpress.electric.mvp.ui.activity.gov;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.BaseActivity;
import com.payexpress.electric.mvp.ui.widget.LoadingDailog;

public class GovActivity extends BaseActivity {

    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private LoadingDailog.Builder  builder;
    private LoadingDailog loadingView;

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

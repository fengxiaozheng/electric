package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.MainActivity;

public class ElectricActivity extends AppCompatActivity {
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.e_frame, new ElectricFragment(), "ElectricMainFragment");
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }


    public void backHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void back(View view) {
        System.out.println("stack count=" + mFragmentManager.getBackStackEntryCount());
        if (mFragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            mFragmentManager.popBackStack();
        }

    }

    public void start(Fragment current, Fragment next, String tag) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.hide(current);
        mFragmentTransaction.add(R.id.e_frame, next, tag);
        mFragmentTransaction.addToBackStack(tag);
        mFragmentTransaction.commit();
    }

}

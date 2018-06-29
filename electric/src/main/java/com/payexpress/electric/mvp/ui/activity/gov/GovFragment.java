package com.payexpress.electric.mvp.ui.activity.gov;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.MainActivity;


public class GovFragment extends Fragment {

    protected GovActivity activity;
    private CountDownTimer timer;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (GovActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Override
    public void onDestroy() {
        activity = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (getActivity() instanceof GovActivity) {
            if (((GovActivity) getActivity()).isDialogShow()){
                ((GovActivity) getActivity()).dismissDialog();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new CountDownTimer(300 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getView() != null) {
                    if (getView().findViewById(R.id.bottom_time) != null) {
                        if (millisUntilFinished / 1000 >= 1) {
                            ((TextView) getView().findViewById(R.id.bottom_time)).setText(String.valueOf(millisUntilFinished / 1000));
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                back();
            }
        };
        timer.start();

        if (activity.getWindow().findViewById(R.id.back) != null) {
            activity.getWindow().findViewById(R.id.back).setOnClickListener(v -> back());
            activity.getWindow().findViewById(R.id.backHome).setOnClickListener(v -> backHome());
        }else {
            System.out.println("           空了");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onPause();
        } else {
            onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    protected void start(Fragment current, Fragment next, String tag) {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.v_fragment_enter,R.anim.v_fragment_pop_exit,
                R.anim.v_fragment_pop_enter,  R.anim.v_fragment_exit);
        mFragmentTransaction.hide(current);
        mFragmentTransaction.add(R.id.e_frame, next, tag);
        mFragmentTransaction.addToBackStack(tag);
        mFragmentTransaction.commit();

    }

    protected void back() {
        System.out.println("stack count=" + mFragmentManager.getBackStackEntryCount());
//        System.out.println("      tag:"+currentFrg.getTag());
        if (getTag() != null) {
            if (getTag().equals("GovWebFragment")
                    && getView().findViewById(R.id.gov_web) != null) {
                WebView webView = getView().findViewById(R.id.gov_web);
                System.out.println("      "+ webView.getUrl());
                if (webView.getUrl().contains("government/info#/detail")) {
                    webView.goBack();
                }else {
                    back2();
                }
            }else {
                back2();
            }
        }else {
            back2();
        }

    }

    private void back2() {
        if (mFragmentManager.getBackStackEntryCount() <= 1) {
            activity.finish();
        } else {
            mFragmentManager.popBackStack();
            //    getCurrent.onStart();
        }
    }

    private void backHome() {
        startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

}

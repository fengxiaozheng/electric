package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.GovernmentActivity;
import com.example.administrator.powerpayment.activity.R;

import java.lang.ref.WeakReference;


public class GovFragment extends Fragment {

    protected GovernmentActivity activity;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;
    private GovCountDownTimer timer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (GovernmentActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (getActivity() instanceof GovernmentActivity) {
            if (((GovernmentActivity) getActivity()).isDialogShow()){
                ((GovernmentActivity) getActivity()).dismissDialog();
            }
        }
        mFragmentManager = null;
        mFragmentTransaction = null;
        activity = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (timer == null) {
            timer = new GovCountDownTimer(this, 300 * 1000, 1000);
        }
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

//    private CountDownTimer timer = new CountDownTimer(300 * 1000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            if (getView() != null) {
//                if (getView().findViewById(R.id.bottom_time) != null) {
//                    if (millisUntilFinished / 1000 >= 1) {
//                        ((TextView) getView().findViewById(R.id.bottom_time)).setText(String.valueOf(millisUntilFinished / 1000));
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void onFinish() {
//            back();
//        }
//    };

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
         //   activity.finish();
            backHome();
        } else {
            mFragmentManager.popBackStack();
            //    getCurrent.onStart();
        }
    }

    private void backHome() {
    //    startActivity(new Intent(activity, MainActivity.class));
        try {
            Intent intent = new Intent();
//            ComponentName cn = new ComponentName("com.usw.adplayer", "com.xindian.imediaplayer.LauncherActivity");//本独
            ComponentName cn = new ComponentName("com.usw.adplayer", "com.usw.adplayer.LauncherActivity");
            intent.setComponent(cn);
            intent.setAction("android.intent.action.MAIN");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity.finish();
    }

    private static class GovCountDownTimer extends CountDownTimer {

        private WeakReference<Fragment> reference;

        public GovCountDownTimer(Fragment fragment, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            reference = new WeakReference<>(fragment);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            if (reference.get() != null) {
                if (reference.get().getView() != null) {
                    if (reference.get().getView().findViewById(R.id.bottom_time) != null) {
                        if (millisUntilFinished / 1000 >= 1) {
                            ((TextView) reference.get().getView().findViewById(R.id.bottom_time)).setText(String.valueOf(millisUntilFinished / 1000));
                        }
                    }
                }
            }
        }

        @Override
        public void onFinish() {
            Fragment f = reference.get();

            if (f != null) {
                if (f instanceof GovFragment) {
                    ((GovFragment) f).back();
                }
            }

        }
    }

}

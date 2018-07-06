package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.LifeChooseActivity;
import com.example.administrator.powerpayment.activity.R;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */

public class PaymentFragment extends Fragment {

    protected LifeChooseActivity activity;
    private MyCountDownTimer timer;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.FragmentTransaction mFragmentTransaction;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (LifeChooseActivity) activity;
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
        if (getActivity() instanceof LifeChooseActivity) {
            if (((LifeChooseActivity) getActivity()).isDialogShow()){
                ((LifeChooseActivity) getActivity()).dismissDialog();
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
            System.out.println("          time空了");

            timer = new MyCountDownTimer(this, 300 * 1000, 1000);

//            timer = new CountDownTimer(300 * 1000, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    if (getView() != null) {
//                        if (getView().findViewById(R.id.bottom_time) != null) {
//                            if (millisUntilFinished / 1000 >= 1) {
//                                ((TextView) getView().findViewById(R.id.bottom_time)).setText(String.valueOf(millisUntilFinished / 1000));
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFinish() {
//                    if (getActivity() instanceof LifeChooseActivity) {
//                        if (Objects.equals(getTag(), "PayResultFragment")) {
//                            backHome();
//                        } else {
//                            back();
//                        }
//                    }
//                }
//            };
        } else {
            System.out.println("          time没空");
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
            if (Objects.equals(getTag(), "PayResultFragment")) {
                payResult();
            }else {
                mFragmentManager.popBackStack();
            }
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

    protected void cComplete() {
        mFragmentManager.popBackStack("CommonPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void cFail() {
        mFragmentManager.popBackStackImmediate("CommonPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    protected void sComplete() {
        mFragmentManager.popBackStack("SmartPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    protected void sFail() {
        mFragmentManager.popBackStackImmediate("SmartPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void payResult() {
        for (int i =0; i < mFragmentManager.getFragments().size(); i++) {
            if ("CommonPowerFragment".equals(mFragmentManager.getFragments().get(i).getTag())) {
                mFragmentManager.popBackStackImmediate("CommonPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            }
            if ("SmartPowerFragment".equals(mFragmentManager.getFragments().get(i).getTag())) {
                mFragmentManager.popBackStackImmediate("SmartPowerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            }
        }

    }


    private static class MyCountDownTimer extends CountDownTimer {

        private WeakReference<Fragment> reference;

        public MyCountDownTimer(Fragment fragment, long millisInFuture, long countDownInterval) {
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
                if (f instanceof PaymentFragment) {
                    if (Objects.equals(f.getTag(), "PayResultFragment")) {
                        ((PaymentFragment) f).backHome();
                    } else {
                        ((PaymentFragment) f).back();
                    }
                }
            }

        }
    }
}

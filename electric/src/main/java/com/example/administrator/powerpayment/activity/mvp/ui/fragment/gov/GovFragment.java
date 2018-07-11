package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.GovernmentActivity;
import com.example.administrator.powerpayment.activity.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


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

    protected void start(Fragment current, Fragment next, String tag) {
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.setCustomAnimations(R.anim.v_fragment_enter,R.anim.v_fragment_pop_exit,
//                R.anim.v_fragment_pop_enter,  R.anim.v_fragment_exit);
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
        //    backHome();
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

    protected void disableShowInput(EditText editText) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
    }

    protected void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

}

package com.payexpress.electric.app;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.jess.arms.integration.FragmentLifecycle;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by fengxiaozheng
 * on 2018/5/23.
 */
@Singleton
public class FragmentLifeCycleCtr extends FragmentLifecycle {
    private CountDownTimer timer;

    @Inject
    public FragmentLifeCycleCtr(){}


    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        timer = new CountDownTimer(300 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (f.getView() != null) {
                    if (f.getView().findViewById(R.id.bottom_time) != null) {
                        if (millisUntilFinished / 1000 >= 1) {
                            ((TextView) f.getView().findViewById(R.id.bottom_time)).setText(String.valueOf(millisUntilFinished / 1000));
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                if (f.getActivity() instanceof PaymentActivity) {
                    ((PaymentActivity) f.getActivity()).back();
                }
            }
        }.start();
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (f.getActivity() instanceof PaymentActivity) {
            if (((PaymentActivity) f.getActivity()).isDialogShow()){
                ((PaymentActivity) f.getActivity()).dismissDialog();
            }
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        if (timer != null) {
            timer.cancel();
        }
    }


    public void pause() {
        if (timer != null) {
            timer.cancel();
        }
    }


}

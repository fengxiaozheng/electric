package com.payexpress.electric.mvp.ui.activity.payment;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */

public class PaymentFragment extends Fragment {

    protected PaymentActivity activity;
    private CountDownTimer timer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (PaymentActivity) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        timer = new CountDownTimer(300*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                activity.back();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
        timer.cancel();
        timer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }
}

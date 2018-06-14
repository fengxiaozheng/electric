package com.payexpress.electric.mvp.ui.activity.payment;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.payexpress.electric.R;

import java.util.Objects;

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
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (getActivity() instanceof PaymentActivity) {
            if (((PaymentActivity) getActivity()).isDialogShow()){
                ((PaymentActivity) getActivity()).dismissDialog();
            }
        }
        activity = null;
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
                if (getActivity() instanceof PaymentActivity) {
                    if (Objects.equals(getTag(), "PayResultFragment")){
                        ((PaymentActivity) getActivity()).backHome();
                    }else {
                        ((PaymentActivity) getActivity()).back();
                    }
                }
            }
        };
        timer.start();
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
}

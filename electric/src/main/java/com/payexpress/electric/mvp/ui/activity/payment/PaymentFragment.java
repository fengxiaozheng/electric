package com.payexpress.electric.mvp.ui.activity.payment;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */

public class PaymentFragment extends Fragment {

    protected PaymentActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (PaymentActivity) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;

    }
}

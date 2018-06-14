package com.payexpress.electric.mvp.ui.activity.gov;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.payexpress.electric.R;


public class GovFragment extends Fragment {

    protected GovActivity activity;
    private CountDownTimer timer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (GovActivity) activity;
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
                if (getActivity() instanceof GovActivity) {
                        ((GovActivity) getActivity()).back();
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

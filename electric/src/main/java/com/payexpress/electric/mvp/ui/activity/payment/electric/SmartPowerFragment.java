package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.SmartUserInfo;
import com.payexpress.electric.mvp.presenter.SmartPowerPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartPowerFragment extends BaseFragment<SmartPowerPresenter, PaymentActivity> {

    @BindView(R.id.s_hasCard)
    LinearLayout s_hasCard;
    @BindView(R.id.s_noCard)
    LinearLayout s_noCard;
    @BindView(R.id.s_check)
    LinearLayout s_check;
    @BindView(R.id.s_query_balance)
    LinearLayout balance;

    public SmartPowerFragment() {
        // Required empty public constructor
    }

    public static SmartPowerFragment newInstance() {
        return new SmartPowerFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smart_power, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.s_hasCard)
    void hasCard() {
        SmartUserInfo info = new SmartUserInfo();
        info.setFlag(0);
        activity.start(SmartPowerFragment.this, SPUserInfoFragment.newInstance(info),
                "SPUserInfoFragment");
    }

    @OnClick(R.id.s_noCard)
    void noCard() {
        activity.start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(3),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_check)
    void check() {
        activity.start(SmartPowerFragment.this, CPRecordFragment.newInstance(true),
                "CPRecordFragment");
    }

    @OnClick(R.id.s_query_balance)
    void query() {
        activity.start(SmartPowerFragment.this, new CPBalanceFragment(),
                "CPBalanceFragment");
    }
}

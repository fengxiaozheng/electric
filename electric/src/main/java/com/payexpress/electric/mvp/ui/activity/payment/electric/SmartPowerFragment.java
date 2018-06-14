package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.Psamcmd;
import com.payexpress.electric.di.component.payment.DaggerSmartPowerComponent;
import com.payexpress.electric.di.module.payment.SmartPowerModule;
import com.payexpress.electric.mvp.contract.payment.SmartPowerContract;
import com.payexpress.electric.mvp.model.entity.paymentEntity.QuerySmartCardRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.SmartUserInfo;
import com.payexpress.electric.mvp.presenter.payment.SmartPowerPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartPowerFragment extends BasePaymentFragment<SmartPowerPresenter>
                            implements SmartPowerContract.View{

    @BindView(R.id.s_hasCard)
    LinearLayout s_hasCard;
    @BindView(R.id.s_noCard)
    LinearLayout s_noCard;
    @BindView(R.id.s_check)
    LinearLayout s_check;
    @BindView(R.id.s_query_balance)
    LinearLayout balance;

    private boolean isFirst = false;

    public SmartPowerFragment() {
        // Required empty public constructor
    }

    public static SmartPowerFragment newInstance() {
        return new SmartPowerFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSmartPowerComponent.builder()
                .appComponent(appComponent)
                .smartPowerModule(new SmartPowerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smart_power, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.s_hasCard)
    void hasCard() {
        if (isFirst) {
            com.halio.Rfid.powerOff();
            com.halio.Rfid.closeCommPort();
            com.halio.Rfid.powerOn();
            com.halio.Rfid.openCommPort();
            Psamcmd.ResetGPIO(55);
            Psamcmd.ResetGPIO(94);
        }
        isFirst = false;
        if (mPresenter != null) {
            mPresenter.querySmartCardInfo();
        }
    }

    @OnClick(R.id.s_noCard)
    void noCard() {
        activity.start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(3),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_check)
    void check() {
        activity.start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(2),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_query_balance)
    void query() {
        activity.start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(4),
                "CPCheckUserFragment");
    }

    @Override
    public void success(QuerySmartCardRes res) {
        SmartUserInfo info = new SmartUserInfo();
        info.setFlag(2);
        info.setUserNo(res.getG7());
        info.setUserName(res.getG8());
        info.setUserAddress(res.getG9());
        activity.start(SmartPowerFragment.this, SPUserInfoFragment.newInstance(info),
                "SPUserInfoFragment");
    }

    @Override
    public void fail(String msg) {
        activity.start(SmartPowerFragment.this,
                ReadCardErrorFragment.newInstance(msg), "ReadCardErrorFragment");
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}

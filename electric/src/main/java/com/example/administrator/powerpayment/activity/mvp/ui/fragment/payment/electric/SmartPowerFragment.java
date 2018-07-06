package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.Psamcmd;
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerSmartPowerComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.SmartPowerModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartUserInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.SmartPowerPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.BasePaymentFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartPowerFragment extends BasePaymentFragment<SmartPowerPresenter>
        implements SmartPowerContract.View {

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
        start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(3),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_check)
    void check() {
        start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(2),
                "CPCheckUserFragment");
    }

    @OnClick(R.id.s_query_balance)
    void query() {
        start(SmartPowerFragment.this, CPCheckUserFragment.newInstance(4),
                "CPCheckUserFragment");
    }

    @Override
    public void success(QuerySmartCardRes res) {
        SmartUserInfo info = new SmartUserInfo();
        info.setFlag(2);
        info.setUserNo(res.getG7());
        info.setUserName(res.getG8());
        info.setUserAddress(res.getG9());
        start(SmartPowerFragment.this, SPUserInfoFragment.newInstance(info),
                "SPUserInfoFragment");
    }

    @Override
    public void fail(String msg) {
        start(SmartPowerFragment.this,
                ReadCardErrorFragment.newInstance(msg), "ReadCardErrorFragment");
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}

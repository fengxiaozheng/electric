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
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerRewriteCardComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.RewriteCardModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteCardContract;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.RewriteCardPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.BasePaymentFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteCardFragment extends BasePaymentFragment<RewriteCardPresenter>
        implements RewriteCardContract.View {

    @BindView(R.id.re_times)
    LinearLayout mLinearLayout;
    @BindView(R.id.re_end)
    LinearLayout mEnd;

    private boolean isFirst = false;


    public RewriteCardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewriteCardFragment newInstance() {
        return new RewriteCardFragment();
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRewriteCardComponent.builder()
                .appComponent(appComponent)
                .rewriteCardModule(new RewriteCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewrite_card, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.re_times)
    void click() {
        start(RewriteCardFragment.this, RewriteInputFragment.newInstance(),
                "RewriteInputFragment");
    }

    @OnClick(R.id.re_end)
    void end() {
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
            mPresenter.rewrite();
        }
    }

    @Override
    public void success() {
        next(true);
    }

    @Override
    public void fail(String msg) {
        next(false);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    private void next(boolean result) {
        start(RewriteCardFragment.this,
                RewriteResultFragment.newInstance(result), "RewriteResultFragment");
    }
}

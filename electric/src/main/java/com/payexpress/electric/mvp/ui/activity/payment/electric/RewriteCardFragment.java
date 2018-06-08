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
import com.payexpress.electric.di.component.DaggerRewriteCardComponent;
import com.payexpress.electric.di.module.RewriteCardModule;
import com.payexpress.electric.mvp.contract.RewriteCardContract;
import com.payexpress.electric.mvp.presenter.RewriteCardPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;

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
        activity.start(RewriteCardFragment.this, RewriteInputFragment.newInstance(),
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
        activity.start(RewriteCardFragment.this,
                RewriteResultFragment.newInstance(result), "RewriteResultFragment");
    }
}

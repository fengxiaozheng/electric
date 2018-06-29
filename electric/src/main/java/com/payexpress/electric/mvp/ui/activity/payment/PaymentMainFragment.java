package com.payexpress.electric.mvp.ui.activity.payment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;
import com.payexpress.electric.R;
import com.payexpress.electric.di.component.payment.DaggerPaymentMainComponent;
import com.payexpress.electric.di.module.payment.PaymentMainModule;
import com.payexpress.electric.mvp.contract.payment.PaymentMainContract;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.presenter.payment.PaymentMainPresenter;
import com.payexpress.electric.mvp.ui.activity.gov.GovWebFragment;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

import org.ayo.view.status.StatusUIManager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentMainFragment extends BasePaymentFragment<PaymentMainPresenter>
        implements MainFragmentClickListener, PaymentMainContract.View {

    @BindView(R.id.gov_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MainFragmentAdapter mAdapter;
    @Inject
    StatusUIManager mUiManager;

    public PaymentMainFragment() {
        // Required empty public constructor
    }

    public static PaymentMainFragment newInstance() {
        return new PaymentMainFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPaymentMainComponent.builder()
                .appComponent(appComponent)
                .paymentMainModule(new PaymentMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_py_ment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);
            mPresenter.getPaymentMainItem();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onItemClick(View view, MainFragmentItemInfo info) {
        if ("01".equals(info.getStatus())) {
            Toast.makeText(activity, "该功能尚未开通，敬请期待", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("01".equals(info.getFuncType())) {
            start(this, GovWebFragment.newInstance(true, info.getUrl()),
                    "GovWebFragment");
        } else {
            Fragment cls;
            cls = Fragment.instantiate(activity, info.getUrl());
            start(this, cls, info.getUrl());
        }
    }

    @Override
    public MainFragmentClickListener getListener() {
        return this;
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

}

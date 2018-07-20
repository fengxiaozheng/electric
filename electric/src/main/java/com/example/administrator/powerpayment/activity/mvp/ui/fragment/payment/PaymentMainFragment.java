package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.BuildConfig;
import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.StringUtils;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerPaymentMainComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.PaymentMainModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.PaymentMainContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.MainFragmentItemInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.PaymentMainPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.MainFragmentAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.MainFragmentClickListener;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov.GovWebFragment;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;

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
    @BindView(R.id.seriNo)
    TextView mTextView;
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
        mTextView.setText(String.format("%s    %s", StringUtils.getUuid(activity), BuildConfig.VERSION_NAME));
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onItemClick(View view, MainFragmentItemInfo info) {
        if ("01".equals(info.getStatus())) {
            ToastUtil.show(activity, "该功能尚未开通，敬请期待");
            return;
        }
        if ("01".equals(info.getFuncType())) {
            start(this, GovWebFragment.newInstance(true, info.getUrl()),
                    "GovWebFragment");
        } else {
            Fragment cls;
            cls = Fragment.instantiate(activity, info.getUrl());
            start(this, cls, cls.getClass().getSimpleName());
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

package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mingle.widget.LoadingView;
import com.payexpress.electric.R;
import com.payexpress.electric.di.component.DaggerPayRecordComponent;
import com.payexpress.electric.di.module.PayRecordModule;
import com.payexpress.electric.mvp.contract.PayRecordContract;
import com.payexpress.electric.mvp.model.entity.payment.PayRecordRes;
import com.payexpress.electric.mvp.presenter.PayRecordPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.StatusUIManager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CPRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CPRecordFragment extends BasePaymentFragment<PayRecordPresenter>
        implements PayRecordContract.PayRecordView, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private static final String ARG_PARAM1 = "param1";
    // TODO: Rename and change types of parameters
    private boolean mParam;
    private String mUserNo;
    @BindView(R.id.btn_to_pay)
    Button mToPay;
    @BindView(R.id.rd_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.rd_user_no)
    TextView user_no;
    @BindView(R.id.rd_user_name)
    TextView user_name;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.tv_notice1)
    TextView mNotice;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    StatusUIManager mUiManager;



    public CPRecordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CPRecordFragment newInstance(boolean param, String userNo) {
        CPRecordFragment fragment = new CPRecordFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM, param);
        args.putString(ARG_PARAM1, userNo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getBoolean(ARG_PARAM);
            mUserNo = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPayRecordComponent.builder()
                .appComponent(appComponent)
                .payRecordModule(new PayRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cprecord, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mParam) {
            mToPay.setVisibility(View.VISIBLE);
        }
        mNotice.setText(getString(R.string.notice5));
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);

            mPresenter.getRecordData(mUserNo);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success(PayRecordRes res) {
        user_no.setText(res.getGrid_user_code());
        user_name.setText(res.getGrid_user_name());
        loadingView.setVisibility(View.GONE);
        if (res.getRows().size() <= 0) {
            mUiManager.show(DefaultStatus.STATUS_EMPTY);
        }
    }

    @Override
    public void fail(String msg) {
        user_name.setText("未知");
        user_no.setText("未知");
        loadingView.setVisibility(View.GONE);
        mUiManager.show(DefaultStatus.STATUS_EMPTY);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_to_pay) {
            activity.sComplete();
        }
    }
}

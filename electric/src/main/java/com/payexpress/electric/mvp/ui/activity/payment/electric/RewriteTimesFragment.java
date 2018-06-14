package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mingle.widget.LoadingView;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.Psamcmd;
import com.payexpress.electric.di.component.payment.DaggerRewriteTimesComponent;
import com.payexpress.electric.di.module.payment.RewriteTimesModule;
import com.payexpress.electric.mvp.contract.payment.RewriteTimesContract;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardListRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesAllParams;
import com.payexpress.electric.mvp.presenter.payment.RewriteTimesPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;
import com.payexpress.electric.mvp.ui.adapter.RewriteTimesAdapter;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.StatusUIManager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteTimesFragment extends BasePaymentFragment<RewriteTimesPresenter>
        implements RewriteTimesContract.View, RewriteTimesAdapter.OnItemViewClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private RewriteTimesAllParams mParam;

    @BindView(R.id.rt_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.rt_user_no)
    TextView user_no;
    @BindView(R.id.rt_user_name)
    TextView user_name;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.tv_notice1)
    TextView mNotice;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RewriteTimesAdapter mAdapter;
    @Inject
    StatusUIManager mUiManager;

    private boolean isFirst;


    public RewriteTimesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewriteTimesFragment newInstance(RewriteTimesAllParams param) {
        RewriteTimesFragment fragment = new RewriteTimesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (RewriteTimesAllParams) getArguments().getSerializable(ARG_PARAM);
        }
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRewriteTimesComponent.builder()
                .appComponent(appComponent)
                .rewriteTimesModule(new RewriteTimesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewrite_times, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mNotice.setText(getString(R.string.notice5));
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemViewClickListener(this);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);

            mPresenter.getListData(mParam);
        }
        isFirst = true;
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void success(RewriteCardListRes res) {
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
    public void onClick(View view, String payCount) {
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
            mPresenter.startRewrite(user_no.getText().toString(), payCount);
        }
    }
}

package com.payexpress.electric.mvp.ui.activity.gov;

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
import com.payexpress.electric.di.component.gov.DaggerGovMainComponent;
import com.payexpress.electric.di.module.gov.GovMainModule;
import com.payexpress.electric.mvp.contract.gov.GovMainContract;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.presenter.gov.GovMainPresenter;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

import org.ayo.view.status.StatusUIManager;

import javax.inject.Inject;

import butterknife.BindView;


public class GovMainFragment extends BaseGovFragment<GovMainPresenter> implements
        GovMainContract.View, MainFragmentClickListener {

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

    public GovMainFragment() {
        // Required empty public constructor
    }

    public static GovMainFragment newInstance() {

        return new GovMainFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovMainComponent.builder()
                .appComponent(appComponent)
                .govMainModule(new GovMainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);
            mPresenter.getTermArea();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void showMessage(@NonNull String message) {

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
}

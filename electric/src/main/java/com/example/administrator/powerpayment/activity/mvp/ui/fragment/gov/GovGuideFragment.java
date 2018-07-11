package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovGuideComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovGuideModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovGuidePresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.GovGuideAdapter;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovGuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovGuideFragment extends BaseGovFragment<GovGuidePresenter>
        implements GovGuideContract.View, GovGuideAdapter.OnGuideItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;

    @BindView(R.id.gov_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    GovGuideAdapter mAdapter;


    public GovGuideFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GovGuideFragment newInstance(String param) {
        GovGuideFragment fragment = new GovGuideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovGuideComponent.builder()
                .appComponent(appComponent)
                .govGuideModule(new GovGuideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_guide, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        if (mPresenter != null) {
            if ("0".equals(mParam)) {
                mPresenter.getGovGuideItem();
            } else {
                mPresenter.getGovOnceItem();
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onItemClick(View view, GovGuideInfo info) {
        if ("1".equals(mParam)) {
            start(this, GovGuideListFragment.newInstance(true, info.getId()),
                    "GovGuideListFragment");
        } else {
            start(this, GovGuideListFragment.newInstance(false, info.getId()),
                    "GovGuideListFragment");
        }
    }

    @Override
    public void success() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void fail() {
        mLoadingView.setVisibility(View.GONE);
//        Toast.makeText(activity, "暂无数据，请稍后重试", Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity,"暂无数据，请稍后重试");
    }
}

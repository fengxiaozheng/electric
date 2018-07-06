package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovGuideListComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovGuideListModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideListContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.Api;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideListInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovGuideListPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.GovGuideListAdapter;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovGuideListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovGuideListFragment extends BaseGovFragment<GovGuideListPresenter>
        implements GovGuideListContract.View, GovGuideListAdapter.OnGuideListItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private static final String  ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam;
    private boolean isOnce;

    @BindView(R.id.gl_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.page_control)
    LinearLayout mPageControl;
    @BindView(R.id.page_num)
    TextView mPageNum;
    @BindView(R.id.page_start)
    Button mPageStart;
    @BindView(R.id.page_last)
    Button mPageLast;
    @BindView(R.id.page_next)
    Button mPageNext;
    @BindView(R.id.page_back)
    Button mPageBack;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    GovGuideListAdapter mAdapter;

    private int pageIndex = 1;
    private int pageSum;

    public GovGuideListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GovGuideListFragment newInstance(boolean isOnc,String param) {
        GovGuideListFragment fragment = new GovGuideListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        args.putBoolean(ARG_PARAM1, isOnc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
            isOnce = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public void success(int size, boolean isFirst) {
        pageSum = (int) Math.ceil((double) size / 5);
        mPageStart.setEnabled(false);
        mPageLast.setEnabled(false);
        mPageNum.setText(String.format("第1页/共%s页", pageSum));
        if (isFirst) {
            mLoadingView.setVisibility(View.GONE);
        }else {
            activity.dismissDialog();
        }
        if (size <= 5) {
            mPageNext.setEnabled(false);
        }else {
            mPageNext.setEnabled(true);
        }
    }

    @Override
    public void fail(int code, boolean isFirst) {
        if (isFirst) {
            mPageControl.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
        }else {
            activity.dismissDialog();
        }
        if (1 == code) {
            mPageControl.setVisibility(View.GONE);
            Toast.makeText(activity, "数据异常，请重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void moreSuccess(List<GovGuideListInfo> data) {
        activity.dismissDialog();
        if (pageIndex == 1) {
            mPageLast.setEnabled(false);
            mPageStart.setEnabled(false);
        } else {
            mPageLast.setEnabled(true);
            mPageStart.setEnabled(true);
        }
        if (data.size() <= 0) {
            mPageNext.setEnabled(false);
        } else if (data.size() < 5) {
            mPageNext.setEnabled(false);
        } else {
            if (pageIndex == pageSum) {
                mPageNext.setEnabled(false);
            } else {
                mPageNext.setEnabled(true);
            }
        }
    }

    @Override
    public void moreFail() {
        activity.dismissDialog();
            mPageControl.setVisibility(View.GONE);
            Toast.makeText(activity, "数据异常，请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovGuideListComponent.builder()
                .appComponent(appComponent)
                .govGuideListModule(new GovGuideListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_guide_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);
            if (isOnce) {
                mPresenter.getOnceData(mParam, pageIndex, true);
            }else {
                mPresenter.getListData(mParam, pageIndex, true);
            }
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onItemClick(View view, GovGuideListInfo info) {
        if (isOnce) {
            start(this,
                    GovWebFragment.newInstance(false, Api.BASE_URL + "config/jinjiang/"+info.getHtmlName()),
                    "GovWebFragment");
        }else {
            start(this, GovGuideDetailFragment.newInstance(info.getId()),
                    "GovGuideDetailFragment");
        }
    }

    @OnClick(R.id.page_start)
    void pageStart() {
        if (mPresenter != null) {
            activity.showDialog();
            pageIndex = 1;
            mPageNum.setText(String.format("第1页/共%s页", pageSum));
            if (isOnce) {
                mPresenter.getOnceData(mParam, 1, false);
            }else {
                mPresenter.getListData(mParam, 1, false);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @OnClick(R.id.page_last)
    void pageLast() {
        pageIndex --;
        System.out.println("        start:"+pageIndex);
        if (mPresenter != null) {
            activity.showDialog();
            mPageNum.setText(String.format("第%d页/共%s页", pageIndex, pageSum));
            if (isOnce) {
                mPresenter.getOnceMoreData(mParam, pageIndex);
            }else {
                mPresenter.getMoreData(mParam, pageIndex);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    @OnClick(R.id.page_next)
    void pageNext() {
        pageIndex ++;
        System.out.println("       start:"+pageIndex);
        if (mPresenter != null) {
            activity.showDialog();
            mPageNum.setText(String.format("第%d页/共%s页", pageIndex, pageSum));
            if (isOnce) {
                mPresenter.getOnceMoreData(mParam, pageIndex);
            }else {
                mPresenter.getMoreData(mParam, pageIndex);
            }
        }
    }

    @OnClick(R.id.page_back)
    void pageBack() {
        back();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadingView = null;
        if (activity != null) {
            if (activity.isDialogShow()) {
                activity.dismissDialog();
            }
        }
    }
}

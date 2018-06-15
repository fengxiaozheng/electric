package com.payexpress.electric.mvp.ui.activity.gov;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.ayo.view.status.DefaultStatus;
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
    private CountDownTimer timer;

    public GovMainFragment() {
        // Required empty public constructor
    }

    public static GovMainFragment newInstance() {

        return new GovMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String[] titles = {getString(R.string.str_001), getString(R.string.str_002),
                getString(R.string.str_003), getString(R.string.str_004),
                getString(R.string.str_005), getString(R.string.str_006),
                getString(R.string.str_007), getString(R.string.str_008)};
        int[] imgs = {R.mipmap.ic_shebao, R.mipmap.ic_shuiwu, R.mipmap.ic_fangchan,
                R.mipmap.ic_gongan, R.mipmap.ic_gongjijin, R.mipmap.ic_yiliao,
                R.mipmap.ic_jiaoyu, R.mipmap.ic_zhengwuxinxi};
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter != null) {
            mPresenter.initStateUI(mRecyclerView);
        }
        timer = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("数据：" + millisUntilFinished/1000);
                if (activity.isFlagTrue()) {
                    if (mPresenter != null) {
                        mPresenter.getTermArea();
                    }
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                System.out.println("数据：结束");
                mLoadingView.setVisibility(View.GONE);
                mUiManager.show(DefaultStatus.STATUS_SERVER_ERROR);
            }
        }.start();
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
        activity.start(this, GovWebFragment.newInstance("1","1"),
                "GovWebFragment");
    }
}

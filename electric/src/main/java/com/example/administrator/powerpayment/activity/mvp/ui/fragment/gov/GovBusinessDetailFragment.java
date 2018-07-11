package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovBusinessDetailComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovBusinessDetailModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessDetailContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessDetailRes;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovBusinessDetailPresenter;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovBusinessDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovBusinessDetailFragment extends BaseGovFragment<GovBusinessDetailPresenter>
    implements GovBusinessDetailContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;

    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.detail_name)
    TextView mName;
    @BindView(R.id.detail_status)
    TextView mStatus;
    @BindView(R.id.detail_time)
    TextView mTime;
    @BindView(R.id.detail_dl)
    TextView mDetail;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;


    public GovBusinessDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GovBusinessDetailFragment newInstance(String param) {
        GovBusinessDetailFragment fragment = new GovBusinessDetailFragment();
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
    public void success(GovBusinessDetailRes res) {
        mTitle.setText(res.getName());
        mName.setText(res.getArchName());
        mStatus.setText(res.getStatus());
        mTime.setText(res.getUpdatedAt());
        String s = res.getLogs().replaceAll("\\$", "\n");
        mDetail.setText(s);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void fail(String msg) {
        mLoadingView.setVisibility(View.GONE);
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity,msg);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovBusinessDetailComponent.builder()
                .appComponent(appComponent)
                .govBusinessDetailModule(new GovBusinessDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_business_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.getDetail(mParam);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }
}

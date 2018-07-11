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
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovGuideDetailComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovGuideDetailModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideDetailContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideDetailRes;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovGuideDetailPresenter;
import com.jess.arms.di.component.AppComponent;
import com.mingle.widget.LoadingView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovGuideDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovGuideDetailFragment extends BaseGovFragment<GovGuideDetailPresenter>
        implements GovGuideDetailContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;

    @BindView(R.id.detail_title)
    TextView mTitle;
    @BindView(R.id.detail_name)
    TextView mName;
    @BindView(R.id.detail_type)
    TextView mType;
    @BindView(R.id.detail_content)
    HtmlTextView mContent;
    @BindView(R.id.detail_apply)
    HtmlTextView mApply;
    @BindView(R.id.detail_condition)
    HtmlTextView mCondition;
    @BindView(R.id.detail_policy)
    HtmlTextView mPolicy;
    @BindView(R.id.detail_time)
    HtmlTextView mTime;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;

    public GovGuideDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GovGuideDetailFragment newInstance(String param) {
        GovGuideDetailFragment fragment = new GovGuideDetailFragment();
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
        DaggerGovGuideDetailComponent.builder()
                .appComponent(appComponent)
                .govGuideDetailModule(new GovGuideDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_guide_detail, container, false);

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

    @Override
    public void success(GovGuideDetailRes res) {
        mTitle.setText(res.getTitle());
        mName.setText(res.getTitle());
        mType.setText(res.getCategoryName());
        mContent.setHtml(res.getContents(), new HtmlHttpImageGetter(mContent));
        mCondition.setHtml(res.getCondition());
        mPolicy.setHtml(res.getPolicy());
        mApply.setHtml(res.getFileDoc());
        mTime.setHtml(res.getLaw());
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void fail(String msg) {
        mTitle.setText("未知错误");
        mName.setText("暂无");
        mType.setText("暂无");
        mContent.setHtml("<p>暂无</p>");
        mCondition.setHtml("<p>暂无</p>");
        mPolicy.setHtml("<p>暂无</p>");
        mApply.setHtml("<p>暂无</p>");
        mTime.setHtml("<p>暂无</p>");
        mLoadingView.setVisibility(View.GONE);
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity, msg);
    }
}

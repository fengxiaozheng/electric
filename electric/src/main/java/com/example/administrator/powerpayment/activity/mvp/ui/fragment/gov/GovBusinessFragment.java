package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.gov.DaggerGovBusinessComponent;
import com.example.administrator.powerpayment.activity.di.module.gov.GovBusinessModule;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.gov.GovBusinessPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.KeyboardAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GovBusinessFragment extends BaseGovFragment<GovBusinessPresenter>
        implements GovBusinessContract.View, OnItemClickListener{

    @BindView(R.id.keyboard)
    RecyclerView mRecyclerView;
    @BindView(R.id.bs_pass)
    EditText mEditText;
    @BindView(R.id.btn_next)
    Button mButton;
    @Inject
    GridLayoutManager mLayoutManager;
    @Inject
    KeyboardAdapter mAdapter;
    @Inject
    StringBuilder sb;

    public GovBusinessFragment() {
        // Required empty public constructor
    }

    @Override
    public OnItemClickListener getListener() {
        return this;
    }

    @Override
    public void success(List<GovBusinessInfo> data) {
        activity.dismissDialog();
        start(this, GovBusinessListFragment.newInstance(data),
                "GovBusinessListFragment");
    }

    @Override
    public void fail(String msg) {
        activity.dismissDialog();
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity,msg);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGovBusinessComponent.builder()
                .appComponent(appComponent)
                .govBusinessModule(new GovBusinessModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gov_business, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        disableShowInput(mEditText);
        mEditText.requestFocus();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mEditText.addTextChangedListener(watch);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.btn_next)
    void click() {
        String str = mEditText.getText().toString();

        if (TextUtils.isEmpty(str)) {
//            Toast.makeText(activity, getString(R.string.str_068), Toast.LENGTH_SHORT).show();
            ToastUtil.show(activity, getString(R.string.str_068));
            return;
        }
        if (mPresenter != null) {
            activity.showDialog();
            mPresenter.getBusinessContent(str);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                sb.append("1");
                break;
            case 1:
                sb.append("2");
                break;
            case 2:
                sb.append("3");
                break;
            case 3:
                sb.append("4");
                break;
            case 4:
                sb.append("5");
                break;
            case 5:
                sb.append("6");
                break;
            case 6:
                sb.append("7");
                break;
            case 7:
                sb.append("8");
                break;
            case 8:
                sb.append("9");
                break;
            case 9:
                sb.append("0");
                break;
            case 10:
                sb.append("X");
                break;
            case 11:
                if (sb.length() > 0) {
                    sb.delete(sb.length() - 1, sb.length());
                }
                break;
            default:
                break;
        }
        if (sb.length() > 18) {
            sb.delete(18, sb.length());
        }
        mEditText.setText(sb.toString());
    }

    private TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mEditText.setSelection(mEditText.getText().length());
        }
    };

    @Override
    public void onDestroy() {
        fixInputMethodManagerLeak(activity);
        if (mEditText != null) {
            mEditText.removeTextChangedListener(watch);
        }
        super.onDestroy();
    }
}

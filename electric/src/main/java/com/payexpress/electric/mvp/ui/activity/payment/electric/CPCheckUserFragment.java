package com.payexpress.electric.mvp.ui.activity.payment.electric;


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
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.di.component.DaggerCPCheckComponent;
import com.payexpress.electric.di.module.CPCheckModule;
import com.payexpress.electric.mvp.contract.CPCheckContract;
import com.payexpress.electric.mvp.model.entity.payment.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.payment.SmartUserInfo;
import com.payexpress.electric.mvp.presenter.CPCheckPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentActivity;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CPCheckUserFragment extends BaseFragment<CPCheckPresenter, PaymentActivity>
        implements CPCheckContract.View, OnItemClickListener {
    @BindView(R.id.keyboard)
    RecyclerView mRecyclerView;
    @BindView(R.id.cpc_user)
    EditText mEditText;
    @BindView(R.id.btn_next)
    Button mButton;
    @Inject
    GridLayoutManager mLayoutManager;
    @Inject
    KeyboardAdapter mAdapter;
    @Inject
    StringBuilder sb;

    @BindView(R.id.bottom_time)
    TextView bottom_time;
    @BindView(R.id.tv_notice1)
    TextView tv_notice;

    private static final String ARG_PARAM = "param";

    private int mParam;

    public static CPCheckUserFragment newInstance(int param) {
        CPCheckUserFragment fragment = new CPCheckUserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }
    }

    public CPCheckUserFragment() {
        // Required empty public constructor
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
                if (sb.length() > 0) {
                    sb.delete(sb.length() - 1, sb.length());
                }
                break;
            default:
                break;
        }
        mEditText.setText(sb.toString());
    }


    @Override
    public void getUserInfo() {

    }

    @Override
    public OnItemClickListener getListener() {
        return this;
    }

    @Override
    public void success(CPUserInfoRes data) {
        activity.dismissDialog();
        switch (mParam) {
            case 0:
                activity.start(CPCheckUserFragment.this,
                        CPBalanceFragment.newInstance(data.getBalance()), "CPBalanceFragment");
                break;
            case 1:
                activity.start(CPCheckUserFragment.this,
                        CPUserInfoFragment.newInstance(data), "CPUserInfoFragment");
                break;
            default:
                break;
        }
    }


    @Override
    public void fail(String msg) {
        activity.dismissDialog();
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCPCheckComponent.builder()
                .appComponent(appComponent)
                .cPCheckModule(new CPCheckModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cpcheck_user, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        KeyboardUtils.disableShowInput(mEditText);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 10) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        tv_notice.setText(getString(R.string.notice4));

        mEditText.addTextChangedListener(watch);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick(R.id.btn_next)
    void click() {
        switch (mParam) {
            case 0:
                startQuery();
                break;
            case 1:
                startQuery();
                break;
            case 2:
                String str = mEditText.getText().toString();
                if (TextUtils.isEmpty(str) || str.length() < 10) {
                    Toast.makeText(activity, "请输入十位用户号", Toast.LENGTH_SHORT).show();
                } else {
                    activity.start(CPCheckUserFragment.this,
                            CPRecordFragment.newInstance(false, str), "CPRecordFragment");
                }
                break;
            case 3:
                SmartUserInfo info = new SmartUserInfo();
                info.setFlag(1);
                activity.start(CPCheckUserFragment.this,
                        SPUserInfoFragment.newInstance(info), "SPUserInfoFragment");
                break;
            default:
                break;
        }
    }

    private void startQuery() {
        String str = mEditText.getText().toString();
        if (TextUtils.isEmpty(str) || str.length() < 10) {
            Toast.makeText(activity, "请输入十位用户号", Toast.LENGTH_SHORT).show();
        } else {
            if (mPresenter != null) {
                activity.showDialog();
                mPresenter.getUserInfo(str);
            }
        }
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
}

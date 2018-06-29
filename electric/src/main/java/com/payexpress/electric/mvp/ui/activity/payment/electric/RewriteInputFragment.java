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

import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.app.utils.Psamcmd;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.di.component.payment.DaggerRewriteInputComponent;
import com.payexpress.electric.di.module.payment.RewriteInputModule;
import com.payexpress.electric.mvp.contract.payment.RewriteInputContract;
import com.payexpress.electric.mvp.model.api.Api;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesAllParams;
import com.payexpress.electric.mvp.presenter.payment.RewriteInputPresenter;
import com.payexpress.electric.mvp.ui.activity.payment.BasePaymentFragment;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteInputFragment extends BasePaymentFragment<RewriteInputPresenter>
        implements RewriteInputContract.View, OnItemClickListener {

    @BindView(R.id.btn_next)
    Button mButton;
    @BindView(R.id.rewrite_input)
    EditText mEditText;
    @BindView(R.id.keyboard)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_notice1)
    TextView tv_notice;
    @Inject
    GridLayoutManager mLayoutManager;
    @Inject
    KeyboardAdapter mAdapter;
    @Inject
    StringBuilder sb;

    private boolean isFirst = false;


    public RewriteInputFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RewriteInputFragment newInstance() {

        return new RewriteInputFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRewriteInputComponent.builder()
                .appComponent(appComponent)
                .rewriteInputModule(new RewriteInputModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewrite_input, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isFirst = true;
        KeyboardUtils.disableShowInput(mEditText);
        mEditText.requestFocus();
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

        tv_notice.setText(getString(R.string.notice6));

        mEditText.addTextChangedListener(watch);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick(R.id.btn_next)
    void click() {
        String str = mEditText.getText().toString();
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(activity, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!str.equals(StringUtils.getConfig(activity, Api.termPsd))) {
            Toast.makeText(activity, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
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
            mPresenter.readCard();
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
                if (sb.length() > 0) {
                    sb.delete(sb.length() - 1, sb.length());
                }
                break;
            default:
                break;
        }
        if (sb.length() > 10) {
            sb.delete(10, sb.length());
        }
        mEditText.setText(sb.toString());
    }

    @Override
    public void success(RewriteTimesAllParams data) {
        start(RewriteInputFragment.this, RewriteTimesFragment.newInstance(data),
                "RewriteTimesFragment");
    }

    @Override
    public void passwordError() {

    }

    @Override
    public void readCardError() {

    }

    @Override
    public OnItemClickListener getListener() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

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
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.setText("");
            sb.delete(0, sb.length());
        }
    }
}

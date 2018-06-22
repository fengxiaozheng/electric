package com.payexpress.electric.mvp.ui.activity.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.di.component.gov.DaggerGovBusinessComponent;
import com.payexpress.electric.di.module.gov.GovBusinessModule;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract;
import com.payexpress.electric.mvp.presenter.gov.GovBusinessPresenter;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        KeyboardUtils.disableShowInput(mEditText);
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
        Pattern p = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)");
        Matcher matcher = p.matcher(str);
        if (!matcher.matches()) {
            Toast.makeText(activity, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
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
}

package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;


import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.di.component.payment.DaggerCPCheckComponent;
import com.example.administrator.powerpayment.activity.di.module.payment.CPCheckModule;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.CPCheckContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartUserInfo;
import com.example.administrator.powerpayment.activity.mvp.presenter.payment.CPCheckPresenter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.KeyboardAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.BasePaymentFragment;
import com.jess.arms.di.component.AppComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CPCheckUserFragment extends BasePaymentFragment<CPCheckPresenter>
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

    @BindView(R.id.tv_notice1)
    TextView tv_notice;

    private static final String ARG_PARAM = "param";

    private int mParam;//0:普通电费余额查询；1：普通电力缴费；2：购电记录查询；

    //3：智能无卡购电；4：电卡余额查询
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
        if (sb.length() > 10) {
            sb.delete(10, sb.length());
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
                start(CPCheckUserFragment.this,
                        CPBalanceFragment.newInstance(data.getBalance(), false), "CPBalanceFragment");
                break;
            case 1:
                start(CPCheckUserFragment.this,
                        CPUserInfoFragment.newInstance(data), "CPUserInfoFragment");
                break;
            case 3:
                SmartUserInfo info = new SmartUserInfo();
                info.setFlag(1);
                info.setBalance(data.getBalance());
                info.setUserAddress(data.getAddress());
                info.setUserName(data.getGrid_user_name());
                info.setUserNo(data.getGrid_user_code());
                start(CPCheckUserFragment.this,
                        SPUserInfoFragment.newInstance(info), "SPUserInfoFragment");
                break;
            case 4:
                start(CPCheckUserFragment.this,
                        CPBalanceFragment.newInstance(data.getBalance(), true), "CPBalanceFragment");
                break;
            default:
                break;
        }
    }


    @Override
    public void fail(String msg) {
        activity.dismissDialog();
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        ToastUtil.show(activity,msg);
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
        disableShowInput(mEditText);
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
        String str = mEditText.getText().toString();
        if (TextUtils.isEmpty(str) || str.length() < 10) {
//            Toast.makeText(activity, "请输入十位用户号", Toast.LENGTH_SHORT).show();
            ToastUtil.show(activity, "请输入十位用户号");
            return;
        }
        switch (mParam) {
            case 0:
                activity.showDialog("查询中...");
                startQuery(str);
                break;
            case 1:
                activity.showDialog("查询中...");
                startQuery(str);
                break;
            case 2:
                start(CPCheckUserFragment.this,
                        CPRecordFragment.newInstance(false, str), "CPRecordFragment");
                break;
            case 3:
                if (mPresenter != null) {
                    activity.showDialog("查询中...");
                    mPresenter.noCardCheck(str);
                }
                break;
            case 4:
                if (mPresenter != null) {
                    activity.showDialog();
                    mPresenter.getBalance(str);
                }
                break;
            default:
                break;
        }
    }

    private void startQuery(String str) {

        if (mPresenter != null) {
            activity.showDialog();
            mPresenter.getUserInfo(str);
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

    private void disableShowInput(EditText editText) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
        try {
            method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {//TODO: handle exception
        }
    }

    @Override
    public void onDestroy() {
        fixInputMethodManagerLeak(activity);
        super.onDestroy();
    }

    private void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}

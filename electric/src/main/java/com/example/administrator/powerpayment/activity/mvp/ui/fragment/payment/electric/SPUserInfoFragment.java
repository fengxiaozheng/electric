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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.KeyboardUtils;
import com.example.administrator.powerpayment.activity.app.utils.ToastUtil;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.ElectricPayInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartUserInfo;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.KeyboardAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPUserInfoFragment extends PaymentFragment implements
        OnItemClickListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private SmartUserInfo mParam;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private KeyboardAdapter mAdapter;
    private EditText mTelphone;
    private EditText mAmount;
    private Button mButton;
    private LinearLayout mLinearLayout;
    private LinearLayout ll_balance;
    private TextView mUserNo;
    private TextView mUserName;
    private TextView mAddress;
    private TextView mBalance;
    private StringBuilder telBuilder;
    private StringBuilder amountBuilder;
    private DecimalFormat format;


    public SPUserInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SPUserInfoFragment newInstance(SmartUserInfo info) {
        SPUserInfoFragment fragment = new SPUserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (SmartUserInfo) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spuser_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.keyboard);
        mAmount = view.findViewById(R.id.sp_user_input);
        mTelphone = view.findViewById(R.id.sp_user_tel);
        mButton = view.findViewById(R.id.sp_next);
        mLinearLayout = view.findViewById(R.id.input_phone);
        mUserNo = view.findViewById(R.id.sp_user_no);
        mUserName = view.findViewById(R.id.sp_user_name);
        mAddress = view.findViewById(R.id.sp_user_address);
        mBalance = view.findViewById(R.id.sp_user_balance);
        ll_balance = view.findViewById(R.id.ll_balance);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mAdapter = new KeyboardAdapter(KeyboardUtils.withPoint(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mParam.getFlag() == 1) {
            mLinearLayout.setVisibility(View.VISIBLE);
            mTelphone.requestFocus();
        } else {
            mAmount.requestFocus();
        }
        if (mParam.getFlag() == 2) {
            ll_balance.setVisibility(View.INVISIBLE);
        }
        telBuilder = new StringBuilder();
        amountBuilder = new StringBuilder();
        format = new DecimalFormat("0.00");
        disableShowInput(mTelphone);
        disableShowInput(mAmount);
        mTelphone.addTextChangedListener(watch);
        mAmount.addTextChangedListener(watch);
        mButton.setOnClickListener(this);
        mUserNo.setText(mParam.getUserNo());
        mUserName.setText(mParam.getUserName());
        mAddress.setText(mParam.getUserAddress());
        if ("未采集".equals(mParam.getBalance())) {
            ll_balance.setVisibility(View.GONE);
        } else {
            mBalance.setText(mParam.getBalance());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mTelphone.isFocused()) {
            switch (position) {
                case 0:
                    telBuilder.append("1");
                    break;
                case 1:
                    telBuilder.append("2");
                    break;
                case 2:
                    telBuilder.append("3");
                    break;
                case 3:
                    telBuilder.append("4");
                    break;
                case 4:
                    telBuilder.append("5");
                    break;
                case 5:
                    telBuilder.append("6");
                    break;
                case 6:
                    telBuilder.append("7");
                    break;
                case 7:
                    telBuilder.append("8");
                    break;
                case 8:
                    telBuilder.append("9");
                    break;
                case 9:
                    telBuilder.append("0");
                    break;
                case 10:
                    telBuilder.append(".");
                    break;
                case 11:
                    if (telBuilder.length() > 0) {
                        telBuilder.delete(telBuilder.length() - 1, telBuilder.length());
                    }
                    break;
                default:
                    break;
            }
            if (telBuilder.length() > 11) {
                telBuilder.delete(11, telBuilder.length());
            }
            mTelphone.setText(telBuilder.toString());
        } else {
            switch (position) {
                case 0:
                    amountBuilder.append("1");
                    break;
                case 1:
                    amountBuilder.append("2");
                    break;
                case 2:
                    amountBuilder.append("3");
                    break;
                case 3:
                    amountBuilder.append("4");
                    break;
                case 4:
                    amountBuilder.append("5");
                    break;
                case 5:
                    amountBuilder.append("6");
                    break;
                case 6:
                    amountBuilder.append("7");
                    break;
                case 7:
                    amountBuilder.append("8");
                    break;
                case 8:
                    telBuilder.append("9");
                    break;
                case 9:
                    amountBuilder.append("0");
                    break;
                case 10:
                    amountBuilder.append(".");
                    break;
                case 11:
                    if (amountBuilder.length() > 0) {
                        amountBuilder.delete(amountBuilder.length() - 1, amountBuilder.length());
                    }
                    break;
                default:
                    break;
            }
            if (amountBuilder.length() > 8) {
                amountBuilder.delete(8, amountBuilder.length());
            }
            mAmount.setText(amountBuilder.toString());
        }
    }

    @Override
    public void onClick(View v) {
        String phone = mTelphone.getText().toString();
        if (mParam.getFlag() == 1) {
            String regex = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);
            if (!matcher.matches()) {
//                Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                ToastUtil.show(activity,"请输入正确的手机号码");
                return;
            }
        }
        String amount = mAmount.getText().toString();
        if (TextUtils.isEmpty(amount) ||
                Double.parseDouble(amount) == 0) {
//            Toast.makeText(activity, "请输入正确的金额", Toast.LENGTH_SHORT).show();
            ToastUtil.show(activity,"请输入正确的金额");
            return;
        }
        ElectricPayInfo info = new ElectricPayInfo();
        info.setFlag(mParam.getFlag());
        info.setPay_amount(format.format(Double.parseDouble(amount)));
        info.setUser_address(mParam.getUserAddress());
        info.setUser_name(mParam.getUserName());
        info.setUser_no(mParam.getUserNo());
        info.setTelPhone(phone);
        start(SPUserInfoFragment.this, StartPayFragment.newInstance(info),
                "StartPayFragment");
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
            if (mTelphone.isFocused()) {
                mTelphone.setSelection(mTelphone.getText().length());
            } else {
                mAmount.setSelection(mAmount.getText().length());
            }
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

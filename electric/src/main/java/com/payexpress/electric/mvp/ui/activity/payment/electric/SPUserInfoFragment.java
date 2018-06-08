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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.model.entity.payment.ElectricPayInfo;
import com.payexpress.electric.mvp.model.entity.payment.SmartUserInfo;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

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
        KeyboardUtils.disableShowInput(mTelphone);
        KeyboardUtils.disableShowInput(mAmount);
        mTelphone.addTextChangedListener(watch);
        mAmount.addTextChangedListener(watch);
        mButton.setOnClickListener(this);
        mUserNo.setText(mParam.getUserNo());
        mUserName.setText(mParam.getUserName());
        mAddress.setText(mParam.getUserAddress());
        mBalance.setText(mParam.getBalance());
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
                Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String amount = mAmount.getText().toString();
        if (TextUtils.isEmpty(amount) ||
                Double.parseDouble(amount) == 0) {
            Toast.makeText(activity, "请输入正确的金额", Toast.LENGTH_SHORT).show();
            return;
        }
        ElectricPayInfo info = new ElectricPayInfo();
        info.setFlag(mParam.getFlag());
        info.setPay_amount(format.format(Double.parseDouble(amount)));
        info.setUser_address(mParam.getUserAddress());
        info.setUser_name(mParam.getUserName());
        info.setUser_no(mParam.getUserNo());
        info.setTelPhone(phone);
        activity.start(SPUserInfoFragment.this, StartPayFragment.newInstance(info),
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
            }else {
                mAmount.setSelection(mAmount.getText().length());
            }
        }
    };

}

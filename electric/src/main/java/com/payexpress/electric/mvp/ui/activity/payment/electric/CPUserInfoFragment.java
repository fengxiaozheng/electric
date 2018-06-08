package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.model.entity.payment.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.payment.ElectricPayInfo;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import java.text.DecimalFormat;


public class CPUserInfoFragment extends PaymentFragment implements
        OnItemClickListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private CPUserInfoRes mParam;



    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private KeyboardAdapter mAdapter;
    private EditText mEditText;
    private Button mButton;
    private TextView cp_user_name;
    private TextView cp_user_no;
    private TextView cp_user_should;
    private TextView cp_user_address;
    private TextView cp_user_balance;
    private TextView tv_notice;
    private StringBuilder sb;
    private DecimalFormat format;

    public CPUserInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CPUserInfoFragment newInstance(CPUserInfoRes info) {
        CPUserInfoFragment fragment = new CPUserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (CPUserInfoRes) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpuser_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.keyboard);
        mEditText = view.findViewById(R.id.cp_user_input);
        mButton = view.findViewById(R.id.cp_next);
        cp_user_address = view.findViewById(R.id.cp_user_address);
        cp_user_balance = view.findViewById(R.id.cp_user_balance);
        cp_user_name = view.findViewById(R.id.cp_user_name);
        cp_user_no = view.findViewById(R.id.cp_user_no);
        cp_user_should = view.findViewById(R.id.cp_user_should);
        tv_notice = view.findViewById(R.id.tv_notice1);
        KeyboardUtils.disableShowInput(mEditText);
        mEditText.requestFocus();
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mAdapter = new KeyboardAdapter(KeyboardUtils.withPoint(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        sb = new StringBuilder();
        format = new DecimalFormat("0.00");
        mEditText.addTextChangedListener(watch);
        cp_user_name.setText(mParam.getGrid_user_name());
        cp_user_no.setText(mParam.getGrid_user_code());
        cp_user_address.setText(mParam.getAddress());
        cp_user_should.setText(mParam.getPayable_amount());
        cp_user_balance.setText(mParam.getBalance());
        tv_notice.setText(getString(R.string.notice3));
        mButton.setOnClickListener(this);
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
                sb.append(".");
                break;
            case 11:
                if (sb.length() > 0) {
                    sb.delete(sb.length() - 1, sb.length());
                }
                break;
            default:
                break;
        }
        if (sb.length() > 8) {
            sb.delete(8, sb.length());
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
    public void onClick(View v) {
        if (v.getId() == R.id.cp_next) {
            String amount = mEditText.getText().toString();
            if (TextUtils.isEmpty(amount)){
                Toast.makeText(activity, "请输入金额", Toast.LENGTH_SHORT).show();
            }else {
                ElectricPayInfo info = new ElectricPayInfo();
                info.setFlag(0);
                info.setUser_no(mParam.getGrid_user_code());
                info.setUser_name(mParam.getGrid_user_name());
                info.setUser_address(mParam.getAddress());
                info.setPay_amount(format.format(Double.parseDouble(amount)));
                activity.start(CPUserInfoFragment.this,
                        StartPayFragment.newInstance(info), "StartPayFragment");
            }
        }
    }

}

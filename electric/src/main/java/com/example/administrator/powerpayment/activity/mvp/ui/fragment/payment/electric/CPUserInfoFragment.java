package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.app.utils.KeyboardUtils;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.ElectricPayInfo;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.KeyboardAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        disableShowInput(mEditText);
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
            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(activity, "请输入金额", Toast.LENGTH_SHORT).show();
            } else {
                ElectricPayInfo info = new ElectricPayInfo();
                info.setFlag(0);
                info.setUser_no(mParam.getGrid_user_code());
                info.setUser_name(mParam.getGrid_user_name());
                info.setUser_address(mParam.getAddress());
                info.setPay_amount(format.format(Double.parseDouble(amount)));
                start(CPUserInfoFragment.this,
                        StartPayFragment.newInstance(info), "StartPayFragment");
            }
        }
    }

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

package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.payment.ElectricPayInfo;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayResultFragment extends PaymentFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private ElectricPayInfo mParam;

    private TextView pr_user_no;
    private TextView pr_user_name;
    private TextView pr_user_address;
    private TextView pr_pay_money;
    private Button btn_complete;


    public PayResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PayResultFragment newInstance(ElectricPayInfo info) {
        PayResultFragment fragment = new PayResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (ElectricPayInfo) getArguments().getSerializable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pr_user_no = view.findViewById(R.id.pr_user_no);
        pr_user_name = view.findViewById(R.id.pr_user_name);
        pr_user_address = view.findViewById(R.id.pr_user_address);
        pr_pay_money = view.findViewById(R.id.pr_pay_money);
        btn_complete = view.findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(this);
        pr_user_no.setText(mParam.getUser_no());
        pr_user_name.setText(mParam.getUser_name());
        pr_user_address.setText(mParam.getUser_address());
        pr_pay_money.setText(mParam.getPay_amount());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_complete) {
            activity.complete();
        }
    }
}

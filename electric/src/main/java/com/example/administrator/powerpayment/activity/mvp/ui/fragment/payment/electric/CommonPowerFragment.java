package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonPowerFragment extends PaymentFragment implements View.OnClickListener {

    private LinearLayout c_pay;
    private LinearLayout c_balance;
    private LinearLayout c_check;

    public CommonPowerFragment() {
        // Required empty public constructor
    }

    public static CommonPowerFragment newInstance() {
        return new CommonPowerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        c_pay = view.findViewById(R.id.c_pay);
        c_balance = view.findViewById(R.id.c_balance);
        c_check = view.findViewById(R.id.c_check);
        c_pay.setOnClickListener(this);
        c_balance.setOnClickListener(this);
        c_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.c_pay:
                next(1);
                break;
            case R.id.c_balance:
                next(0);
                break;
            case R.id.c_check:
                next(2);
                break;
            default:
                break;
        }
    }

    private void next(int flag) {
        start(CommonPowerFragment.this, CPCheckUserFragment.newInstance(flag),
                "CPCheckUserFragment");
    }
}

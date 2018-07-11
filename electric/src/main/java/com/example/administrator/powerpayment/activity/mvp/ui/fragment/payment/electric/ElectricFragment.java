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
public class ElectricFragment extends PaymentFragment implements View.OnClickListener {

    private LinearLayout e_smart;
    private LinearLayout e_common;
    private LinearLayout e_rewrite;

    public ElectricFragment() {
        // Required empty public constructor
    }

    public static ElectricFragment newInstance() {
        return new ElectricFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electric_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        e_smart = view.findViewById(R.id.e_smart);
        e_common = view.findViewById(R.id.e_common);
        e_rewrite = view.findViewById(R.id.e_bx);
        e_smart.setOnClickListener(this);
        e_common.setOnClickListener(this);
        e_rewrite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.e_smart:
                start(ElectricFragment.this, SmartPowerFragment.newInstance(),
                        "SmartPowerFragment");
                break;
            case R.id.e_common:
                start(ElectricFragment.this, CommonPowerFragment.newInstance(),
                        "CommonPowerFragment");
                break;
            case R.id.e_bx:
                start(ElectricFragment.this, RewriteCardFragment.newInstance(),
                        "RewriteCardFragment");
            default:
                break;

        }
    }

}

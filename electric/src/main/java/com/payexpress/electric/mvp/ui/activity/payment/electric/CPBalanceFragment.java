package com.payexpress.electric.mvp.ui.activity.payment.electric;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;


public class CPBalanceFragment extends PaymentFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;
    private TextView mTextView;
    private Button mButton;


    public CPBalanceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CPBalanceFragment newInstance(String param) {
        CPBalanceFragment fragment = new CPBalanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cpbalance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = view.findViewById(R.id.cp_query_balance);
        mButton = view.findViewById(R.id.cp_query_ok);
        mButton.setOnClickListener(this);
        mTextView.setText(mParam);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cp_query_ok) {
            activity.complete();
        }
    }
}
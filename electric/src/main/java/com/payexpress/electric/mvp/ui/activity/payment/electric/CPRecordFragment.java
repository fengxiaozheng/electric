package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.payexpress.electric.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CPRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CPRecordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private boolean mParam;
    private Button mToPay;


    public CPRecordFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CPRecordFragment newInstance(boolean param) {
        CPRecordFragment fragment = new CPRecordFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getBoolean(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cprecord, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToPay = view.findViewById(R.id.btn_to_pay);
        if (mParam) {
            mToPay.setVisibility(View.VISIBLE);
        }
    }
}

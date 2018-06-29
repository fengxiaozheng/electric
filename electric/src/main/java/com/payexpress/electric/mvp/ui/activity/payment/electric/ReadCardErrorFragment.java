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
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadCardErrorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadCardErrorFragment extends PaymentFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mParam;

    private TextView mTextView;
    private Button mButton;


    public ReadCardErrorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReadCardErrorFragment newInstance(String param) {
        ReadCardErrorFragment fragment = new ReadCardErrorFragment();
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
        return inflater.inflate(R.layout.fragment_read_card_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = view.findViewById(R.id.fail_reason);
        mButton = view.findViewById(R.id.btn_confirm);
        mTextView.setText(mParam);
        mButton.setOnClickListener(v -> sComplete());
    }
}

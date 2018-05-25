package com.payexpress.electric.mvp.ui.activity.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.payexpress.electric.R;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.model.entity.payment.SmartUserInfo;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentFragment;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SPUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPUserInfoFragment extends PaymentFragment implements OnItemClickListener {
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
        mAmount = view.findViewById(R.id.cp_user_input);
        mTelphone = view.findViewById(R.id.cp_user_tel);
        mButton = view.findViewById(R.id.cp_next);
        mLinearLayout = view.findViewById(R.id.input_phone);
        mAmount.setInputType(InputType.TYPE_NULL);
        mTelphone.setInputType(InputType.TYPE_NULL);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mAdapter = new KeyboardAdapter(KeyboardUtils.withPoint(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (mParam.getFlag() == 1) {
            mLinearLayout.setVisibility(View.VISIBLE);
        }
        mButton.setOnClickListener(v -> activity.start(SPUserInfoFragment.this,
                new PayResultFragment(), "PayResultFragment"));
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}

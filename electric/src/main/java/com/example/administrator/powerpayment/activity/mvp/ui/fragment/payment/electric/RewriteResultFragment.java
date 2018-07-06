package com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewriteResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewriteResultFragment extends PaymentFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private boolean mParam;

    private TextView mResult;
    private TextView mTell;
    private Button mButton;
    private ImageView mImageView;


    public RewriteResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RewriteResultFragment newInstance(boolean param) {
        RewriteResultFragment fragment = new RewriteResultFragment();
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
        return inflater.inflate(R.layout.fragment_rewrite_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResult = view.findViewById(R.id.rewrite_result);
        mTell = view.findViewById(R.id.rewrite_tell);
        mButton = view.findViewById(R.id.rr_confirm);
        mImageView = view.findViewById(R.id.rewrite_img);
        if (mParam) {
            mImageView.setImageResource(R.mipmap.img_success);
            mResult.setText("末笔补写成功");
            mTell.setText("请拔出卡片并收好您的卡！");
        } else {
            mImageView.setImageResource(R.mipmap.img_fail);
            mResult.setText("末笔补写失败");
            mTell.setText("请重新插卡，进行再次补写！");
        }
        mButton.setOnClickListener(v -> back());
    }
}

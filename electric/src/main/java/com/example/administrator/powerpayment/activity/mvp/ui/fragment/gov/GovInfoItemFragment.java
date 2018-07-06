package com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.powerpayment.activity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GovInfoItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GovInfoItemFragment extends GovFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout g_zn;
    private LinearLayout g_zx;
    private LinearLayout g_bl;
    private LinearLayout g_yc;


    public GovInfoItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GovInfoItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GovInfoItemFragment newInstance(String param1, String param2) {
        GovInfoItemFragment fragment = new GovInfoItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gov_info_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        g_zn = view.findViewById(R.id.g_zn);
        g_zx = view.findViewById(R.id.g_zx);
        g_bl = view.findViewById(R.id.g_bl);
        g_yc = view.findViewById(R.id.g_yc);
        g_zn.setOnClickListener(this);
        g_zx.setOnClickListener(this);
        g_bl.setOnClickListener(this);
        g_yc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.g_zn:
                start(this, GovGuideFragment.newInstance("0"), "GovGuideFragment");
                break;
            case R.id.g_zx:
                start(this, new GovLocationFragment(), "GovLocationFragment");
                break;
            case R.id.g_bl:
                start(this, new GovBusinessFragment(), "GovBusinessFragment");
                break;
            case R.id.g_yc:
                start(this, GovGuideFragment.newInstance("1"), "GovGuideFragment");
                break;
            default:
                break;
        }
    }
}

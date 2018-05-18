package com.payexpress.electric.mvp.ui.activity.payment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.ui.activity.payment.electric.ElectricFragment;
import com.payexpress.electric.mvp.ui.adapter.GovAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentMainFragment extends PaymentFragment implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private GovAdapter mAdapter;

    public PaymentMainFragment() {
        // Required empty public constructor
    }

    public static PaymentMainFragment newInstance() {
        return new PaymentMainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_py_ment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] titles = {getString(R.string.str_014), getString(R.string.str_015),
                getString(R.string.str_016), getString(R.string.str_017),
                getString(R.string.str_018), getString(R.string.str_019),
                getString(R.string.str_020), getString(R.string.str_021)};
        int[] imgs = {R.mipmap.ic_shuifei, R.mipmap.ic_dianfei, R.mipmap.ic_ranqifei,
                R.mipmap.ic_tongxun, R.mipmap.ic_jiaojing, R.mipmap.ic_guangdian,
                R.mipmap.ic_jiayouka, R.mipmap.ic_baoxian};
        mRecyclerView = view.findViewById(R.id.gov_rv);
        mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GovAdapter(titles, imgs, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case R.mipmap.ic_dianfei:
               activity.start(PaymentMainFragment.this, ElectricFragment.newInstance(),
                       "ElectricFragment");
                break;
            default:
                break;
        }
    }
}

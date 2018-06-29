package com.payexpress.electric.mvp.ui.activity.gov;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payexpress.electric.R;
import com.payexpress.electric.mvp.model.entity.govEntity.GovBusinessInfo;
import com.payexpress.electric.mvp.ui.adapter.GovBusinessListAdapter;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GovBusinessListFragment extends GovFragment implements
        GovBusinessListAdapter.OnBusinessListItemClickListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters
    private List<GovBusinessInfo> mParam;
    private List<GovBusinessInfo> infos;

    private RecyclerView mRecyclerView;
    private LinearLayout mPageControl;
    private TextView mPageNum;
    private Button mPageStart;
    private Button mPageLast;
    private Button mPageNext;
    private Button mPageBack;

    private RecyclerView.LayoutManager mLayoutManager;
    private GovBusinessListAdapter mAdapter;
    private StatusUIManager mUiManager;
    private int pageIndex = 1;
    private int pageSum;

    public GovBusinessListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GovBusinessListFragment newInstance(List<GovBusinessInfo> param) {
        GovBusinessListFragment fragment = new GovBusinessListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, (Serializable) param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = (List<GovBusinessInfo>) getArguments().getSerializable(ARG_PARAM);
            if (mParam != null) {
                pageSum = (int) Math.ceil((double) mParam.size() / 5);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gov_business_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.gbl_recyclerView);
        mPageControl = view.findViewById(R.id.page_control);
        mPageNum = view.findViewById(R.id.page_num);
        mPageLast = view.findViewById(R.id.page_last);
        mPageNext = view.findViewById(R.id.page_next);
        mPageBack = view.findViewById(R.id.page_back);
        mPageStart = view.findViewById(R.id.page_start);
        infos = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(activity);
        mAdapter = new GovBusinessListAdapter(infos);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mPageControl.setOnClickListener(this);
        mPageStart.setOnClickListener(this);
        mPageBack.setOnClickListener(this);
        mPageNext.setOnClickListener(this);
        mPageLast.setOnClickListener(this);
        mUiManager = new StatusUIManager();
        initView();
        initData();
    }

    private void initData() {
        int size = mParam.size();
        if (size <= 0) {
            mUiManager.show(DefaultStatus.STATUS_EMPTY);
            mPageControl.setVisibility(View.GONE);
        } else if (size <= 5) {
            infos.addAll(mParam);
            mAdapter.notifyDataSetChanged();
            mPageStart.setEnabled(false);
            mPageLast.setEnabled(false);
            mPageNext.setEnabled(false);
        } else {
            for (int i = 0; i < 5; i++) {
                infos.add(mParam.get(i));
            }
            mAdapter.notifyDataSetChanged();
            mPageStart.setEnabled(false);
            mPageLast.setEnabled(false);
            mPageNext.setEnabled(true);
        }
        mPageNum.setText(String.format("第1页/共%s页", pageSum));
    }

    private void initView() {
        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultEmptyStatusView(activity,
                        DefaultStatus.STATUS_EMPTY,
                        mRecyclerView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    @Override
    public void onItemClick(View view, GovBusinessInfo info) {
        start(this, GovBusinessDetailFragment.newInstance(info.getId()),
                "GovBusinessDetailFragment");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        infos.clear();
        switch (v.getId()) {
            case R.id.page_start:
                for (int i = 0; i < 5; i++) {
                    infos.add(mParam.get(i));
                }
                mAdapter.notifyDataSetChanged();
                mPageStart.setEnabled(false);
                mPageLast.setEnabled(false);
                mPageNext.setEnabled(true);
                mPageNum.setText(String.format("第1页/共%s页", pageSum));
                break;
            case R.id.page_last:
                pageIndex--;
                if (pageIndex == 1) {
                    mPageStart.setEnabled(false);
                    mPageLast.setEnabled(false);
                    mPageNext.setEnabled(true);
                } else {
                    mPageStart.setEnabled(true);
                    mPageLast.setEnabled(true);
                    mPageNext.setEnabled(true);
                }
                for (int k = 0; k < 5; k++) {
                    infos.add(mParam.get(pageIndex + 4 * (pageIndex - 1) - 1));
                }
                mAdapter.notifyDataSetChanged();
                mPageNum.setText(String.format("第%d页/共%s页", pageIndex, pageSum));
                break;
            case R.id.page_next:
                pageIndex++;
                if (pageIndex == pageSum) {
                    mPageStart.setEnabled(true);
                    mPageLast.setEnabled(true);
                    mPageNext.setEnabled(false);
                } else {
                    mPageStart.setEnabled(true);
                    mPageLast.setEnabled(true);
                    mPageNext.setEnabled(true);
                }
                for (int j = 0; j < 5; j++) {
                    infos.add(mParam.get(pageIndex + 4 * (pageIndex - 1) - 1));
                }
                mAdapter.notifyDataSetChanged();
                mPageNum.setText(String.format("第%d页/共%s页", pageIndex, pageSum));
                break;
            case R.id.page_back:
                back();
                break;
            default:
                break;
        }
    }
}

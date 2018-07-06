package com.example.administrator.powerpayment.activity.mvp.ui.adapter;

import android.view.View;

import com.example.administrator.powerpayment.activity.mvp.model.entity.MainFragmentItemInfo;

/**
 * Created by fengxiaozheng
 * on 2018/6/15.
 */

public interface MainFragmentClickListener {

    void onItemClick(View view, MainFragmentItemInfo info);
}

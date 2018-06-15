package com.payexpress.electric.mvp.ui.adapter;

import android.view.View;

import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;

/**
 * Created by fengxiaozheng
 * on 2018/6/15.
 */

public interface MainFragmentClickListener {

    void onItemClick(View view, MainFragmentItemInfo info);
}

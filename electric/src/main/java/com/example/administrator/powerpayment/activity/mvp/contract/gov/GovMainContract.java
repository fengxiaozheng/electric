package com.example.administrator.powerpayment.activity.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mingle.widget.LoadingView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovMainRes;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.MainFragmentClickListener;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public interface GovMainContract {

    interface View extends IView {
        MainFragmentClickListener getListener();
        LoadingView getLoadingView();
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<GovMainRes> getGovMainFragmentItem();
    }
}

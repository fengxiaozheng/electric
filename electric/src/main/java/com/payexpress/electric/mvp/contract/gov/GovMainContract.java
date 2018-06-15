package com.payexpress.electric.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mingle.widget.LoadingView;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

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

package com.payexpress.electric.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */

public interface GovBusinessContract {

    interface View extends IView {
        Activity getActivity();
        OnItemClickListener getListener();
    }

    interface Model extends IModel {
        Observable<BaseGovResponse> getGovBusiness(String keyWord);
    }
}

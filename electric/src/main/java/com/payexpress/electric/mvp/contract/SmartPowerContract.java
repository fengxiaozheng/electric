package com.payexpress.electric.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.payment.QuerySmartCardReq;
import com.payexpress.electric.mvp.model.entity.payment.QuerySmartCardRes;

import io.reactivex.Observable;


/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */

public interface SmartPowerContract {

    interface View extends IView {
        void success(QuerySmartCardRes res);
        void fail(String msg);
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<QuerySmartCardRes> querySmartCardInfo(QuerySmartCardReq req);
    }
}

package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteInputRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesAllParams;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */

public interface RewriteInputContract {

    interface View extends IView {
        void success(RewriteTimesAllParams data);
        void passwordError();
        void readCardError();
        OnItemClickListener getListener();
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<RewriteInputRes> getTermPassword();
    }
}

package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardListRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesRes;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */

public interface RewriteTimesContract {

    interface View extends IView {
        void success(RewriteCardListRes res);
        void fail(String msg);
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<RewriteCardListRes> getRewriteData(String req);
        Observable<RewriteTimesRes> startRewriteTimes(String req);
    }
}

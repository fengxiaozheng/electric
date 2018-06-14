package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardRes;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/5.
 */

public interface RewriteCardContract {

    interface View extends IView {
        void success();
        void fail(String msg);
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<RewriteCardRes> rewriteCard(RewriteCardReq req);
    }
}

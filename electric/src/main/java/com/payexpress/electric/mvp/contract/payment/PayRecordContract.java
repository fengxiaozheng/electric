package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PayRecordRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.UserNoReq;

import io.reactivex.Observable;


/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */

public interface PayRecordContract {

    interface PayRecordView extends IView{
        void success(PayRecordRes res);
        void fail(String msg);
        Activity getActivity();
    }

    interface PayRecordModel extends IModel {
        Observable<PayRecordRes> getPayRecord(UserNoReq req);
    }
}

package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.UserNoReq;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */

public interface CPCheckContract {

    interface View extends IView {
        void getUserInfo();
        Activity getActivity();
        OnItemClickListener getListener();
        void success(CPUserInfoRes data);
        void fail(String msg);
    }

    interface Model extends IModel {
        Observable<CPUserInfoRes> getUserInfo(String userNo);
        Observable<NoCardCheckRes> noCardCheck(NoCardCheckReq req);
        Observable<CardBalanceRes> queryCardBalance(UserNoReq req);
    }
}

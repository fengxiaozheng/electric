package com.payexpress.electric.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.payment.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.payment.CodePayReq;
import com.payexpress.electric.mvp.model.entity.payment.CodePayRes;
import com.payexpress.electric.mvp.model.entity.payment.TransNoReq;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public interface ElectricPayContract {

    interface PayView extends IView {
        void success();
        void fail(String msg);
        void checkPay(int flag, String transNo);
        Activity getActivity();
    }

    interface PayModel extends IModel {
        Observable<CodePayRes> doPay(CodePayReq req);
        Observable<CheckPayRes> checkPayStatus(TransNoReq req);
        Observable<BaseResponse> commonElectricBuy(TransNoReq req);
    }
 }

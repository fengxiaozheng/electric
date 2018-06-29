package com.payexpress.electric.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mingle.widget.LoadingView;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PaymentMainRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteInputRes;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/26.
 */

public interface PaymentMainContract {

    interface View extends IView {
        MainFragmentClickListener getListener();
        LoadingView getLoadingView();
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<PaymentMainRes> getPaymentMainFragmentItem();
        Observable<RewriteInputRes> getTermPassword();
    }
}

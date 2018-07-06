package com.example.administrator.powerpayment.activity.mvp.contract.payment;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesAllParams;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;

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
    }
}

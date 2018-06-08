package com.payexpress.electric.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.payment.RewriteTimesAllParams;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

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

    interface Model extends IModel {}

}

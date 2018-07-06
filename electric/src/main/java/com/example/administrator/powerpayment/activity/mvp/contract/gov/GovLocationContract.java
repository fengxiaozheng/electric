package com.example.administrator.powerpayment.activity.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovLocationRes;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */

public interface GovLocationContract {

    interface View extends IView {
        Activity getActivity();
        void success(GovLocationRes res);
        void fail(String msg);
    }

    interface Model extends IModel {
        Observable<GovLocationRes> getGovLocation();
    }
}

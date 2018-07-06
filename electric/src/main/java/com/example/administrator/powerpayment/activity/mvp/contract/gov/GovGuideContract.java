package com.example.administrator.powerpayment.activity.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideRes;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public interface GovGuideContract {

    interface View extends IView {
        void success();
        void fail();
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<GovGuideRes> getGovGuideItem();
        Observable<GovGuideRes> getGovOnceItem();
    }
}

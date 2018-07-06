package com.example.administrator.powerpayment.activity.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideDetailRes;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */

public interface GovGuideDetailContract {

    interface View extends IView {
        void success(GovGuideDetailRes res);
        void fail(String msg);
        Activity getActivity();
    }

    interface Model extends IModel {
        Observable<GovGuideDetailRes> getGuideDetail(String id);
    }
}

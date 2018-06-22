package com.payexpress.electric.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideDetailRes;

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

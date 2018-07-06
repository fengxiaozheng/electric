package com.example.administrator.powerpayment.activity.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideListInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideListRes;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public interface GovGuideListContract {

    interface View extends IView {
        Activity getActivity();
        void success(int size, boolean isFirst);
        void fail(int code, boolean isFirst);
        void moreSuccess(List<GovGuideListInfo> data);
        void moreFail();
    }

    interface Model extends IModel {
        Observable<GovGuideListRes> getGovGuideList(Map<String, Object> options);
        Observable<GovGuideListRes> getGovOnceList(Map<String, Object> options);
    }
}

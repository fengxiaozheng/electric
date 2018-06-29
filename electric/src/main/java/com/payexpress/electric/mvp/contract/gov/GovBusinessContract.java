package com.payexpress.electric.mvp.contract.gov;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.payexpress.electric.mvp.model.entity.govEntity.GovBusinessInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovBusinessRes;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */

public interface GovBusinessContract {

    interface View extends IView {
        Activity getActivity();
        OnItemClickListener getListener();
        void success(List<GovBusinessInfo> data);
        void fail(String msg);
    }

    interface Model extends IModel {
        Observable<GovBusinessRes> getGovBusiness(String keyWord);
    }
}

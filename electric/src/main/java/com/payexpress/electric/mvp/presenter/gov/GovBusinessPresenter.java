package com.payexpress.electric.mvp.presenter.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract.View;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */
@FragmentScope
public class GovBusinessPresenter extends BasePresenter<Model, View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GovBusinessPresenter(Model model, View view) {
        super(model, view);
    }

    public void getBusinessContent(String keyWord) {

    }
}

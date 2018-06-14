package com.payexpress.electric.mvp.presenter.gov;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.View;
import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */
@FragmentScope
public class GovMainPresenter extends BasePresenter<Model, View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GovMainPresenter(Model model, View view) {
        super(model, view);
    }

    public void getTermArea() {
        mModel.getTermArea()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseGovResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseGovResponse baseGovResponse) {
                        System.out.println("数据呀："+ JSON.toJSONString(baseGovResponse));
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}

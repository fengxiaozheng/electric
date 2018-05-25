package com.payexpress.electric.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.CPCheckContract;
import com.payexpress.electric.mvp.model.entity.payment.CPUserInfoRes;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */
@ActivityScope
public class CPCheckPresenter extends BasePresenter<CPCheckContract.Model, CPCheckContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public CPCheckPresenter(CPCheckContract.Model model, CPCheckContract.View view) {
        super(model, view);
    }


    public void getUserInfo(String userNo){
        mModel.getUserInfo(userNo)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CPUserInfoRes>(mErrorHandler) {
                    @Override
                    public void onNext(CPUserInfoRes Response) {
                        System.out.println("数据："+ JSON.toJSONString(Response));
                        if (Response.isSuccess()) {
                            mRootView.success(Response);
                        } else {
                            mRootView.fail(Response.getRet_msg());
                        }
                    }
                });

    }

    public void getBalance(String userNo) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}

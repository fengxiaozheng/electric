package com.example.administrator.powerpayment.activity.mvp.presenter.gov;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessContract.Model;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessContract.View;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessRes;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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
        mModel.getGovBusiness(keyWord)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovBusinessRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovBusinessRes govBusinessRes) {
                        if (mRootView != null) {
                            if (govBusinessRes.isSuccess()) {
                                mRootView.success(govBusinessRes.getData());
                            } else {
                                mRootView.fail(govBusinessRes.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.fail(mRootView.getActivity().getString(R.string.server_error));
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}

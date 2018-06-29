package com.payexpress.electric.mvp.presenter.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract.View;
import com.payexpress.electric.mvp.model.entity.govEntity.GovBusinessRes;

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

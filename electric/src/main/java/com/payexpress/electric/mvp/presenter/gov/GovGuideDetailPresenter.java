package com.payexpress.electric.mvp.presenter.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.contract.gov.GovGuideDetailContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovGuideDetailContract.View;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideDetailRes;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
public class GovGuideDetailPresenter extends BasePresenter<Model, View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GovGuideDetailPresenter(Model model, View view) {
        super(model, view);
    }

    public void getDetail(String id) {
        mModel.getGuideDetail(id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GovGuideDetailRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideDetailRes govGuideDetailRes) {
                        if (mRootView != null) {
                            if (govGuideDetailRes.isSuccess()) {
                                mRootView.success(govGuideDetailRes);
                            } else {
                                mRootView.fail(govGuideDetailRes.getMessage());
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

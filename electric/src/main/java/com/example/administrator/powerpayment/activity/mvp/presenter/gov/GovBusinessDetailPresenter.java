package com.example.administrator.powerpayment.activity.mvp.presenter.gov;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessDetailContract.Model;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessDetailContract.View;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessDetailRes;
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
 * on 2018/6/25.
 */
@FragmentScope
public class GovBusinessDetailPresenter extends BasePresenter<Model, View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public GovBusinessDetailPresenter(Model model, View view) {
        super(model, view);
    }

    public void getDetail(String id) {
        mModel.getGovBusinessDetail(id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovBusinessDetailRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovBusinessDetailRes govBusinessDetailRes) {
                        if (mRootView != null) {
                            if (govBusinessDetailRes.isSuccess()) {
                                mRootView.success(govBusinessDetailRes);
                            } else {
                                mRootView.fail(govBusinessDetailRes.getMessage());
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

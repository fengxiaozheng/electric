package com.example.administrator.powerpayment.activity.mvp.presenter.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideContract.Model;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideContract.View;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideRes;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.GovGuideAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@FragmentScope
public class GovGuidePresenter extends BasePresenter<Model, View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    GovGuideAdapter mAdapter;
    @Inject
    List<GovGuideInfo> infos;

    @Inject
    public GovGuidePresenter(Model model, View view) {
        super(model, view);
    }

    public void getGovGuideItem() {
        mModel.getGovGuideItem()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovGuideRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideRes govGuideRes) {
                        if (mRootView != null) {
                            if (govGuideRes.isSuccess()) {
                                if (govGuideRes.getData().size() > 0) {
                                    infos.addAll(govGuideRes.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mRootView.success();
                                } else {
                                    mRootView.fail();
                                }
                            } else {
                                mRootView.fail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.fail();
                        }
                    }
                });

    }

    public void getGovOnceItem() {
        mModel.getGovOnceItem()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovGuideRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideRes govGuideRes) {
                        if (govGuideRes.isSuccess()) {
                            if (govGuideRes.getData().size() > 0) {
                                infos.addAll(govGuideRes.getData());
                                mAdapter.notifyDataSetChanged();
                                mRootView.success();
                            } else {
                                mRootView.fail();
                            }
                        }else {
                            mRootView.fail();
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
        mAdapter = null;
        infos = null;
    }
}

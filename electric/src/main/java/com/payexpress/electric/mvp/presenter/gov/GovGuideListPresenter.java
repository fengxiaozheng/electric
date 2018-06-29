package com.payexpress.electric.mvp.presenter.gov;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.gov.GovGuideListContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovGuideListContract.View;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideListInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideListRes;
import com.payexpress.electric.mvp.ui.adapter.GovGuideListAdapter;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.util.List;
import java.util.Map;

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
public class GovGuideListPresenter extends BasePresenter<Model, View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    StatusUIManager mUiManager;
    @Inject
    Map<String, Object> options;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<GovGuideListInfo> data;
    @Inject
    GovGuideListAdapter mAdapter;



    @Inject
    public GovGuideListPresenter(Model model, View view) {
        super(model, view);
    }

    public void getListData(String id, int start, boolean isFirst) {
        options.put("categoryId", id);
        options.put("page", start);
        options.put("pageSize", 5);
        mModel.getGovGuideList(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GovGuideListRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideListRes res) {
                        if (mRootView != null) {
                            if (res.isSuccess()) {
                                if (res.getTotal() > 0) {
                                    if (data != null) {
                                        data.clear();
                                        data.addAll(res.getData());
                                        mAdapter.notifyDataSetChanged();
                                        mRootView.success(res.getTotal(), isFirst);
                                    }
                                } else {
                                    if (isFirst) {
                                        mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                    }
                                    mRootView.fail(0, isFirst);
                                }
                            } else {
                                mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                mRootView.fail(1, isFirst);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.fail(1, isFirst);
                        }
                    }
                });
    }


    public void getMoreData(String id, int start) {
        options.put("categoryId", id);
        options.put("page", start);
        options.put("pageSize", 5);
        mModel.getGovGuideList(options)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GovGuideListRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideListRes res) {
                        if (mRootView != null) {
                            if (res.isSuccess()) {
                                if (res.getTotal() > 0) {
                                    if (data != null) {
                                        data.clear();
                                        data.addAll(res.getData());
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                                mRootView.moreSuccess(res.getData());

                            } else {
                                mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                mRootView.moreFail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.moreFail();
                        }
                    }
                });
    }

    public void initStateUI(android.view.View contentView) {
        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultEmptyStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_EMPTY,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    public void getOnceData(String id, int start, boolean isFirst) {
        options.put("categoryId", id);
        options.put("page", start);
        options.put("pageSize", 5);
        mModel.getGovOnceList(options)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GovGuideListRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideListRes res) {
                        if (mRootView != null) {
                            if (res.isSuccess()) {
                                if (res.getTotal() > 0) {
                                    if (data != null) {
                                        data.clear();
                                        data.addAll(res.getData());
                                        mAdapter.notifyDataSetChanged();
                                        mRootView.success(res.getTotal(), isFirst);
                                    }
                                } else {
                                    if (isFirst) {
                                        mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                    }
                                    mRootView.fail(0, isFirst);
                                }
                            } else {
                                mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                mRootView.fail(1, isFirst);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.fail(1, isFirst);}
                    }
                });
    }


    public void getOnceMoreData(String id, int start) {
        options.put("categoryId", id);
        options.put("page", start);
        options.put("pageSize", 5);
        mModel.getGovOnceList(options)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GovGuideListRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovGuideListRes res) {
                        if (mRootView != null) {
                            if (res.isSuccess()) {
                                if (res.getTotal() > 0) {
                                    if (data != null) {
                                        data.clear();
                                        data.addAll(res.getData());
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                                mRootView.moreSuccess(res.getData());

                            } else {
                                mUiManager.show(DefaultStatus.STATUS_EMPTY);
                                mRootView.moreFail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            mRootView.moreFail();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.data = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
        this.mUiManager = null;
    }
}

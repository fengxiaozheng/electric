package com.payexpress.electric.mvp.presenter.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.Model;
import com.payexpress.electric.mvp.contract.gov.GovMainContract.View;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.util.List;

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
    MainFragmentAdapter mAdapter;
    @Inject
    List<MainFragmentItemInfo> infos;
    @Inject
    StatusUIManager mUiManager;

    @Inject
    public GovMainPresenter(Model model, View view) {
        super(model, view);
    }

    public void getTermArea() {
        mModel.getGovMainFragmentItem()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GovMainRes>(mErrorHandler) {
                    @Override
                    public void onNext(GovMainRes govMainRes) {
                        mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                        if (govMainRes.isSuccess()) {
                            infos.addAll(govMainRes.getGovernmentServices());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mUiManager.show(DefaultStatus.STATUS_LOGIC_FAIL);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getLoadingView().setVisibility(android.view.View.GONE);
                        mUiManager.show(DefaultStatus.STATUS_SERVER_ERROR);
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

        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultServerErrorStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_SERVER_ERROR,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));

        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultLogicFailStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_LOGIC_FAIL,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.infos = null;
        this.mUiManager = null;
        this.mErrorHandler = null;
    }
}

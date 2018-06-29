package com.payexpress.electric.mvp.presenter.payment;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.R;
import com.payexpress.electric.mvp.contract.payment.CPCheckContract;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.UserNoReq;

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

    private UserNoReq req;
    private NoCardCheckReq checkReq;

    @Inject
    public CPCheckPresenter(CPCheckContract.Model model, CPCheckContract.View view) {
        super(model, view);
    }


    public void getUserInfo(String userNo) {
        mModel.getUserInfo(userNo)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CPUserInfoRes>(mErrorHandler) {
                        @Override
                        public void onNext(CPUserInfoRes Response) {
                            if (mRootView != null) {
                                if (Response.isSuccess()) {
                                    mRootView.success(Response);
                                } else {
                                    mRootView.fail(Response.getRet_msg());
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

    public void noCardCheck(String userNo) {
        if (checkReq == null) {
            checkReq = new NoCardCheckReq();
        }
        checkReq.setG3(userNo);
        mModel.noCardCheck(checkReq)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<NoCardCheckRes>(mErrorHandler) {
                    @Override
                    public void onNext(NoCardCheckRes noCardCheckRes) {
                        if (mRootView != null) {
                            if (noCardCheckRes.isSuccess()) {
                                getBalance(userNo);
                            } else {
                                mRootView.fail(noCardCheckRes.getRet_msg());
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

    public void getBalance(String userNo) {
        if (req == null) {
            req = new UserNoReq();
        }
        req.setGrid_user_code(userNo);
        mModel.queryCardBalance(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CardBalanceRes>(mErrorHandler) {
                    @Override
                    public void onNext(CardBalanceRes cardBalanceRes) {
                        if (mRootView != null) {
                            if (cardBalanceRes.isSuccess()) {
                                CPUserInfoRes data = new CPUserInfoRes();
                                data.setAddress(cardBalanceRes.getAddress());
                                data.setBalance(cardBalanceRes.getBalance());
                                data.setGrid_user_code(cardBalanceRes.getGrid_user_code());
                                data.setGrid_user_name(cardBalanceRes.getGrid_user_name());
                                mRootView.success(data);
                            } else {
                                mRootView.fail(cardBalanceRes.getRet_msg());
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
        this.mErrorHandler = null;
    }
}

package com.example.administrator.powerpayment.activity.mvp.presenter.payment;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.UserNoReq;
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
 * on 2018/5/16.
 */
@FragmentScope
public class SmartPowerPresenter extends BasePresenter<SmartPowerContract.Model,
        SmartPowerContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    private QuerySmartCardReq req;


    @Inject
    public SmartPowerPresenter(SmartPowerContract.Model model,
                               SmartPowerContract.View view) {
        super(model, view);
    }


    public void getCardInfo(String file1, String file2, String file3,
                            String file4, String file5) {
        if (req == null) {
            req = new QuerySmartCardReq();
        }
        req.setG5(file1);
        req.setG6(file2);
        req.setG7(file3);
        req.setG8(file4);
        req.setG9(file5);
        req.setSesb_no("51401");
        mModel.querySmartCardInfo(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<QuerySmartCardRes>(mErrorHandler) {
                    @Override
                    public void onNext(QuerySmartCardRes querySmartCardRes) {
                        if (mRootView != null) {
                            if (querySmartCardRes.isSuccess()) {
                                mRootView.success(querySmartCardRes);
                            } else {
                                mRootView.fail(querySmartCardRes.getRet_msg());
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
        UserNoReq req = new UserNoReq();
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
                                mRootView.queryBalanceSuccess(cardBalanceRes);
                            } else {
                                mRootView.queryBalanceFail("未采集");
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
        mErrorHandler = null;
        super.onDestroy();
    }

}

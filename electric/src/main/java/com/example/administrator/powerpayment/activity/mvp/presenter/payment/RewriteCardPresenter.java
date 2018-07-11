package com.example.administrator.powerpayment.activity.mvp.presenter.payment;

import com.example.administrator.powerpayment.activity.R;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteCardContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardRes;
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
public class RewriteCardPresenter extends BasePresenter<RewriteCardContract.Model,
        RewriteCardContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public RewriteCardPresenter(RewriteCardContract.Model model,
                                RewriteCardContract.View view) {
        super(model, view);
    }

    public void startRewrite(String file1, String file2, String file31,
                             String file32, String file41, String file42,
                             String file5, String idata, String code_id,
                             String rand) {
        RewriteCardReq req = new RewriteCardReq();
        req.setFile1(file1);
        req.setFile2(file2);
        req.setFile31(file31);
        req.setFile32(file32);
        req.setFile41(file41);
        req.setFile42(file42);
        req.setFile5(file5);
        req.setCard_info(idata);
        req.setCard_seq(code_id);
        req.setCard_random(rand);
        mModel.rewriteCard(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RewriteCardRes>(mErrorHandler) {
                    @Override
                    public void onNext(RewriteCardRes rewriteCardRes) {
                        if (mRootView != null) {
                            if (rewriteCardRes.isSuccess()) {
                                mRootView.rewriteCard2(rewriteCardRes);
                            } else {
                                mRootView.fail("1111");
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

package com.payexpress.electric.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.ElectricPayContract;
import com.payexpress.electric.mvp.model.entity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.payment.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.payment.CodePayReq;
import com.payexpress.electric.mvp.model.entity.payment.CodePayRes;
import com.payexpress.electric.mvp.model.entity.payment.TransNoReq;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */
@FragmentScope
public class ElectricPayPresenter extends BasePresenter<ElectricPayContract.PayModel, ElectricPayContract.PayView> {

    @Inject
    RxErrorHandler mErrorHandler;

    private int times = 3;

    @Inject
    public ElectricPayPresenter(ElectricPayContract.PayModel model, ElectricPayContract.PayView view) {
        super(model, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    //flag   0:普通购电，1：智能无卡购电，2：智能有卡购电
    public void  qrCodePay(int flag, String userNo, String amount, String code) {
        CodePayReq req = new CodePayReq();
        req.setG3(userNo);
        req.setAmount(amount);
        req.setBar_code(code);
        mModel.doPay(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CodePayRes>(mErrorHandler) {
                    @Override
                    public void onNext(CodePayRes codePayRes) {
                        if (codePayRes.isSuccess()) {
                            if ("00".equals(codePayRes.getPay_status())) {
                                if (flag == 0) {
                                            TransNoReq req1 = new TransNoReq();
                                            req1.setTrans_no(codePayRes.getTrans_no());
                                            commonElectricBuy(req1);
                                } else if (flag == 1) {
                                    isNotCardElectricBuy();
                                } else {
                                    isCardElectricBuy();
                                }
                            }else if ("01".equals(codePayRes.getPay_status())){
                                mRootView.fail("支付失败");
                            } else {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        checkPayStatus(false, flag, codePayRes.getTrans_no());
                                        this.cancel();
                                    }
                                }, 10* 1000);
                            }
                        } else {
                            mRootView.fail(codePayRes.getRet_msg());
                        }
                    }
                });
    }

    public void checkPayStatus(boolean over, int flag, String transNo) {
        TransNoReq req = new TransNoReq();
        req.setTrans_no(transNo);
        getData(over, flag, req, transNo);
    }

    private void getData(boolean over, int flag, TransNoReq req, String transNo) {
        mModel.checkPayStatus(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CheckPayRes>(mErrorHandler) {
                    @Override
                    public void onNext(CheckPayRes checkPayRes) {
                        if (checkPayRes.isSuccess()) {
                            if ("00".equals(checkPayRes.getPay_status())) {
                                times = 3;
                                if (flag == 0) {
                                    commonElectricBuy(req);
                                } else if (flag == 1) {
                                    isNotCardElectricBuy();
                                } else {
                                    isCardElectricBuy();
                                }
                            } else {
                                times--;
                                if (times >0) {
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            getData(over, flag, req, transNo);
                                            this.cancel();
                                        }
                                    }, 5* 1000);

                                } else {
                                    times = 3;
                                    if ("02".equals(checkPayRes.getPay_status())){
                                        if (over) {
                                            mRootView.fail("待支付");
                                        }else {
                                            mRootView.checkPay(flag, transNo);
                                        }
                                    } else {
                                        mRootView.fail("支付失败");
                                    }
                                }
                            }
                        }else {
                            times--;
                            if (times >0) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        getData(over, flag, req, transNo);
                                        this.cancel();
                                    }
                                }, 5* 1000);
                            } else {
                                times = 3;
                                mRootView.fail(checkPayRes.getRet_msg());
                            }
                        }
                    }
                });
    }

    public void commonElectricBuy(TransNoReq req) {
        mModel.commonElectricBuy(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            mRootView.success();
                        } else {
                            mRootView.fail(baseResponse.getRet_msg());
                        }
                    }
                });
    }

    public void isCardElectricBuy() {

    }

    public void isNotCardElectricBuy() {

    }

}

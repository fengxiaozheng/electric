package com.payexpress.electric.mvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.payexpress.electric.mvp.contract.PayRecordContract;
import com.payexpress.electric.mvp.model.entity.payment.PayRecordRes;
import com.payexpress.electric.mvp.model.entity.payment.RecordInfo;
import com.payexpress.electric.mvp.model.entity.payment.UserNoReq;

import org.ayo.view.status.DefaultStatus;
import org.ayo.view.status.DefaultStatusProvider;
import org.ayo.view.status.StatusProvider;
import org.ayo.view.status.StatusUIManager;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */

public class PayRecordPresenter extends BasePresenter<PayRecordContract.PayRecordModel,
                                                PayRecordContract.PayRecordView> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    List<RecordInfo> infos;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    StatusUIManager mUiManager;
    @Inject
    DecimalFormat format;

    private StringBuilder builder;

    @Inject
    public PayRecordPresenter(PayRecordContract.PayRecordModel model,
                              PayRecordContract.PayRecordView view) {
        super(model, view);
    }

    public void getRecordData(String userNo) {
        UserNoReq req = new UserNoReq();
        req.setGrid_user_code(userNo);
        mModel.getPayRecord(req)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<PayRecordRes>(mErrorHandler) {
                    @Override
                    public void onNext(PayRecordRes res) {
                        if (res.isSuccess()) {
                            if (res.getRows().size() > 0) {
                                for (RecordInfo info : res.getRows()) {
                                    builder = new StringBuilder();
                                    info.setTrans_date(builder
                                            .append(info.getTrans_date().substring(0, 4))
                                            .append("-")
                                            .append(info.getTrans_date().substring(4, 6))
                                            .append("-")
                                            .append(info.getTrans_date().substring(6, 8))
                                            .append(info.getTrans_date().substring(8, 17))
                                            .toString());
                                    builder.delete(0, 19);
                                    info.setAmount(builder
                                            .append(format.format(Double.parseDouble(info.getAmount())))
                                            .append("元")
                                            .toString());
                                }
                                infos.addAll(res.getRows());
                                mAdapter.notifyDataSetChanged();
                            }
                            mRootView.success(res);
                        } else {
                            if (mRootView != null ) {
                                mRootView.fail(res.getRet_msg());
                            }
                        }
                    }
                });
    }

    public void initStateUI(View contentView) {
        mUiManager.addStatusProvider(
                new DefaultStatusProvider.DefaultEmptyStatusView(mRootView.getActivity(),
                        DefaultStatus.STATUS_EMPTY,
                        contentView,
                        (StatusProvider.OnStatusViewCreateCallback) (status, statusView) -> {

                        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        infos = null;
        mAdapter = null;
        mUiManager = null;
        builder = null;
    }
}

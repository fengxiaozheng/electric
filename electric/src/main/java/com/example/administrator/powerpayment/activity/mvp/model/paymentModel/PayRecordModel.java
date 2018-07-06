package com.example.administrator.powerpayment.activity.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.PayRecordContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.PaymentService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.PayRecordRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.UserNoReq;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */
@FragmentScope
public class PayRecordModel extends BaseModel implements PayRecordContract.PayRecordModel {

    @Inject
    public PayRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<PayRecordRes> getPayRecord(UserNoReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .payRecord(JSON.toJSONString(req));
    }
}

package com.example.administrator.powerpayment.activity.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.UserNoReq;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.PaymentService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.QuerySmartCardRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */
@FragmentScope
public class SmartPowerModel extends BaseModel implements SmartPowerContract.Model {

    @Inject
    public SmartPowerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<QuerySmartCardRes> querySmartCardInfo(QuerySmartCardReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .querySmartCardInfo(JSON.toJSONString(req));
    }

    @Override
    public Observable<CardBalanceRes> queryCardBalance(UserNoReq req) {
        return mRepositoryManager
                .obtainRetrofitService(PaymentService.class)
                .queryCardBalance(JSON.toJSONString(req));
    }
}

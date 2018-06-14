package com.payexpress.electric.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.PayRecordContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PayRecordRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.UserNoReq;

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
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .payRecord(JSON.toJSONString(req));
    }
}

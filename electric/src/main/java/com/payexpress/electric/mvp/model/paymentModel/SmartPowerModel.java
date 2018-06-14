package com.payexpress.electric.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.SmartPowerContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.QuerySmartCardReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.QuerySmartCardRes;

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
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .querySmartCardInfo(JSON.toJSONString(req));
    }
}

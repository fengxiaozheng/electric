package com.payexpress.electric.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.RewriteCardContract;
import com.payexpress.electric.mvp.model.api.service.PaymentService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/5.
 */
@FragmentScope
public class RewriteCardModel extends BaseModel implements RewriteCardContract.Model {

    @Inject
    public RewriteCardModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RewriteCardRes> rewriteCard(RewriteCardReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .rewriteCard(JSON.toJSONString(req));
    }
}

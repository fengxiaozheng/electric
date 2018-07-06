package com.example.administrator.powerpayment.activity.mvp.model.paymentModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteTimesContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.PaymentService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardListRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteTimesRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@FragmentScope
public class RewriteTimesModel extends BaseModel implements RewriteTimesContract.Model {

    @Inject
    public RewriteTimesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RewriteCardListRes> getRewriteData(String req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .rewriteCardTimesList(req);
    }

    @Override
    public Observable<RewriteTimesRes> startRewriteTimes(String req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .rewriteCardTimes(req);
    }
}

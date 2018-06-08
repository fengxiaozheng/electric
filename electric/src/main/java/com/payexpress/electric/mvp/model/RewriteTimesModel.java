package com.payexpress.electric.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.RewriteTimesContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.payment.RewriteCardListRes;
import com.payexpress.electric.mvp.model.entity.payment.RewriteTimesRes;

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
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .rewriteCardTimesList(req);
    }

    @Override
    public Observable<RewriteTimesRes> startRewriteTimes(String req) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .rewriteCardTimes(req);
    }
}

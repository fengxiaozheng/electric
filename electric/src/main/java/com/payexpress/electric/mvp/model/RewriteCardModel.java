package com.payexpress.electric.mvp.model;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.RewriteCardContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.payment.RewriteCardReq;
import com.payexpress.electric.mvp.model.entity.payment.RewriteCardRes;

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
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .rewriteCard(JSON.toJSONString(req));
    }
}

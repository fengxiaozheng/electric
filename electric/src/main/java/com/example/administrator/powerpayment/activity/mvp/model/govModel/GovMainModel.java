package com.example.administrator.powerpayment.activity.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovMainContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.cache.CommonCache;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.GovService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovMainRes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */
@FragmentScope
public class GovMainModel extends BaseModel implements GovMainContract.Model {

    @Inject
    public GovMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<GovMainRes> getGovMainFragmentItem() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(GovService.class)
                .getGovMainItem())
                .flatMap(govMainResObservable -> mRepositoryManager.obtainCacheService(CommonCache.class)
                        .getGovMainFragmentInfo(govMainResObservable,
                                new DynamicKey("GovMainFragment"),
                                new EvictDynamicKey(false))
                        .map(govMainResReply -> govMainResReply.getData()));

    }
}

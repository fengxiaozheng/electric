package com.payexpress.electric.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.gov.GovMainContract;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;

import javax.inject.Inject;

import io.reactivex.Observable;

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
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovMainItem();
    }
}

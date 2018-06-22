package com.payexpress.electric.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */
@FragmentScope
public class GovBusinessModel extends BaseModel implements GovBusinessContract.Model {

    @Inject
    public GovBusinessModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseGovResponse> getGovBusiness(String keyWord) {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovBusiness(keyWord);
    }
}

package com.example.administrator.powerpayment.activity.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.GovService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@FragmentScope
public class GovGuideModel extends BaseModel implements GovGuideContract.Model {

    @Inject
    public GovGuideModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GovGuideRes> getGovGuideItem() {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovGuideItem();
    }

    @Override
    public Observable<GovGuideRes> getGovOnceItem() {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovOnceItem();
    }
}

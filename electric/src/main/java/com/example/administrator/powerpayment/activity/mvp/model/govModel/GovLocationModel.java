package com.example.administrator.powerpayment.activity.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovLocationContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.GovService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovLocationRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
public class GovLocationModel extends BaseModel implements GovLocationContract.Model {

    @Inject
    public GovLocationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GovLocationRes> getGovLocation() {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovLocation();
    }
}

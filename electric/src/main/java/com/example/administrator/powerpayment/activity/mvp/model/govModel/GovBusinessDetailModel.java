package com.example.administrator.powerpayment.activity.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessDetailContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.GovService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovBusinessDetailRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */
@FragmentScope
public class GovBusinessDetailModel extends BaseModel implements GovBusinessDetailContract.Model {

    @Inject
    public GovBusinessDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GovBusinessDetailRes> getGovBusinessDetail(String id) {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovBusinessDetail(id);
    }
}

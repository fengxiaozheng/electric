package com.example.administrator.powerpayment.activity.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideDetailContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.GovService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideDetailRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
public class GovGuideDetailModel extends BaseModel implements GovGuideDetailContract.Model {

    @Inject
    public GovGuideDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GovGuideDetailRes> getGuideDetail(String id) {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGuideDetail(id);
    }
}

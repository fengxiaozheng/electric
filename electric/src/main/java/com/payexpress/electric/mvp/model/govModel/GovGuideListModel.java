package com.payexpress.electric.mvp.model.govModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.gov.GovGuideListContract;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideListRes;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@FragmentScope
public class GovGuideListModel extends BaseModel implements GovGuideListContract.Model {

    @Inject
    public GovGuideListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<GovGuideListRes> getGovGuideList(Map<String, Object> options) {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovGuideList(options);
    }

    @Override
    public Observable<GovGuideListRes> getGovOnceList(Map<String, Object> options) {
        return mRepositoryManager.obtainRetrofitService(GovService.class)
                .getGovOnceList(options);
    }
}

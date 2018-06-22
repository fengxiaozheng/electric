package com.payexpress.electric.di.module.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.gov.GovGuideDetailContract;
import com.payexpress.electric.mvp.model.govModel.GovGuideDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@Module
public class GovGuideDetailModule {
    private GovGuideDetailContract.View view;

    public GovGuideDetailModule(GovGuideDetailContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovGuideDetailContract.View provideGuideDetailView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovGuideDetailContract.Model provideGuideDetailModel(GovGuideDetailModel model) {
        return model;
    }
}

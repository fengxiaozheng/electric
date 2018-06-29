package com.payexpress.electric.di.module.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.gov.GovBusinessDetailContract;
import com.payexpress.electric.mvp.model.govModel.GovBusinessDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */
@Module
public class GovBusinessDetailModule {
    private GovBusinessDetailContract.View view;

    public GovBusinessDetailModule(GovBusinessDetailContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovBusinessDetailContract.View provideBusinessDetailView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovBusinessDetailContract.Model provideBusinessDetailModel(GovBusinessDetailModel model) {
        return model;
    }
}

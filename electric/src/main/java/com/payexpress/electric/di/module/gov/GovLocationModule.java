package com.payexpress.electric.di.module.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.gov.GovLocationContract;
import com.payexpress.electric.mvp.model.govModel.GovLocationModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@Module
public class GovLocationModule {
    private GovLocationContract.View view;

    public GovLocationModule(GovLocationContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovLocationContract.View provideGovLocationView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovLocationContract.Model provideGovLocationModel(GovLocationModel model) {
        return model;
    }
}

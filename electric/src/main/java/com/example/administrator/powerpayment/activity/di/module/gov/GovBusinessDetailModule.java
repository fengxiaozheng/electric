package com.example.administrator.powerpayment.activity.di.module.gov;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovBusinessDetailContract;
import com.example.administrator.powerpayment.activity.mvp.model.govModel.GovBusinessDetailModel;

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

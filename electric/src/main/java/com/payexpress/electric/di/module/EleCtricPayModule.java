package com.payexpress.electric.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.ElectricPayContract;
import com.payexpress.electric.mvp.model.CodePayModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */
@Module
public class EleCtricPayModule {

    private ElectricPayContract.PayView view;

    public EleCtricPayModule(ElectricPayContract.PayView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ElectricPayContract.PayView providePayView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ElectricPayContract.PayModel providePayModel(CodePayModel model) {
        return model;
    }
}

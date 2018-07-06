package com.example.administrator.powerpayment.activity.di.module.payment;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.ElectricPayContract;
import com.example.administrator.powerpayment.activity.mvp.model.paymentModel.CodePayModel;

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

package com.example.administrator.powerpayment.activity.di.module.payment;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.SmartPowerContract;
import com.example.administrator.powerpayment.activity.mvp.model.paymentModel.SmartPowerModel;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.AlertDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */
@Module
public class SmartPowerModule {

    private SmartPowerContract.View view;

    public SmartPowerModule(SmartPowerContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SmartPowerContract.View provideSPView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SmartPowerContract.Model provideSPModel(SmartPowerModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    AlertDialog provideAlertDialog() {
        return new AlertDialog(view.getActivity());
    }
}

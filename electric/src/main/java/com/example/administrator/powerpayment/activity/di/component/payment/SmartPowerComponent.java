package com.example.administrator.powerpayment.activity.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.payment.SmartPowerModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric.SmartPowerFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */
@FragmentScope
@Component(modules = SmartPowerModule.class, dependencies = AppComponent.class)
public interface SmartPowerComponent {
    void inject(SmartPowerFragment fragment);
}

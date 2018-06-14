package com.payexpress.electric.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.payment.SmartPowerModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.SmartPowerFragment;

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

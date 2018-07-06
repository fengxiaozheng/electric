package com.example.administrator.powerpayment.activity.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.payment.EleCtricPayModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric.StartPayFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */
@FragmentScope
@Component(modules = EleCtricPayModule.class, dependencies = AppComponent.class)
public interface ElectricPayComponent {
    void inject(StartPayFragment fragment);
}

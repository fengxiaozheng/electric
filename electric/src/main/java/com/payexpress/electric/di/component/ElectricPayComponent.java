package com.payexpress.electric.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.EleCtricPayModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.StartPayFragment;

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

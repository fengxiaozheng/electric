package com.payexpress.electric.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.payment.PaymentMainModule;
import com.payexpress.electric.mvp.ui.activity.payment.PaymentMainFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/26.
 */
@FragmentScope
@Component(modules = PaymentMainModule.class, dependencies = AppComponent.class)
public interface PaymentMainComponent {
    void inject(PaymentMainFragment fragment);
}

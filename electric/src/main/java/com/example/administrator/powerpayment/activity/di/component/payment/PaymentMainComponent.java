package com.example.administrator.powerpayment.activity.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.payment.PaymentMainModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.PaymentMainFragment;

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

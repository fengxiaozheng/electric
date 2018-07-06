package com.example.administrator.powerpayment.activity.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.payment.PayRecordModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric.CPRecordFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */
@FragmentScope
@Component(modules = PayRecordModule.class, dependencies = AppComponent.class)
public interface PayRecordComponent {
    void inject(CPRecordFragment fragment);
}

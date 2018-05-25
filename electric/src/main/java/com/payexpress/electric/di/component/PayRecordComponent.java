package com.payexpress.electric.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.PayRecordModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.CPRecordFragment;

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

package com.example.administrator.powerpayment.activity.di.component.payment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.payment.RewriteTimesModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.payment.electric.RewriteTimesFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@FragmentScope
@Component(modules = RewriteTimesModule.class, dependencies = AppComponent.class)
public interface RewriteTimesComponent {
    void inject(RewriteTimesFragment fragment);
}

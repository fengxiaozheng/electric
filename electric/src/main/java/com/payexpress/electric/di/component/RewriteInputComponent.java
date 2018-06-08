package com.payexpress.electric.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.RewriteInputModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.RewriteInputFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@FragmentScope
@Component(modules = RewriteInputModule.class, dependencies = AppComponent.class)
public interface RewriteInputComponent {
    void inject(RewriteInputFragment fragment);
}

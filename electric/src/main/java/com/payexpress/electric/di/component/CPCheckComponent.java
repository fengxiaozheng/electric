package com.payexpress.electric.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.payexpress.electric.di.module.CPCheckModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.CPCheckUserFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */
@ActivityScope
@Component(modules = CPCheckModule.class, dependencies = AppComponent.class)
public interface CPCheckComponent {
    void inject(CPCheckUserFragment fragment);
}

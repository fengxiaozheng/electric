package com.payexpress.electric.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.RewriteCardModule;
import com.payexpress.electric.mvp.ui.activity.payment.electric.RewriteCardFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/5.
 */
@FragmentScope
@Component(modules = RewriteCardModule.class, dependencies = AppComponent.class)
public interface RewriteCardComponent {
    void inject(RewriteCardFragment fragment);
}

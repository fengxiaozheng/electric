package com.payexpress.electric.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.gov.GovMainModule;
import com.payexpress.electric.mvp.ui.activity.gov.GovMainFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */
@FragmentScope
@Component(modules = GovMainModule.class, dependencies = AppComponent.class)
public interface GovMainComponent {
    void inject(GovMainFragment fragment);
}

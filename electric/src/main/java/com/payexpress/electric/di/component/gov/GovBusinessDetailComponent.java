package com.payexpress.electric.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.gov.GovBusinessDetailModule;
import com.payexpress.electric.mvp.ui.activity.gov.GovBusinessDetailFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */
@FragmentScope
@Component(modules = GovBusinessDetailModule.class, dependencies = AppComponent.class)
public interface GovBusinessDetailComponent {
    void inject(GovBusinessDetailFragment fragment);
}

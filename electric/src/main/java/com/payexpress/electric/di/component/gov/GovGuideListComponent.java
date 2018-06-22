package com.payexpress.electric.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.gov.GovGuideListModule;
import com.payexpress.electric.mvp.ui.activity.gov.GovGuideListFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@FragmentScope
@Component(modules = GovGuideListModule.class, dependencies = AppComponent.class)
public interface GovGuideListComponent {
    void inject(GovGuideListFragment fragment);
}

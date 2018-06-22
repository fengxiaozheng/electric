package com.payexpress.electric.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.gov.GovGuideDetailModule;
import com.payexpress.electric.mvp.ui.activity.gov.GovGuideDetailFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
@Component(modules = GovGuideDetailModule.class, dependencies = AppComponent.class)
public interface GovGuideDetailComponent {
    void inject(GovGuideDetailFragment fragment);
}

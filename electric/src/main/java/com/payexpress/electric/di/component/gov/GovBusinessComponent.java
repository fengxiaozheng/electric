package com.payexpress.electric.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.di.module.gov.GovBusinessModule;
import com.payexpress.electric.mvp.ui.activity.gov.GovBusinessFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */
@FragmentScope
@Component(modules = GovBusinessModule.class, dependencies = AppComponent.class)
public interface GovBusinessComponent {
    void inject(GovBusinessFragment fragment);
}

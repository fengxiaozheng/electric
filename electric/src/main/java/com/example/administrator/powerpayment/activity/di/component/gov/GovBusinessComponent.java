package com.example.administrator.powerpayment.activity.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.gov.GovBusinessModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov.GovBusinessFragment;

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

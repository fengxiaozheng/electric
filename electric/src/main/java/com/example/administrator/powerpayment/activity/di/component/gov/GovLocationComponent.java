package com.example.administrator.powerpayment.activity.di.component.gov;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.di.module.gov.GovLocationModule;
import com.example.administrator.powerpayment.activity.mvp.ui.fragment.gov.GovLocationFragment;

import dagger.Component;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */
@FragmentScope
@Component(modules = GovLocationModule.class, dependencies = AppComponent.class)
public interface GovLocationComponent {
    void inject(GovLocationFragment fragment);
}

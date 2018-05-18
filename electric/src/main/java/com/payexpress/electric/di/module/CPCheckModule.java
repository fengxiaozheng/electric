package com.payexpress.electric.di.module;

import android.support.v7.widget.GridLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.contract.CPCheckContract;
import com.payexpress.electric.mvp.model.CPCheckModel;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */
@Module
public class CPCheckModule {

    private CPCheckContract.View view;

    public CPCheckModule(CPCheckContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CPCheckContract.View provideCPCview() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CPCheckContract.Model provideCPCheckModel(CPCheckModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    GridLayoutManager provideCPCGridLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 3);
    }

    @ActivityScope
    @Provides
    OnItemClickListener provideOnitemClickListener() {
        return  view.getListener();
    }

    @ActivityScope
    @Provides
    KeyboardAdapter provideKeyboardAdapter(OnItemClickListener listener) {
        return new KeyboardAdapter(KeyboardUtils.withoutPoint(), listener);
    }

    @ActivityScope
    @Provides
    StringBuilder provideStringBuilder() {
        return new StringBuilder();
    }

}

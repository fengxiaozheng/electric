package com.payexpress.electric.di.module.gov;

import android.support.v7.widget.GridLayoutManager;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.app.utils.KeyboardUtils;
import com.payexpress.electric.mvp.contract.gov.GovBusinessContract;
import com.payexpress.electric.mvp.model.govModel.GovBusinessModel;
import com.payexpress.electric.mvp.ui.adapter.KeyboardAdapter;
import com.payexpress.electric.mvp.ui.adapter.OnItemClickListener;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/22.
 */
@Module
public class GovBusinessModule {
    private GovBusinessContract.View view;

    public GovBusinessModule(GovBusinessContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovBusinessContract.View provideCPCview() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovBusinessContract.Model provideGBCheckModel(GovBusinessModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    GridLayoutManager provideGBGridLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 3);
    }

    @FragmentScope
    @Provides
    OnItemClickListener provideOnitemClickListener() {
        return  view.getListener();
    }

    @FragmentScope
    @Provides
    KeyboardAdapter provideKeyboardAdapter(OnItemClickListener listener) {
        return new KeyboardAdapter(KeyboardUtils.withIDCard(), listener);
    }

    @FragmentScope
    @Provides
    StringBuilder provideStringBuilder() {
        return new StringBuilder();
    }

}

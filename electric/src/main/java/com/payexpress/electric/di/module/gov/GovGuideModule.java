package com.payexpress.electric.di.module.gov;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.gov.GovGuideContract;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideInfo;
import com.payexpress.electric.mvp.model.govModel.GovGuideModel;
import com.payexpress.electric.mvp.ui.adapter.GovGuideAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@Module
public class GovGuideModule {

    private GovGuideContract.View view;

    public GovGuideModule(GovGuideContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovGuideContract.View provideGovGuideView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovGuideContract.Model provideGovGuideModel(GovGuideModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideGovGuideLayoutManager() {
    return new GridLayoutManager(view.getActivity(), 4);
    }

    @FragmentScope
    @Provides
    List<GovGuideInfo> provideGovGuideData() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    GovGuideAdapter provideGovGuideAdapter(List<GovGuideInfo> infos) {
        return new GovGuideAdapter(infos);
    }
}

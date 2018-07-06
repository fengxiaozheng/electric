package com.example.administrator.powerpayment.activity.di.module.gov;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.mvp.contract.gov.GovGuideListContract;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.GovGuideListInfo;
import com.example.administrator.powerpayment.activity.mvp.model.govModel.GovGuideListModel;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.GovGuideListAdapter;

import org.ayo.view.status.StatusUIManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */
@Module
public class GovGuideListModule {
    private GovGuideListContract.View view;

    public GovGuideListModule(GovGuideListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovGuideListContract.View provideGuideListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovGuideListContract.Model provideGuideModel(GovGuideListModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideGuideListLayout() {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    GovGuideListAdapter provideGuideListAdapter(List<GovGuideListInfo> data) {
        return new GovGuideListAdapter(data);
    }

    @FragmentScope
    @Provides
    List<GovGuideListInfo> provideGuideListInfo() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    StatusUIManager provideGuideListUIManager() {
        return new StatusUIManager();
    }

    @FragmentScope
    @Provides
    Map<String, Object> provideGuideListMap() {
        return new HashMap<>();
    }
}

package com.payexpress.electric.di.module.gov;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.gov.GovMainContract;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.model.govModel.GovMainModel;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

import org.ayo.view.status.StatusUIManager;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */
@Module
public class GovMainModule {

    private GovMainContract.View view;

    public GovMainModule(GovMainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    GovMainContract.View provideGovMainView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    GovMainContract.Model provideGovMainModel(GovMainModel mainModel) {
        return mainModel;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideGMLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 4);
    }

    @FragmentScope
    @Provides
    MainFragmentAdapter provideGovMainAdapter(List<MainFragmentItemInfo> infos, MainFragmentClickListener listener) {
        return new MainFragmentAdapter(infos, listener);
    }

    @FragmentScope
    @Provides
    List<MainFragmentItemInfo> provideGovMainInfo() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    MainFragmentClickListener provideGovListener() {
        return view.getListener();
    }

    @FragmentScope
    @Provides
    StatusUIManager provideGovUIManager() {
        return new StatusUIManager();
    }

}

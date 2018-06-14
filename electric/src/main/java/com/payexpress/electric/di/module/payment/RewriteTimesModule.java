package com.payexpress.electric.di.module.payment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.payment.RewriteTimesContract;
import com.payexpress.electric.mvp.model.paymentModel.RewriteTimesModel;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesInfo;
import com.payexpress.electric.mvp.ui.adapter.RewriteTimesAdapter;
import com.payexpress.electric.mvp.ui.widget.RewriteTimesDialog;

import org.ayo.view.status.StatusUIManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@Module
public class RewriteTimesModule {

    private RewriteTimesContract.View view;

    public RewriteTimesModule(RewriteTimesContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RewriteTimesContract.View provideTimesView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RewriteTimesContract.Model provideTimesModel(RewriteTimesModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideRdLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    List<RewriteTimesInfo> provideRecordInfo() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    RewriteTimesAdapter provideTimesdAdapter(List<RewriteTimesInfo> infos) {
        return new RewriteTimesAdapter(infos);
    }

    @FragmentScope
    @Provides
    StatusUIManager provideUIManager() {
        return new StatusUIManager();
    }

    @FragmentScope
    @Provides
    DecimalFormat provideDecimalFormat() {
        return new DecimalFormat("0.00");
    }

    @FragmentScope
    @Provides
    RewriteTimesDialog provideTimesDialog() {
        return new RewriteTimesDialog(view.getActivity());
    }
}

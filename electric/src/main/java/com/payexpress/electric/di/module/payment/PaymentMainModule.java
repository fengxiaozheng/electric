package com.payexpress.electric.di.module.payment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.payment.PaymentMainContract;
import com.payexpress.electric.mvp.model.entity.MainFragmentItemInfo;
import com.payexpress.electric.mvp.model.paymentModel.PaymentMainModel;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentAdapter;
import com.payexpress.electric.mvp.ui.adapter.MainFragmentClickListener;

import org.ayo.view.status.StatusUIManager;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/26.
 */
@Module
public class PaymentMainModule {

    private PaymentMainContract.View view;

    public PaymentMainModule(PaymentMainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PaymentMainContract.View providePaymentMainView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    PaymentMainContract.Model providePaymentMainModel(PaymentMainModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideGMLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 4);
    }

    @FragmentScope
    @Provides
    MainFragmentAdapter providePaymentMainAdapter(List<MainFragmentItemInfo> infos, MainFragmentClickListener listener) {
        return new MainFragmentAdapter(infos, listener);
    }

    @FragmentScope
    @Provides
    List<MainFragmentItemInfo> providePaymentMainInfo() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    MainFragmentClickListener providePaymentListener() {
        return view.getListener();
    }

    @FragmentScope
    @Provides
    StatusUIManager providePaymentUIManager() {
        return new StatusUIManager();
    }

}

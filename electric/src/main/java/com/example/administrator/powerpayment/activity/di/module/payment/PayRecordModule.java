package com.example.administrator.powerpayment.activity.di.module.payment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.PayRecordContract;
import com.example.administrator.powerpayment.activity.mvp.model.paymentModel.PayRecordModel;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RecordInfo;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.PayRecordAdapter;

import org.ayo.view.status.StatusUIManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */
@Module
public class PayRecordModule {

    private PayRecordContract.PayRecordView view;

    public PayRecordModule(PayRecordContract.PayRecordView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PayRecordContract.PayRecordView providePayRecordView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    PayRecordContract.PayRecordModel providePayRecordModel(PayRecordModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideRdLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    List<RecordInfo> provideRecordInfo() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    RecyclerView.Adapter provideRecordAdapter(List<RecordInfo> infos) {
        return new PayRecordAdapter(infos);
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
}

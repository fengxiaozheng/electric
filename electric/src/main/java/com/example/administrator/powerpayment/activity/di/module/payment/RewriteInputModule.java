package com.example.administrator.powerpayment.activity.di.module.payment;

import android.support.v7.widget.GridLayoutManager;

import com.jess.arms.di.scope.FragmentScope;
import com.example.administrator.powerpayment.activity.app.utils.KeyboardUtils;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.RewriteInputContract;
import com.example.administrator.powerpayment.activity.mvp.model.paymentModel.RewriteInputModel;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.KeyboardAdapter;
import com.example.administrator.powerpayment.activity.mvp.ui.adapter.OnItemClickListener;
import com.example.administrator.powerpayment.activity.mvp.ui.widget.AlertDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@Module
public class RewriteInputModule {

    private RewriteInputContract.View view;

    public RewriteInputModule(RewriteInputContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RewriteInputContract.View provideRewriteInputView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    RewriteInputContract.Model provideRewriteInputModel(RewriteInputModel model) {
        return model;
    }


    @FragmentScope
    @Provides
    GridLayoutManager provideCPCGridLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 3);
    }

    @FragmentScope
    @Provides
    OnItemClickListener provideOnItemClickListener() {
        return  view.getListener();
    }

    @FragmentScope
    @Provides
    KeyboardAdapter provideKeyboardAdapter(OnItemClickListener listener) {
        return new KeyboardAdapter(KeyboardUtils.withoutPoint(), listener);
    }

    @FragmentScope
    @Provides
    StringBuilder provideStringBuilder() {
        return new StringBuilder();
    }

    @FragmentScope
    @Provides
    AlertDialog provideInputDialog() {
        return new AlertDialog(view.getActivity());
    }
}

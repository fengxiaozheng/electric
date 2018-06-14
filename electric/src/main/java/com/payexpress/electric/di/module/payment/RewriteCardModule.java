package com.payexpress.electric.di.module.payment;

import com.jess.arms.di.scope.FragmentScope;
import com.payexpress.electric.mvp.contract.payment.RewriteCardContract;
import com.payexpress.electric.mvp.model.paymentModel.RewriteCardModel;
import com.payexpress.electric.mvp.ui.widget.AlertDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fengxiaozheng
 * on 2018/6/5.
 */
@Module
public class RewriteCardModule {

    private RewriteCardContract.View view;

    public RewriteCardModule(RewriteCardContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RewriteCardContract.View provideRCView () {
        return this.view;
    }

    @FragmentScope
    @Provides
    RewriteCardContract.Model provideRCModel(RewriteCardModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    AlertDialog provideAlertDialog() {
        return new AlertDialog(view.getActivity());
    }

}

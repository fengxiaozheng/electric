package com.payexpress.electric.mvp.model.paymentModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.RewriteInputContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteInputRes;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */
@FragmentScope
public class RewriteInputModel extends BaseModel implements RewriteInputContract.Model {

    @Inject
    public RewriteInputModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RewriteInputRes> getTermPassword() {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .getTermPassword();
    }
}

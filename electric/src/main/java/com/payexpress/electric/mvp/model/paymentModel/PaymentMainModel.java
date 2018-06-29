package com.payexpress.electric.mvp.model.paymentModel;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.PaymentMainContract;
import com.payexpress.electric.mvp.model.api.cache.CommonCache;
import com.payexpress.electric.mvp.model.api.service.PaymentService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PaymentMainRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteInputRes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by fengxiaozheng
 * on 2018/6/26.
 */
@FragmentScope
public class PaymentMainModel extends BaseModel implements PaymentMainContract.Model {

    @Inject
    public PaymentMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<PaymentMainRes> getPaymentMainFragmentItem() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(PaymentService.class)
                .getPaymentMainItem())
                .flatMap(paymentMainResObservable -> mRepositoryManager
                        .obtainCacheService(CommonCache.class)
                        .getPaymentMainFragmentInfo(paymentMainResObservable,
                                new DynamicKey("PaymentMainFragment"),
                                new EvictDynamicKey(false))
                        .map(paymentMainResReply -> paymentMainResReply.getData()));
    }

    @Override
    public Observable<RewriteInputRes> getTermPassword() {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .getTermPassword();
    }
}

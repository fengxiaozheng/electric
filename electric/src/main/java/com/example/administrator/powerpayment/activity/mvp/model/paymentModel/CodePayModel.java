package com.example.administrator.powerpayment.activity.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.example.administrator.powerpayment.activity.mvp.contract.payment.ElectricPayContract;
import com.example.administrator.powerpayment.activity.mvp.model.api.service.PaymentService;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.BaseResponse;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CheckPayRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CodePayReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.CodePayRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.NoCardBuyReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.RewriteCardRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartCardBuyCheckReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartCardBuyReq;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.SmartCardBuyRes;
import com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity.TransNoReq;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */
@FragmentScope
public class CodePayModel extends BaseModel implements ElectricPayContract.PayModel {

    @Inject
    public CodePayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<CodePayRes> doPay(CodePayReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .doPay(JSON.toJSONString(req));
    }

    @Override
    public Observable<CheckPayRes> checkPayStatus(TransNoReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .checkPayStatus(JSON.toJSONString(req));
    }

    @Override
    public Observable<BaseResponse> commonElectricBuy(TransNoReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .commonElectricBuy(JSON.toJSONString(req));
    }

    @Override
    public Observable<BaseResponse> noCardElectricBuy(NoCardBuyReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .noCardElectricBuy(JSON.toJSONString(req));
    }

    @Override
    public Observable<SmartCardBuyRes> hsaCardElectricBuy(SmartCardBuyReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .smartCardBuy(JSON.toJSONString(req));
    }

    @Override
    public Observable<BaseResponse> hasCardBuyCheck(SmartCardBuyCheckReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .smartCardBuyCheck(JSON.toJSONString(req));
    }

    @Override
    public Observable<RewriteCardRes> rewriteCard(RewriteCardReq req) {
        return mRepositoryManager.obtainRetrofitService(PaymentService.class)
                .rewriteCard(JSON.toJSONString(req));
    }
}

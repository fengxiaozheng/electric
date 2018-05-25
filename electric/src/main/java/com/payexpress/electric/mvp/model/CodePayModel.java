package com.payexpress.electric.mvp.model;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.ElectricPayContract;
import com.payexpress.electric.mvp.model.api.service.CommonService;
import com.payexpress.electric.mvp.model.entity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.payment.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.payment.CodePayReq;
import com.payexpress.electric.mvp.model.entity.payment.CodePayRes;
import com.payexpress.electric.mvp.model.entity.payment.TransNoReq;

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
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .doPay(JSON.toJSONString(req));
    }

    @Override
    public Observable<CheckPayRes> checkPayStatus(TransNoReq req) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .checkPayStatus(JSON.toJSONString(req));
    }

    @Override
    public Observable<BaseResponse> commonElectricBuy(TransNoReq req) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class)
                .commonElectricBuy(JSON.toJSONString(req));
    }
}

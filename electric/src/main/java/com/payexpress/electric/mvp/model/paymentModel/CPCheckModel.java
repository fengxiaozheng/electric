package com.payexpress.electric.mvp.model.paymentModel;

import com.alibaba.fastjson.JSON;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.payexpress.electric.mvp.contract.payment.CPCheckContract;
import com.payexpress.electric.mvp.model.api.service.PaymentService;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckReq;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.UserNoReq;

import org.json.JSONException;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fengxiaozheng
 * on 2018/5/14.
 */
@ActivityScope
public class CPCheckModel extends BaseModel implements CPCheckContract.Model {

    @Inject
    public CPCheckModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<CPUserInfoRes> getUserInfo(String userNo) {
        org.json.JSONObject object = new org.json.JSONObject();
        try {
            object.put("grid_user_code", userNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(PaymentService.class)
                .getCommonUserInfo(object.toString());
    }

    @Override
    public Observable<NoCardCheckRes> noCardCheck(NoCardCheckReq req) {
        return mRepositoryManager
                .obtainRetrofitService(PaymentService.class)
                .noCardCheck(JSON.toJSONString(req));
    }

    @Override
    public Observable<CardBalanceRes> queryCardBalance(UserNoReq req) {
        return mRepositoryManager
                .obtainRetrofitService(PaymentService.class)
                .queryCardBalance(JSON.toJSONString(req));
    }
}

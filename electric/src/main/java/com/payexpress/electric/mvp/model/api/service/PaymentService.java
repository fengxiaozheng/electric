/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.payexpress.electric.mvp.model.api.service;

import com.payexpress.electric.mvp.model.entity.paymentEntity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CardBalanceRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.CodePayRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.NoCardCheckRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PayRecordRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.PaymentMainRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.QuerySmartCardRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardListRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteCardRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteInputRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.RewriteTimesRes;
import com.payexpress.electric.mvp.model.entity.paymentEntity.SmartCardBuyRes;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * ================================================
 * 存放通用的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface PaymentService {

    @POST("payexpress/proxy5001.json")
    Observable<CPUserInfoRes> getCommonUserInfo(@Body String req);

    @POST("payexpress/proxy4002.json")
    Observable<CodePayRes> doPay(@Body String req);

    @POST("payexpress/proxy4003.json")
    Observable<CheckPayRes> checkPayStatus(@Body String req);

    @POST("payexpress/proxy4011.json")
    Observable<BaseResponse> commonElectricBuy(@Body String req);

    @POST("payexpress/proxy5007.json")
    Observable<PayRecordRes> payRecord(@Body String req);

    @POST("payexpress/proxy4001.json")
    Observable<NoCardCheckRes> noCardCheck(@Body String req);

    @POST("payexpress/proxy5009.json")
    Observable<CardBalanceRes> queryCardBalance(@Body String req);

    @POST("payexpress/proxy4004.json")
    Observable<BaseResponse> noCardElectricBuy(@Body String req);

    @POST("payexpress/proxy4007.json")
    Observable<QuerySmartCardRes> querySmartCardInfo(@Body String req);

    @POST("payexpress/proxy4008.json")
    Observable<SmartCardBuyRes> smartCardBuy(@Body String req);

    @POST("payexpress/proxy4009.json")
    Observable<BaseResponse> smartCardBuyCheck(@Body String req);

    @POST("payexpress/proxy4020.json")
    Observable<RewriteCardRes> rewriteCard(@Body String req);

    @POST("payexpress/proxy5010.json")
    Observable<RewriteCardListRes> rewriteCardTimesList(@Body String req);

    @POST("payexpress/proxy5011.json")
    Observable<RewriteTimesRes> rewriteCardTimes(@Body String req);

    @GET("citizen-restful/config")
    Observable<RewriteInputRes> getTermPassword();

    @GET("citizen-restful/func")
    Observable<PaymentMainRes> getPaymentMainItem();
}

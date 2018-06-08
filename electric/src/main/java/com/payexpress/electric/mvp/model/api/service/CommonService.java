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

import com.payexpress.electric.mvp.model.entity.BaseResponse;
import com.payexpress.electric.mvp.model.entity.payment.CPUserInfoRes;
import com.payexpress.electric.mvp.model.entity.payment.CardBalanceRes;
import com.payexpress.electric.mvp.model.entity.payment.CheckPayRes;
import com.payexpress.electric.mvp.model.entity.payment.CodePayRes;
import com.payexpress.electric.mvp.model.entity.payment.NoCardCheckRes;
import com.payexpress.electric.mvp.model.entity.payment.PayRecordRes;
import com.payexpress.electric.mvp.model.entity.payment.QuerySmartCardRes;
import com.payexpress.electric.mvp.model.entity.payment.RewriteCardListRes;
import com.payexpress.electric.mvp.model.entity.payment.RewriteCardRes;
import com.payexpress.electric.mvp.model.entity.payment.RewriteTimesRes;
import com.payexpress.electric.mvp.model.entity.payment.SmartCardBuyRes;

import io.reactivex.Observable;
import retrofit2.http.Body;
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
public interface CommonService {

    @POST("proxy5001.json")
    Observable<CPUserInfoRes> getCommonUserInfo(@Body String req);

    @POST("proxy4002.json")
    Observable<CodePayRes> doPay(@Body String req);

    @POST("proxy4003.json")
    Observable<CheckPayRes> checkPayStatus(@Body String req);

    @POST("proxy4011.json")
    Observable<BaseResponse> commonElectricBuy(@Body String req);

    @POST("proxy5007.json")
    Observable<PayRecordRes> payRecord(@Body String req);

    @POST("proxy4001.json")
    Observable<NoCardCheckRes> noCardCheck(@Body String req);

    @POST("proxy5009.json")
    Observable<CardBalanceRes> queryCardBalance(@Body String req);

    @POST("proxy4004.json")
    Observable<BaseResponse> noCardElectricBuy(@Body String req);

    @POST("proxy4007.json")
    Observable<QuerySmartCardRes> querySmartCardInfo(@Body String req);

    @POST("proxy4008.json")
    Observable<SmartCardBuyRes> smartCardBuy(@Body String req);

    @POST("proxy4009.json")
    Observable<BaseResponse> smartCardBuyCheck(@Body String req);

    @POST("proxy4020.json")
    Observable<RewriteCardRes> rewriteCard(@Body String req);

    @POST("proxy5010.json")
    Observable<RewriteCardListRes> rewriteCardTimesList(@Body String req);

    @POST("proxy5011.json")
    Observable<RewriteTimesRes> rewriteCardTimes(@Body String req);
}

package com.payexpress.electric.mvp.model.api.service;

import com.payexpress.electric.mvp.model.entity.ElectricUser;
import com.payexpress.electric.mvp.model.entity.LoginRes;
import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideDetailRes;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideListRes;
import com.payexpress.electric.mvp.model.entity.govEntity.GovGuideRes;
import com.payexpress.electric.mvp.model.entity.govEntity.GovLocationRes;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public interface GovService {

    @POST("login")
    Call<LoginRes> getLoginInfo(@Body ElectricUser user);

    @GET("citizen-restful/func")
    Observable<GovMainRes> getGovMainItem();

    @GET("citizen-restful/government/guide/category")
    Observable<GovGuideRes> getGovGuideItem();

    @GET("citizen-restful/government/company/category")
    Observable<GovGuideRes> getGovOnceItem();

    @GET("citizen-restful/government/guide")
    Observable<GovGuideListRes> getGovGuideList(@QueryMap Map<String, Object> options);

    @GET("citizen-restful/government/company")
    Observable<GovGuideListRes> getGovOnceList(@QueryMap Map<String, Object> options);

    @GET("citizen-restful/government/guide/{id}")
    Observable<GovGuideDetailRes> getGuideDetail(@Path("id") String id);

    @GET("citizen-restful/government/arch")
    Observable<GovLocationRes> getGovLocation();

    @GET("citizen-restful/government/business")
    Observable<BaseGovResponse> getGovBusiness(@Query("keyword") String keyWord);
}

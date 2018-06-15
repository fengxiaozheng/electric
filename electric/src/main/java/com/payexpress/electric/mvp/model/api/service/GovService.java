package com.payexpress.electric.mvp.model.api.service;

import com.payexpress.electric.mvp.model.entity.ElectricUser;
import com.payexpress.electric.mvp.model.entity.LoginRes;
import com.payexpress.electric.mvp.model.entity.govEntity.GovMainRes;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public interface GovService {

    @POST("login")
    Call<LoginRes> getLoginInfo(@Body ElectricUser user);

    @GET("citizen-restful/func")
    Observable<GovMainRes> getGovMainItem();
}

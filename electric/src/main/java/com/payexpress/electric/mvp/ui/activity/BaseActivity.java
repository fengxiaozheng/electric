package com.payexpress.electric.mvp.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.mvp.model.api.Api;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.ElectricUser;
import com.payexpress.electric.mvp.model.entity.LoginRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public class BaseActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String PREFS_DEVICE_ID = "access_token";
    protected boolean flag = false;//维true时方可进行网络请求，确保拿到token值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String PREFS_FILE = "access_token.xml";
        prefs = getSharedPreferences(PREFS_FILE, 0);
        String id = prefs.getString(PREFS_DEVICE_ID, (String) null);
        if (id != null) {
            flag = true;
            System.out.println("数据：AccessTokenFactory的存储在pres文件中的id" + id);
        } else {
            getData();
        }
    }

    private void getData() {
        String uuid = StringUtils.getUuid(this);
        StringBuilder sb = new StringBuilder();
        sb.append(uuid).append("-citizen");
        String psd = StringUtils.MD5(sb.toString());
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Api.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GovService service = retrofit.create(GovService.class);
        System.out.println("md5数据："+psd);
        ElectricUser user = new ElectricUser();
        user.setUsername(uuid);
        user.setPassword(psd);
        Call<LoginRes> observable = service.getLoginInfo(user);
        observable.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.body().isSuccess()) {
                    prefs.edit().putString(PREFS_DEVICE_ID, response.body().getToken()).commit();
                    flag = true;
                }
                System.out.println("数据1："+ JSON.toJSONString(response.body()));
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                System.out.println("数据错误："+JSON.toJSONString(t));
            }
        });
    }
}

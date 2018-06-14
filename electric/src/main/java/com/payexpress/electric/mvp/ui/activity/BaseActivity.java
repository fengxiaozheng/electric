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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                baseUrl(Api.GOV_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GovService service = retrofit.create(GovService.class);
        System.out.println("md5数据："+psd);
        ElectricUser user = new ElectricUser();
        user.setUsername(uuid);
        user.setPassword(psd);
        Observable<LoginRes> observable = service.getLoginInfo(user);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DefaultObserver<LoginRes>() {
            @Override
            public void onNext(LoginRes response) {
                if (response.isSuccess()) {
                    prefs.edit().putString(PREFS_DEVICE_ID, response.getToken()).commit();
                    flag = true;
                }
                System.out.println("数据1："+ JSON.toJSONString(response));

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}

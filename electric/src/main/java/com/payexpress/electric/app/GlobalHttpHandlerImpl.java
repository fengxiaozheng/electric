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
package com.payexpress.electric.app;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jess.arms.http.GlobalHttpHandler;
import com.payexpress.electric.app.utils.StringUtils;
import com.payexpress.electric.mvp.model.api.Api;
import com.payexpress.electric.mvp.model.api.service.GovService;
import com.payexpress.electric.mvp.model.entity.APIBodyData;
import com.payexpress.electric.mvp.model.entity.ElectricUser;
import com.payexpress.electric.mvp.model.entity.LoginRes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;
    private Gson gson;
    private Response responseResult = null;
    private ExecutorService executor;
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private String[] apiData = {"4001", "4002", "4003", "4004", "4007",
            "4008", "4009", "4011", "4020", "5001", "5007", "5009",
            "5010", "5011"};

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
        gson = new Gson();
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                    /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                       重新请求token,并重新执行请求 */


                 /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                    注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                    create a new request and modify it accordingly using the new token
                    Request newRequest = chain.request().newBuilder().header("token", newToken)
                                         .build();

                    retry the request

                    response.body().close();
                    如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                    如果不需要返回新的结果,则直接把response参数返回出去 */


        System.out.println("数据：返回" + response.request().url());

        if (response.request().url().toString().contains("payexpress")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

            Map<String, Object> resultMap = null;
            try {
                resultMap = gson.fromJson(response.body().string(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = null;
            System.out.println("未解密的服务器数据：" + resultMap);
            try {
                result = AESCipher.aesDecryptString(resultMap.get("data").toString(), AESCipher.KEY);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("解密的服务器数据：" + result);
            ResponseBody body = ResponseBody.create(MEDIA_TYPE, result);
            responseResult = response.newBuilder().body(body).build();

        } else {
            try {
                String x = response.body().string();
                JSONObject d = JSON.parseObject(x);
                if (d.getString("code").equals("000006")) {


                    String uuid = StringUtils.getUuid(context);
                    StringBuilder sb = new StringBuilder();
                    sb.append(uuid).append("-citizen");
                    String psd = StringUtils.MD5(sb.toString());
                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl(Api.LOGIN_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    GovService service = retrofit.create(GovService.class);
                    System.out.println("接口md5数据：" + psd);
                    ElectricUser user = new ElectricUser();
                    user.setUsername(uuid);
                    user.setPassword(psd);
                    retrofit2.Call<LoginRes> observable = service.getLoginInfo(user);
                    retrofit2.Response<LoginRes> m = observable.execute();
                    System.out.println("同步token数据："+m.body().getToken());
                    StringUtils.setToken(context, m.body().getToken());
                                        requestAgain(chain);

                } else {
                    ResponseBody body = ResponseBody.create(MEDIA_TYPE, x);
                    responseResult = response.newBuilder().body(body).build();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseResult;
    }

    // 这里可以在请求服务器之前可以拿到request,做一些操作比如给request统一添加token或者header以及参数加密等操作
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                    /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的request如增加header,不做操作则直接返回request参数*/
        if (request.url().toString().contains("payexpress")) {
            RetrofitUrlManager.getInstance().setGlobalDomain(Api.ELECTRIC_BASE_URL);
            RequestBody oldBody = request.body();
            Buffer buffer = new Buffer();
            try {
                oldBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String strOldBody = buffer.readUtf8();
            JSONObject deal = JSON.parseObject(strOldBody);
            deal.put("access_token", Api.ELECTRIC_ACCESS_TOKEN);
            deal.put(Api.termNo, StringUtils.getConfig(context, Api.termNo));
            deal.put(Api.mchtNo, StringUtils.getConfig(context, Api.mchtNo));
            String newData = JSON.toJSONString(deal);
            System.out.println("request中传递的json数据：" + newData);
            APIBodyData data = new APIBodyData();
            try {
                data.setData(AESCipher.aesEncryptString(newData, AESCipher.KEY));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String postBody = gson.toJson(data);
            System.out.println("request转化后的数据：" + postBody);
            RequestBody body = RequestBody.create(MEDIA_TYPE, postBody);
            return request.newBuilder().method(request.method(), body).build();
        } else {
            RetrofitUrlManager.getInstance().setGlobalDomain(Api.BASE_URL);
            return request.newBuilder()
                    .header("X-Authorization", StringUtils.getToken(context)).build();
        }
    }

    private void requestAgain(Interceptor.Chain chain) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request newRequst = chain.request().newBuilder()
                .header("X-Authorization", StringUtils.getToken(context)).build();
        Call call = okHttpClient.newCall(newRequst);
        try {
            responseResult = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

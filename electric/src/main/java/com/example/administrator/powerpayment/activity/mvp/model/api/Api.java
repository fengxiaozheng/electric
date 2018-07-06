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
package com.example.administrator.powerpayment.activity.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    //String ELECTRIC_BASE_URL = "http://182.140.224.136:7080/";测试
  //  String ELECTRIC_BASE_URL = "http://118.122.120.25:10000/";
    String ELECTRIC_BASE_URL = "https://mpos.cdzft.cn:18099/";//生产
   // String ELECTRIC_ACCESS_TOKEN = "ZFTUSER_5BCAACF499D30D1E3B16AFA719797012";//测试
    //String BASE_URL = "http://118.122.120.25:10000/";//本地
    String BASE_URL = "http://citizen.cdzft.cn:8080/";//生产
   // String ELECTRIC_ACCESS_TOKEN = "ZFTUSER_82A2E47E3A035D209D4118797DDFB016";//本地
    String ELECTRIC_ACCESS_TOKEN = "ZFTUSER_9C899315CD3929517733068CE54241EF";//生产
    String RequestSuccess = "0000";
    String OtherRequestSuccess = "000000";
    String LOGIN_URL = BASE_URL + "citizen-restful/";
    String termPsd = "termPsd";
    String mchtNo = "merchant_no";
    String termNo = "terminal_no";
}

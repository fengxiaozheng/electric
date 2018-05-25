package com.payexpress.electric.mvp.model.entity;

import com.payexpress.electric.mvp.model.api.Api;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public class BaseRequest implements Serializable {

    private final String access_token = Api.ACCESS_TOKEN;


    public String getAccess_token() {
        return access_token;
    }
}

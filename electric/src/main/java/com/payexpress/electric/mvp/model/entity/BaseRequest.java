package com.payexpress.electric.mvp.model.entity;

import com.payexpress.electric.mvp.model.api.Api;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public class BaseRequest implements Serializable {


    public String getAccess_token() {
        return Api.ACCESS_TOKEN;
    }
}

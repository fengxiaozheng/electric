package com.payexpress.electric.mvp.model.entity;

import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;

/**
 * Created by fengxiaozheng
 * on 2018/6/14.
 */

public class LoginRes extends BaseGovResponse {
    private String token;

    public String getToken() {
        return token;
    }
}

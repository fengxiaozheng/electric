package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseRequest;

/**
 * Created by fengxiaozheng
 * on 2018/5/28.
 */

public class NoCardCheckReq extends BaseRequest {

    private String g3;

    public String getG3() {
        return g3;
    }

    public void setG3(String g3) {
        this.g3 = g3;
    }
}

package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */

public class QuerySmartCardRes extends BaseResponse {

    private String g3;
    private String g7;
    private String g8;
    private String g9;
    private String g10;

    public String getG3() {
        return g3;
    }

    public String getG7() {
        return g7;
    }

    public String getG8() {
        return g8;
    }

    public String getG9() {
        return g9;
    }

    public String getG10() {
        return g10;
    }
}

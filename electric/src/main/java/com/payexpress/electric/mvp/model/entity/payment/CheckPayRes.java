package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public class CheckPayRes extends BaseResponse {

    private String pay_status;

    public String getPay_status() {
        return pay_status;
    }
}

package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public class CodePayRes extends BaseResponse {

    private String trans_no;
    private String pay_status;

    public String getTrans_no() {
        return trans_no;
    }

    public String getPay_status() {
        return pay_status;
    }
}

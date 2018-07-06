package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

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

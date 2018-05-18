package com.payexpress.electric.mvp.model.entity;

/**
 * Created by fengxiaozheng
 * on 2018/5/17.
 */

public class CPUserInfoRes extends BaseResponse {

    private String eseb_no;
    private String grid_user_name;
    private String grid_user_code;
    private String address;
    private String balance;
    private String grid_month;
    private String pay_amount;
    private String break_amount;
    private String payable_amount;

    public String getEseb_no() {
        return eseb_no;
    }

    public String getGrid_user_name() {
        return grid_user_name;
    }

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getGrid_month() {
        return grid_month;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public String getBreak_amount() {
        return break_amount;
    }

    public String getPayable_amount() {
        return payable_amount;
    }
}

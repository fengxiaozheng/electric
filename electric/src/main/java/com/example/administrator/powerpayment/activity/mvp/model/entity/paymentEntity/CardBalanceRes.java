package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/28.
 */

public class CardBalanceRes extends BaseResponse {

    private String grid_user_code;
    private String grid_user_name;
    private String address;
    private String balance;
    private String acquisition_time;

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public String getGrid_user_name() {
        return grid_user_name;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getAcquisition_time() {
        return acquisition_time;
    }
}

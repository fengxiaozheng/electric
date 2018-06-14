package com.payexpress.electric.mvp.model.entity.paymentEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/28.
 */

public class NoCardCheckRes extends BaseResponse {

    private String user_name;
    private String grid_user_code;
    private String address;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public void setGrid_user_code(String grid_user_code) {
        this.grid_user_code = grid_user_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

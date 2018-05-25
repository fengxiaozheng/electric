package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseRequest;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */

public class UserNoReq extends BaseRequest {
    private String grid_user_code;

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public void setGrid_user_code(String grid_user_code) {
        this.grid_user_code = grid_user_code;
    }
}

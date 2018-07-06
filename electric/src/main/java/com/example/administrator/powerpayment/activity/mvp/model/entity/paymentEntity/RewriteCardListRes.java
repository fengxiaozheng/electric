package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/5/30.
 */

public class RewriteCardListRes extends BaseResponse {

    private String pay_date;
    private String grid_user_code;
    private String grid_user_name;
    private String pay_count;
    private List<RewriteTimesInfo> rows;

    public String getPay_date() {
        return pay_date;
    }

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public String getGrid_user_name() {
        return grid_user_name;
    }

    public String getPay_count() {
        return pay_count;
    }

    public List<RewriteTimesInfo> getRows() {
        return rows;
    }
}

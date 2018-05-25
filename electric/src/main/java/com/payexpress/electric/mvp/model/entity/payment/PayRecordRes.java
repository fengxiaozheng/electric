package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */

public class PayRecordRes extends BaseResponse {

    private String grid_user_code;
    private String grid_user_name;
    private int count;
    private List<RecordInfo> rows;


    public String getGrid_user_code() {
        return grid_user_code;
    }

    public String getGrid_user_name() {
        return grid_user_name;
    }

    public int getCount() {
        return count;
    }

    public List<RecordInfo> getRows() {
        return rows;
    }
}

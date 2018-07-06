package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */

public class GovLocationRes extends BaseGovResponse {

    private int total;
    private List<GovLocationInfo> data;

    public int getTotal() {
        return total;
    }

    public List<GovLocationInfo> getData() {
        return data;
    }
}

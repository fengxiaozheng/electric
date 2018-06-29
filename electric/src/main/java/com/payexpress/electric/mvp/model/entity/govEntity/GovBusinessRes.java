package com.payexpress.electric.mvp.model.entity.govEntity;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */

public class GovBusinessRes extends BaseGovResponse {

    private int total;
    private List<GovBusinessInfo> data;

    public int getTotal() {
        return total;
    }

    public List<GovBusinessInfo> getData() {
        return data;
    }
}

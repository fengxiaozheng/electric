package com.payexpress.electric.mvp.model.entity.govEntity;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public class GovGuideListRes extends BaseGovResponse {
    private List<GovGuideListInfo> data;
    private int total;

    public List<GovGuideListInfo> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}

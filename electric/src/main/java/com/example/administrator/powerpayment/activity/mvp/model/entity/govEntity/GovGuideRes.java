package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public class GovGuideRes extends BaseGovResponse {

    private List<GovGuideInfo> data;

    public List<GovGuideInfo> getData() {
        return data;
    }
}

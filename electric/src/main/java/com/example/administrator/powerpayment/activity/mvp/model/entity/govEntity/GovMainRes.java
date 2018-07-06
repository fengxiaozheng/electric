package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

import com.example.administrator.powerpayment.activity.mvp.model.entity.MainFragmentItemInfo;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/15.
 */

public class GovMainRes extends BaseGovResponse {

    private List<MainFragmentItemInfo> governmentServices;

    public List<MainFragmentItemInfo> getGovernmentServices() {
        return governmentServices;
    }
}

package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

import com.example.administrator.powerpayment.activity.mvp.model.entity.MainFragmentItemInfo;
import com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity.BaseGovResponse;

import java.util.List;

/**
 * Created by fengxiaozheng
 * on 2018/6/26.
 */

public class PaymentMainRes extends BaseGovResponse {

    private List<MainFragmentItemInfo> conveniences;

    public List<MainFragmentItemInfo> getConveniences() {
        return conveniences;
    }
}

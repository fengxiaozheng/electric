package com.payexpress.electric.mvp.model.entity.paymentEntity;

import com.payexpress.electric.mvp.model.entity.govEntity.BaseGovResponse;

/**
 * Created by fengxiaozheng
 * on 2018/6/15.
 */

public class RewriteInputRes extends BaseGovResponse {

    private String writeCardPassword;
    private String merchantNo;
    private String terminalNo;

    public String getWriteCardPassword() {
        return writeCardPassword;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public String getTerminalNo() {
        return terminalNo;
    }
}

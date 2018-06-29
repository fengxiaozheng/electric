package com.payexpress.electric.mvp.model.entity.govEntity;

import com.payexpress.electric.mvp.model.api.Api;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/6/13.
 */

public class BaseGovResponse implements Serializable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (code.equals(Api.OtherRequestSuccess)) {
            return true;
        } else {
            return false;
        }
    }
}

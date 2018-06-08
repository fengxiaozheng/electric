package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

/**
 * Created by fengxiaozheng
 * on 2018/4/27.
 */

public class RewriteCardRes extends BaseResponse {
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String authKey;
    private String outKey;
    private String useKey;
    private String file1;
    private String file2;
    private String file31;
    private String file32;
    private String file41;
    private String file42;
    private String file5;

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }

    public String getKey3() {
        return key3;
    }

    public String getKey4() {
        return key4;
    }

    public String getAuthKey() {
        return authKey;
    }

    public String getOutKey() {
        return outKey;
    }

    public String getUseKey() {
        return useKey;
    }

    public String getFile1() {
        return file1;
    }

    public String getFile2() {
        return file2;
    }

    public String getFile31() {
        return file31;
    }

    public String getFile32() {
        return file32;
    }

    public String getFile41() {
        return file41;
    }

    public String getFile42() {
        return file42;
    }

    public String getFile5() {
        return file5;
    }
}

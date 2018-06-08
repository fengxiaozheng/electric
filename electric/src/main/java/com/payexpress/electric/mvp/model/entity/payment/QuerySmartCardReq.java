package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseRequest;

/**
 * Created by fengxiaozheng
 * on 2018/5/29.
 */

public class QuerySmartCardReq extends BaseRequest {

    private String sesb_no;
    private String g5;
    private String g6;
    private String g7;
    private String g8;
    private String g9;

    public String getSesb_no() {
        return sesb_no;
    }

    public void setSesb_no(String sesb_no) {
        this.sesb_no = sesb_no;
    }

    public String getG5() {
        return g5;
    }

    public void setG5(String g5) {
        this.g5 = g5;
    }

    public String getG6() {
        return g6;
    }

    public void setG6(String g6) {
        this.g6 = g6;
    }

    public String getG7() {
        return g7;
    }

    public void setG7(String g7) {
        this.g7 = g7;
    }

    public String getG8() {
        return g8;
    }

    public void setG8(String g8) {
        this.g8 = g8;
    }

    public String getG9() {
        return g9;
    }

    public void setG9(String g9) {
        this.g9 = g9;
    }
}

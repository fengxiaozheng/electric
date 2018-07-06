package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/28.
 */

public class NoCardBuyReq extends BaseRequest {

    private String trans_no;
    private String cellphone;

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}

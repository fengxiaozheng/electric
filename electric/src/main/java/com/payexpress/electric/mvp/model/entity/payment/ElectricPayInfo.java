package com.payexpress.electric.mvp.model.entity.payment;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/5/21.
 */

public class ElectricPayInfo implements Serializable {

    private int flag;
    private String user_name;
    private String user_no;
    private String user_address;
    private String pay_amount;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }
}

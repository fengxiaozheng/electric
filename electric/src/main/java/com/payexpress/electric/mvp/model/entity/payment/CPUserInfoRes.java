package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseResponse;

/**
 * Created by fengxiaozheng
 * on 2018/5/17.
 */

public class CPUserInfoRes extends BaseResponse {

    private String eseb_no;
    private String grid_user_name;
    private String grid_user_code;
    private String address;
    private String balance;
    private String grid_month;
    private String pay_amount;
    private String break_amount;
    private String payable_amount;

    public String getEseb_no() {
        return eseb_no;
    }

    public String getGrid_user_name() {
        return grid_user_name;
    }

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getGrid_month() {
        return grid_month;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public String getBreak_amount() {
        return break_amount;
    }

    public String getPayable_amount() {
        return payable_amount;
    }

    public void setEseb_no(String eseb_no) {
        this.eseb_no = eseb_no;
    }

    public void setGrid_user_name(String grid_user_name) {
        this.grid_user_name = grid_user_name;
    }

    public void setGrid_user_code(String grid_user_code) {
        this.grid_user_code = grid_user_code;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setGrid_month(String grid_month) {
        this.grid_month = grid_month;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public void setBreak_amount(String break_amount) {
        this.break_amount = break_amount;
    }

    public void setPayable_amount(String payable_amount) {
        this.payable_amount = payable_amount;
    }
}

package com.payexpress.electric.mvp.model.entity.payment;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/5/16.
 */

public class SmartUserInfo implements Serializable {
    private int flag;
    private String userNo;
    private String userName;
    private String userAddress;
    private String balance;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

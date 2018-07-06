package com.example.administrator.powerpayment.activity.mvp.model.entity.paymentEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/24.
 */

public class RecordInfo {

    private String trans_date;
    private String amount;
    private String agency;
    private String branch;
    private String teller;
    private String grid_trans_no;

    public String getTrans_date() {
        return trans_date;
    }

    public String getAmount() {
        return amount;
    }

    public String getAgency() {
        return agency;
    }

    public String getBranch() {
        return branch;
    }

    public String getTeller() {
        return teller;
    }

    public String getGrid_trans_no() {
        return grid_trans_no;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setTeller(String teller) {
        this.teller = teller;
    }

    public void setGrid_trans_no(String grid_trans_no) {
        this.grid_trans_no = grid_trans_no;
    }
}

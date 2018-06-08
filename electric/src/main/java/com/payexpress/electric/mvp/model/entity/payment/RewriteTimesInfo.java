package com.payexpress.electric.mvp.model.entity.payment;

/**
 * Created by fengxiaozheng
 * on 2018/5/30.
 */

public class RewriteTimesInfo {

    private String pay_date;
    private String pay_amount;
    private String writable_amount;
    private String pay_count;
    private String arch;
    private String site_no;
    private String teller;
    private String grid_trans_no;

    public String getPay_date() {
        return pay_date;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public String getWritable_amount() {
        return writable_amount;
    }

    public String getPay_count() {
        return pay_count;
    }

    public String getArch() {
        return arch;
    }

    public String getSite_no() {
        return site_no;
    }

    public String getTeller() {
        return teller;
    }

    public String getGrid_trans_no() {
        return grid_trans_no;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public void setWritable_amount(String writable_amount) {
        this.writable_amount = writable_amount;
    }

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public void setSite_no(String site_no) {
        this.site_no = site_no;
    }

    public void setTeller(String teller) {
        this.teller = teller;
    }

    public void setGrid_trans_no(String grid_trans_no) {
        this.grid_trans_no = grid_trans_no;
    }
}

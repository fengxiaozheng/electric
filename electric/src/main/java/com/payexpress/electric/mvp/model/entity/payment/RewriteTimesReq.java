package com.payexpress.electric.mvp.model.entity.payment;

import com.payexpress.electric.mvp.model.entity.BaseRequest;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */

public class RewriteTimesReq extends BaseRequest {

    private String file1;
    private String file2;
    private String file31;
    private String file32;
    private String file41;
    private String file42;
    private String file5;
    private String user_card_info;//卡片信息
    private String user_seq;//卡序列号
    private String user_random;//卡随机数
    private String pay_count;
    private String grid_user_code;

    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public String getFile31() {
        return file31;
    }

    public void setFile31(String file31) {
        this.file31 = file31;
    }

    public String getFile32() {
        return file32;
    }

    public void setFile32(String file32) {
        this.file32 = file32;
    }

    public String getFile41() {
        return file41;
    }

    public void setFile41(String file41) {
        this.file41 = file41;
    }

    public String getFile42() {
        return file42;
    }

    public void setFile42(String file42) {
        this.file42 = file42;
    }

    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }

    public String getUser_card_info() {
        return user_card_info;
    }

    public void setUser_card_info(String user_card_info) {
        this.user_card_info = user_card_info;
    }

    public String getUser_seq() {
        return user_seq;
    }

    public void setUser_seq(String user_seq) {
        this.user_seq = user_seq;
    }

    public String getUser_random() {
        return user_random;
    }

    public void setUser_random(String user_random) {
        this.user_random = user_random;
    }

    public String getPay_count() {
        return pay_count;
    }

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }

    public String getGrid_user_code() {
        return grid_user_code;
    }

    public void setGrid_user_code(String grid_user_code) {
        this.grid_user_code = grid_user_code;
    }
}

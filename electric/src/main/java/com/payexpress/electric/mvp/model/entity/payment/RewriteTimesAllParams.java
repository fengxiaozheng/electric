package com.payexpress.electric.mvp.model.entity.payment;

import java.io.Serializable;

/**
 * Created by fengxiaozheng
 * on 2018/6/6.
 */

public class RewriteTimesAllParams implements Serializable {

    private String file1;
    private String file2;
    private String file3;
    private String file31;
    private String file32;
    private String file4;
    private String file41;
    private String file42;
    private String file5;
    private String card_info;//卡片信息
    private String card_seq;//卡序列号
    private String card_random;//卡随机数

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

    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
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

    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
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

    public String getCard_info() {
        return card_info;
    }

    public void setCard_info(String card_info) {
        this.card_info = card_info;
    }

    public String getCard_seq() {
        return card_seq;
    }

    public void setCard_seq(String card_seq) {
        this.card_seq = card_seq;
    }

    public String getCard_random() {
        return card_random;
    }

    public void setCard_random(String card_random) {
        this.card_random = card_random;
    }
}

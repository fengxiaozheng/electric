package com.payexpress.electric.mvp.model.entity.paymentEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/30.
 */

public class SmartCardBuyCheckReq extends BaseRequest {

    private String sesb_no;
    private String file1;
    private String file2;
    private String file3;
    private String file4;
    private String file5;

    public String getSesb_no() {
        return sesb_no;
    }

    public void setSesb_no(String sesb_no) {
        this.sesb_no = sesb_no;
    }

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

    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }
}

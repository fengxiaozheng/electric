package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

/**
 * Created by fengxiaozheng
 * on 2018/6/25.
 */

public class GovBusinessDetailRes extends BaseGovResponse {

    private String id;
    private String idNo;
    private String archName;
    private String name;
    private String visitCount;
    private String logs;
    private String createdAt;
    private String updatedAt;
    private String status;

    public String getId() {
        return id;
    }

    public String getIdNo() {
        return idNo;
    }

    public String getArchName() {
        return archName;
    }

    public String getName() {
        return name;
    }

    public String getVisitCount() {
        return visitCount;
    }

    public String getLogs() {
        return logs;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }
}

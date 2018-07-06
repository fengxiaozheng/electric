package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */

public class GovGuideDetailRes extends BaseGovResponse {
    private String id;
    private String categoryId;
    private String categoryName;
    private String categoryPart;
    private String title;
    private String guideId;
    private String createdAt;
    private String updatedAt;
    private String author;
    private String contents;
    private String condition;
    private String law;
    private String policy;
    private String fileDoc;

    public String getId() {
        return id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryPart() {
        return categoryPart;
    }

    public String getTitle() {
        return title;
    }

    public String getGuideId() {
        return guideId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public String getCondition() {
        return condition;
    }

    public String getLaw() {
        return law;
    }

    public String getPolicy() {
        return policy;
    }

    public String getFileDoc() {
        return fileDoc;
    }
}

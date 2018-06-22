package com.payexpress.electric.mvp.model.entity.govEntity;

/**
 * Created by fengxiaozheng
 * on 2018/6/19.
 */

public class GovGuideListInfo {
    private String id;
    private String categoryId;
    private String categoryName;
    private String title;
    private String contents;
    private String author;
    private String guideId;
    private String condition;
    private String law;
    private String policy;
    private String visitCount;
    private String fileDoc;
    private String createdAt;
    private String updatedAt;

    //仅跑一次
    private String docName;
    private String htmlName;

    public String getId() {
        return id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    public String getGuideId() {
        return guideId;
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

    public String getVisitCount() {
        return visitCount;
    }

    public String getFileDoc() {
        return fileDoc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDocName() {
        return docName;
    }

    public String getHtmlName() {
        return htmlName;
    }
}

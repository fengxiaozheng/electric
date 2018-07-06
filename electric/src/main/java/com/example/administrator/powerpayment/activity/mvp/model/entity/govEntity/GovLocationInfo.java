package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

/**
 * Created by fengxiaozheng
 * on 2018/6/21.
 */

public class GovLocationInfo {
    private String streetName;
    private String id;
    private String streetId;
    private String name;
    private String tel;
    private String workStart;
    private String workEnd;
    private String address;
    private double longitude;
    private double latitude;
    private String url;
    private String imageUrl;
    private String createdAt;

    public String getStreetName() {
        return streetName;
    }

    public String getId() {
        return id;
    }

    public String getStreetId() {
        return streetId;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getWorkStart() {
        return workStart;
    }

    public String getWorkEnd() {
        return workEnd;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

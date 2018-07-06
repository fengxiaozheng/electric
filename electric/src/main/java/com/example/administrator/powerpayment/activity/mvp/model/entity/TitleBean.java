package com.example.administrator.powerpayment.activity.mvp.model.entity;

import java.io.Serializable;

/**
 * 字幕表
 */

public class TitleBean implements Serializable {

    private String titles;  //  标题
    private String content; //  内容
    private int onOff = 1; //0是关，1是开

    private long startTime;    //开始时间
    private long failureTime;  //失效时间
    private String color = "#FFFFFF";   //颜色

    public TitleBean(String s) {
        content = s;
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(long failureTime) {
        this.failureTime = failureTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }
}

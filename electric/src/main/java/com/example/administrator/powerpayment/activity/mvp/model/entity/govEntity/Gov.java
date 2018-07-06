package com.example.administrator.powerpayment.activity.mvp.model.entity.govEntity;

/**
 * Created by fengxiaozheng
 * on 2018/5/10.
 */

public class Gov {
    private final int image;
    private final String title;


    public Gov(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}

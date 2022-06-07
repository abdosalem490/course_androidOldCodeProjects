package com.abdosalm.cmp2024.Activities.Model;

public class FeatureModel {
    private String title;
    private int ImageId;

    public FeatureModel(String title, int imageId) {
        this.title = title;
        ImageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }
}

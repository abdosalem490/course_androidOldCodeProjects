package com.abdosalm.cmp2024.Activities.Model;

public class YoutubeSubject {
    private String photoURL;
    private String title;

    public YoutubeSubject(String photoURL, String title) {
        this.photoURL = photoURL;
        this.title = title;
    }

    public YoutubeSubject() {
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

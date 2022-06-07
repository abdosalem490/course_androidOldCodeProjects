package com.abdosalm.cmp2024.Activities.Model;

import java.util.List;

public class YoutubeSemester {
    private String title;
    private List<YoutubeSubject> subjectList;

    public YoutubeSemester(String title, List<YoutubeSubject> subjectList) {
        this.title = title;
        this.subjectList = subjectList;
    }

    public YoutubeSemester() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<YoutubeSubject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<YoutubeSubject> subjectList) {
        this.subjectList = subjectList;
    }
}

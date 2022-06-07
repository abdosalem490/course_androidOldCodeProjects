package com.abdosalm.mycourselistfragment.data;

import android.content.Context;

public class Course {
    private String courseName;
    private String courseImage;

    public int getImageResourceId(Context context){
        return context.getResources().getIdentifier(this.courseImage , "drawable" , context.getPackageName());
    }

    public Course(String courseName, String courseImage) {
        this.courseName = courseName;
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }
}

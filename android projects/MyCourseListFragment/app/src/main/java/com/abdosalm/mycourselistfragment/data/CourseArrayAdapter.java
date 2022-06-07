package com.abdosalm.mycourselistfragment.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abdosalm.mycourselistfragment.R;

import java.util.List;

public class CourseArrayAdapter extends ArrayAdapter<Course> {
    private Context context;
    private List<Course> courses;
    public CourseArrayAdapter(@NonNull Context context, int resource, List<Course> courses) {
        super(context, resource, courses);
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Course course = courses.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.course_listitem , null);
        ImageView imageView = view.findViewById(R.id.courseImageId);
        imageView.setImageResource(course.getImageResourceId(context));
        TextView textView = view.findViewById(R.id.courseName);
        textView.setText(course.getCourseName());
        return view;
    }
}

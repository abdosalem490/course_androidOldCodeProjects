package com.abdosalm.mycourselistfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.abdosalm.mycourselistfragment.Util.ScreenUtility;
import com.abdosalm.mycourselistfragment.data.Course;
import com.abdosalm.mycourselistfragment.data.CourseArrayAdapter;
import com.abdosalm.mycourselistfragment.data.CourseData;

import java.util.List;

public class CourseListFragment extends ListFragment {
    List<Course> courses = new CourseData().courseList();
    private static final String TAG = "MyFragment";
    private Callbacks activity;
    public CourseListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtility screenUtility =  new ScreenUtility(getActivity());
        Log.d(TAG, "onCreate: "+screenUtility.getDpWidth());
        CourseArrayAdapter adapter = new CourseArrayAdapter(getActivity() , R.layout.course_listitem , courses);
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment, container , false);
        return view;
    }
    public interface Callbacks{
        void onItemSelected(Course course , int position);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Course course = courses.get(position);
        this.activity.onItemSelected(course , position);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Callbacks) context;
    }
}

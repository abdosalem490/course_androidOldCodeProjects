package com.example.tasktimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class FirstFragment extends Fragment
{
    private static final String TAG = "FirstFragment";
    private enum FragmentEditMode{EDIT , ADD}

    private FragmentEditMode mMode;
    private EditText mNameTextView;
    private EditText mDescriptionTextView;
    private EditText mSortOrderTextView;
    private Button mSaveButton;
    private OnSaveClicked mSaveListener = null;

    interface OnSaveClicked
    {
        void onSaveClicked();
    }
    public FirstFragment()
    {
        Log.d(TAG, "FirstFragment: constructor called");
    }

    public boolean canClose()
    {
        return false;
    }
    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        Log.d(TAG, "onAttach: starts");
        super.onAttach(context);
        Activity activity = getActivity();
        if (!(activity instanceof OnSaveClicked))
        {
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement addEditActivityFragment.OnSaveClicked interface");

        }
        mSaveListener = (OnSaveClicked) activity;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: starts");
        super.onDetach();
        mSaveListener = null;
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit , container , false);
        mNameTextView = (EditText) view.findViewById(R.id.addedit_name);
        mDescriptionTextView = (EditText) view.findViewById(R.id.addedit_description);
        mSortOrderTextView = (EditText) view.findViewById(R.id.assedit_sortorder);
        mSaveButton = (Button) view.findViewById(R.id.addedit_save);

       // Bundle arguments = getActivity().getIntent().getExtras();
        Bundle arguments =getArguments();

        final Task task;
        if (arguments != null)
        {
            Log.d(TAG, "onCreateView: retrieving task details");
            task = (Task)arguments.getSerializable(Task.class.getSimpleName());
            if (task != null)
            {
                Log.d(TAG, "onCreateView: Task details found , editing...");
                mNameTextView.setText(task.getName());
                mDescriptionTextView.setText(task.getDescription());
                mSortOrderTextView.setText(Integer.toString(task.getSortOrder()));
                mMode = FragmentEditMode.EDIT;
            }else{
                mMode = FragmentEditMode.ADD;
            }
        }else{
            task = null;
            Log.d(TAG, "onCreateView: No arguments , adding new record");
            mMode = FragmentEditMode.ADD;
        }
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int so;
                if(mSortOrderTextView.length() > 0)
                {
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                }else
                {
                    so =0;
                }
                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();
                switch (mMode)
                {
                    case EDIT:
                        if (task == null) {
                            // remove warnings , will never execute
                            break;
                        }
                        if (! mNameTextView.getText().toString().equals(task.getName())) {
                            values.put(TasksContract.Columns.TASKS_NAME, mNameTextView.getText().toString());
                        }
                        if (!mDescriptionTextView.getText().toString().equals(task.getDescription()))
                        {
                            values.put(TasksContract.Columns.TASKS_DESCRIPTION, mDescriptionTextView.getText().toString());
                        }
                        if (so != task.getSortOrder())
                        {
                            values.put(TasksContract.Columns.TASKS_SORTORDER , so);
                        }
                        if (values.size() != 0)
                        {
                            Log.d(TAG, "onClick: updating task");
                            contentResolver.update(TasksContract.buildTaskUri(task.getId()) , values ,  null , null);
                        }
                        break;
                    case ADD:
                        if (mNameTextView.length() >0)
                        {
                            Log.d(TAG, "onClick: adding new Task");
                            values.put(TasksContract.Columns.TASKS_NAME , mNameTextView.getText().toString());
                            values.put(TasksContract.Columns.TASKS_DESCRIPTION , mDescriptionTextView.getText().toString());
                            values.put(TasksContract.Columns.TASKS_SORTORDER , so);
                            contentResolver.insert(TasksContract.CONTENT_URI , values);
                        }
                        break;
                }
                Log.d(TAG, "onClick: Done editing");
                if (mSaveListener != null)
                {
                    mSaveListener.onSaveClicked();
                }
            }
        });
        Log.d(TAG, "onCreateView: exiting");
        return view;
    }

}

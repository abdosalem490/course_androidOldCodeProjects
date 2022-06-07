package com.example.tasktimer;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.BuildConfig;

import com.example.tasktimer.debug.testData;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnTaskClickListener, FirstFragment.OnSaveClicked,AppDialog.DialogEvents
{

    private static final String TAG = "MainActivity";
    private boolean mTwoPane =false;
    public static final int DIALOG_ID_DELETE = 1;
    public static final int DIALOG_ID_CANCEL_EDIT = 2;
    private AlertDialog mDialog = null;
    private static final int DIALOG_ID_CANCEL_EDIT_UP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* if (findViewById(R.id.task_details_container)!= null)
        {
            mTwoPane = true;
        }*/
        mTwoPane = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
        Log.d(TAG, "onCreate: twoPane is "+mTwoPane);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Boolean editing = fragmentManager.findFragmentById(R.id.task_details_container) != null;
        Log.d(TAG, "onCreate: editing is "+ editing);
        View addEditLayout = findViewById(R.id.task_details_container);
        View mainFragment = findViewById(R.id.fragment);
        if (mTwoPane)
        {
            Log.d(TAG, "onCreate: twoPane mode");
            mainFragment.setVisibility(View.VISIBLE);
            addEditLayout.setVisibility(View.VISIBLE);
        }else if (editing){
            Log.d(TAG, "onCreate: single pane , editing");
            mainFragment.setVisibility(View.GONE);
        }else{
            Log.d(TAG, "onCreate: single pane , not editing");
            mainFragment.setVisibility(View.VISIBLE);
            addEditLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (com.example.tasktimer.BuildConfig.DEBUG)
        {
            MenuItem generate = menu.findItem(R.id.menumain_generate);
            generate.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {
            case R.id.maenumain_addTask:
                    taskEditRequest(null);
                break;
            case R.id.menumain_showDurations:
                startActivity(new Intent(this , DurationsReport.class));
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showAbout:
                showAboutDialog();
                break;
            case R.id.menumain_generate:
                testData.generateTestData(getContentResolver());
                break;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected:  home button pressed");
                FirstFragment fragment = (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.task_details_container);
                if (fragment.canClose()) {
                    return super.onOptionsItemSelected(item);
                } else {
                    showConfirmationDialog(DIALOG_ID_CANCEL_EDIT_UP);
                    return true;
                }

        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetTextI18n")
    public void showAboutDialog()
    {
        @SuppressLint("InflateParams") View messageView =  getLayoutInflater().inflate(R.layout.about , null , false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(messageView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             //   Log.d(TAG, "onClick: Entering messageView.onCLick , showing = "+mDialog.isShowing());
                if (mDialog != null && mDialog.isShowing())
                {
                    mDialog.dismiss();
                }
            }
        });
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);


        TextView tv = (TextView) messageView.findViewById(R.id.about_version);
        tv.setText("v" + com.example.tasktimer.BuildConfig.VERSION_NAME);
        TextView about_url = (TextView) messageView.findViewById(R.id.about_url);
        if (about_url != null)
        {
            about_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String s = ((TextView)v).getText().toString();
                    intent.setData(Uri.parse(s));
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(MainActivity.this , "No browser application found , can't visit world-wide web" , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        mDialog.show();
    }


    private void taskEditRequest(Task task)
    {
        Log.d(TAG, "TaskEditRequest: starts");

        Log.d(TAG, "TaskEditRequest: in two-pane mode(tablet)");
        FirstFragment fragment = new FirstFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Task.class.getSimpleName() , task);
        fragment.setArguments(arguments);

        Log.d(TAG, "taskEditRequest: twoPaneMode");

        getSupportFragmentManager().beginTransaction().replace(R.id.task_details_container , fragment).commit();

        if(!mTwoPane)
        {
            Log.d(TAG, "TaskEditRequest: in single-pane mode (phone)");
            View mainFragment = findViewById(R.id.fragment);
            View addEditLayout = findViewById(R.id.task_details_container);
            mainFragment.setVisibility(View.GONE);
            addEditLayout.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "exiting task taskEditRequest");
    }

    @Override
    public void onEditClick(@NotNull Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(@NotNull Task task) {
        Log.d(TAG, "onDeleteClick: starts");
        AppDialog dialog= new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID , DIALOG_ID_DELETE);
        args.putString(AppDialog.DIALOG_MESSAGE , getString(R.string.deldiag_message , task.getId() , task.getName()));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID , R.string.deldiag_positive_caption);
        args.putLong("TaskId" , task.getId());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager() , null);
    }

    @Override
    public void onSaveClicked() {
        Log.d(TAG, "onSaveClicked: starts");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.task_details_container);
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
           /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();*/
        }
        View addEditLayout = findViewById(R.id.task_details_container);
        View mainFragment = findViewById(R.id.fragment);
        if (!mTwoPane)
        {
            addEditLayout.setVisibility(View.GONE);
            mainFragment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: called");
        switch (dialogId)
        {
            case DIALOG_ID_DELETE:
                Long taskId = args.getLong("TaskId");
                if (BuildConfig.DEBUG && taskId == 0 )throw new AssertionError("Task ID is zero");
                getContentResolver().delete(TasksContract.buildTaskUri(taskId) , null , null);
                break;
            case DIALOG_ID_CANCEL_EDIT:
            case DIALOG_ID_CANCEL_EDIT_UP:
                break;
        }


    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: called");
        switch (dialogId)
        {
            case DIALOG_ID_DELETE:
                break;
            case DIALOG_ID_CANCEL_EDIT:
            case DIALOG_ID_CANCEL_EDIT_UP:
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.task_details_container);
                if (fragment != null)
                {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    if (mTwoPane)
                    {
                        if (dialogId == DIALOG_ID_CANCEL_EDIT) {
                            finish();
                        }
                    }else{
                        View addEditLayout = findViewById(R.id.task_details_container);
                        View mainFragment = findViewById(R.id.fragment);

                        addEditLayout.setVisibility(View.GONE);
                        mainFragment.setVisibility(View.VISIBLE);
                    }
                }else{
                    finish();
                }
                break;
        }
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: called");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");
        FragmentManager  fragmentManager  = getSupportFragmentManager();
        FirstFragment fragment = (FirstFragment) fragmentManager.findFragmentById(R.id.task_details_container);
        if (fragment == null  || fragment.canClose())
        {
            super.onBackPressed();
        }else{
            showConfirmationDialog(DIALOG_ID_CANCEL_EDIT);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }

    @Override
    public void onAttachFragment(@NonNull @NotNull Fragment fragment) {
        Log.d(TAG, "onAttachFragment: called , fragment is "+fragment.toString());
        super.onAttachFragment(fragment);
    }

    private void showConfirmationDialog(int dialogId)
    {
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID , dialogId);
        args.putString(AppDialog.DIALOG_MESSAGE , getString(R.string.cancelEditDiag_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID , R.string.cancelEditDiag_positive_caption);
        args.putInt(AppDialog.DIALOG_NEGATIVE_RID , R.string.cancelEditDiag_negative_caption);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager() , null);
    }

    @Override
    public void onTaskLongClick(@NotNull Task task) {

    }
}

package com.abdosalm.cmp2024.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.abdosalm.cmp2024.Activities.Adapter.MainViewRecyclerView;
import com.abdosalm.cmp2024.Activities.Constants.Constant;
import com.abdosalm.cmp2024.Activities.Model.FeatureModel;
import com.abdosalm.cmp2024.Activities.interfaces.IMainViewClicked;
import com.abdosalm.cmp2024.R;

import java.util.ArrayList;
import java.util.List;

public class MainView extends AppCompatActivity implements IMainViewClicked {
    private RecyclerView recyclerView;
    private List<FeatureModel> featureModels;
    private MainViewRecyclerView mainViewRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        recyclerView = findViewById(R.id.mainViewRecyclerView);

        String[] temp = getResources().getStringArray(R.array.features);
        featureModels = new ArrayList<>();
        for (int i = 0 ; i < temp.length ; i++){
            FeatureModel fTemp = new FeatureModel(temp[i] , this.getResources().getIdentifier("main_view_"+(i+1),"drawable",getPackageName()));
            featureModels.add(fTemp);
        }
        mainViewRecyclerView = new MainViewRecyclerView(this,featureModels , this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(mainViewRecyclerView);
        //TODO
        //checks if its admin or not

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_view_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOut){
            SharedPreferences sharedPreferences = this.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(Constant.IS_LOGGED_IN,false).apply();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }else if (id == R.id.notification){
            //TODO: move to another activity and check for notifications

        }else if (id == R.id.copyRights){
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialog(){
        View view = getLayoutInflater().inflate(R.layout.copy_rights_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.create().show();
    }

    @Override
    public void goFromMainViewTo(int id) {
        if (id == 0){
            // go to youtube activity
            startActivity(new Intent(getApplicationContext(),YoutubeActivity.class));
        }else if (id == 1){
            // go to announcement activity

        }else if (id == 2){
            // go to quizzes activity

        }else if (id == 3){
            // go to wanted activity

        }
    }
}
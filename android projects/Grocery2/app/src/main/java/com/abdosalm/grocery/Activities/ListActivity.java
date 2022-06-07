package com.abdosalm.grocery.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.grocery.Data.DatabaseHandler;
import com.abdosalm.grocery.Model.Grocery;
import com.abdosalm.grocery.R;
import com.abdosalm.grocery.UI.RecyclerViewAdapter;
import com.abdosalm.grocery.databinding.ActivityListBinding;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListBinding binding;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        //Get items from database
        groceryList = db.getAllGroceries();
        for (Grocery c : groceryList){
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Qty : " + c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateItemAdded("Added on : " + c.getDateItemAdded());

            listItems.add(grocery);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this , listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

}
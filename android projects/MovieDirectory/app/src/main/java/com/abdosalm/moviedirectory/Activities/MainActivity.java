package com.abdosalm.moviedirectory.Activities;

import android.os.Bundle;

import com.abdosalm.moviedirectory.Data.MovieRecyclerViewAdapter;
import com.abdosalm.moviedirectory.Model.Movie;
import com.abdosalm.moviedirectory.R;
import com.abdosalm.moviedirectory.Util.Constants;
import com.abdosalm.moviedirectory.Util.Prefs;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.moviedirectory.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String API = "http://www.omdbapi.com/?i=tt3896198&apikey=c015847a";
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private List<Movie>movieList;
    private RequestQueue queue;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        queue = Volley.newRequestQueue(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();
ees
        movieList = new ArrayList<>();

        movieList = getMovies(search);
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this,movieList);
        recyclerView.setAdapter(movieRecyclerViewAdapter);

        movieRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_search) {
            showInputDialog();
           //return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showInputDialog(){
        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view,null);
        EditText newSearchEdt = view.findViewById(R.id.searchEdt);
        Button submitButton = view.findViewById(R.id.submitButton);

        alertDialogBuilder.setView(view);
        dialog = alertDialogBuilder.create();
        dialog.show();

        submitButton.setOnClickListener(v->{
            Prefs prefs = new Prefs(MainActivity.this);
            if (!newSearchEdt.getText().toString().isEmpty()){
                String search = newSearchEdt.getText().toString();
                prefs.setSearch(search);
                movieList.clear();

                getMovies(search);

                movieRecyclerViewAdapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        });
    }
    //Get movies
    public List<Movie> getMovies(String searchTerm){
        movieList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT + searchTerm + Constants.URL_RIGHT, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray moviesArray = response.getJSONArray("Search");
                    for (int i =0 ; i < moviesArray.length();i++){
                        JSONObject movieObject = moviesArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(movieObject.getString("Title"));
                        movie.setYear(movieObject.getString("Year"));
                        movie.setMovieType(movieObject.getString("Type"));
                        movie.setPoster(movieObject.getString("Poster"));
                        movie.setImdbId(movieObject.getString("imdbID"));
                        movieList.add(movie);
                    }
                    movieRecyclerViewAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
        return movieList;
    }
}
package com.abdosalm.moviedirectory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdosalm.moviedirectory.Model.Movie;
import com.abdosalm.moviedirectory.R;
import com.abdosalm.moviedirectory.Util.Constants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {
    private Movie movie;
    private ImageView movieImage;
    private TextView movieTitle;
    private TextView movieYear;

    private RequestQueue queue;
    private String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        queue = Volley.newRequestQueue(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getImdbId();
        setUpUI();
        getMovieDetails(movieId);
    }

    private void getMovieDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("Ratings")){
                        JSONArray ratings = response.getJSONArray("Ratings");
                        String source = null;
                        String value = null;
                        if (ratings.length() > 0){
                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                        }else{

                        }
                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText(response.getString("Released"));

                        Picasso.get().load(response.getString("Poster"))
                                .into(movieImage);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);
    }

    private void setUpUI() {
        movieImage = findViewById(R.id.movieImageIDDets);
        movieTitle = findViewById(R.id.movieTitleIDDets);
        movieYear = findViewById(R.id.movieReleaseIDDets);
    }
}
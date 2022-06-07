package com.abdosalm.moviedirectory.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.moviedirectory.Activities.MovieDetailActivity;
import com.abdosalm.moviedirectory.Model.Movie;
import com.abdosalm.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Movie>movieList;
    public MovieRecyclerViewAdapter(Context context , List<Movie>movies) {
        this.context = context;
        movieList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row,parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        String posterLink = movie.getPoster();

        holder.title.setText(movie.getTitle());
        holder.type.setText(movie.getMovieType());
        Picasso.get().load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.poster);
        holder.year.setText(movie.getYear());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView poster;
        public TextView year;
        public TextView type;
        public ViewHolder(@NonNull View view , Context ctx) {
            super(view);
            context = ctx;

            title = view.findViewById(R.id.movieTitleID);
            poster = view.findViewById(R.id.movieImageID);
            year = view.findViewById(R.id.movieReleaseID);
            type = view.findViewById(R.id.movieCatID);

            view.setOnClickListener(v->{
                Movie movie = movieList.get(getAdapterPosition());
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie",movie);
                ctx.startActivity(intent);
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}

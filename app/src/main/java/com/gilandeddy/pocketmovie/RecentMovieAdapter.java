package com.gilandeddy.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbert on 5/12/18.
 */

public class RecentMovieAdapter extends RecyclerView.Adapter<RecentMovieAdapter.MovieViewHolder> {
    private List<Movie> movies;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View movieView = layoutInflater.inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView movieTitleView = itemViewGroup.findViewById(R.id.movieTitleView);
        TextView ratingTitleView = itemViewGroup.findViewById(R.id.movieRatingView);
        movieTitleView.setText(movie.getName());
        ratingTitleView.setText("Rating : " + movie.getRatingString());


    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return  movies.size();
        } else {
            return 0;
        }

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private MovieViewHolder(View itemView){
            super(itemView);
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }
}

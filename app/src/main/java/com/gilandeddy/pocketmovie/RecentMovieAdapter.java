package com.gilandeddy.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbert on 5/12/18.
 */

public class RecentMovieAdapter extends RecyclerView.Adapter<RecentMovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> movies ;

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
        ImageView posterImageView = itemViewGroup.findViewById(R.id.posterImageView);
        movieTitleView.setText(movie.getName());
        ratingTitleView.setText("Rating : " + movie.getRatingString());
        Picasso.get().load(TMDBUrl.imageUrlHead + movie.getPosterImageUrl()).into(posterImageView);


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

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }
}

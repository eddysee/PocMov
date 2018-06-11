package com.gilandeddy.pocketmovie;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.TMDBUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gilbert on 5/12/18.
 */

public class RecentRecyclerAdapter extends RecyclerView.Adapter<RecentRecyclerAdapter.MovieViewHolder> {


    public static ArrayList<Movie> movies;

    final private ListItemClickListener listItemClickListener;

    public RecentRecyclerAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View movieView = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ViewGroup itemViewGroup = (ViewGroup) holder.itemView;
        TextView movieTitleView = itemViewGroup.findViewById(R.id.movieTitleView);
        TextView ratingTitleView = itemViewGroup.findViewById(R.id.movieRatingView);
        ImageView posterImageView = itemViewGroup.findViewById(R.id.posterImageView);
        TextView nextPageTextView = itemViewGroup.findViewById(R.id.nextPageTextView);
        if (position < movies.size()) {
            Movie movie = movies.get(position);
            nextPageTextView.setVisibility(View.INVISIBLE);
            movieTitleView.setText(movie.getName());
            ratingTitleView.setText("Rating : " + movie.getRatingString());
            Picasso.get().load(TMDBUrl.getImageUrlHead() + movie.getPosterImageUrl()).into(posterImageView);
            movieTitleView.setVisibility(View.VISIBLE);
            ratingTitleView.setVisibility(View.VISIBLE);
            posterImageView.setVisibility(View.VISIBLE);
        } else if (position == movies.size()) {
            nextPageTextView.setVisibility(View.VISIBLE);
            movieTitleView.setVisibility(View.INVISIBLE);
            ratingTitleView.setVisibility(View.INVISIBLE);
            posterImageView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            int listSize = movies.size() + 1;
            return listSize;
        } else {
            return 0;
        }

    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = this.getAdapterPosition();
            RecentRecyclerAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
            Log.d("tag", "item clicked");
        }
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
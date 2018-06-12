package com.gilandeddy.pocketmovie;

import android.os.Bundle;
import android.os.Parcelable;
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
 * @author Gilbert & Eddy
 * This class PockerRecyclerAdapter extends RecyclerViewAdapter
 */

public class RecentRecyclerAdapter extends RecyclerView.Adapter<RecentRecyclerAdapter.MovieViewHolder> {

    /**
     *
     */


    public static ArrayList<Movie> movies;

    final private ListItemClickListener listItemClickListener;

    /** Constructor for list item clicked in the recent popular recycler
     *
     * @param listItemClickListener
     */
    public RecentRecyclerAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    /** This method inflates the recycler with the item movie
     *
     * @param parent
     * @param viewType
     * @return movie view holder
     */

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View movieView = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(movieView);
    }

    /** This method internally calls onBindViewHolder(ViewHolder, int) to update the RecyclerView.
     *  ViewHolder contents with the item at the given position and also sets up private fields to be used by RecyclerView.
     *
     * @param holder
     * @param position
     */
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

    /** This method returns the total number of items in the data set held by the adapter.
     *
     * @return list size or 0
     */
    @Override
    public int getItemCount() {
        if (movies != null) {
            int listSize = movies.size() + 1;
            return listSize;
        } else {
            return 0;
        }

    }

    /** This sub-class MovieViewHolder describes an item view and metadata about its place within the RecyclerView.
     * RecyclerView.Adapter implementations subclass ViewHolder
     *
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        /**
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = this.getAdapterPosition();
            RecentRecyclerAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
            Log.d("tag", "item clicked");
        }
    }

    /** This methods shows the view when onClick
     *  XXXX
     *
     * @param movies
     */
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    /** XXXX
     *
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }




}

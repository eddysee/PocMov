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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by gilbert on 5/21/18.
 */

public class PocketRecyclerAdapter extends RecyclerView.Adapter<PocketRecyclerAdapter.MovieViewHolder> {


    public static ArrayList<Movie> movies;

    final private ListItemClickListener listItemClickListener;

    public PocketRecyclerAdapter(ListItemClickListener listItemClickListener) {
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
        TextView nextPageView = itemViewGroup.findViewById(R.id.nextPageTextView);
        nextPageView.setVisibility(View.INVISIBLE);
        Movie movie = movies.get(position);
        movieTitleView.setText(movie.getName());
        ratingTitleView.setText("Rating : " + movie.getRatingString());
        Picasso.get().load(TMDBUrl.getImageUrlHead() + movie.getPosterImageUrl()).into(posterImageView);


    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            int listSize = movies.size();
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
            PocketRecyclerAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
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

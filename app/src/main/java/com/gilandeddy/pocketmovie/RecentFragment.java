package com.gilandeddy.pocketmovie;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment implements RecentMovieAdapter.ListItemClickListener{
public static RecentMovieAdapter recentMovieAdapter;

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_recent, container,false);
        RecyclerView recentRecyclerView = (RecyclerView)view.findViewById(R.id.recentMovieRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recentRecyclerView.setLayoutManager(linearLayoutManager);
        //recentMovieAdapter.setMovies(MainActivity.getMovies());
        recentMovieAdapter = new RecentMovieAdapter(this); // create the proper RV adapter
        recentRecyclerView.setAdapter(recentMovieAdapter);
        // Inflate the layout for this fragment
        return view;

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("tag","recent fragment onlistItemclick");
        Movie selectedMovie = RecentMovieAdapter.movies.get(clickedItemIndex);
        Intent detailIntent = new Intent(getContext(),DetailActivity.class);
        detailIntent.putExtra("movieObject",selectedMovie);
        startActivity(detailIntent);



    }
}

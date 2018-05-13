package com.gilandeddy.pocketmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {




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
        RecentMovieAdapter recentMovieAdapter = MainActivity.recentMovieAdapter; // static adapter from MainActivity can be instantiated here
        recentMovieAdapter.setMovies(MainActivity.getMovies());
        recentRecyclerView.setAdapter(recentMovieAdapter);

        // Inflate the layout for this fragment
        return view;

    }

}

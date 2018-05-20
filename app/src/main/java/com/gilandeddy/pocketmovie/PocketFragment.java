package com.gilandeddy.pocketmovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.PocketedMoviesManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PocketFragment extends Fragment implements RecentMovieAdapter.ListItemClickListener{
    public static RecentMovieAdapter recentMovieAdapter;
    private static ArrayList<Movie> pocketMovies = new ArrayList<>();



    public PocketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pocket, container, false);
        RecyclerView pocketRecyclerView = view.findViewById(R.id.pocketMovieRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        pocketRecyclerView.setLayoutManager(linearLayoutManager);
        recentMovieAdapter = new RecentMovieAdapter(this); // create the proper RV adapter
        pocketRecyclerView.setAdapter(recentMovieAdapter);
        pocketMovies.addAll(PocketedMoviesManager.getInstance().getAllMovies());
        recentMovieAdapter.setMovies(pocketMovies);

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}

package com.gilandeddy.pocketmovie;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gilandeddy.pocketmovie.model.HttpRequestService;
import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.PocketedMoviesManager;
import com.gilandeddy.pocketmovie.model.TMDBUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PocketFragment extends Fragment implements PocketRecyclerAdapter.ListItemClickListener {
    public static PocketRecyclerAdapter pocketRecyclerAdapter;
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
        pocketRecyclerAdapter = new PocketRecyclerAdapter(this);
        pocketRecyclerView.setAdapter(pocketRecyclerAdapter);
        pocketMovies.addAll(PocketedMoviesManager.getInstance().getAllMovies());
        pocketRecyclerAdapter.setMovies(pocketMovies);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<Movie> freshPocket;
        freshPocket = PocketedMoviesManager.getInstance().getAllMovies();
        if (pocketMovies != null && pocketMovies.equals(freshPocket)) {
        } else {
            pocketRecyclerAdapter.setMovies(freshPocket);
        }
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("tag", "pocket fragment onclick clicked");
        Movie selectedMovie = PocketRecyclerAdapter.movies.get(clickedItemIndex);
        Intent detailIntent = new Intent(getContext(), DetailActivity.class);
        detailIntent.putExtra("movieObject", selectedMovie);
        detailIntent.putExtra("requestInfo",true);
        startActivity(detailIntent);
    }

}


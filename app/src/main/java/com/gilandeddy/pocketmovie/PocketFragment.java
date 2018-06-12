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


/** @author Gilbert & Eddy
 *  This class PocketFragment extends Fragment and implements a recycler adapter
 *  This class diplays the pocketed movies by inflating a recycler view and adding movies
 *  A simple {@link Fragment} subclass.
 *
 */

public class PocketFragment extends Fragment implements PocketRecyclerAdapter.ListItemClickListener {
    public PocketRecyclerAdapter pocketRecyclerAdapter;
    private static ArrayList<Movie> pocketMovies = new ArrayList<>();

    /**
     * Required empty public constructor
     */

    public PocketFragment() {

    }

    /** The onCreateView methods inflates the pocket fragment layout.
     * The LinearLayoutManager is initialised
     * The recycler adapter is loaded and the view is launched
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return pocked fragment view with recycler
     */

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
        pocketRecyclerAdapter.setMovies(pocketMovies); // Initialize dataset
        return view;
    }

    /** The method is launched on Start
     * XXX
     *
     */
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

    /** This method is launched when an item in the list of the recycler is clicked
     * This method initiates the detail activity
     *
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("tag", "pocket fragment onclick clicked");
        Movie selectedMovie = PocketRecyclerAdapter.movies.get(clickedItemIndex);
        Intent detailIntent = new Intent(getContext(), DetailActivity.class);
        detailIntent.putExtra("movieObject", selectedMovie);
        detailIntent.putExtra("requestInfo", true);
        startActivity(detailIntent);
    }

}


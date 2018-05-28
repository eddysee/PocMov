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
        ArrayList<Movie> freshPocket = new ArrayList<>();
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
        HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getDetailsURL(selectedMovie.getId()));
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        getActivity().registerReceiver(httpReceiver, intentFilter);
    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("tag", "OnReceive called in pocket fragment");
            String response = intent.getStringExtra("responseString");
            Movie selectedMovie = parseMovieDetails(response);
            Intent detailIntent = new Intent(getContext(), DetailActivity.class);
            detailIntent.putExtra("movieObject", selectedMovie);
            startActivity(detailIntent);
        }

    }

    private Movie parseMovieDetails(String jsonString) {
        Movie newMovie = new Movie();
        if (jsonString != null) {
            try {
                JSONObject jsonMovie = new JSONObject(jsonString);
                String name = jsonMovie.getString("title");
                double rating = jsonMovie.getDouble("vote_average");
                String posterImageUrl = jsonMovie.getString("poster_path");
                String releaseDate = jsonMovie.getString("release_date");
                String summary = jsonMovie.getString("overview");
                String detailImageUrl = jsonMovie.getString("backdrop_path");
                int id = jsonMovie.getInt("id");
                newMovie = new Movie(id, name, rating, posterImageUrl, releaseDate, summary, detailImageUrl);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newMovie;
        }
        Log.d("TAG", "jsonMovie empty, bullshit movie returned");
        return new Movie(666, "Fake Movie", 6.6, "", "25-12-0000", " a fake movie place holder", "");
    }


}


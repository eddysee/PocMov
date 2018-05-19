package com.gilandeddy.pocketmovie;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment implements RecentMovieAdapter.ListItemClickListener {
    private static ArrayList<Movie> movies = new ArrayList<>();
    public static RecentMovieAdapter recentMovieAdapter;
    private int pageNumber;

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        pageNumber = 1;
        HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getPopularUrl(pageNumber));
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        getActivity().registerReceiver(httpReceiver, intentFilter);
        PocketedMoviesManager.getInstance().initWithContext(getActivity().getApplicationContext()); // initiating db helper
        //PocketedMoviesManager.getInstance().addMovieToPocket(1,1,"1",1,"11"); failed test to create a record.


        RecyclerView recentRecyclerView = (RecyclerView) view.findViewById(R.id.recentMovieRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recentRecyclerView.setLayoutManager(linearLayoutManager);
        recentMovieAdapter = new RecentMovieAdapter(this); // create the proper RV adapter
        recentRecyclerView.setAdapter(recentMovieAdapter);
        return view;

    }
    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("responseString");
            movies.addAll(parsePopularMovies(response));
            RecentMovieAdapter recentMovieAdapter = RecentFragment.recentMovieAdapter; //fetch the proper adapter from Fragment
            recentMovieAdapter.setMovies(movies);
            PocketedMoviesManager.getInstance().saveRecentMovies(movies); // expected this to create table and add 20 records
        }

    }

    private ArrayList<Movie> parsePopularMovies(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                String name = jsonMovie.getString("title");
                double rating = jsonMovie.getDouble("vote_average");
                String posterImageUrl = jsonMovie.getString("poster_path");
                String releaseDate = jsonMovie.getString("release_date");
                String summary = jsonMovie.getString("overview");
                String detailImageUrl = jsonMovie.getString("backdrop_path");
                int id = jsonMovie.getInt("id");
                Movie newMovie = new Movie(id, name, rating, posterImageUrl, releaseDate, summary, detailImageUrl);
                movies.add(newMovie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
   // public void onNextButtonClicked(View view){ // loads the next 20 results
     //   pageNumber++ ;
       // HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getPopularUrl(pageNumber));
    //}


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("tag", "recent fragment onlistItemclick");
        if (clickedItemIndex < movies.size()) {
            Movie selectedMovie = RecentMovieAdapter.movies.get(clickedItemIndex);
            Intent detailIntent = new Intent(getContext(), DetailActivity.class);
            detailIntent.putExtra("movieObject", selectedMovie);
            startActivity(detailIntent);
        }
        else if (clickedItemIndex == movies.size()){
            pageNumber++;
            HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getPopularUrl(pageNumber));
        }


    }
}

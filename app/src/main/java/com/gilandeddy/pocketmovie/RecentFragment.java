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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gilandeddy.pocketmovie.model.HttpRequestService;
import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.PocketedMoviesManager;
import com.gilandeddy.pocketmovie.model.RecentMovies;
import com.gilandeddy.pocketmovie.model.TMDBUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * @author Gilbert & Eddy
 * This class RecentFragment extends Fragment and implements a recycler adapter
 * This class diplays the popular movies by inflating a recycler view and adding the movies
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment implements RecentRecyclerAdapter.ListItemClickListener {
    public  RecentRecyclerAdapter recentRecyclerAdapter;
    private int pageNumber = 1;
    private HttpReceiver httpReceiver = new HttpReceiver();
    private ProgressBar progressBar;
    private TextView errorTextView;

    /**
     * Required empty public constructor
     */
    public RecentFragment() {

    }

    /** The onCreateView methods inflates the recent popular movie fragment layout.
     * The LinearLayoutManager is initialised
     * The recycler adapter is loaded and the view is launched
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        PocketedMoviesManager.getInstance().initWithContext(getActivity().getApplicationContext());
        // initiating db helper
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        errorTextView = view.findViewById(R.id.errorTextView);
        errorTextView.setVisibility(View.INVISIBLE);
        RecyclerView recentRecyclerView = view.findViewById(R.id.recentMovieRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recentRecyclerView.setLayoutManager(linearLayoutManager);
        recentRecyclerAdapter = new RecentRecyclerAdapter(this); // create the proper RV adapter
        recentRecyclerView.setAdapter(recentRecyclerAdapter);

        return view;

    }

    /** This class HttpReciever which extends BreadcastReciever allows to register for system events
     *
     */
    private class HttpReceiver extends BroadcastReceiver {
        /**
         *
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("tag", "OnReceive Called in RECENT fragment");
            String response = intent.getStringExtra("responseString");
            if (intent.getAction().equalsIgnoreCase("httpRequestComplete")) {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.INVISIBLE);
                RecentMovies.getInstance().addToRecentMovies(parsePopularMovies(response));
                RecentRecyclerAdapter recentRecyclerAdapter = RecentFragment.this.recentRecyclerAdapter; //fetch the proper adapter from Fragment
                recentRecyclerAdapter.setMovies(RecentMovies.getInstance().getRecentMovies());

            } else if (intent.getAction().equalsIgnoreCase("failedHttpRequest")) {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(R.string.errorMessage);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(R.string.unexpectedMessage);
            }
        }

    }

    /** This method parses the jsonString of the popular movie object arraylist to a String value.
     *
     * @param jsonString
     * @return movie object arraylist
     */
    private ArrayList<Movie> parsePopularMovies(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<>();
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
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(R.string.unexpectedMessage);
            e.printStackTrace();
        }
        return movies;
    }

    /** This method is launched when an item in the list of the recycler is clicked
     * This method initiates the detail activity
     *
     * @param clickedItemIndex
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("tag", "recent fragment onlistItemclick");
        if (clickedItemIndex < RecentMovies.getInstance().getRecentMovies().size()) {
            Movie selectedMovie = RecentRecyclerAdapter.movies.get(clickedItemIndex);
            Intent detailIntent = new Intent(getContext(), DetailActivity.class);
            detailIntent.putExtra("movieObject", selectedMovie);
            detailIntent.putExtra("requestInfo", false);
            startActivity(detailIntent);
        } else if (clickedItemIndex == RecentMovies.getInstance().getRecentMovies().size()) {
            pageNumber++;
            HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getPopularUrl(pageNumber));
        }


    }

    /** The method is launched on Start
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("failedHttpRequest");
        getActivity().registerReceiver(httpReceiver, intentFilter);
        if (RecentMovies.getInstance().getRecentMovies().size() < 1) {
            HttpRequestService.startActionRequestHttp(getContext(), TMDBUrl.getPopularUrl(pageNumber));
            Log.d("HTTP", "HttpRequest launched");
            progressBar.setVisibility(View.VISIBLE);

        } else {
            recentRecyclerAdapter.setMovies(RecentMovies.getInstance().getRecentMovies());
        }
    }

    /** The method is launched on Stop to unregister the httpreceiver
     *
     */
    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(httpReceiver);

    }



}

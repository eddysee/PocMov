package com.gilandeddy.pocketmovie;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gilandeddy.pocketmovie.model.HttpRequestService;
import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.RecentMovies;
import com.gilandeddy.pocketmovie.model.SearchedMovies;
import com.gilandeddy.pocketmovie.model.TMDBUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements RecentRecyclerAdapter.ListItemClickListener {
    public static RecentRecyclerAdapter searchRecyclerAdapter;
    private int pageNumber = 1;
    private HttpReceiver httpReceiver = new HttpReceiver();
    private ProgressBar searchProgressBar;
    private TextView searchErrorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag", "SearchActivity created");
        setContentView(R.layout.activity_search);
        searchProgressBar = findViewById(R.id.progressBar);
        searchErrorTextView = findViewById(R.id.errorTextView);
        searchProgressBar.setVisibility(View.INVISIBLE);
        searchErrorTextView.setVisibility(View.INVISIBLE);

        RecyclerView searchedRecyclerView = findViewById(R.id.searchedMovieRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchedRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerAdapter = new RecentRecyclerAdapter(this);
        searchedRecyclerView.setAdapter(searchRecyclerAdapter);
        searchRecyclerAdapter.setMovies(SearchedMovies.getInstance().getSearchedMovies());

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Log.d("tag", "recent fragment onlistItemclick");
        if (clickedItemIndex < SearchedMovies.getInstance().getSearchedMovies().size()) {
            Movie selectedMovie = RecentRecyclerAdapter.movies.get(clickedItemIndex);
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra("movieObject", selectedMovie);
            detailIntent.putExtra("requestInfo", false);
            startActivity(detailIntent);
        } else if (clickedItemIndex == RecentMovies.getInstance().getRecentMovies().size()) {
            pageNumber++;
            HttpRequestService.startActionRequestHttp(this, TMDBUrl.getPopularUrl(pageNumber));
        }


    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("tag", "OnReceive Called in RECENT fragment");
            String response = intent.getStringExtra("responseString");
            if (intent.getAction().equalsIgnoreCase("httpRequestComplete")) {
                searchProgressBar.setVisibility(View.INVISIBLE);
                searchErrorTextView.setVisibility(View.INVISIBLE);
                SearchedMovies.getInstance().addToSearchedMovies(parseSearchedMovies(response));
                RecentRecyclerAdapter searchRecyclerAdapter = SearchActivity.searchRecyclerAdapter; //fetch the proper adapter from Fragment
                searchRecyclerAdapter.setMovies(SearchedMovies.getInstance().getSearchedMovies());

            } else if (intent.getAction().equalsIgnoreCase("failedHttpRequest")) {
                searchProgressBar.setVisibility(View.INVISIBLE);
                searchErrorTextView.setVisibility(View.VISIBLE);
                searchErrorTextView.setText(R.string.errorMessage);
            } else {
                searchProgressBar.setVisibility(View.INVISIBLE);
                searchErrorTextView.setVisibility(View.VISIBLE);
                searchErrorTextView.setText(R.string.unexpectedMessage);
            }
        }


    }

    private ArrayList<Movie> parseSearchedMovies(String jsonString) {
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
            searchErrorTextView.setVisibility(View.VISIBLE);
            searchErrorTextView.setText(R.string.unexpectedMessage);
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("failedHttpRequest");
        registerReceiver(httpReceiver, intentFilter);
        if (SearchedMovies.getInstance().getSearchedMovies().size() < 1) {
            handleIntent(getIntent());
            searchProgressBar.setVisibility(View.VISIBLE);

        } else {
            searchRecyclerAdapter.setMovies(SearchedMovies.getInstance().getSearchedMovies());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            HttpRequestService.startSearchRequest(this, TMDBUrl.getSearchURL(pageNumber, query));
        }
    }
}


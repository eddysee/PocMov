package com.gilandeddy.pocketmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecentMovieAdapter recentMovieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpRequestService.startActionRequestHttp(this,TMDBUrl.getPopularUrl(1));
        HttpReceiver httpReceiver= new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        registerReceiver(httpReceiver,intentFilter);
        RecyclerView recentRecyclerView = findViewById(R.id.recentMovieRecycler);
        recentMovieAdapter = new RecentMovieAdapter();
        recentRecyclerView.setAdapter(recentMovieAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recentRecyclerView.setLayoutManager(linearLayoutManager);


    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("responseString");
            ArrayList<Movie> movies = parsePopularMovies(response);
            recentMovieAdapter.setMovies(movies);

        }

    }
    private ArrayList<Movie> parsePopularMovies (String jsonString){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i< jsonArray.length();++i){
                JSONObject jsonMovie = jsonArray.getJSONObject(i);
                String name = jsonMovie.getString("title");
                double rating = jsonMovie.getDouble("vote_average");
                String posterImageUrl = jsonMovie.getString("poster_path");
                String releaseDate = jsonMovie.getString("release_date");
                String summary = jsonMovie.getString("overview");
                String detailImageUrl = jsonMovie.getString("backdrop_path");
                int id = jsonMovie.getInt("id");
                Movie newMovie = new Movie(id,name,rating,posterImageUrl,releaseDate,summary,detailImageUrl);
                movies.add(newMovie);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return movies;
    }
}

package com.gilandeddy.pocketmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private static ArrayList<Movie> movies = new ArrayList<>();

public static ArrayList<Movie> getMovies(){
    return movies;
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpRequestService.startActionRequestHttp(this,TMDBUrl.getPopularUrl(1));
        HttpReceiver httpReceiver= new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        registerReceiver(httpReceiver,intentFilter);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(mainPagerAdapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);





    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("responseString");
            movies = parsePopularMovies(response);



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

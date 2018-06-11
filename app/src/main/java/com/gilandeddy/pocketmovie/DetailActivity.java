package com.gilandeddy.pocketmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilandeddy.pocketmovie.model.HttpRequestService;
import com.gilandeddy.pocketmovie.model.Movie;
import com.gilandeddy.pocketmovie.model.PocketedMoviesManager;
import com.gilandeddy.pocketmovie.model.TMDBUrl;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private ActionProvider shareActionProvider;
    private ImageView detailImageView;
    private TextView detailTitleView;
    private TextView summaryView;
    private TextView ratingView;
    private TextView releaseDateView;
    private TextView genreView;
    private CheckBox checkBox;
    private Movie selectedMovie;
    private String youtubeID;
    private boolean isInPocket;
    private HttpReceiver httpReceiver = new HttpReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailImageView = findViewById(R.id.detailImageView);
        detailTitleView = findViewById(R.id.detailTitleView);
        summaryView = findViewById(R.id.summaryView);
        ratingView = findViewById(R.id.ratingDetailView);
        releaseDateView = findViewById(R.id.releaseDateView);
        genreView = findViewById(R.id.genreView);
        checkBox = findViewById(R.id.pocketCheckBox);
        Intent intent = getIntent();
        selectedMovie = (Movie) intent.getSerializableExtra("movieObject");

        HttpRequestService.startMovieDetailRequest(this, TMDBUrl.getDetailsURL(selectedMovie.getId()));
        IntentFilter moviedetailIntentFilter = new IntentFilter();
        moviedetailIntentFilter.addAction("detailRequestComplete");
        registerReceiver(httpReceiver, moviedetailIntentFilter);
        isInPocket = PocketedMoviesManager.getInstance().isInPocketDatabase(selectedMovie.getId());
        checkBox.setChecked(isInPocket);
        detailTitleView.setText(selectedMovie.getName());
        summaryView.setText(selectedMovie.getSummary());
        ratingView.setText("Rating : " + selectedMovie.getRatingString());
        releaseDateView.setText(selectedMovie.getReleaseDate());


        HttpRequestService.startTrailerPathRequest(this, TMDBUrl.getVideoUrl(selectedMovie.getId()));
        Picasso.get().load(TMDBUrl.getImageUrlHead() + selectedMovie.getDetailImageUrl()).into(detailImageView);

        Log.d("Tag", "Detail Activity Created");
    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            String response = intent.getStringExtra("responseString");
            if (intentAction.equalsIgnoreCase("trailerRequestComplete")) {
                youtubeID = parseTrailerYoutubeID(response);
            } else if (intentAction.equalsIgnoreCase("detailRequestComplete")) {
                parseMovieDetails(response);
                isInPocket = PocketedMoviesManager.getInstance().isInPocketDatabase(selectedMovie.getId());
                checkBox.setChecked(isInPocket);
                detailTitleView.setText(selectedMovie.getName());
                summaryView.setText(selectedMovie.getSummary());
                ratingView.setText("Rating : " + selectedMovie.getRatingString());
                releaseDateView.setText(selectedMovie.getReleaseDate());
                genreView.setText(parseGenres(response));

                Picasso.get().load(TMDBUrl.getImageUrlHead() + selectedMovie.getDetailImageUrl()).into(detailImageView);
            }

        }
    }


    private String parseTrailerYoutubeID(String jsonString) {
        boolean lookingforTrailer = true;
        String youtubeID = new String();
        int i = 0;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            while (lookingforTrailer) {
                JSONObject jsonVideo = jsonArray.getJSONObject(i);
                if (jsonVideo.getString("type").equalsIgnoreCase("trailer")) {
                    youtubeID = jsonVideo.getString("key");
                    lookingforTrailer = false;
                } else {
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return youtubeID;
    }

    private String parseGenres(String jsonString){
        String result = new String();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("genres");
            for (int i = 0; i<2 ; i++){
                JSONObject jsonGenre = jsonArray.getJSONObject(i);
                result += jsonGenre.getString("name") + " ";
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    private void parseMovieDetails(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            selectedMovie.setSummary(jsonObject.getString("overview"));
            selectedMovie.setReleaseDate(jsonObject.getString("release_date"));
            selectedMovie.setDetailImageUrl(jsonObject.getString("backdrop_path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void onPocketClicked(View view) {
        Log.d("tag", "onpocketclicked");
        if (isInPocket) {
            PocketedMoviesManager.getInstance().deleteMovieInPocket(selectedMovie.getId());
            isInPocket = false;
            checkBox.setChecked(isInPocket);

        } else {
            PocketedMoviesManager.getInstance().addMovieToPocket(selectedMovie.getId(), 1, selectedMovie.getName(), selectedMovie.getRating(), selectedMovie.getPosterImageUrl());
            isInPocket = true;
            checkBox.setChecked(isInPocket);
        }

    }

    public void onVideoClicked(View view) {

        Intent playTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID));
        startActivity(playTrailer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        MenuItem shareSelectedMovie = menu.findItem(R.id.action_share);
        shareActionProvider = MenuItemCompat.getActionProvider(shareSelectedMovie);
        shareSelectedMovie.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem shareSelectedMovie) {
                String message = new String("Hey! Do you want to go see " + selectedMovie.getName() + " ?");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(shareIntent);
                return true;
            }
        });


        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(httpReceiver);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("trailerRequestComplete");
        registerReceiver(httpReceiver, intentFilter);

    }
}

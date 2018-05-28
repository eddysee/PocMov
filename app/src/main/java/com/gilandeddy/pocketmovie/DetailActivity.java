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
    ImageView detailImageView;
    TextView detailTitleView;
    TextView summaryView;
    TextView ratingView;
    TextView releaseDateView;
    CheckBox checkBox;
    Movie selectedMovie;
    String youtubeID;
    boolean isInPocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        selectedMovie = (Movie) intent.getSerializableExtra("movieObject");
        detailImageView = findViewById(R.id.detailImageView);
        detailTitleView = findViewById(R.id.detailTitleView);
        summaryView = findViewById(R.id.summaryView);
        ratingView = findViewById(R.id.ratingDetailView);
        releaseDateView = findViewById(R.id.releaseDateView);
        checkBox = findViewById(R.id.pocketCheckBox);
        isInPocket = PocketedMoviesManager.getInstance().isInPocketDatabase(selectedMovie.getId());
        checkBox.setChecked(isInPocket);
        detailTitleView.setText(selectedMovie.getName());
        summaryView.setText(selectedMovie.getSummary());
        ratingView.setText("Rating : " + selectedMovie.getRatingString());
        releaseDateView.setText(selectedMovie.getReleaseDate());
        Picasso.get().load(TMDBUrl.getImageUrlHead() + selectedMovie.getDetailImageUrl()).into(detailImageView);
        Log.d("Tag", "Detail Activity Created");
        // Leaked intent receiver error happens here often 1

    }

    private class HttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("responseString");
            youtubeID = parseTrailerYoutubeID(response);
            Intent playTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID));
            startActivity(playTrailer);

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

    public void onPocketClicked(View view) {
        Log.d("tag", "onpocketclicked");
        if (isInPocket){
            PocketedMoviesManager.getInstance().deleteMovieInPocket(selectedMovie.getId());
            isInPocket = false;
            checkBox.setChecked(isInPocket);

        }else {
            PocketedMoviesManager.getInstance().addMovieToPocket(selectedMovie.getId(), 1, selectedMovie.getName(), selectedMovie.getRating(), selectedMovie.getPosterImageUrl());
            isInPocket = true;
            checkBox.setChecked(isInPocket);
        }

    }

    public void onVideoClicked(View view){
        HttpRequestService.startActionRequestHttp(this, TMDBUrl.getVideoUrl(selectedMovie.getId()));
        HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        registerReceiver(httpReceiver,intentFilter);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
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


}

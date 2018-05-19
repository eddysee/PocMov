package com.gilandeddy.pocketmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    ImageView detailImageView;
    TextView detailTitleView;
    TextView summaryView;
    TextView ratingView;
    TextView releaseDateView;
    CheckBox checkBox;
    Movie selectedMovie;


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
        checkBox.setChecked(selectedMovie.isInPocket());
        detailTitleView.setText(selectedMovie.getName());
        summaryView.setText(selectedMovie.getSummary());
        ratingView.setText("Rating : " + selectedMovie.getRatingString());
        releaseDateView.setText(selectedMovie.getReleaseDate());
        Picasso.get().load(TMDBUrl.imageUrlHead + selectedMovie.getDetailImageUrl()).into(detailImageView);

    }

    public void onPocketClicked(View view) {
        Log.d("tag","onpocketclicked");
        if (checkBox.isChecked()) {
            // remove from DB and set to false in view AND in selectedMovie
        } else {
            // add to DB and set to true in view AND in selectedMovie
            selectedMovie.setInPocket(true);
            checkBox.setChecked(true);
            PocketedMoviesManager.getInstance().addMovieToPocket(selectedMovie.getId(), 1,selectedMovie.getName(), selectedMovie.getRating(), selectedMovie.getPosterImageUrl());

       }
    }
}

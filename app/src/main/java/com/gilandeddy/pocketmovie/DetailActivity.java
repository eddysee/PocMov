package com.gilandeddy.pocketmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Movie selectedMovie = (Movie)intent.getSerializableExtra("movieObject");
        ImageView detailImageView = findViewById(R.id.detailImageView);
        TextView detailTitleView = findViewById(R.id.detailTitleView);
        TextView summaryView = findViewById(R.id.summaryView);
        detailTitleView.setText(selectedMovie.getName());
        summaryView.setText(selectedMovie.getSummary());
        Picasso.get().load(TMDBUrl.imageUrlHead + selectedMovie.getDetailImageUrl()).into(detailImageView);

    }
}

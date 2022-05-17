package com.olivares.flixster;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.olivares.flixster.databinding.ActivityMainBinding;
import com.olivares.flixster.models.Movie;

public class DescriptionActivity extends AppCompatActivity {

    // The view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop;
    Button btPlay;
    String id;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        // Resolve view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        btPlay = (Button) findViewById(R.id.btPlay);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);

        // Need to write some stuff here to unwrap all the movie info received from intent
        // The movie to display
        Movie movie;
        // Unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("DescriptionActivity", String.format("Showing details for '%s'",movie.getTitle()));

        // Get movie id for trailer
        id = movie.getId();
        // Set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Glide.with(this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .centerCrop()
                .into(ivBackdrop);

        ivBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DescriptionActivity.this, MovieTrailerActivity.class);
                i.putExtra("ID", id);
                startActivity(i);
            }
        });

        // Vote average is 0..10, convert to 0..5 by dividing by 2

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);


    }
}
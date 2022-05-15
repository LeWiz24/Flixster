package com.olivares.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.olivares.flixster.adapters.MovieAdapter;
import com.olivares.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // Creating string for URL so we can reference it instead of writing it every time
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // Creating tag to our MainActivity, helps to log data
    public static final String TAG = "MainActivity";
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // Create an adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this,movies);


        // Set adapter on the recyclerview

        rvMovies.setAdapter(movieAdapter);



        // Set a layout manager on the recyclerview
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Create instance of our asynchHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        // Making a get request on this url
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception", e);

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");

            }
        });
    }
}
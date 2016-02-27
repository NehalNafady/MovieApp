package com.nehalnafady.MovieApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nehal nafady on 2/2/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        if(savedInstanceState == null)
        {
            Bundle args = new Bundle();
            Intent intent = getIntent();
            args.putString("MovieId", intent.getStringExtra("MovieId"));
            MovieDetailsActivityFragment fragment = new MovieDetailsActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container , fragment )
                    .commit();
        }


    }
}

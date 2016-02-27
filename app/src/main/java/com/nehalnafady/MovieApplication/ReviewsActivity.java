package com.nehalnafady.MovieApplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nehal nafady on 2/25/2016.
 */
public class ReviewsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_reviews);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.review_fragment_placeholder , new ReviewsFragment())
                    .commit();
        }
    }
}

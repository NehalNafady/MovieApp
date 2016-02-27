package com.nehalnafady.MovieApplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nehal nafady on 2/15/2016.
 */
public class SettingsActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.settings_fragment_placeholder, new SettingsActivityFragment())
                    .commit();
        }



    }
}

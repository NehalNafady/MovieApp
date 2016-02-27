package com.nehalnafady.MovieApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    GridView MovieGridView ;
    private ImageAdapter adapter ;
    private int result_pageNumber = 1;
    private List<Movie> moviesList;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =  inflater.inflate(R.layout.fragment_main , container , false);
        moviesList = new ArrayList<>();
        SharedPreferences settingsPreference = getActivity().getSharedPreferences("settings" , Context.MODE_PRIVATE);

        final String sortBy = settingsPreference.getString("sort_by", Settings.sortBypopularity());




        URL url ;
        try {

            if(sortBy.equals("favorite" ))
            {

                SQLiteMoviesAdapter sqLiteMoviesAdapter = new SQLiteMoviesAdapter(getActivity());

                List<Movie> movies = new ArrayList<>();
                movies = sqLiteMoviesAdapter.selectAllMovies();


                for(Movie m : movies)
                {

                     URLBuilder builder = new URLBuilder();
                    url = builder.BuildSimilarMoviesRequest(m.getID(), result_pageNumber++);
                    FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
                    fatchMoviesInfo.execute(url);

                }

            }else {

                    URLBuilder Builder = new URLBuilder();
                    url = Builder.BuildDiscoverUrl(sortBy, result_pageNumber++);
                    FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
                    fatchMoviesInfo.execute(url);
            }


        }catch (IOException e)
        {
            Log.e("url error" , e.toString());
        }



        adapter = new ImageAdapter(getActivity() , moviesList);

         MovieGridView = (GridView) rootView.findViewById(R.id.movies_gridview);
        MovieGridView.setAdapter(adapter);

        MovieGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (MovieGridView.getLastVisiblePosition() >= MovieGridView.getCount() - MovieGridView.getNumColumns()) {
                    URL url;
                    try {

                        if (sortBy.equals("favorite")) {
                            SQLiteMoviesAdapter sqLiteMoviesAdapter = new SQLiteMoviesAdapter(getActivity());

                            List<Movie> movies = new ArrayList<>();
                            movies = sqLiteMoviesAdapter.selectAllMovies();


                            for (Movie m : movies) {

                                URLBuilder builder = new URLBuilder();
                                url = builder.BuildSimilarMoviesRequest(m.getID(), result_pageNumber++);
                                FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
                                fatchMoviesInfo.execute(url);

                            }

                        } else {

                            URLBuilder Builder = new URLBuilder();
                            url = Builder.BuildDiscoverUrl(sortBy, result_pageNumber++);
                            FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
                            fatchMoviesInfo.execute(url);
                        }


                    } catch (IOException e) {
                        Log.e("url error", e.toString());
                    }

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        MovieGridView.setOnItemClickListener(this);



        return  rootView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ((callBack) getActivity()).onItemClick(moviesList.get(position).getID());



    }


    public  interface callBack
    {
        public void onItemClick(String MovieId);

    }


    public  class FatchMoviesInfo extends AsyncTask<URL , Void , Movie[]>
    {
        @Override
        protected Movie[] doInBackground(URL... params) {

            HttpURLConnection uriconnect = null ;
            BufferedReader reader  = null ;


            Movie[] MovieList ;


            try {

                URL url = params[0];
                uriconnect = (HttpURLConnection) url.openConnection();
                Log.v("request url" , url.toString());
                uriconnect.connect();

                InputStream inputStream = uriconnect.getInputStream();
                StringBuffer buffer = new StringBuffer();



                if(inputStream == null)
                    return null ;


                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line ;
                while ( (line = reader.readLine()) != null  )
                {
                    buffer.append(line + "\n") ;
                }

                String JsonString = buffer.toString() ;
                //  Log.e("Json" , JsonString + "no things");
                JsonDataParser parser = new JsonDataParser();
                MovieList = parser.getMoveiesList(JsonString);


                return MovieList ;

            }catch (IOException e)
            {
                Log.e("IO" , e.toString());

            }catch (JSONException e)
            {
                Log.e("JSON" , e.toString());
            }
            finally {
                if (uriconnect != null) {
                    uriconnect.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
                }
            }





            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {


            if (movies != null) {

                for (int i = 0; i < movies.length; i++) {
                    moviesList.add(movies[i]);
                }
                adapter.notifyDataSetChanged();

                if(MainActivity.isTwoPane())
                    ((callBack) getActivity()).onItemClick(moviesList.get(0).getID());
            }
        }
    }


}

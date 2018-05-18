package com.example.android.popular_movies;

import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.android.popular_movies.data.PopularMovie;
import com.example.android.popular_movies.databinding.ActivityMainBinding;
import com.example.android.popular_movies.databinding.ActivityMoviesBinding;
import com.example.android.popular_movies.utilities.MovieAdapter;
import com.example.android.popular_movies.utilities.MovieNetworkUtil;
import com.example.android.popular_movies.utilities.MovieParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements
        LoaderCallbacks<List<PopularMovie>>,
        MovieAdapter.MovieAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private MovieAdapter movieAdapter;
    private SharedPreferences mSharedPrefs;
    private RecyclerView mRecyclerView;
    private LoaderCallbacks<List<PopularMovie>> mCallback;
    private static final int MOVIE_LOADER_ID = 0;

    public SharedPreferences getSharedPrefs (){
        return mSharedPrefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_recyclerview);
        this.reorient();
        movieAdapter = new MovieAdapter(this, this);
        mCallback = MainActivity.this;
        mRecyclerView.setAdapter(movieAdapter);
        setupSharedPreferences();
        //this.load();
    }

    @Override
    protected void onPostResume() {
        this.load();
        super.onPostResume();
    }

    private void reorient(){
        GridLayoutManager mLayoutManager;
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
            mLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    private void setupSharedPreferences() {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void load() {
        getSupportLoaderManager().destroyLoader(MOVIE_LOADER_ID);
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, mCallback);
    }

    @Override
    protected void onStart() {
//        this.reorient();
//        this.load();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onClick(PopularMovie movie) {

    }

    @Override
    public Loader<List<PopularMovie>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<PopularMovie>>(this) {

            Context context = MainActivity.this;
            List<PopularMovie> popularMovies;
            //SharedPreferences preferences;

            @Override
            protected void onStartLoading() {
                if (popularMovies != null) {
                    deliverResult(popularMovies);
                }else {
//                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }


            public void deliverResult(List<PopularMovie> popularMovies) {
                this.popularMovies = popularMovies;
                super.deliverResult(popularMovies);
            }

            @Override
            public List<PopularMovie> loadInBackground() {
                popularMovies = new ArrayList<>();
                SharedPreferences sharedPreferences = getSharedPrefs();
                String setting = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popular_value));
                try {
                    JSONArray array = MovieNetworkUtil.query(setting, context);
                    if (array != null) {
                        for (int i = 0; i < array.length(); i++) {
                            popularMovies.add(new MovieParser((JSONObject) array.get(i)).parseJSON(context));
                        }
                    }
                    return popularMovies;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<PopularMovie>> loader, List<PopularMovie> data) {
        mRecyclerView.setVisibility(View.VISIBLE);

        movieAdapter.setmPopularMovies(data);
        //getSupportLoaderManager().getLoader(MOVIE_LOADER_ID).deliverResult(movieAdapter.popularMovies());
    }

    @Override
    public void onLoaderReset(Loader<List<PopularMovie>> loader) {
    }
}

package com.example.android.popular_movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.popular_movies.databinding.ActivitySingleMovieBinding;
import com.squareup.picasso.Picasso;

// based off the detailactivty.java from sunshine
// where a single day's weather is received and shown

public class SingleMovie extends AppCompatActivity {

    private ActivitySingleMovieBinding mSingleMovie;
    private String mTitle, mPosterPath, mOverview, mReleaseDate, mVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingleMovie = DataBindingUtil.setContentView(this, R.layout.activity_single_movie);
        Intent mStartingIntent = getIntent();
        mTitle = mStartingIntent.getStringExtra(getString(R.string.title));
        mPosterPath = mStartingIntent.getStringExtra(getString(R.string.poster_path));
        mOverview = mStartingIntent.getStringExtra(getString(R.string.overview));
        mReleaseDate = mStartingIntent.getStringExtra(getString(R.string.release_date));
        mVoteAverage = mStartingIntent.getStringExtra(getString(R.string.vote_average));
        this.setLayout();
    }

    void setLayout(){
        Picasso.with(this).load(mPosterPath).into(mSingleMovie.ivSingleMoviePoster);
        mSingleMovie.tvSingleMovieTitle.setText(mTitle);
        mSingleMovie.tvSingleMovieDescription.setText(mOverview);
        mSingleMovie.tvSingleMovieRating.setText(mVoteAverage);
        mSingleMovie.tvSingleMovieReleaseDate.setText(mReleaseDate);
    }

}

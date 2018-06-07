package com.example.android.popular_movies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.popular_movies.databinding.ActivityReviewBinding;

public class ReviewActivity extends AppCompatActivity {

    private ActivityReviewBinding mSingleMovieReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mSingleMovieReview = DataBindingUtil.setContentView(this, R.layout.activity_review);

    }
}

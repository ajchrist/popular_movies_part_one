package com.example.android.popular_movies.utilities;

import android.content.Context;

import com.example.android.popular_movies.R;
import com.example.android.popular_movies.data.PopularMovie;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieParser {
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static JSONObject mMovieJSON;

    public MovieParser(JSONObject movieJSON){
        mMovieJSON = movieJSON;
    }

    public PopularMovie parseJSON(Context context) throws JSONException {
        String id = String.valueOf(mMovieJSON.getInt(context.getString(R.string.id)));
        String title = mMovieJSON.getString(context.getString(R.string.title));
        String imagePath = mMovieJSON.getString(context.getString(R.string.poster_path)).replace("\\", "");
        String description = mMovieJSON.getString(context.getString(R.string.overview));
        String releaseDate = mMovieJSON.getString(context.getString(R.string.release_date));
        double vote_average = mMovieJSON.getDouble(context.getString(R.string.vote_average));
        String imageURL = BASE_IMAGE_URL + imagePath;
        return new PopularMovie(id, title, description, vote_average, releaseDate, imageURL);
    }

}
